package com.geekbrains.spring.web.cart.api;

import com.geekbrains.spring.web.lib.dto.ProductDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;



@FeignClient(value = "products", url = "http://localhost:5004/product-service/api/v1/products")
public interface ProductApi {

    @GetMapping()
    Page<ProductDto> getAllProducts();

    @GetMapping("/{id}")
    ProductDto getProductById(@PathVariable long id);

}
