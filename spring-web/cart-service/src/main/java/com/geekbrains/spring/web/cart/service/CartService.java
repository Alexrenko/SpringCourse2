package com.geekbrains.spring.web.cart.service;

import com.geekbrains.spring.web.cart.dto.Cart;
import com.geekbrains.spring.web.cart.dto.ProductDto;
import com.geekbrains.spring.web.cart.exceptions.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Optional;

@Service
//@RequiredArgsConstructor
public class CartService {

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private CacheManager cacheManager;

    private Cart cart;
    @Value("${other.cache.carts}")
    private String CARTS_CACHE;

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
        ProductDto productDto = restTemplate
                .getForObject("http://localhost:5002/core-service/api/v1/products/" + id, ProductDto.class);
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
                .getForObject("http://localhost:5002/core-service/api/v1/products/" + id, ProductDto.class);
        if (cart.findOrderInItems(productDto) != null)
            return true;
        return false;
    }


}

