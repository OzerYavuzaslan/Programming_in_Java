package com.ozeryavuzaslan.emailservice.service.rabbitmq;

import com.ozeryavuzaslan.basedomains.dto.emails.EmailDTO;
import com.ozeryavuzaslan.basedomains.dto.enums.EmailType;
import com.ozeryavuzaslan.basedomains.dto.stocks.StockDTO;
import com.ozeryavuzaslan.emailservice.model.Email;
import com.ozeryavuzaslan.emailservice.objectPropertySetter.EmailPropertySetter;
import com.ozeryavuzaslan.emailservice.repository.EmailRepository;
import com.ozeryavuzaslan.emailservice.service.EmailServiceUtilImpl;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Service
@EnableRabbit
@RequiredArgsConstructor
public class EmailListener {
    private final EmailDTO emailDTO;
    private final ModelMapper modelMapper;
    private final EmailRepository emailRepository;
    private final EmailPropertySetter emailPropertySetter;
    private final EmailServiceUtilImpl emailServiceUtilImpl;

    @RabbitListener(queues = "${rabbit.stock.email.queue.name}")
    public void paymentListener(StockDTO stockDTO) {
        emailPropertySetter.setSomeProperties(emailDTO, stockDTO.getBrandCompanyEmail(), null, stockDTO.getProductCode() + " " + stockDTO.getBrandName(), EmailType.STOCK);
        emailServiceUtilImpl.sendEmail(emailDTO);
        emailPropertySetter.setSomeProperties(emailDTO);
        emailRepository.save(modelMapper.map(emailDTO, Email.class));
        System.err.println("Stock Message --> " + stockDTO);
    }
}
