package com.example.test_task_clevertec.service.impl;

import com.example.test_task_clevertec.exceptions.BusinessException;
import com.example.test_task_clevertec.service.ReceiptDataValidatorService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.regex.Pattern;

@Service
public class ReceiptDataValidatorServiceImpl implements ReceiptDataValidatorService {
    private static final Pattern ITEM_PATTERN = Pattern.compile("\\d+-\\d+");
    private static final Pattern CARD_PATTERN = Pattern.compile("card-\\d{4}", Pattern.CASE_INSENSITIVE);

    @Override
    public void validate(List<String> items, String cardNumber) {
        if (items == null || items.isEmpty()) {
            throw new BusinessException("wasn't received data for receipt generation");
        }

        if (cardNumber != null && !CARD_PATTERN.matcher(cardNumber).matches()) {
            throw new BusinessException(String.format("invalid card number: %s", cardNumber));
        }

        items.forEach(item -> {
            if (!ITEM_PATTERN.matcher(item).matches()){
                throw new BusinessException(String.format("invalid value for receipt: %s", item));
            }
        });
    }
}
