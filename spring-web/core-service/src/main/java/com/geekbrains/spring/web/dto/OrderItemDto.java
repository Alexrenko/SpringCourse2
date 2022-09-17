package com.geekbrains.spring.web.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class OrderItemDto {

    private Long productId;
    private String title;
    private int quantity;
    private int pricePerProduct;
    private int price;

    public OrderItemDto(ProductDto productDto){
        this.productId = productDto.getId();
        this.title = productDto.getTitle();
        this.quantity = 1;
        this.pricePerProduct = productDto.getPrice();
        this.price = productDto.getPrice();
    }

    public OrderItemDto(ProductDto productDto, int quantity) {
        this(productDto);
        this.quantity = quantity;
    }

    public void changeQuantity(int delta){
        this.quantity += delta;
        this.price = this.quantity * this.pricePerProduct;
    }

}
