package com.geekbrains.spring.web.tests;

import com.geekbrains.spring.web.cart.CartServiceApp;
import com.geekbrains.spring.web.cart.dto.Cart;
import com.geekbrains.spring.web.cart.service.CartService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;

@SpringBootTest(classes = CartServiceApp.class,
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)

public class ControllerTests {

    @MockBean
    private CartService cartService;

    @Autowired
    private TestRestTemplate testRestTemplate;

    private final String cartName = "cartName";

    @Test
    public void getCart() {
        Cart mockCart = new Cart(cartName);

//        ProductDto productDto = new ProductDto(5L, "Coffee", 100);
//        mockCart.addProduct(productDto, 1);

        Mockito.doReturn(mockCart).when(cartService).getCurrentCart(cartName);

        Cart cart = testRestTemplate.postForObject("/api/v1/carts", cartName, Cart.class);

        Assertions.assertNotNull(cart);
        Assertions.assertNotNull(cart.getItems());
    }

}
