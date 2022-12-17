package com.example.test_task_clevertec.repository;

import com.example.test_task_clevertec.model.entity.Card;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CardRepository extends JpaRepository<Card, Long> {

    @Query(value = "SELECT c FROM Card c WHERE c.number = ?1")
    Optional<Card> findByCardNumber(String cardNumber);
}
