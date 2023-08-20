package com.ozeryavuzaslan.util;

import com.launchdarkly.eventsource.EventHandler;
import com.launchdarkly.eventsource.MessageEvent;
import com.ozeryavuzaslan.util.enums.CustomLoggerClassType;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class WikimediaChangesHandler implements EventHandler {
    private final KafkaTemplate<String, String> kafkaTemplate;
    private final CustomLogger customLogger;
    @Value("${kafka.wikimedia.topic}")
    private String wikimediaRecentChangeTopic;

    @Override
    public void onMessage(String s, MessageEvent messageEvent) throws Exception {
        kafkaTemplate.send(wikimediaRecentChangeTopic, messageEvent.getData());
        customLogger.getSpecificLogger(CustomLoggerClassType.WIKIMEDIA_CHANGES_HANDLER).info(String.format("event data --> %s", messageEvent.getData()));
    }


    @Override
    public void onOpen() throws Exception {
    }

    @Override
    public void onClosed() throws Exception {
    }

    @Override
    public void onComment(String s) throws Exception {
    }

    @Override
    public void onError(Throwable throwable) {
    }
}
