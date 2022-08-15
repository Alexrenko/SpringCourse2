package com.geekbrains.spring.web.dto;

import com.geekbrains.spring.web.entities.Product;
import lombok.Data;
import org.springframework.cache.CacheManager;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

@Data
public class Cart {

    private List<OrderItemDto> items;
    private  int totalPrice;

    public Cart() {
        this.items = new ArrayList<>();
    }

    public Cart(String cartName, CacheManager manager){
        Cart cart = manager.getCache("Cart").get(cartName, Cart.class);
        if(Optional.ofNullable(cart).isPresent()){
            this.items = cart.getItems();
            this.totalPrice = cart.getTotalPrice();
        } else {
            this.items = new ArrayList<>();
            this.totalPrice = 0;
            manager.getCache("Cart").put(cartName, Cart.class);
        }
    }

    //добавляет один товар в корзину
    public void addProduct(Product product) {
        addProduct(product, 1);
    }

    //добавляет несколько товаров в конзину
    public void addProduct(Product product, int delta){
        OrderItemDto o = findOrderInItems(product);
        if(o != null)
            o.changeQuantity(delta);
        else
            items.add(new OrderItemDto(product, delta));
        recalculate();
    }

    //проверяет, есть ли такой товар в корзине
    public OrderItemDto findOrderInItems(Product p) {
        for(OrderItemDto o : items)
            if(o.getProductId().equals(p.getId()))
                return o;
        return null;
    }

    //полностью удаляет товар из коризны
    public void removeProduct(Long id) {
        items.removeIf(o -> o.getProductId().equals(id));
        recalculate();
    }

    //уменьшает количество товара на один,
    //или удаляет, если кол-во меньше одного
    public void decreaseProduct(Long id) {
        decreaseProduct(id, -1);
    }

    //уменьшает количество товара на delta,
    //или удаляет, если кол-во меньше delta
    public void decreaseProduct(Long id, int delta) {
        Iterator<OrderItemDto> iter = items.iterator();
        while (iter.hasNext()){
            OrderItemDto o = iter.next();
            if(o.getProductId().equals(id)){
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
