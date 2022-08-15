package com.geekbrains.spring.web.services;

import com.geekbrains.spring.web.dto.Cart;
import com.geekbrains.spring.web.entities.Product;
import com.geekbrains.spring.web.exceptions.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.CacheManager;
import org.springframework.stereotype.Service;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CartService {
    private final ProductsService productsService;
    private final CacheManager cacheManager;
    private Cart cart;

    //возвращает корзину, если такой корзины в кэше нет, то создает новую
    public Cart getCurrentCart(String cartName){
        cart = cacheManager.getCache("Cart").get(cartName, Cart.class);
        if(!Optional.ofNullable(cart).isPresent()){
            cart = new Cart(cartName, cacheManager);
            cacheManager.getCache("Cart").put(cartName, cart);
        }
        return cart;
    }

    //добавляет один товар в конзину
    public void addProductByIdToCart(Long id, String cartName) {
        addProductByIdToCart(id, cartName, 1);
    }

    //добавляет несколько товаров в корзину
    public void addProductByIdToCart(Long id, String cartName, int quantity) {
        Cart cart = getCurrentCart(cartName);
        Product product = productsService.findById(id).orElseThrow(() -> new ResourceNotFoundException("Не удалось найти продукт"));
        cart.addProduct(product, quantity);
        cacheManager.getCache("Cart").put(cartName, cart);
    }

    //полностью удаляет товар из корзины
    public void removeProduct(Long id, String cartName) {
        Cart cart = getCurrentCart(cartName);
        if (isItemInTheCart(id))
            cart.removeProduct(id);
        else
            throw new ResourceNotFoundException("В корзине нет такого товара");
        cacheManager.getCache("Cart").put(cartName, cart);
    }

    //уменьшает количество товара на один,
    //или удаляет, если кол-во меньше одного
    public void decreaseProduct(Long id, String cartName) {
        decreaseProduct(id, cartName, -1);
        cacheManager.getCache("Cart").put(cartName, cart);
    }

    //уменьшает количество товара на delta,
    //или удаляет, если кол-во меньше delta
    public void decreaseProduct(Long id, String cartName, int delta) {
        Cart cart = getCurrentCart(cartName);
        if (delta > 0)
            delta *= -1;
        if (isItemInTheCart(id))
            cart.decreaseProduct(id, delta);
        else
            throw new ResourceNotFoundException("В корзине нет такого товара");
        cacheManager.getCache("Cart").put(cartName, cart);
    }

    //очищает корзину
    public void clear(String cartName){
        Cart cart = getCurrentCart(cartName);
        cart.clear();
        cacheManager.getCache("Cart").put(cartName, cart);
    }

    //проверяет наличие товара в корзине
    private boolean isItemInTheCart(Long id) {
        Product product = productsService.findById(id).orElseThrow(() -> new ResourceNotFoundException("Не удалось найти продукт"));
        if (cart.findOrderInItems(product) != null)
            return true;
        return false;
    }


}

