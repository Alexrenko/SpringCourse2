package com.geekbrains.spring.web.front;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.kafka.annotation.EnableKafka;

@SpringBootApplication
@EnableKafka
public class FrontServiceApp {

	public static void main(String[] args) {
		SpringApplication.run(FrontServiceApp.class, args);
	}
}
