package com.ozeryavuzaslan.basedomains.util;

import feign.RequestTemplate;
import feign.codec.EncodeException;
import feign.codec.Encoder;
import org.springframework.data.domain.Pageable;

import java.lang.reflect.Type;

public class PageableEncoder implements Encoder {
    private final Encoder delegate;

    public PageableEncoder(Encoder delegate) {
        this.delegate = delegate;
    }

    @Override
    public void encode(Object object, Type bodyType, RequestTemplate template) throws EncodeException {
        if (object instanceof Pageable pageable) {
            template.query("page", String.valueOf(pageable.getPageNumber()));
            template.query("size", String.valueOf(pageable.getPageSize()));
            pageable.getSort().forEach(order -> template.query("sort", order.getProperty() + "," + order.getDirection()));
        }

        delegate.encode(object, bodyType, template);
    }
}