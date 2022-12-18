package com.example.test_task_clevertec.service;

import com.example.test_task_clevertec.model.dto.ReceiptResponseDto;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ReceiptService {

    ReceiptResponseDto generateReceiptFromFile(MultipartFile file);

    ReceiptResponseDto generateReceipt(List<String> items, String cardNumber);
}
