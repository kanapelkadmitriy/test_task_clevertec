package com.example.test_task_clevertec.service.impl;

import com.example.test_task_clevertec.exceptions.BusinessException;
import com.example.test_task_clevertec.service.ReceiptDataValidatorService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ReceiptDataValidatorServiceImplTest {

    @Autowired
    private ReceiptDataValidatorService receiptDataValidatorService;

    @Test
    void successfulValidate() {
        List<String> validItems = List.of("1-6","2-5","3-8");
        String validCardNumber = "Card-1234";
        String validCardNumber2 = "CARD-1234";
        assertDoesNotThrow(() -> receiptDataValidatorService.validate(validItems,validCardNumber));
        assertDoesNotThrow(() -> receiptDataValidatorService.validate(validItems,validCardNumber2));
    }

    @Test
    void validateWithInvalidItems() {
        List<String> invalidItems = List.of("ADC-6","2-5","3-8");
        String validCardNumber2 = "CARD-1234";
        assertThrows(BusinessException.class, () -> receiptDataValidatorService.validate(invalidItems,validCardNumber2));
    }

    @Test
    void validateWithInvalidCardNumber() {
        List<String> validItems = List.of("1-6","2-5","3-8");
        String invalidCardNumber = "CARD-124";
        String invalidCardNumber1 = "CA1D-1234";
        String invalidCardNumber2 = "CARD_1234";
        assertThrows(BusinessException.class, () -> receiptDataValidatorService.validate(validItems,invalidCardNumber));
        assertThrows(BusinessException.class, () -> receiptDataValidatorService.validate(validItems,invalidCardNumber1));
        assertThrows(BusinessException.class, () -> receiptDataValidatorService.validate(validItems,invalidCardNumber2));
    }
}