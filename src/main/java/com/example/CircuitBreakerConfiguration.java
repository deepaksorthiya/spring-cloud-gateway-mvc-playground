package com.example;

import io.github.resilience4j.circuitbreaker.CircuitBreaker;
import io.github.resilience4j.circuitbreaker.CircuitBreakerConfig;
import org.springframework.cloud.circuitbreaker.resilience4j.Resilience4JCircuitBreakerFactory;
import org.springframework.cloud.client.circuitbreaker.Customizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * only for testing purposes don't use in production
 * default time limiting setting is 1s, in order to test we set 5s
 * so that /delay/6 api fail and go to the fallback endpoint
 * and circuit breaker status to force open
 *
 * @see org.springframework.cloud.gateway.server.mvc.filter.CircuitBreakerFilterFunctions
 */
@Configuration
public class CircuitBreakerConfiguration {

    @Bean
    public Customizer<Resilience4JCircuitBreakerFactory> circuitBreakerCustomizer() {
        return factory -> {
            factory.addCircuitBreakerCustomizer(CircuitBreaker::transitionToForcedOpenState, "forced-open");

            factory.configure(builder -> builder
                    .circuitBreakerConfig(CircuitBreakerConfig.ofDefaults()), "fallback", "without_fallback");
        };
    }
}
