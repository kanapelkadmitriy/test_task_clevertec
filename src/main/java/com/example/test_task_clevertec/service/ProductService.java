package com.example.test_task_clevertec.service;

import com.example.test_task_clevertec.model.dto.ProductDto;
import com.example.test_task_clevertec.model.entity.Product;


public interface ProductService {

    Product findById(Long id);

    ProductDto getById(Long id);

    ProductDto createProduct(ProductDto productDto);

    ProductDto updateProduct(Long id, ProductDto productDto);

    void deleteProduct(Long id);
}
