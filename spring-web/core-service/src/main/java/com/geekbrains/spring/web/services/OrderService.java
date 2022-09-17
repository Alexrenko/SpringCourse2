package com.geekbrains.spring.web.services;

import com.geekbrains.spring.web.dtoLibrary.OrderDto;
import com.geekbrains.spring.web.entities.Order;
import com.geekbrains.spring.web.entities.OrderItem;
import com.geekbrains.spring.web.exceptions.ResourceNotFoundException;
import com.geekbrains.spring.web.repositories.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.stream.Collectors;

@Service
//@RequiredArgsConstructor
public class OrderService {

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private ProductService productService;
    @Autowired
    private OrderRepository orderRepository;

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

    @Transactional
    @KafkaListener(topics = "${spring.kafka.topic}")
    public void saveOrder(OrderDto orderDto){
        Order order = new Order();
        order.setAddress(orderDto.getAddress());
        order.setPhone(orderDto.getPhone());
        order.setUsername(orderDto.getUsername());
        order.setTotalPrice(orderDto.getTotalPrice());
        List<OrderItem> items = orderDto.getItems().stream()
                .map(o -> {
                    OrderItem orderItem = new OrderItem();
                    orderItem.setOrder(order);
                    orderItem.setQuantity(o.getQuantity());
                    orderItem.setPricePerProduct(o.getPricePerProduct());
                    orderItem.setPrice(o.getPrice());
                    orderItem.setProduct(productService.findById(o.getProductId()).orElseThrow(
                            () -> new ResourceNotFoundException("Product not found"))
                    );
                    return orderItem;
                }).collect(Collectors.toList());
        order.setItems(items);
        orderRepository.save(order);
    }

    public List<Order> findOrderByUsername(String username){
        return orderRepository.findByUsername(username);
    }

}
