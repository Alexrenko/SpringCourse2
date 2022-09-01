package edu.geekbrains.productservice.controllers;

import edu.geekbrains.productservice.converters.ProductConverter;
import edu.geekbrains.productservice.dto.ProductDto;
import edu.geekbrains.productservice.services.ProductService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/products")
public class ProductController {

    private ProductService productService;
    private ProductConverter productConverter;

    @GetMapping
    public List<ProductDto> findAll() {
        return productService.findAll().stream().map(
                p -> productConverter.entityToDto(p)
        ).collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public ProductDto findById(@PathVariable Long id) {

        return productConverter.entityToDto(productService.findById(id).get());

    }

}
