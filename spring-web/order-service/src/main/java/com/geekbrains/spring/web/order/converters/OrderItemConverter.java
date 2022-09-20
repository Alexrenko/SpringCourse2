package com.geekbrains.spring.web.order.converters;

import com.geekbrains.spring.web.lib.dto.OrderItemDto;

import com.geekbrains.spring.web.order.entities.OrderItem;
import org.springframework.stereotype.Component;

@Component
public class OrderItemConverter {

    public OrderItem dtoToEntity(OrderItemDto orderItemDto){
        throw new UnsupportedOperationException();
    }

    public OrderItemDto entityToDto(OrderItem orderItem){
        return new OrderItemDto(orderItem.getId(), orderItem.getTitle(),
                orderItem.getQuantity(), orderItem.getPricePerProduct(), orderItem.getPrice());
    }

}
