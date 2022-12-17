package com.example.test_task_clevertec.service;

import com.example.test_task_clevertec.model.entity.Card;

public interface CardService {

    Card findByCardNumber(String cardNumber);
}
