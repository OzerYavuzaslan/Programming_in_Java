package com.ozeryavuzaslan.gateway.filters;

import lombok.RequiredArgsConstructor;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import reactor.core.publisher.Mono;

@Configuration
@RequiredArgsConstructor
public class ResponseTraceFilter {
    final FilterUtility filterUtility;

    @Bean
    public GlobalFilter postGlobalFilter() {
        return (exchange, chain) -> {
            return chain.filter(exchange).then(Mono.fromRunnable(() -> {
                HttpHeaders requestHeaders = exchange.getRequest().getHeaders();
                String correlationId = filterUtility.getCorrelationId(requestHeaders);

                if(!(exchange.getResponse().getHeaders().containsKey(FilterUtility.CORRELATION_ID)))
                    exchange.getResponse().getHeaders().add(FilterUtility.CORRELATION_ID, correlationId);
            }));
        };
    }
}