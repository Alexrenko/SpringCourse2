package com.geekbrains.spring.web;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.kafka.annotation.EnableKafka;

@SpringBootApplication
@EnableKafka
public class CoreServiceApp {

	public static void main(String[] args) {
		SpringApplication.run(CoreServiceApp.class, args);
	}
}
