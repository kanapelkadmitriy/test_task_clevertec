package com.example.test_task_clevertec.service;

import com.example.test_task_clevertec.model.dto.ReceiptResponseDto;

public interface ReceiptWriterService {

    void write(ReceiptResponseDto receiptResponse);
}
