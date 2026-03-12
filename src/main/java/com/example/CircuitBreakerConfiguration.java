package com.example;

import io.github.resilience4j.circuitbreaker.CircuitBreaker;
import io.github.resilience4j.circuitbreaker.CircuitBreakerConfig;
import io.github.resilience4j.timelimiter.TimeLimiterConfig;
import org.springframework.cloud.circuitbreaker.resilience4j.Resilience4JCircuitBreakerFactory;
import org.springframework.cloud.client.circuitbreaker.Customizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.Duration;

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
                    .timeLimiterConfig(TimeLimiterConfig.custom().timeoutDuration(Duration.ofMillis(5000)).build())
                    .circuitBreakerConfig(CircuitBreakerConfig.ofDefaults()), "fallback", "without_fallback");
        };
    }
}
