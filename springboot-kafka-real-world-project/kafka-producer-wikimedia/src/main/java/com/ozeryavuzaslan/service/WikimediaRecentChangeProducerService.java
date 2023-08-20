package com.ozeryavuzaslan.service;

import com.launchdarkly.eventsource.EventSource;
import com.ozeryavuzaslan.util.WikimediaChangesHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.net.URI;
import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
public class WikimediaRecentChangeProducerService {
    private final WikimediaChangesHandler eventHandler;

    @Value("${kafka.wikimedia.topic}")
    private String wikimediaRecentChange;

    @Value("${wikimedia.event.changes.url}")
    private String wikimediaRecentChangeUrl;

    public void sendMessage() throws InterruptedException {
        EventSource.Builder builder = new EventSource.Builder(eventHandler, URI.create(wikimediaRecentChangeUrl));
        EventSource eventSource = builder.build();
        eventSource.start();

        TimeUnit.SECONDS.sleep(30);
    }
}