package com.example;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class FallBackController {

    @GetMapping("/status/{status}")
    public ResponseEntity<Integer> status(@PathVariable int status) {
        return ResponseEntity.status(status).body(status);
    }

    @GetMapping("/fallback")
    public String fallback() {
        return "fallback response data";
    }

}
