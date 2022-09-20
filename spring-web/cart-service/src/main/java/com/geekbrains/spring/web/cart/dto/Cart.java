package com.geekbrains.spring.web.cart.dto;

import com.geekbrains.spring.web.lib.dto.OrderItemDto;
import com.geekbrains.spring.web.lib.dto.ProductDto;
import lombok.Data;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Data
public class Cart {

    private List<OrderItemDto> items;
    private  int totalPrice;

    public Cart() {
    }

    public Cart(String cartName){
        this.items = new ArrayList<>();
        this.totalPrice = 0;
    }

    public void addProduct(ProductDto productDto, int delta){
        OrderItemDto o = findOrderInItems(productDto);
        if(o != null)
            o.changeQuantity(delta);
        else
            items.add(new OrderItemDto(productDto, delta));
        recalculate();
    }

    public OrderItemDto findOrderInItems(ProductDto p){
        for(OrderItemDto o : items)
            if(o.getProductId() == p.getId())
                return o;
        return null;
    }

    public void removeProduct(Long id){
        items.removeIf(o -> o.getProductId() == id);
        recalculate();
    }

    public void decreaseProduct(Long id, int delta){
        Iterator<OrderItemDto> iter = items.iterator();
        while (iter.hasNext()){
            OrderItemDto o = iter.next();
            if(o.getProductId() == id){
                o.changeQuantity(delta);
                if(o.getQuantity() <= 0){
                    iter.remove();
                }
                recalculate();
                return;
            }
        }
    }

    public void clear(){
        items.clear();
        totalPrice = 0;
    }

    private void recalculate(){
        totalPrice = 0;
        for(OrderItemDto o: items){
            totalPrice += o.getPrice();
        }
    }
}
