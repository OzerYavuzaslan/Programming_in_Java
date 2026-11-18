package com.omniflow.order.infra.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.support.TransactionTemplate;

import java.time.Clock;

/**
 * Infrastructure configuration providing core beans for transaction templates and time.
 *
 * @author Özer Yavuzaslan
 * @version 1.0.0
 */
@Configuration
public class OrderServiceConfig {
    @Bean
    public Clock clock() {
        return Clock.systemUTC();
    }

    @Bean(name = "writeTransactionTemplate")
    public TransactionTemplate writeTransactionTemplate(PlatformTransactionManager platformTransactionManager) {
        return new TransactionTemplate(platformTransactionManager);
    }

    @Bean(name = "readOnlyTransactionTemplate")
    public TransactionTemplate readOnlyTransactionTemplate(PlatformTransactionManager platformTransactionManager) {
        TransactionTemplate transactionTemplate = new TransactionTemplate(platformTransactionManager);
        transactionTemplate.setReadOnly(true);
        return transactionTemplate;
    }
}