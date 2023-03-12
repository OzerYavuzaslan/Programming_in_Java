package com.emlakcepte.controller;

import com.emlakcepte.model.Banner;
import com.emlakcepte.service.BannerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class BannerController {
    @Autowired
    private BannerService bannerService;

    @PostMapping(value = "/banners")
    public Banner create(@RequestBody Banner banner){
        bannerService.create(banner);

        return banner;
    }

    @GetMapping(value = "/banners")
    public ResponseEntity<List<Banner>> getAll(){
        return new ResponseEntity<List<Banner>>(bannerService.getAll(), HttpStatus.OK);
    }
}
