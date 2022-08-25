package com.geekbrains.spring.web.controllers;

import com.geekbrains.spring.web.dto.Cart;
import com.geekbrains.spring.web.services.CartService;
import com.geekbrains.spring.web.utils.ServiceStatistic;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController

@RequestMapping("/api/v1/carts")
@RequiredArgsConstructor
public class CartController {
    private final CartService service;

    @Autowired
    private ServiceStatistic serviceStatistic;

    @PostMapping
    public Cart getCurrentCart(@RequestBody String cartName){
        return service.getCurrentCart(cartName);
    }

    @PostMapping("/add/{id}")
    public void addProductToCart(@PathVariable Long id,
                                 @RequestBody String cartName,
                                 @RequestParam (name = "q", defaultValue = "1") int quantity) {
        service.addProductByIdToCart(id, cartName, quantity);
    }

    @PostMapping("/decrease/{id}")
    public void decreaseProductFromCart(@PathVariable Long id,
                                        @RequestBody String cartName,
                                        @RequestParam (name = "d", defaultValue = "1") int delta) {
        service.decreaseProduct(id, cartName, delta);
    }

    @PostMapping("/remove/{id}")
    public void removeProductFromCart(@PathVariable Long id,
                                      @RequestBody String cartName) {
        service.removeProduct(id, cartName);
    }

    @PostMapping("/clear")
    public void clearCart(@RequestBody String cartName) {
        service.clear(cartName);
    }

    @PostMapping("/print")
    public void print(@RequestBody String cartName) {
        System.out.println(cartName);
    }

}
