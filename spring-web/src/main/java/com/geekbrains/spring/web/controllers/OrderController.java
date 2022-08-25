package com.geekbrains.spring.web.controllers;

import com.geekbrains.spring.web.converters.OrderConverter;
import com.geekbrains.spring.web.dto.OrderDetailsDto;
import com.geekbrains.spring.web.entities.Order;
import com.geekbrains.spring.web.entities.OrderItem;
import com.geekbrains.spring.web.entities.User;
import com.geekbrains.spring.web.repositories.UserRepository;
import com.geekbrains.spring.web.services.OrderService;
import com.geekbrains.spring.web.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.Map;

@RestController

@RequestMapping("/api/v1/order")
@RequiredArgsConstructor
public class OrderController {
    private UserService userService;
    private OrderService orderService;

    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    public void print(Principal principal, @RequestBody Map<String,String> map) {

        Order order = orderService.createOrder(
                userService.findByUsername(principal.getName()).get(),
                new OrderDetailsDto(map.get("address"), map.get("phone")),
                map.get("cartName")
        );

        System.out.println("id: " + order.getId());
        System.out.println("address: " + order.getAddress());
        System.out.println("phone: " + order.getPhone());
        System.out.println("username: " + order.getUser().getUsername());
        System.out.println("totalPrice: " + order.getTotalPrice());
        for(OrderItem item : order.getItems()) {
            System.out.println(item.getProduct().getTitle() + "\t" + item.getQuantity() + "шт.");
        }
    }

}
