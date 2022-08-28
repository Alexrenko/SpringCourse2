package com.geekbrains.spring.web.controllers;

import com.geekbrains.spring.web.converters.OrderConverter;
import com.geekbrains.spring.web.dto.OrderDetailsDto;
import com.geekbrains.spring.web.dto.OrderDto;
import com.geekbrains.spring.web.entities.Order;
import com.geekbrains.spring.web.entities.OrderItem;
import com.geekbrains.spring.web.entities.User;
import com.geekbrains.spring.web.exceptions.ResourceNotFoundException;
import com.geekbrains.spring.web.repositories.UserRepository;
import com.geekbrains.spring.web.services.OrderService;
import com.geekbrains.spring.web.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.Map;

@RestController

@RequestMapping("/api/v1/order")
//@RequiredArgsConstructor
public class OrderController {

    @Autowired
    private UserService userService;

    @Autowired
    private OrderService orderService;

    @Autowired
    private OrderConverter orderConverter;

    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    public void createOrder(Principal principal, @RequestBody Map<String,String> map) {
        Order order = orderService.createOrder(
                userService.findByUsername(principal.getName())
                        .orElseThrow(() -> new ResourceNotFoundException("User is not found")),
                new OrderDetailsDto(map.get("address"), map.get("phone")),
                map.get("cartName")
        );
    }

}
