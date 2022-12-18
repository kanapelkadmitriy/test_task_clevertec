package com.example.test_task_clevertec.service.impl;

import com.example.test_task_clevertec.service.ProductParser;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ProductParserImpl implements ProductParser {

    private final static String SEPARATOR = "-";

    @Override
    public Map<Long, Integer> parse(List<String> items) {
        final Map<Long, Integer> productItems = new HashMap<>();
        for (String item : items) {
            int separatorIndex = item.indexOf(SEPARATOR);
            final Long productId = Long.valueOf(item.substring(0, separatorIndex));
            final Integer quantity = Integer.valueOf(item.substring(separatorIndex + 1));
            productItems.put(productId, quantity);
        }
        return productItems;
    }
}
