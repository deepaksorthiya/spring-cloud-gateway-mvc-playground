package com.example;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.web.servlet.function.RouterFunction;
import org.springframework.web.servlet.function.ServerResponse;

import java.net.URI;

import static org.springframework.cloud.gateway.server.mvc.filter.BeforeFilterFunctions.uri;
import static org.springframework.cloud.gateway.server.mvc.filter.CircuitBreakerFilterFunctions.circuitBreaker;
import static org.springframework.cloud.gateway.server.mvc.filter.FilterFunctions.rewritePath;
import static org.springframework.cloud.gateway.server.mvc.handler.GatewayRouterFunctions.route;
import static org.springframework.cloud.gateway.server.mvc.handler.HandlerFunctions.http;
import static org.springframework.cloud.gateway.server.mvc.predicate.GatewayRequestPredicates.*;

@Configuration
public class GatewayRoutesConfig {

    public static final String HTTPS_HTTPBIN_ORG = "https://httpbin.org";

    @Bean
    public RouterFunction<ServerResponse> gatewayRoutes() {

        return route("path_route")
                .route(path("/get"), http())
                .before(uri(HTTPS_HTTPBIN_ORG))
                .build()

                .and(route("regex_path_route")
                        .route(path("/todos/**"), http())
                        .before(uri("https://jsonplaceholder.typicode.com"))
                        .build())

                .and(route("host_route")
                        .route(host("*.myhost.org"), http())
                        .before(uri(HTTPS_HTTPBIN_ORG))
                        .build())

                .and(route("rewrite_route")
                        .route(host("*.rewrite.org"), http())
                        .before(uri(HTTPS_HTTPBIN_ORG))
                        .filter(rewritePath("/foo/(?<segment>.*)",
                                "/${segment}"))
                        .build())

                .and(route("method_route")
                        .route(method(HttpMethod.POST), http())
                        .before(uri(HTTPS_HTTPBIN_ORG))
                        .build())

                .and(route("circuitbreaker_route")
                        .route(host("*.circuitbreaker.org"), http())
                        .before(uri(HTTPS_HTTPBIN_ORG))
                        .filter(circuitBreaker("without_fallback"))
                        .build())

                .and(route("circuitbreaker_fallback_route")
                        .route(path("/delay/{seconds}"), http())
                        .before(uri(HTTPS_HTTPBIN_ORG))
                        .filter(circuitBreaker(config -> config.setId("fallback")
                                .setFallbackUri(URI.create("forward:/fallback"))
                                .setStatusCodes("500", "504")))
                        .build());
    }
}