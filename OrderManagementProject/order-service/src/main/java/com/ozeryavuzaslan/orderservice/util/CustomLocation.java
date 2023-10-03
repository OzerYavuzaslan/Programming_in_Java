package com.ozeryavuzaslan.orderservice.util;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@Component
public class CustomLocation {
    public URI getURILocation(String path, String build){
        return ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path(path)
                .buildAndExpand(build)
                .toUri();
    }

    public URI getURILocation(String path, long build){
        return ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path(path)
                .buildAndExpand(build)
                .toUri();
    }
}