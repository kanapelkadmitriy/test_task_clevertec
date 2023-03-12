package com.example.test_task_clevertec.cache;

import com.example.test_task_clevertec.model.entity.Product;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConditionalOnProperty(prefix = "cache", name = "is_able", havingValue = "true")
@RequiredArgsConstructor
public class CacheConfig {
    private final CacheProperty cacheProperty;

    @Bean
    public LRUCache<Product> productLRUCache() {
        return new LRUCache<>(cacheProperty.getCapacity());
    }
}
