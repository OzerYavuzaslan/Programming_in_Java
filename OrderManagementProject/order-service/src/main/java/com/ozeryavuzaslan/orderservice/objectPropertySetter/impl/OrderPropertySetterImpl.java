package com.ozeryavuzaslan.orderservice.objectPropertySetter.impl;

import com.ozeryavuzaslan.basedomains.dto.orders.OrderDTO;
import com.ozeryavuzaslan.basedomains.dto.orders.enums.OrderStatusType;
import com.ozeryavuzaslan.basedomains.dto.payments.StripePaymentResponseDTO;
import com.ozeryavuzaslan.basedomains.dto.payments.enums.PaymentStatus;
import com.ozeryavuzaslan.basedomains.dto.stocks.ReservedStockDTO;
import com.ozeryavuzaslan.basedomains.dto.stocks.enums.ReserveType;
import com.ozeryavuzaslan.orderservice.model.Order;
import com.ozeryavuzaslan.orderservice.model.OrderStock;
import com.ozeryavuzaslan.orderservice.objectPropertySetter.OrderPropertySetter;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;
import org.springframework.web.context.WebApplicationContext;

import java.util.Comparator;
import java.util.List;

@Component
@RequiredArgsConstructor
@Scope(value = WebApplicationContext.SCOPE_REQUEST, proxyMode = ScopedProxyMode.TARGET_CLASS)
public class OrderPropertySetterImpl implements OrderPropertySetter {
    private final ModelMapper modelMapper;

    @Override
    public Order setSomeProperties(OrderDTO orderDTO) {
        Order order = modelMapper.map(orderDTO, Order.class);
        order.setPaymentStatus(PaymentStatus.PENDING);
        order.setReserveType(ReserveType.NOT_RESERVED);
        order.setOrderStatusType(OrderStatusType.TAKEN);

        if (order.getOrderStockList() != null)
            for (OrderStock orderStock : order.getOrderStockList())
                orderStock.setOrder(order);

        return order;
    }

    @Override
    public void setSomeProperties(OrderDTO orderDTO, double totalPriceWithoutTax, double totalPriceWithDiscountWithoutTax,
                                  double totalPrice, double totalPriceWithDiscount, double taxRate) {
        orderDTO.setTaxRate(taxRate);
        orderDTO.setTotalPrice(totalPrice);
        orderDTO.setTotalPriceWithoutTax(totalPriceWithoutTax);
        orderDTO.setTotalPriceWithDiscount(totalPriceWithDiscount);
        orderDTO.setTotalPriceWithDiscountWithoutTax(totalPriceWithDiscountWithoutTax);
    }

    @Override
    public void setSomeProperties(OrderDTO orderDTO, StripePaymentResponseDTO stripePaymentResponseDTO) {
        orderDTO.setPaymentid(stripePaymentResponseDTO.getPaymentid());
        orderDTO.setOrderStatusType(OrderStatusType.APPROVED);
        orderDTO.setPaymentStatus(PaymentStatus.SUCCESS);
        orderDTO.setTotalPrice(stripePaymentResponseDTO.getTotalPrice());
        orderDTO.setTotalPriceWithoutTax(stripePaymentResponseDTO.getTotalPriceWithoutTax());
        orderDTO.setTotalPriceWithDiscount(stripePaymentResponseDTO.getTotalPriceWithDiscount());
        orderDTO.setTotalPriceWithDiscountWithoutTax(stripePaymentResponseDTO.getTotalPriceWithDiscountWithoutTax());
    }

    @Override
    public void setSomeProperties(List<ReservedStockDTO> reservedStockDTOList, Order order) {
        order.setReserveType(ReserveType.RESERVED);

        reservedStockDTOList.sort(Comparator.comparing((ReservedStockDTO stockQuantityDTO) -> stockQuantityDTO.getProductCode().toString()).reversed());
        order.getOrderStockList().sort(Comparator.comparing((OrderStock orderStock) -> orderStock.getProductCode().toString()).reversed());

        for (int i = 0; i < reservedStockDTOList.size(); i++) {
            order.getOrderStockList().get(i).setDiscountAmount(reservedStockDTOList.get(i).getStock().getDiscountAmount());
            order.getOrderStockList().get(i).setDiscountPercentage(reservedStockDTOList.get(i).getStock().getDiscountPercentage());
            order.getOrderStockList().get(i).setDiscountEndDate(reservedStockDTOList.get(i).getStock().getDiscountEndDate());
            order.getOrderStockList().get(i).setPrice(reservedStockDTOList.get(i).getStock().getPrice());
        }
    }

    @Override
    public void setReserveType(OrderDTO orderDTO) {
        orderDTO.setReserveType(ReserveType.STOCK_DECREASED);
    }

    @Override
    public void setOrderStatusAsPreparing(Order order) {
        order.setOrderStatusType(OrderStatusType.PREPARING);
    }

    @Override
    public void setOrderStatusAsCanceled(Order order) {
        order.setOrderStatusType(OrderStatusType.CANCELED_BY_CUSTOMER);
    }

    @Override
    public void setOrderStatusAsInCargo(Order order) {
        order.setOrderStatusType(OrderStatusType.IN_CARGO);
    }

    @Override
    public void setOrderStatusAsDelivered(Order order) {
        order.setOrderStatusType(OrderStatusType.ORDER_DELIVERED);
    }
}