package com.example.test_task_clevertec;

import com.example.test_task_clevertec.repository.CardRepository;
import com.example.test_task_clevertec.repository.ProductRepository;
import org.junit.jupiter.api.AfterEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.testcontainers.junit.jupiter.Testcontainers;

@SpringBootTest
@Testcontainers
@AutoConfigureMockMvc
public class IntegrationTest {

    @Autowired
    protected MockMvc mockMvc;
    @Autowired
    protected CardRepository cardRepository;
    @Autowired
    protected ProductRepository productRepository;

    @AfterEach
    void clear() {
        cardRepository.deleteAll();
        productRepository.deleteAll();
    }
}
