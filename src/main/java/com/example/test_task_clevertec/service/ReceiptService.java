package com.example.test_task_clevertec.service;

import com.example.test_task_clevertec.model.dto.ReceiptResponseDto;

import java.util.List;

public interface ReceiptService {

    ReceiptResponseDto generateReceipt(List<String> items, String cardNumber);
}
