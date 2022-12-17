package com.example.test_task_clevertec.service.impl;

import com.example.test_task_clevertec.model.dto.ReceiptResponseDto;
import com.example.test_task_clevertec.service.CardService;
import com.example.test_task_clevertec.service.ProductService;
import com.example.test_task_clevertec.service.ReceiptDataValidatorService;
import com.example.test_task_clevertec.service.ReceiptService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ReceiptServiceImpl implements ReceiptService {

    private final ProductService productService;
    private final CardService cardService;
    private final ReceiptDataValidatorService receiptDataValidatorService;

    @Override
    public ReceiptResponseDto generateReceipt(List<String> item, String cardNumber) {
        receiptDataValidatorService.validate(item, cardNumber);

//        for (String it : item) {
//            int separatorIndex = it.indexOf("-");
//            final Long productId = Long.valueOf(it.substring(0, separatorIndex));
//            final Integer quantity = Integer.valueOf(it.substring(separatorIndex + 1));
//        }
        return null;
    }
}
