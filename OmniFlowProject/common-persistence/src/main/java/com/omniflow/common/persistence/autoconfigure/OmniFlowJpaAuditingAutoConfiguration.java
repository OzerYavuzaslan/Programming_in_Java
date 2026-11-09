package com.omniflow.common.persistence.autoconfigure;

import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.data.auditing.DateTimeProvider;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import java.time.Clock;
import java.time.Instant;
import java.util.Optional;

@AutoConfiguration
@ConditionalOnClass(name = "jakarta.persistence.EntityManager")
@EnableJpaAuditing(auditorAwareRef = "omniFlowAuditorAware", dateTimeProviderRef = "omniFlowAuditingDateTimeProvider")
public class OmniFlowJpaAuditingAutoConfiguration {
    public static final String SYSTEM_AUDITOR = "system";

    @Bean
    @ConditionalOnMissingBean(Clock.class)
    public Clock omniFlowClock() {
        return Clock.systemUTC();
    }

    @Bean
    @ConditionalOnMissingBean(name = "omniFlowAuditorAware")
    public AuditorAware<String> omniFlowAuditorAware() {
        return () -> Optional.of(SYSTEM_AUDITOR);
    }

    @Bean
    public DateTimeProvider omniFlowAuditingDateTimeProvider(Clock clock) {
        return () -> Optional.of(Instant.now(clock));
    }
}