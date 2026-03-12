package com.example;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;

import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class SpringCloudGatewayRoutesTests {

    @Autowired
    public TestRestTemplate testRestTemplate;

    @Test
    public void pathRouteWorks() {
        ResponseEntity<Map> response = testRestTemplate.getForEntity("/get",
                Map.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getHeaders().getContentType()).isEqualTo(MediaType.APPLICATION_JSON);
    }

    @Test
    public void regexPathSingleObjectRouteWorks() {
        ResponseEntity<Map> response = testRestTemplate.getForEntity("/todos/1",
                Map.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getHeaders().getContentType()).isEqualTo(MediaType.APPLICATION_JSON_UTF8);
    }

    @Test
    public void regexPathListOfObjectsRouteWorks() {
        ResponseEntity<List<Map>> response = testRestTemplate.exchange(
                "/todos",
                HttpMethod.GET,
                null, // HttpEntity
                new ParameterizedTypeReference<List<Map>>() {
                }
        );
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getHeaders().getContentType()).isEqualTo(MediaType.APPLICATION_JSON_UTF8);
        assertThat(response.getBody()).size().isGreaterThan(0);
    }

    @Test
    public void hostRouteWorks() {
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(List.of(MediaType.APPLICATION_JSON));
        headers.set("Host", "www.myhost.org");
        HttpEntity<Void> entity = new HttpEntity<>(null, headers);

        ResponseEntity<Map> response = testRestTemplate.postForEntity("/post", entity, Map.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getHeaders().getContentType()).isEqualTo(MediaType.APPLICATION_JSON);
    }

    @Test
    public void rewriteRouteWorks() {
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(List.of(MediaType.APPLICATION_JSON));
        headers.set("Host", "www.rewrite.org");
        HttpEntity<Void> entity = new HttpEntity<>(null, headers);

        ResponseEntity<Map> response = testRestTemplate.exchange("/foo/get", HttpMethod.GET, entity, Map.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getHeaders().getContentType()).isEqualTo(MediaType.APPLICATION_JSON);
    }

    @Test
    public void postMethodRouteWorks() {
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(List.of(MediaType.APPLICATION_JSON));
        Map<String, String> map = Map.of("key1", "value1", "key2", "value2");
        HttpEntity<Map<String, String>> entity = new HttpEntity<>(map, headers);

        ResponseEntity<Map> response = testRestTemplate.postForEntity("/post", entity, Map.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getHeaders().getContentType()).isEqualTo(MediaType.APPLICATION_JSON);
    }

    @Test
    public void circuitBreakerRouteWorks() {
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(List.of(MediaType.APPLICATION_JSON));
        headers.set("Host", "www.circuitbreaker.org");
        HttpEntity<Void> entity = new HttpEntity<>(null, headers);

        ResponseEntity<Map> response = testRestTemplate.exchange("/delay/6", HttpMethod.GET, entity, Map.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.GATEWAY_TIMEOUT);
        assertThat(response.getHeaders().getContentType()).isEqualTo(MediaType.APPLICATION_PROBLEM_JSON);
    }

    @Test
    public void circuitBreakerFallbackRouteWorks() {
        ResponseEntity<String> response = testRestTemplate.getForEntity("/delay/6",
                String.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isEqualTo("fallback response data");
    }

}
