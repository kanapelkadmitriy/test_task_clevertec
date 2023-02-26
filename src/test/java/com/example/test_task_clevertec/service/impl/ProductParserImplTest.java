package com.example.test_task_clevertec.service.impl;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.InjectMocks;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;


@SpringBootTest
class ProductParserImplTest {

    @InjectMocks
    private ProductParserImpl productParser;

    @ParameterizedTest
    @MethodSource("provideData")
    void successfulParse(List<String> items, Map<Long, Integer> result) {
        Map<Long, Integer> actual = productParser.parse(items);
        actual.keySet().forEach(id -> assertEquals(result.get(id),result.get(id)));
    }

    private static Stream<Arguments> provideData() {
        return Stream.of(
                Arguments.of(List.of("1-5", "2-10", "3-15"), Map.of(1L, 5, 2L, 10, 3L, 15)),
                Arguments.of(List.of("1-4","3-5","2-7"), Map.of(1L, 4, 3L, 5, 2L, 7)),
                Arguments.of(List.of("1-5","3-6","4-8"), Map.of(1L, 5, 3L, 6, 4L, 8)),
                Arguments.of(List.of("2-2","1-5","3-4"), Map.of(2L, 2, 1L, 5, 3L, 4)),
                Arguments.of(List.of("1-6","2-4","3-3"), Map.of(1L, 6, 2L, 4, 3L, 3))
        );
    }
}