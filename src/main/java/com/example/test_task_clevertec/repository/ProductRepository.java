package com.example.test_task_clevertec.repository;

import com.example.test_task_clevertec.model.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {
}
