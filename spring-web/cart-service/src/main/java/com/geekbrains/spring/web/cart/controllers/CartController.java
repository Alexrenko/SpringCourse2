package com.geekbrains.spring.web.cart.controllers;


import com.geekbrains.spring.web.cart.dto.Cart;
import com.geekbrains.spring.web.cart.service.CartService;
import com.geekbrains.spring.web.dtoLibrary.OrderDetailsDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/v1/carts")
@RequiredArgsConstructor
public class CartController {
    private final CartService cartService;

    @PostMapping
    public Cart getCurrentCart(@RequestBody String cartName){
        return cartService.getCurrentCart(cartName);
    }

    @PostMapping("/add/{id}")
    public void addProductToCart(@PathVariable Long id,
                                 @RequestBody String cartName,
                                 @RequestParam (name = "q", defaultValue = "1") int quantity) {
        cartService.addProductByIdToCart(id, cartName, quantity);
    }

    @PostMapping("/decrease/{id}")
    public void decreaseProductFromCart(@PathVariable Long id,
                                        @RequestBody String cartName,
                                        @RequestParam (name = "d", defaultValue = "1") int delta) {
        cartService.decreaseProduct(id, cartName, delta);
    }

    @PostMapping("/remove/{id}")
    public void removeProductFromCart(@PathVariable Long id,
                                      @RequestBody String cartName) {
        cartService.removeProduct(id, cartName);
    }

    @PostMapping("/clear")
    public void clearCart(@RequestBody String cartName) {
        cartService.clear(cartName);
    }

    @PostMapping("/createOrder")
    public void createOrder(@RequestHeader String username,
                            @RequestBody Map<String, String> map) {
        cartService.createOrder(
                username,
                new OrderDetailsDto(map.get("address"), map.get("phone")),
                map.get("cartName")
        );
    }

}
