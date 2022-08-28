package com.geekbrains.spring.web.endpoints;

import com.geekbrains.spring.web.services.ProductService;
import com.geekbrains.spring.web.soap.soap.*;
import lombok.RequiredArgsConstructor;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

@Endpoint
@RequiredArgsConstructor
public class ProductEndpoint {

    private static final String NAMESPACE_URI = "http://www.mvg.com/spring/ws/products";
    private final ProductService productService;

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "getAllProductsRequest")
    @ResponsePayload
    public GetAllProductsResponse getAllProducts(@RequestPayload GetAllProductsRequest request){
        GetAllProductsResponse response = new GetAllProductsResponse();
        productService.findAllProducts().forEach(u -> {
            Product product = new Product();
            product.setId(u.getId());
            product.setTitle(u.getTitle());
            product.setPrice(u.getPrice());
            response.getProducts().add(product);
        });
        return response;
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "getProductByIdRequest")
    @ResponsePayload
    public GetProductByIdResponse getProductById(@RequestPayload GetProductByIdRequest request){
        long id = request.getId();
        GetProductByIdResponse response = new GetProductByIdResponse();
        Product product = new Product();
        product.setId(productService.findById(id).get().getId());
        product.setTitle(productService.findById(id).get().getTitle());
        product.setPrice(productService.findById(id).get().getPrice());
        response.setProduct(product);
        return response;
    }
}
