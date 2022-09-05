package com.geekbrains.spring.web.services;

import com.geekbrains.spring.web.entities.Order;
import com.geekbrains.spring.web.entities.OrderItem;
import com.geekbrains.spring.web.repositories.OrderRepository;
import com.geekbrains.spring.web.dto.Cart;
import com.geekbrains.spring.web.dto.OrderDetailsDto;
import com.geekbrains.spring.web.exceptions.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final CartService cartService;
    private final ProductService productService;
    private final OrderRepository orderRepository;


    public Order createOrder(String username, OrderDetailsDto orderDetails, String cartName){
        Cart currentCart = cartService.getCurrentCart(cartName);
        Order order = new Order();
        order.setAddress(orderDetails.getAddress());
        order.setPhone(orderDetails.getPhone());
        order.setUsername(username);
        order.setTotalPrice(currentCart.getTotalPrice());
        List<OrderItem> items = currentCart.getItems().stream()
                .map(o -> {
                    OrderItem orderItem = new OrderItem();
                    orderItem.setOrder(order);
                    orderItem.setQuantity(o.getQuantity());
                    orderItem.setPricePerProduct(o.getPricePerProduct());
                    orderItem.setProduct(productService.findById(o.getProductId()).orElseThrow(() -> new ResourceNotFoundException("Продукт не найден")));
                    return orderItem;
                }).collect(Collectors.toList());
        order.setItems(items);
        orderRepository.save(order);
        currentCart.clear();
        return order;
    }

    public List<Order> findOrderByUsername(String username){
        return orderRepository.findByUsername(username);
    }

}
