package com.geekbrains.spring.web.lib.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderDetailsDto implements Serializable {
    private String address;
    private String phone;
}