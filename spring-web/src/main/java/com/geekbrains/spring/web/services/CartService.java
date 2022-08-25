package com.geekbrains.spring.web.services;

import com.geekbrains.spring.web.dto.Cart;
import com.geekbrains.spring.web.entities.Product;
import com.geekbrains.spring.web.exceptions.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CartService {
    private final ProductService productService;
    private final CacheManager cacheManager;
    private Cart cart;
    @Value("${other.cache.carts}")
    private String CARTS_CACHE;

    @Cacheable(value = "${other.cache.carts}", key = "#cartName")
    public Cart getCurrentCart(String cartName){
        cart = cacheManager.getCache(CARTS_CACHE).get(cartName, Cart.class);
        if(!Optional.ofNullable(cart).isPresent()){
            cart = new Cart(cartName, cacheManager);
            cacheManager.getCache(CARTS_CACHE).put(cartName, cart);
        }
        return cart;
    }

    @CachePut(value = "CartsCache", key = "#cartName")
    public Cart addProductByIdToCart(Long id, String cartName, int quantity){
        Cart cart = getCurrentCart(cartName);
        Product product = productService.findById(id).orElseThrow(() -> new ResourceNotFoundException("Не удалось найти продукт"));
        cart.addProduct(product, quantity);
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
        Product product = productService.findById(id).orElseThrow(() -> new ResourceNotFoundException("Не удалось найти продукт"));
        if (cart.findOrderInItems(product) != null)
            return true;
        return false;
    }


}

