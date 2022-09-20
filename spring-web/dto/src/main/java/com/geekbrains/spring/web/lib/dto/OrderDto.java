package com.geekbrains.spring.web.lib.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderDto {
    private long id;
    private String username;
    private List<OrderItemDto> items;
    private int totalPrice;
    private String address;
    private String phone;
}
