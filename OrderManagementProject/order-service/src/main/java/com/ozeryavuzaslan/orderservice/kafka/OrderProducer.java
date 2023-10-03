package com.ozeryavuzaslan.orderservice.kafka;

//@Service
//@RequiredArgsConstructor
public class OrderProducer {
    /*
    private final NewTopic topic;
    private final KafkaTemplate<String, Object> kafkaTemplate;
    private final OrderConverter orderConverter;
    private static final Logger LOGGER = LoggerFactory.getLogger(OrderProducer.class);

    public void sendMessage(OrderDTO orderDTO){
        OrderEventDTO orderEventDTO = orderConverter.convert(orderDTO);

        Message<OrderEventDTO> orderEventDTOMessage = MessageBuilder
                .withPayload(orderEventDTO)
                .setHeader(KafkaHeaders.TOPIC, topic.name())
                .build();
        kafkaTemplate.send(orderEventDTOMessage);

        LOGGER.info(String.format("Order event has been sent --> %s", orderEventDTO));
    }

     */
}
