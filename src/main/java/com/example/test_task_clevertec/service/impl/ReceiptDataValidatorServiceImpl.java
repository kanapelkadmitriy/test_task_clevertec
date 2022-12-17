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
    public void validate(List<String> item, String cardNumber) {
        if (item == null || item.isEmpty()) {
            throw new BusinessException("wasn't received data for receipt generation");
        }

        if (cardNumber != null && !CARD_PATTERN.matcher(cardNumber).matches()) {
            throw new BusinessException(String.format("invalid card number: %s", cardNumber));
        }

        for (String it : item) {
            if (!ITEM_PATTERN.matcher(it).matches()){
                throw new BusinessException(String.format("invalid value for receipt: %s", it));
            }
        }
    }
}
