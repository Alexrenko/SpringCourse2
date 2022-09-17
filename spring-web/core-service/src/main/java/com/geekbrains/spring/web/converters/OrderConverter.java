package com.geekbrains.spring.web.converters;

import com.geekbrains.spring.web.dtoLibrary.OrderDto;
import com.geekbrains.spring.web.entities.Order;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.jaxb.SpringDataJaxb;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class OrderConverter{
    private OrderItemConverter converter;

    public Order dtoToEntity(OrderDto orderDto){
        throw new UnsupportedOperationException();
    }

    public OrderDto entityToDto(Order order){
        OrderDto out = new OrderDto();
        out.setId(order.getId());
        out.setUsername(order.getUsername());
        out.setItems(order.getItems().stream().map(converter::entityToDto).collect(Collectors.toList()));
        out.setTotalPrice(order.getTotalPrice());
        out.setAddress(order.getAddress());
        out.setPhone(order.getPhone());
        return out;
    }
}
