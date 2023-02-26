package com.example.test_task_clevertec.service.impl;

import com.example.test_task_clevertec.IntegrationTest;
import com.example.test_task_clevertec.model.entity.Product;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ProductServiceTest extends IntegrationTest {

    @Autowired
    ProductServiceImpl productService;

    @ParameterizedTest
    @MethodSource("provideSuccessfulProductData")
    void successfulFindById(Product product) {
        Product createdProduct = productRepository.save(product);
        Product result = productService.findById(createdProduct.getId());
        assertEquals(createdProduct.getId(), result.getId());
        assertEquals(createdProduct.getDescription(), result.getDescription());
        assertEquals(createdProduct.getQuantity(), result.getQuantity());
        assertEquals(createdProduct.getPrice(), result.getPrice());
    }

    private static Stream<Arguments> provideSuccessfulProductData() {
        return Stream.of(
                Arguments.of(Product.builder()
                        .description("product_1")
                        .price(BigDecimal.valueOf(100))
                        .quantity(100)
                        .isDiscounted(true)
                        .build()),
                Arguments.of(Product.builder()
                        .description("product_2")
                        .price(BigDecimal.valueOf(100))
                        .quantity(100)
                        .isDiscounted(true)
                        .build()),
                Arguments.of(Product.builder()
                        .description("product_3")
                        .price(BigDecimal.valueOf(100))
                        .quantity(100)
                        .isDiscounted(true)
                        .build())
        );
    }

}
