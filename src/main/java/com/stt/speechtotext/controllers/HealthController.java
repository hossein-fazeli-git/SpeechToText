package com.stt.speechtotext.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HealthController {

    @RestController
    public class SimpleHealthCheckController {

        @GetMapping("/health")
        public ResponseEntity<String> ping() {
            return ResponseEntity.ok("OK");
        }
    }

}
