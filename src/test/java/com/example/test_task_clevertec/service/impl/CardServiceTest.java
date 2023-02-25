package com.example.test_task_clevertec.service.impl;

import com.example.test_task_clevertec.IntegrationTest;
import com.example.test_task_clevertec.exceptions.BusinessException;
import com.example.test_task_clevertec.model.entity.Card;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class CardServiceTest extends IntegrationTest {

    @Autowired
    CardServiceImpl cardService;

    @ParameterizedTest
    @MethodSource("provideSuccessfulCardData")
    void successfulFindByCardNumberTest(Card card, String cardNumber) {
        Card createdCard = cardRepository.save(card);
        Card result = cardService.findByCardNumber(cardNumber);
        assertEquals(createdCard.getNumber(), result.getNumber());
        assertEquals(createdCard.getDiscount(), result.getDiscount());
        assertEquals(createdCard.getId(), result.getId());
    }

    @ParameterizedTest
    @MethodSource("provideUnsuccessfulCardData")
    void unsuccessfulFindByCardNumberTest(Card card, String cardNumber) {
        cardRepository.save(card);
        assertThrows(BusinessException.class, () -> cardService.findByCardNumber(cardNumber));
    }

    private static Stream<Arguments> provideSuccessfulCardData() {
        return Stream.of(
                Arguments.of(Card.builder().number("Card-1234").discount(5).build(), "Card-1234")
        );
    }

    private static Stream<Arguments> provideUnsuccessfulCardData() {
        return Stream.of(
                Arguments.of(Card.builder().number("Card-1234").discount(5).build(), "Card-1235")
        );
    }
}
