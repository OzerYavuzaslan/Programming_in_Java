package com.ozeryavuzaslan.orderservice.service.impl;

import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ozeryavuzaslan.basedomains.dto.ErrorDetailsDTO;
import com.ozeryavuzaslan.basedomains.dto.orders.OrderDTO;
import com.ozeryavuzaslan.basedomains.dto.orders.enums.OrderStatusType;
import com.ozeryavuzaslan.basedomains.dto.payments.StripePaymentResponseDTO;
import com.ozeryavuzaslan.basedomains.dto.revenues.TaxRateDTO;
import com.ozeryavuzaslan.basedomains.dto.stocks.ReservedStockDTO;
import com.ozeryavuzaslan.basedomains.util.HandledHTTPExceptions;
import com.ozeryavuzaslan.basedomains.util.TypeFactoryHelper;
import com.ozeryavuzaslan.orderservice.dto.PaymentRequestDTOForPaymentService;
import com.ozeryavuzaslan.orderservice.exception.*;
import com.ozeryavuzaslan.orderservice.kafka.OrderProducer;
import com.ozeryavuzaslan.orderservice.model.Order;
import com.ozeryavuzaslan.orderservice.model.OrderStock;
import com.ozeryavuzaslan.orderservice.objectPropertySetter.OrderPropertySetter;
import com.ozeryavuzaslan.orderservice.objectPropertySetter.PaymentPropertySetter;
import com.ozeryavuzaslan.orderservice.objectPropertySetter.StockPropertySetter;
import com.ozeryavuzaslan.orderservice.repository.OrderRepository;
import com.ozeryavuzaslan.orderservice.service.OrderService;
import com.ozeryavuzaslan.orderservice.service.PriceCalculationService;
import com.ozeryavuzaslan.orderservice.service.RedirectAndFallbackHandler;
import com.ozeryavuzaslan.orderservice.service.SagaRollbackChainService;
import feign.Response;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import java.util.Comparator;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {
    private final ModelMapper modelMapper;
    private final ObjectMapper objectMapper;
    private final OrderProducer orderProducer;
    private final OrderRepository orderRepository;
    private final OrderPropertySetter orderPropertySetter;
    private final StockPropertySetter stockPropertySetter;
    private final PaymentPropertySetter paymentPropertySetter;
    private final PlatformTransactionManager transactionManager;
    private final PriceCalculationService priceCalculationService;
    private final SagaRollbackChainService sagaRollbackChainService;
    private final RedirectAndFallbackHandler redirectAndFallbackHandler;
    private final PaymentRequestDTOForPaymentService paymentRequestDTOForPaymentService;

    @Value("${order.not.found.exception}")
    private String orderNotFoundMsg;

    @Value("${order.not.approved.exception}")
    private String orderNotApprovedMsg;

    @Value("${order.not.canceled.exception}")
    private String orderNotCanceledMsg;

    @Value("${order.already.canceled.exception}")
    private String orderAlreadyCanceledMsg;

    @Value("${order.already.delivered.exception}")
    private String orderAlreadyDeliveredMsg;

    @Value("${order.not.delivered.exception}")
    private String orderNotDeliveredMsg;

    @Value("${order.not.given.to.cargo.company.exception}")
    private String orderNotGivenToCargoCompanyMsg;

    /**
     * Servisler arası haberleşme Email servis hariç feignClient ile senkrondur.
     * Her şey yolunda giderse, önce rezerv yapıyor (stock servisin reserv ile ilgili controllerına istek atıyor), sonra revenue servisten taxRate getiriyor
     * TaxRate'i aldıktan sonra ödenecek tutarı hesaplıyor. İndirim varsa ona göre hesaplıyor.
     * Ödeme yapıldıktan sonra Order'ın payment durumunu güncelliyor.
     * Ödemeyi yaptıktan sonra bu sefer fiziki olarak stocktan miktar düşmek için tekrar stock servise gidiyor.
     * Bundan sonra siparişi kayıt edip asenkron bir şekilde email servisin dinlediği kuyruğa mesaj bırakıyor.
     * Bu işlemler esnasında herhangi bir serviste exception alır ya da servislerden en az biri erişilemez olursa,
     * exception aldığı noktadan geriye doğru rollback işlemleri için bir zincirleme hareket başlatıyor.
     * Rollback esnasında da başka bir exception gelirse, bu sefer de DB'ye kayıt ediyor. Hangi phasedeyken rollback işlemleri başarısız olduysa onun kaydını tutuyor.
     *
     * @param orderDTO
     * @return orderDTO
     * @throws Exception
     */
    //TODO: failed_orders tablosuna eklenenleri rollback yapacak bir mekanizma yaz.

    //@Transactional anotasyonu varken exceptionları catchleyemiyordum. Dolayısla manuel transaction açıp kapıyorum :/
    //Daha düzgün bir çözüm yönetimi olan bana haber versin lütfen :)
    @Override
    public OrderDTO takeOrder(OrderDTO orderDTO) throws Exception {
        Order order;
        List<ReservedStockDTO> reservedStockDTOList;
        TransactionStatus transactionStatus = transactionManager.getTransaction(new DefaultTransactionDefinition());

        try {
            order = orderPropertySetter.setSomeProperties(orderDTO);
            modelMapper.map(orderRepository.save(order), orderDTO);
            reservedStockDTOList = stockPropertySetter.setSomeProperties(orderDTO);
            JavaType reservedStockDTOType = TypeFactoryHelper.constructCollectionType(ReservedStockDTO.class, objectMapper);
            int statusCode;

            try (Response reserveStockResponse = redirectAndFallbackHandler.redirectReserveStocks(reservedStockDTOList)) {
                statusCode = reserveStockResponse.status();

                if (HandledHTTPExceptions.checkHandledExceptionStatusCode(statusCode))
                    throw new CustomOrderServiceException(objectMapper.readValue(reserveStockResponse.body().asInputStream(), ErrorDetailsDTO.class).getMessage() + "_" + statusCode);

                reservedStockDTOList = objectMapper.readValue(reserveStockResponse.body().asInputStream(), reservedStockDTOType);
            }

            orderPropertySetter.setSomeProperties(reservedStockDTOList, order);
            order.getOrderStockList().sort(Comparator.comparing(OrderStock::getId));
            orderRepository.save(order);
            TaxRateDTO taxRateDTO;

            try (Response taxRateResponse = redirectAndFallbackHandler.redirectGetSpecificTaxRate(reservedStockDTOList)) {
                sagaRollbackChainService.checkResponseAndBeginRollbackPhase1IfFailed(taxRateResponse.status(), orderDTO, reservedStockDTOList, taxRateResponse);
                taxRateDTO = objectMapper.readValue(taxRateResponse.body().asInputStream(), TaxRateDTO.class);
            }

            priceCalculationService.calculateOrderPrice(reservedStockDTOList, taxRateDTO, orderDTO);
            paymentPropertySetter.setSomeProperties(orderDTO, paymentRequestDTOForPaymentService);

            try (Response paymentResponse = redirectAndFallbackHandler.redirectMakePayment(orderDTO, paymentRequestDTOForPaymentService, reservedStockDTOList)) {
                switch (orderDTO.getPaymentProviderType()) {
                    case STRIPE -> {
                        sagaRollbackChainService.checkResponseAndBeginRollbackPhase1IfFailed(paymentResponse.status(), orderDTO, reservedStockDTOList, paymentResponse);
                        StripePaymentResponseDTO stripePaymentResponseDTO = objectMapper.readValue(paymentResponse.body().asInputStream(), StripePaymentResponseDTO.class);
                        orderPropertySetter.setSomeProperties(orderDTO, stripePaymentResponseDTO);
                    }
                    case PAYPAL, CREDIT_CARD -> {
                    }
                }
            }

            try (Response reserveStockResponse = redirectAndFallbackHandler.redirectDecreaseStocks(reservedStockDTOList, orderDTO)) {
                sagaRollbackChainService.checkResponseAndBeginRollbackPhase2IfFailed(reserveStockResponse.status(), orderDTO, reservedStockDTOList, reserveStockResponse);
                reservedStockDTOList = objectMapper.readValue(reserveStockResponse.body().asInputStream(), reservedStockDTOType);
            }
        } catch (Exception exception) {
            if (!transactionStatus.isCompleted())
                transactionManager.rollback(transactionStatus);

            throw exception;
        }

        try {
            orderPropertySetter.setReserveType(orderDTO);
            modelMapper.map(orderDTO, order);
            modelMapper.map(orderRepository.save(order), orderDTO);
            transactionManager.commit(transactionStatus);
            orderProducer.sendMessage(orderDTO);
        } catch (Exception exception) {
            sagaRollbackChainService.rollbackChainPhase3(orderDTO, reservedStockDTOList);

            if (!transactionStatus.isCompleted())
                transactionManager.rollback(transactionStatus);

            throw exception;
        }

        return orderDTO;
    }

    @Override
    public OrderDTO getByOrderID(long orderID) {
        return modelMapper.map(getSpecificOrder(orderID), OrderDTO.class);
    }

    @Override
    @Transactional
    public OrderDTO prepareByOrderID(long orderID) {
        Order order = getSpecificOrder(orderID);

        if (order.getOrderStatusType().equals(OrderStatusType.APPROVED))
            orderPropertySetter.setOrderStatusAsPreparing(order);
        else
            throw new OrderNotApprovedException(orderNotApprovedMsg + " " + order.getOrderStatusType());

        orderRepository.save(order);
        OrderDTO orderDTO = modelMapper.map(order, OrderDTO.class);
        orderProducer.sendMessage(orderDTO);
        return orderDTO;
    }

    @Override
    public OrderDTO cancelByOrderID(long orderID) throws Exception {
        Order order = getSpecificOrder(orderID);

        if (order.getOrderStatusType().equals(OrderStatusType.ORDER_DELIVERED) || order.getOrderStatusType().equals(OrderStatusType.IN_CARGO))
            throw new OrderNotCanceledException(orderNotCanceledMsg + " " + order.getOrderStatusType());
        else if (order.getOrderStatusType().equals(OrderStatusType.CANCELED_BY_CUSTOMER))
            throw new OrderNotCanceledException(orderAlreadyCanceledMsg);

        TransactionStatus transactionStatus = transactionManager.getTransaction(new DefaultTransactionDefinition());
        OrderDTO orderDTO = modelMapper.map(order, OrderDTO.class);

        try {
            orderPropertySetter.setOrderStatusAsCanceled(order);

            try (Response reservedStockListResponse = redirectAndFallbackHandler.redirectRollbackStocksAndReservedStocksByOrderID(orderDTO)) {
                sagaRollbackChainService.beginOrderCancellation(orderDTO, reservedStockListResponse);
            }

            orderRepository.save(order);
            transactionManager.commit(transactionStatus);
        } catch (Exception exception) {
            if (!transactionStatus.isCompleted())
                transactionManager.rollback(transactionStatus);

            throw exception;
        }

        orderProducer.sendMessage(orderDTO);
        return orderDTO;
    }

    @Override
    @Transactional
    public OrderDTO deliverByOrderID(long orderID) {
        Order order = getSpecificOrder(orderID);

        if (order.getOrderStatusType().equals(OrderStatusType.ORDER_DELIVERED)
                || order.getOrderStatusType().equals(OrderStatusType.APPROVED)
                || order.getOrderStatusType().equals(OrderStatusType.TAKEN))
            throw new OrderNotDeliveredException(orderAlreadyDeliveredMsg);
        else if (order.getOrderStatusType().equals(OrderStatusType.CANCELED_BY_CUSTOMER))
            throw new OrderNotDeliveredException(orderNotDeliveredMsg);

        orderPropertySetter.setOrderStatusAsDelivered(order);
        orderRepository.save(order);
        OrderDTO orderDTO = modelMapper.map(order, OrderDTO.class);
        orderProducer.sendMessage(orderDTO);

        return orderDTO;
    }

    @Override
    @Transactional
    public OrderDTO giveToCargoCompanyByOrderID (long orderID) {
        Order order = getSpecificOrder(orderID);

        if (order.getOrderStatusType().equals(OrderStatusType.APPROVED)
                || order.getOrderStatusType().equals(OrderStatusType.CANCELED_BY_CUSTOMER)
                || order.getOrderStatusType().equals(OrderStatusType.TAKEN)
                || order.getOrderStatusType().equals(OrderStatusType.ORDER_DELIVERED))
            throw new OrderNotGivenToCargoCompanyException(orderNotGivenToCargoCompanyMsg + " " + order.getOrderStatusType());

        orderPropertySetter.setOrderStatusAsInCargo(order);
        orderRepository.save(order);
        OrderDTO orderDTO = modelMapper.map(order, OrderDTO.class);
        orderProducer.sendMessage(orderDTO);

        return orderDTO;
    }

    private Order getSpecificOrder(long orderID) {
        return orderRepository
                .findById(orderID)
                .orElseThrow(() -> new OrderNotFoundException(orderNotFoundMsg));
    }
}