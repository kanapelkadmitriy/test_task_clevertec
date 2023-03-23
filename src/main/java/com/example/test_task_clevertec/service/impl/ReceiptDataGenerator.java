package com.example.test_task_clevertec.service.impl;

import com.example.test_task_clevertec.model.dto.ReceiptResponseDto;

import java.util.List;

public interface ReceiptDataGenerator {

    List<String> generateReceiptData(ReceiptResponseDto receiptResponse);
}
