package com.ozeryavuzaslan.orderservice.service.impl;

import com.ozeryavuzaslan.orderservice.service.BeginSagaRollbackChain;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;
import org.springframework.web.context.WebApplicationContext;

@Service
@RequiredArgsConstructor
@Scope(value = WebApplicationContext.SCOPE_REQUEST, proxyMode = ScopedProxyMode.TARGET_CLASS)
public class BeginSagaRollbackChainImpl implements BeginSagaRollbackChain {
}
