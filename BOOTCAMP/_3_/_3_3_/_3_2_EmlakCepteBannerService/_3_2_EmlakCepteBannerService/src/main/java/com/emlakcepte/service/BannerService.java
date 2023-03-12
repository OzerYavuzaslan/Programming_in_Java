package com.emlakcepte.service;

import com.emlakcepte.model.Banner;
import com.emlakcepte.repository.BannerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BannerService {
    @Autowired
    private BannerRepository bannerRepository;

    public void create(Banner banner){
        bannerRepository.save(banner);
    }

    public List<Banner> getAll(){
        return bannerRepository.findAll();
    }
}
