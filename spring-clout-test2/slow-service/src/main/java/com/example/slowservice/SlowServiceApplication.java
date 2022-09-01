package com.example.slowservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@EnableEurekaClient
@RestController
public class SlowServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(SlowServiceApplication.class, args);
    }

    @GetMapping("/api/v1/slow")
    public String getData(@RequestParam(defaultValue = "0") Long delay) throws InterruptedException {
        Thread.sleep(delay);
        return "Slow Data";
    }

}
