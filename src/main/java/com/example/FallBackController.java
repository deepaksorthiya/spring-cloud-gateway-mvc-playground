package com.example;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class FallBackController {

    @GetMapping("/status/{status}")
    public ResponseEntity<Map<String, Object>> status(@PathVariable int status) {
        return ResponseEntity.status(status).body(Map.of(
                "status", status, "message", HttpStatus.resolve(status).getReasonPhrase()
        ));
    }

    @GetMapping("/fallback")
    public String fallback() {
        return "fallback response data";
    }

}
