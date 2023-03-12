package com.emlakcepte.client;

import org.springframework.boot.Banner;
import org.springframework.http.HttpEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class BannerServiceClient {
    public void create(Banner banner){
        String url = "http://localhost:8081/banners";
        RestTemplate restTemplate = new RestTemplate();
        HttpEntity<Banner> bannerHttpEntity = new HttpEntity<>(banner);
        restTemplate.postForObject(url, bannerHttpEntity, Banner.class);
    }
}
