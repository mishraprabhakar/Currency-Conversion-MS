package com.microservices.currencyexchangems.controller;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
public class CircuitBreakerController {

    Logger logger = LoggerFactory.getLogger(CircuitBreakerController.class);

    @GetMapping("/sample-api")
//    @Retry(name = "sample-api", fallbackMethod = "printHelloWorld")
    @CircuitBreaker(name = "default", fallbackMethod = "printHelloWorld")
    public String sampleApi(){
        logger.info("sample API is called");

        ResponseEntity<String> forEntity = new RestTemplate()
                .getForEntity("http://localhost:8080/some-dummy-url", String.class);
        return forEntity.getBody();
    }

    public String printHelloWorld(Exception exception){
        logger.info("fallback method is called");
        return "Hello World fallback page.";
    }
}
