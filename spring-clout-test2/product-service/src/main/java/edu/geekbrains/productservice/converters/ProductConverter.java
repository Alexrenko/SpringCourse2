package edu.geekbrains.productservice.converters;

import edu.geekbrains.productservice.dto.ProductDto;
import edu.geekbrains.productservice.entities.Product;
import org.springframework.stereotype.Component;

@Component
public class ProductConverter {

    public ProductDto entityToDto(Product product) {
        return new ProductDto(product.getId(), product.getTitle(), product.getPrice());
    }
}
