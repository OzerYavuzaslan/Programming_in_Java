package com.ozeryavuzaslan.emailservice.service.rabbitmq;

import com.ozeryavuzaslan.emailservice.dto.EmailDTO;
import com.ozeryavuzaslan.emailservice.dto.enums.EmailType;
import com.ozeryavuzaslan.basedomains.dto.stocks.StockDTO;
import com.ozeryavuzaslan.emailservice.service.EmailManagementService;
import com.ozeryavuzaslan.emailservice.model.Email;
import com.ozeryavuzaslan.emailservice.objectPropertySetter.EmailPropertySetter;
import com.ozeryavuzaslan.emailservice.repository.EmailRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.HashMap;

@Service
@EnableRabbit
@RequiredArgsConstructor
public class EmailStockListener {
    private final EmailDTO emailDTO;
    private final ModelMapper modelMapper;
    private final EmailRepository emailRepository;
    private final EmailPropertySetter emailPropertySetter;
    private final EmailManagementService emailManagementService;

    @Value("${hash.map.product.code.key}")
    private String productCodeKey;

    @Value("${hash.map.product.name.key}")
    private String productNameKey;

    @Value("${hash.map.email.to.key}")
    private String mailToKey;

    @Value("${hash.map.email.cc.key}")
    private String mailCcKey;

    @RabbitListener(queues = "${rabbit.stock.email.queue.name}")
    public void stockListener(StockDTO stockDTO) {
        HashMap<String, String> stockDTOMap = new HashMap<>();
        stockDTOMap.put(productCodeKey, stockDTO.getProductCode().toString());
        stockDTOMap.put(productNameKey, stockDTO.getProductName());
        stockDTOMap.put(mailToKey, stockDTO.getBrandCompanyEmail());
        stockDTOMap.put(mailCcKey, null);

        emailPropertySetter.setSomeProperties(emailDTO, stockDTOMap, EmailType.STOCK, null);
        emailManagementService.sendEmail(emailDTO);
        emailPropertySetter.setSomeProperties(emailDTO);
        emailRepository.save(modelMapper.map(emailDTO, Email.class));
    }
}