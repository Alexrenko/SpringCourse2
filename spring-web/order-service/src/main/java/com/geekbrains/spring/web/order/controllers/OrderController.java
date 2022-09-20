package com.geekbrains.spring.web.order.controllers;

import com.geekbrains.spring.web.lib.dto.OrderDto;
import com.geekbrains.spring.web.order.converters.OrderConverter;
import com.geekbrains.spring.web.order.services.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/order")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @Autowired
    private OrderConverter orderConverter;

    @GetMapping
    public List<OrderDto> getCurrenOrders(@RequestHeader String username){
        System.out.println(username);
        return orderService.findOrderByUsername(username).stream()
                .map(orderConverter::entityToDto).collect(Collectors.toList());
    }

}
