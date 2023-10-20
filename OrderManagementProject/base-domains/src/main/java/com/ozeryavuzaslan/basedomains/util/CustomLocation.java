package com.ozeryavuzaslan.basedomains.util;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class CustomLocation {
    public static URI getURILocation(String path, String build){
        return ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path(path)
                .buildAndExpand(build)
                .toUri();
    }

    public static URI getURILocation(String path, long build){
        return ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path(path)
                .buildAndExpand(build)
                .toUri();
    }
}