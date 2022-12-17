package com.example.test_task_clevertec.service;

import java.util.List;

public interface ReceiptDataValidatorService {

    void validate(List<String> items, String cardNumber);
}
