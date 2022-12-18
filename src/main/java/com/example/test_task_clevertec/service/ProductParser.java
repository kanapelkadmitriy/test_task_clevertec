package com.example.test_task_clevertec.service;

import java.util.List;
import java.util.Map;

public interface ProductParser {

    Map<Long, Integer> parse(List<String> items);
}
