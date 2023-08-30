package com.ozeryavuzaslan.stockservice.util;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@Component
public class CustomLocation {
    public URI getURILocation(String path, String buildStr){
        return ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path(path)
                .buildAndExpand(buildStr)
                .toUri();
    }
}
