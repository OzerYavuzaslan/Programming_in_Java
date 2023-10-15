package com.ozeryavuzaslan.orderservice.configuration;

import com.ozeryavuzaslan.basedomains.dto.orders.OrderDTO;
import com.ozeryavuzaslan.basedomains.dto.payments.StripePaymentRequestDTO;
import com.ozeryavuzaslan.basedomains.dto.payments.StripeRefundRequestDTO;
import com.ozeryavuzaslan.basedomains.dto.payments.abstracts.PaymentResponseDTO;
import com.ozeryavuzaslan.orderservice.dto.FailedOrderStockDTO;
import com.ozeryavuzaslan.orderservice.model.FailedOrderStock;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeMap;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ModelMapperConfig {
    @Bean
    public ModelMapper getModelMapperBean(){
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);

        TypeMap<OrderDTO, PaymentResponseDTO> typeMap1 = modelMapper.createTypeMap(OrderDTO.class, PaymentResponseDTO.class);
        typeMap1.addMapping(OrderDTO::getId, PaymentResponseDTO::setOrderid);

        TypeMap<OrderDTO, StripePaymentRequestDTO> typeMap2 = modelMapper.createTypeMap(OrderDTO.class, StripePaymentRequestDTO.class);
        typeMap2.addMapping(OrderDTO::getId, StripePaymentRequestDTO::setOrderid);

        TypeMap<FailedOrderStock, FailedOrderStockDTO> typeMap3 = modelMapper.createTypeMap(FailedOrderStock.class, FailedOrderStockDTO.class);
        typeMap3.addMapping(failedOrderStock -> failedOrderStock.getFailedOrder().getId(), FailedOrderStockDTO::setFailedOrderID);

        TypeMap<OrderDTO, StripeRefundRequestDTO> typeMap4 = modelMapper.createTypeMap(OrderDTO.class, StripeRefundRequestDTO.class);
        typeMap4.addMapping(OrderDTO::getTotalPriceWithDiscount, StripeRefundRequestDTO::setRefundRequestAmount);
        typeMap4.addMapping(OrderDTO::getId, StripeRefundRequestDTO::setOrderid);

        return modelMapper;
    }
}
