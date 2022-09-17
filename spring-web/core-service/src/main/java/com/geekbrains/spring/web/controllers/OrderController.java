package com.geekbrains.spring.web.controllers;

import com.geekbrains.spring.web.converters.OrderConverter;
import com.geekbrains.spring.web.dtoLibrary.OrderDto;
import com.geekbrains.spring.web.services.OrderService;
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

//    @PostMapping()
//    @ResponseStatus(HttpStatus.CREATED)
//    public void createOrder(@RequestHeader String username,
//                            @RequestBody Map<String,String> map) {
//        String address = map.get("address");
//        String phone = map.get("phone");
//        String cartName = map.get("cartName");
//        OrderDetailsDto orderDetailsDto = new OrderDetailsDto(address, phone);
//        orderService.createOrder(username, orderDetailsDto, cartName);
//    }

    @GetMapping
    public List<OrderDto> getCurrenOrders(@RequestHeader String username){
        return orderService.findOrderByUsername(username).stream()
                .map(orderConverter::entityToDto).collect(Collectors.toList());
    }

}
