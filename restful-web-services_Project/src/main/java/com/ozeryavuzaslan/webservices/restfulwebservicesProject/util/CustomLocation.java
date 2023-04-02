package com.ozeryavuzaslan.webservices.restfulwebservicesProject.util;

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

    public URI getURILocation(String originPath, String path, String buildStr){
        return ServletUriComponentsBuilder
                .fromCurrentContextPath()
                    .path(originPath + path)
                        .buildAndExpand(buildStr)
                            .toUri();
    }
}