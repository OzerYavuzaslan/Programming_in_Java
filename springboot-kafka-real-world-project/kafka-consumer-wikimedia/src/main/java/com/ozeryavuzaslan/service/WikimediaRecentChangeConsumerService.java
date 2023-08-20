package com.ozeryavuzaslan.service;

import com.ozeryavuzaslan.model.WikimediaData;
import com.ozeryavuzaslan.repository.WikimediaRecentChangeRepository;
import com.ozeryavuzaslan.util.CustomLogger;
import com.ozeryavuzaslan.util.enums.CustomLoggerClassType;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class WikimediaRecentChangeConsumerService {
    private final CustomLogger customLogger;
    private final WikimediaRecentChangeRepository wikimediaRecentChangeRepository;

    @KafkaListener(topics = "${kafka.wikimedia.topic}", groupId = "${kafka.wikimedia.group}", containerFactory = "wikimediaListenerFactory")
    public void consume(String eventMessage){
        customLogger.getSpecificLogger(CustomLoggerClassType.CONSUMER_SERVICE).info(String.format("Event message received --> %s", eventMessage));

        WikimediaData wikimediaData = new WikimediaData();
        wikimediaData.setWikiEventData(eventMessage);
        wikimediaRecentChangeRepository.save(wikimediaData);

        customLogger.getSpecificLogger(CustomLoggerClassType.CONSUMER_SERVICE).info("Event message has been stored in the database");
    }
}
