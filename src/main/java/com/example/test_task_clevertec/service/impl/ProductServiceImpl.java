package com.example.test_task_clevertec.service.impl;

import com.example.test_task_clevertec.exceptions.BusinessException;
import com.example.test_task_clevertec.model.entity.Product;
import com.example.test_task_clevertec.repository.ProductRepository;
import com.example.test_task_clevertec.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;

    @Override
    public Product findById(Long id) {
        return productRepository.findById(id)
                .orElseThrow(() -> new BusinessException(String.format("product with id %s not fouond", id)));
    }
}
