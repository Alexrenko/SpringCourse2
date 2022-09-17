package com.geekbrains.spring.web.tests;


import com.geekbrains.spring.web.cart.CartServiceApp;
import com.geekbrains.spring.web.cart.dto.Cart;
import com.geekbrains.spring.web.cart.dto.ProductDto;
import com.geekbrains.spring.web.cart.service.CartService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.cache.CacheManager;
import org.springframework.web.client.RestTemplate;

@SpringBootTest(classes = CartServiceApp.class)
public class ServiceTests {

    @Autowired
    public CartService cartService;

    @MockBean
    public RestTemplate restTemplate;

    @SpyBean
    public CacheManager cacheManager;

    @Value("${other.cache.carts}")
    private String CARTS_CACHE;
    private final String cartName = "cartName";

    @BeforeEach
    public void init() {
        cartService.clear(cartName);
    }

    @Test
    public void createCart() {
        Cart cart = cartService.getCurrentCart(cartName);
        Cart cart2 = cartService.getCurrentCart(cartName);
        Cart cart3 = cartService.getCurrentCart(cartName);
        Cart cart4 = cartService.getCurrentCart(cartName);
        Cart cart5 = cartService.getCurrentCart(cartName);

        Assertions.assertNotNull(cart);
        Assertions.assertEquals(cart.getClass(), Cart.class);
        Assertions.assertEquals(cart.getTotalPrice(), 0);

        Mockito.verify(cacheManager, Mockito.times(2)).getCache(CARTS_CACHE);
    }

    @Test
    public void addAndRemoveProduct() {
        ProductDto productDto = new ProductDto();
        productDto.setId(5L);
        productDto.setTitle("Товар");
        productDto.setPrice(1000);

        Mockito.doReturn(productDto).when(restTemplate)
                .getForObject("http://localhost:5002/core-service/api/v1/products/" + 5L, ProductDto.class);

        cartService.addProductByIdToCart(5L, cartName, 1);
        Assertions.assertEquals(1, cartService.getCurrentCart(cartName).getItems().size());
        Assertions.assertEquals("Товар", cartService.getCurrentCart(cartName).getItems().get(0).getTitle());
        Assertions.assertEquals(1000, cartService.getCurrentCart(cartName).getItems().get(0).getPrice());

        cartService.removeProduct(5L, cartName);
        Assertions.assertEquals(0, cartService.getCurrentCart(cartName).getItems().size());
    }

}
