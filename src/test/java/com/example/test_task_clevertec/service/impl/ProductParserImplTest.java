package com.example.test_task_clevertec.service.impl;

import com.example.test_task_clevertec.service.ProductParser;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;


@SpringBootTest
class ProductParserImplTest {

    @Autowired
    private ProductParser productParser;

    @Test
    void successfulParse() {
        List<String> items = List.of("1-5", "2-10", "3-15");
        Map<Long, Integer> actual = Map.of(1L, 5, 2L, 10, 3L, 15);
        Map<Long, Integer> result = productParser.parse(items);
        assertEquals(actual.get(0L),result.get(0L));
        assertEquals(actual.get(1L),result.get(1L));
        assertEquals(actual.get(2L),result.get(2L));
    }
}