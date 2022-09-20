package com.geekbrains.spring.web.cart.service;

import com.geekbrains.spring.web.cart.api.ProductApi;
import com.geekbrains.spring.web.cart.dto.Cart;
import com.geekbrains.spring.web.cart.exceptions.ResourceNotFoundException;
import com.geekbrains.spring.web.lib.dto.OrderDetailsDto;
import com.geekbrains.spring.web.lib.dto.OrderDto;
import com.geekbrains.spring.web.lib.dto.ProductDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Optional;

@Service
//@RequiredArgsConstructor
public class CartService {

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private ProductApi productApi;

    @Autowired
    private CacheManager cacheManager;

    @Autowired
    private KafkaTemplate<Long, OrderDto> kafkaTemplate;

    @Value("${spring.kafka.topic}")
    private String topic;

    private Cart cart;
    @Value("${other.cache.carts}")
    private String CARTS_CACHE;

    @Value("${other.path.product-service}")
    private String productServicePath;

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

    @Cacheable(value = "${other.cache.carts}", key = "#cartName")
    public Cart getCurrentCart(String cartName){
        cart = cacheManager.getCache(CARTS_CACHE).get(cartName, Cart.class);
        if(!Optional.ofNullable(cart).isPresent()){
            cart = new Cart(cartName);
            cacheManager.getCache(CARTS_CACHE).put(cartName, cart);
        }
        return cart;
    }

    @CachePut(value = "CartsCache", key = "#cartName")
    public Cart addProductByIdToCart(Long id, String cartName, int quantity){
        Cart cart = getCurrentCart(cartName);
        ProductDto productDto = productApi.getProductById(id);
        cart.addProduct(productDto, quantity);
        return cart;
    }

    @CachePut(value = "CartsCache", key = "#cartName")
    public Cart removeProduct(Long id, String cartName){
        Cart cart = getCurrentCart(cartName);
        if (isItemInTheCart(id))
            cart.removeProduct(id);
        else
            throw new ResourceNotFoundException("В корзине нет такого товара");
        return cart;
    }

    @CachePut(value = "CartsCache", key = "#cartName")
    public Cart decreaseProduct(Long id, String cartName, int delta){
        Cart cart = getCurrentCart(cartName);
        if (delta > 0)
            delta *= -1;
        if (isItemInTheCart(id))
            cart.decreaseProduct(id, delta);
        else
            throw new ResourceNotFoundException("В корзине нет такого товара");
        return cart;
    }

    @CachePut(value = "CartsCache", key = "#cartName")
    public Cart clear(String cartName){
        Cart cart = getCurrentCart(cartName);
        cart.clear();
        return cart;
    }

    private boolean isItemInTheCart(Long id){
        ProductDto productDto = restTemplate
                .getForObject(productServicePath + "/api/v1/products/" + id, ProductDto.class);
        if (cart.findOrderInItems(productDto) != null)
            return true;
        return false;
    }

    public void createOrder(String username, OrderDetailsDto orderDetailsDto, String cartName) {
        Cart currentCart = getCurrentCart(cartName);
        OrderDto order = new OrderDto();
        order.setId(2L);
        order.setAddress(orderDetailsDto.getAddress());
        order.setPhone(orderDetailsDto.getPhone());
        order.setUsername(username);
        order.setTotalPrice(currentCart.getTotalPrice());
        order.setItems(new ArrayList<>(currentCart.getItems()));
        currentCart.clear();
        kafkaTemplate.send(topic, order);
    }

}

