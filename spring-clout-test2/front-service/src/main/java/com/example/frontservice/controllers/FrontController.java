package com.example.frontservice.controllers;

import com.example.frontservice.dto.ProductDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@RestController
@RequestMapping("/api/v1/products")
public class FrontController {

    @Autowired
    private RestTemplate restTemplate;

    @Bean
    @LoadBalanced
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

    @GetMapping()
    public List<ProductDto> getProducts() {

        return restTemplate.getForObject("http://product-service/api/v1/products", List.class);

    }

    @GetMapping("/{id}")
    public ProductDto getProductById(@PathVariable long id) {

        return restTemplate.getForObject("http://product-service/api/v1/products/" + id, ProductDto.class);

    }

}
