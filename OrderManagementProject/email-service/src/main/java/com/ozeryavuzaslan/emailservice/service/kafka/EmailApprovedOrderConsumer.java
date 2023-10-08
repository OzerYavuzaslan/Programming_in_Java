package com.ozeryavuzaslan.emailservice.service.kafka;

import com.ozeryavuzaslan.basedomains.dto.emails.EmailDTO;
import com.ozeryavuzaslan.basedomains.dto.emails.enums.EmailType;
import com.ozeryavuzaslan.basedomains.dto.orders.OrderDTO;
import com.ozeryavuzaslan.emailservice.service.EmailManagementService;
import com.ozeryavuzaslan.emailservice.model.Email;
import com.ozeryavuzaslan.emailservice.objectPropertySetter.EmailPropertySetter;
import com.ozeryavuzaslan.emailservice.repository.EmailRepository;
import lombok.RequiredArgsConstructor;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.time.format.DateTimeFormatter;
import java.util.HashMap;

@Service
@RequiredArgsConstructor
public class EmailApprovedOrderConsumer {
    private final EmailDTO emailDTO;
    private final ModelMapper modelMapper;
    private final EmailRepository emailRepository;
    private final EmailPropertySetter emailPropertySetter;
    private final EmailManagementService emailManagementService;

    @Value("${hash.map.email.to.key}")
    private String mailToKey;

    @Value("${hash.map.email.cc.key}")
    private String mailCcKey;

    @Value("${hash.map.order.orderId.key}")
    private String orderID;

    @Value("${hash.map.order.orderDate.key}")
    private String orderDate;

    @Value("${hash.map.email.body.full.name}")
    private String fullName;

    @Value("${hash.map.order.address1.key}")
    private String address1;

    @Value("${hash.map.order.address2.key}")
    private String address2;

    @Value("${hash.map.order.statusType.key}")
    private String orderStatusType;

    @KafkaListener(topics = "${kafka.approved.order.topic}", groupId = "${kafka.email.group}", containerFactory = "emailOrderEventListenerFactory")
    public void consume(ConsumerRecord<String, OrderDTO> orderPayload) {
        System.err.println(orderPayload.value());
        OrderDTO orderDTO = orderPayload.value();

        HashMap<String, String> approvedOrderDTOMap = new HashMap<>();
        approvedOrderDTOMap.put(mailToKey, orderDTO.getEmail());
        approvedOrderDTOMap.put(mailCcKey, null);
        approvedOrderDTOMap.put(orderID, String.valueOf(orderDTO.getId()));
        approvedOrderDTOMap.put(orderDate, orderDTO.getOrderDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
        approvedOrderDTOMap.put(fullName, orderDTO.getName() + " " + orderDTO.getSurname());
        approvedOrderDTOMap.put(address1, orderDTO.getAddress1());

        if (!orderDTO.getAddress2().isBlank() && !orderDTO.getAddress2().isEmpty())
            approvedOrderDTOMap.put(address2, orderDTO.getAddress2());

        approvedOrderDTOMap.put(orderStatusType, orderDTO.getOrderStatusType().toString());

        emailPropertySetter.setSomeProperties(emailDTO, approvedOrderDTOMap, EmailType.ORDER, null);
        emailManagementService.sendEmail(emailDTO);
        emailPropertySetter.setSomeProperties(emailDTO);
        emailRepository.save(modelMapper.map(emailDTO, Email.class));
        System.err.println("Stock Message --> " + orderDTO);
    }
}
