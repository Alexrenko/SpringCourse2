package com.geekbrains.spring.web.front.controllers;

import com.geekbrains.spring.web.front.dto.OrderComponentsDto;
import com.geekbrains.spring.web.front.dto.OrderDetailsDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/v1/order")
public class ProducerController {

    @Qualifier(value = "KafkaTest")
    @Autowired
    private KafkaTemplate<Long, OrderComponentsDto> kafkaTemplate;

    @PostMapping()
    public void sendOrderComponents(@RequestBody Map<String,String> map) {
//        System.out.println("sendOrderComponents(" + map.get("username") + ")");
//        System.out.println("sendOrderComponents(" + map.get("address") + ")");
//        System.out.println("sendOrderComponents(" + map.get("phone") + ")");
        OrderComponentsDto orderComponentsDto = new OrderComponentsDto(
                map.get("username"),
                new OrderDetailsDto(map.get("address"), map.get("phone")),
                map.get("cartName")
        );

        kafkaTemplate.send("Order", 1L, orderComponentsDto);

        //orderService.createOrder(username, orderDetailsDto, cartName);
    }

}
