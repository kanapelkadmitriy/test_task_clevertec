package com.example.test_task_clevertec.service.impl;

import com.example.test_task_clevertec.exceptions.BusinessException;
import com.example.test_task_clevertec.model.entity.Card;
import com.example.test_task_clevertec.repository.CardRepository;
import com.example.test_task_clevertec.service.CardService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CardServiceImpl implements CardService {

    private final CardRepository cardRepository;

    @Override
    public Card findByCardNumber(String cardNumber) {
        return cardRepository.findByCardNumber(cardNumber)
                .orElseThrow(() -> new BusinessException(String.format("card with number %s not fouond", cardNumber)));
    }
}
