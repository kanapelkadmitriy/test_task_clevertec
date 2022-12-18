package com.example.test_task_clevertec.controller;

import com.example.test_task_clevertec.model.dto.ReceiptResponseDto;
import com.example.test_task_clevertec.service.ReceiptService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/receipt")
@RequiredArgsConstructor
public class ReceiptController {
    public static final String GENERATE_URL = "/generate";

    private final ReceiptService receiptService;

    @GetMapping(GENERATE_URL)
    public ReceiptResponseDto generateReceipt(@RequestParam(value = "item", required = false) List<String> items,
                                              @RequestParam(value = "card_number", required = false) String cardNumber) {
        return receiptService.generateReceipt(items, cardNumber);
    }

    @PostMapping(GENERATE_URL)
    public ReceiptResponseDto generateReceiptFromFile(@RequestBody MultipartFile file) {
        return receiptService.generateReceiptFromFile(file);
    }
}
