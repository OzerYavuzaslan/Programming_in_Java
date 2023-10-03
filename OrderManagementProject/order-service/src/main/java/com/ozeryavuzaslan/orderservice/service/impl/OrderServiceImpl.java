package com.ozeryavuzaslan.orderservice.service.impl;

import com.ozeryavuzaslan.basedomains.dto.orders.OrderDTO;
import com.ozeryavuzaslan.basedomains.dto.payments.PaymentRequestDTOForPaymentService;
import com.ozeryavuzaslan.basedomains.dto.payments.StripePaymentResponseDTO;
import com.ozeryavuzaslan.basedomains.dto.revenues.TaxRateDTO;
import com.ozeryavuzaslan.basedomains.dto.revenues.enums.TaxRateType;
import com.ozeryavuzaslan.basedomains.dto.stocks.ReservedStockDTO;
import com.ozeryavuzaslan.orderservice.client.PaymentServiceClient;
import com.ozeryavuzaslan.orderservice.client.RevenueServiceClient;
import com.ozeryavuzaslan.orderservice.client.StockServiceClient;
import com.ozeryavuzaslan.orderservice.model.Order;
import com.ozeryavuzaslan.orderservice.objectPropertySetter.OrderPropertySetter;
import com.ozeryavuzaslan.orderservice.objectPropertySetter.PaymentPropertySetter;
import com.ozeryavuzaslan.orderservice.objectPropertySetter.StockPropertySetter;
import com.ozeryavuzaslan.orderservice.repository.OrderRepository;
import com.ozeryavuzaslan.orderservice.service.OrderService;
import com.ozeryavuzaslan.orderservice.service.PriceCalculationService;
import feign.FeignException;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {
    private final ModelMapper modelMapper;
    private final OrderRepository orderRepository;
    private final StockServiceClient stockServiceClient;
    private final OrderPropertySetter orderPropertySetter;
    private final StockPropertySetter stockPropertySetter;
    private final RevenueServiceClient revenueServiceClient;
    private final PaymentServiceClient paymentServiceClient;
    private final PaymentPropertySetter paymentPropertySetter;
    private final PriceCalculationService priceCalculationService;
    private final PaymentRequestDTOForPaymentService paymentRequestDTOForPaymentService;

    @Override
  //  @Transactional
    public OrderDTO takeOrder(OrderDTO orderDTO) {
        Order order = orderPropertySetter.setSomeProperties(orderDTO);
        modelMapper.map(orderRepository.save(order), orderDTO);

        List<ReservedStockDTO> reservedStockDTOList = stockPropertySetter.setSomeProperties(orderDTO);
        reservedStockDTOList = redirectReserveStocks(reservedStockDTOList);

        TaxRateDTO taxRateDTO = redirectGetSpecificTaxRate();
        priceCalculationService.calculateOrderPrice(reservedStockDTOList, taxRateDTO, orderDTO);

        paymentPropertySetter.setSomeProperties(orderDTO, paymentRequestDTOForPaymentService);
        redirectMakePayment(orderDTO, paymentRequestDTOForPaymentService);

        //TODO: Orderdate ilk başta insert edildiğinde adddate ve upddatelerin db'de olduğundan emin ol
        //TODO: Payment yapıldıktan sonra OrderPropertySetter içinde Order objesinin paymentStatus'ünü değiştir
        //TODO: fiziken stock'tan düşmek için stock-service'e request at.
        //TODO: 62. satırda, productName oluyor sana name oluyor. ModelMapper'da skip varmış, onu uygula.

        modelMapper.map(orderDTO, order);
        modelMapper.map(orderRepository.save(order), orderDTO);

        return orderDTO;
    }

    //TODO:CircuitBreaker uygula
    private List<ReservedStockDTO> redirectReserveStocks(List<ReservedStockDTO> reservedStockDTOList){
        try {
            return stockServiceClient.reserveStock(reservedStockDTOList);
        } catch (FeignException feignException) {
            int responseStatusCode = feignException.status();
            HttpStatus httpStatus = HttpStatus.valueOf(responseStatusCode);

            //TODO: Exception olduğunda saga pattern cancel işlemini uygula
            //TODO: Servise erişilmiyorsa CircuitBreaker pattern uygula
            return null;
        }
    }

    //TODO:CircuitBreaker uygula
    private TaxRateDTO redirectGetSpecificTaxRate(){
        try {
            LocalDate currentDate = LocalDate.now();
            int taxYear = currentDate.getYear();
            int taxMonth = currentDate.getMonthValue();
            return revenueServiceClient.getSpecificTaxRate(taxYear, taxMonth, TaxRateType.KDV);
        } catch (FeignException feignException) {
            int responseStatusCode = feignException.status();
            HttpStatus httpStatus = HttpStatus.valueOf(responseStatusCode);

            //TODO: Exception olduğunda circuitBraker çalışacak şekilde kontrol yap
            return null;
        }
    }

    private void redirectMakePayment(OrderDTO orderDTO,
                                     PaymentRequestDTOForPaymentService paymentRequestDTOForPaymentService){
        try{
            switch (orderDTO.getPaymentProviderType()){
                case STRIPE -> {
                    StripePaymentResponseDTO stripePaymentResponseDTO = paymentServiceClient
                            .payViaStripe(paymentRequestDTOForPaymentService.getStripePaymentRequestDTO());
                    modelMapper.map(stripePaymentResponseDTO, orderDTO);
                }
                case PAYPAL -> {}
                case CREDIT_CARD -> {}
            }
        } catch (FeignException feignException){
            int responseStatusCode = feignException.status();
            HttpStatus httpStatus = HttpStatus.valueOf(responseStatusCode);

            //TODO: Exception olduğunda saga pattern cancel işlemini uygula
            //TODO: Servise erişilmiyorsa CircuitBreaker pattern uygula
        }
    }
}