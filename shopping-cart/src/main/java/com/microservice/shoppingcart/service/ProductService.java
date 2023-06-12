package com.microservice.shoppingcart.service;

import com.microservice.shoppingcart.dto.request.ProductRequest;
import com.microservice.shoppingcart.dto.response.ProductResponse;
import com.microservice.shoppingcart.entity.Product;
import com.microservice.shoppingcart.exception.CustomException;
import com.microservice.shoppingcart.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static org.springframework.beans.BeanUtils.copyProperties;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    public long addProduct(ProductRequest productRequest) {
        Product product = new Product();
        product.setProductName(productRequest.getName());
        product.setQuantity(productRequest.getQuantity());
        product.setPrice(productRequest.getPrice());

        return productRepository.save(product).getProductId();
    }

    public ProductResponse getProductById(long productId) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new CustomException("Product with given Id not found", "PRODUCT_NOT_FOUND"));

        ProductResponse productResponse = new ProductResponse();

        copyProperties(product, productResponse);

        return productResponse;
    }

    public void reduceQuantity(long productId, long quantity) {

        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new CustomException("Product with given Id not found", "PRODUCT_NOT_FOUND"));

        if (product.getQuantity() < quantity) {
            throw new CustomException("Product does not have sufficient Quantity", "INSUFFICIENT_QUANTITY");
        }

        product.setQuantity(product.getQuantity() - quantity);
        productRepository.save(product);
    }

    public void deleteProductById(long productId) {
        if (!productRepository.existsById(productId)) {
            throw new CustomException("Product with given with Id: " + productId + " not found", "PRODUCT_NOT_FOUND");
        }
        productRepository.deleteById(productId);
    }
}
