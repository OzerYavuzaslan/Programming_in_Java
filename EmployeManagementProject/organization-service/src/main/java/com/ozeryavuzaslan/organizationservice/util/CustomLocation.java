package com.ozeryavuzaslan.organizationservice.util;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@Component
@RequiredArgsConstructor
public class CustomLocation {
    public URI getURILocation(String path, String buildStr){
        return ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path(path)
                .buildAndExpand(buildStr)
                .toUri();
    }
}
