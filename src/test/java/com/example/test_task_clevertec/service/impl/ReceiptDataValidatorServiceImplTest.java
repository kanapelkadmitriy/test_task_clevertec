package com.example.test_task_clevertec.service.impl;

import com.example.test_task_clevertec.exceptions.BusinessException;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.InjectMocks;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
class ReceiptDataValidatorServiceImplTest {

    @InjectMocks
    private ReceiptDataValidatorServiceImpl receiptDataValidatorService;

    @ParameterizedTest
    @MethodSource("provideValidData")
    void successfulValidate(List<String> items, String cardNumber) {
        assertDoesNotThrow(() -> receiptDataValidatorService.validate(items,cardNumber));
    }

    @ParameterizedTest
    @MethodSource("provideInvalidItemsData")
    void validateWithInvalidItems(List<String> invalidItems, String cardNumber) {
        assertThrows(BusinessException.class, () -> receiptDataValidatorService.validate(invalidItems,cardNumber));
    }


    @ParameterizedTest
    @MethodSource("provideInvalidCardNumberData")
    void validateWithInvalidCardNumber(List<String> validItems, String cardNumber) {
        assertThrows(BusinessException.class, () -> receiptDataValidatorService.validate(validItems,cardNumber));
    }

    private static Stream<Arguments> provideValidData() {
        return Stream.of(
                Arguments.of(List.of("2-3","2-5","3-9"), "CarD-1234"),
                Arguments.of(List.of("1-4","3-5","3-7"), "card-1234"),
                Arguments.of(List.of("2-5","2-6","2-8"), "CARD-1234"),
                Arguments.of(List.of("2-2","1-5","3-4"), "Card-1234"),
                Arguments.of(List.of("2-6","2-4","3-3"), "CaRd-1234")
        );
    }

    private static Stream<Arguments> provideInvalidItemsData() {
        return Stream.of(
                Arguments.of(List.of("ADC-6","2-5","3-8"), "Card-1234"),
                Arguments.of(List.of("1-6","asd-5","3-8"), "Card-1234"),
                Arguments.of(List.of("!d-6","2-5","3-8"), "Card-1234"),
                Arguments.of(List.of("ADC-6","2-5","3  -  8"), "Card-1234"),
                Arguments.of(List.of("/-6","2-5","3-8"), "Card-1234")
        );
    }

    private static Stream<Arguments> provideInvalidCardNumberData() {
        return Stream.of(
                Arguments.of(List.of("2-3","2-5","3-9"), "Ca4D-1234"),
                Arguments.of(List.of("1-4","3-5","3-7"), "Card-12a4"),
                Arguments.of(List.of("2-5","2-6","2-8"), "CAR!-1234"),
                Arguments.of(List.of("2-2","1-5","3-4"), "Card - 1234"),
                Arguments.of(List.of("2-6","2-4","3-3"), "Ca45-1234")
        );
    }
}