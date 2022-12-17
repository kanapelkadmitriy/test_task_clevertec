package com.example.test_task_clevertec.controller;

import com.example.test_task_clevertec.model.dto.ReceiptResponseDto;
import com.example.test_task_clevertec.service.ReceiptService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/receipt")
@RequiredArgsConstructor
public class ReceiptController {

    private final ReceiptService receiptService;

    @GetMapping("/generate")
    public ReceiptResponseDto generateReceipt(@RequestParam(value = "item", required = false) List<String> item,
                                              @RequestParam(value = "card_number", required = false) String cardNumber) {
        return receiptService.generateReceipt(item, cardNumber);
    }
}
