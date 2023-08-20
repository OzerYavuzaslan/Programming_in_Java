package com.ozeryavuzaslan.service;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class WikimediaOnlineDataRecordService implements CommandLineRunner {
    private final WikimediaRecentChangeProducerService wikimediaRecentChangeProducerService;

    @Override
    public void run(String... args) throws Exception {
        wikimediaRecentChangeProducerService.sendMessage();
    }
}