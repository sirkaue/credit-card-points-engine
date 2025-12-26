package com.sirkaue.creditcardpointsengine.domain;

import com.sirkaue.creditcardpointsengine.domain.exception.InvalidTransactionException;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.Instant;

import static org.junit.jupiter.api.Assertions.*;

class TransactionTest {

    @Test
    void shouldCreateTransactionWithDefaultStrategy() {
        BigDecimal amount = BigDecimal.valueOf(100);
        Currency currency = Currency.USD;
        Instant date = Instant.now();
        Card card = new Card("1234", CardType.GOLD, "John Doe");

        Transaction transaction = new Transaction(amount, currency, date, card);

        assertNotNull(transaction.getId());
        assertEquals(amount, transaction.getAmount());
        assertEquals(currency, transaction.getCurrency());
        assertEquals(date, transaction.getDate());
        assertEquals(card, transaction.getCard());
        assertEquals(StrategyType.DEFAULT, transaction.getStrategyType());
    }

    @Test
    void shouldCreateTransactionWithGivenStrategy() {
        BigDecimal amount = BigDecimal.valueOf(100);
        Currency currency = Currency.USD;
        Instant date = Instant.now();
        Card card = new Card("1234", CardType.GOLD, "John Doe");

        Transaction transaction = new Transaction(amount, currency, date, card, StrategyType.TRAVEL);

        assertEquals(StrategyType.TRAVEL, transaction.getStrategyType());
    }

    @Test
    void shouldUseDefaultStrategyWhenNull() {
        BigDecimal amount = BigDecimal.valueOf(100);
        Currency currency = Currency.USD;
        Instant date = Instant.now();
        Card card = new Card("1234", CardType.GOLD, "John Doe");

        Transaction transaction = new Transaction(amount, currency, date, card, null);

        assertEquals(StrategyType.DEFAULT, transaction.getStrategyType());
    }

    @Test
    void shouldThrowExceptionWhenAmountIsNull() {
        Currency currency = Currency.USD;
        Instant date = Instant.now();
        Card card = new Card("1234", CardType.GOLD, "John Doe");

        NullPointerException exception = assertThrows(NullPointerException.class,
                () -> new Transaction(null, currency, date, card));
        assertEquals("Transaction amount is required.", exception.getMessage());
    }

    @Test
    void shouldThrowExceptionWhenAmountIsNegative() {
        Currency currency = Currency.USD;
        Instant date = Instant.now();
        Card card = new Card("1234", CardType.GOLD, "John Doe");

        InvalidTransactionException exception = assertThrows(InvalidTransactionException.class,
                () -> new Transaction(BigDecimal.valueOf(-1), currency, date, card));
        assertEquals("Transaction amount cannot be negative.", exception.getMessage());
    }

    @Test
    void shouldThrowExceptionWhenCurrencyIsNull() {
        BigDecimal amount = BigDecimal.valueOf(100);
        Instant date = Instant.now();
        Card card = new Card("1234", CardType.GOLD, "John Doe");

        NullPointerException exception = assertThrows(NullPointerException.class,
                () -> new Transaction(amount, null, date, card));
        assertEquals("Currency is required.", exception.getMessage());
    }

    @Test
    void shouldThrowExceptionWhenDateIsNull() {
        BigDecimal amount = BigDecimal.valueOf(100);
        Currency currency = Currency.USD;
        Card card = new Card("1234", CardType.GOLD, "John Doe");

        NullPointerException exception = assertThrows(NullPointerException.class,
                () -> new Transaction(amount, currency, null, card));
        assertEquals("Transaction timestamp is required.", exception.getMessage());
    }

    @Test
    void shouldThrowExceptionWhenCardIsNull() {
        BigDecimal amount = BigDecimal.valueOf(100);
        Currency currency = Currency.USD;
        Instant date = Instant.now();

        NullPointerException exception = assertThrows(NullPointerException.class,
                () -> new Transaction(amount, currency, date, null));
        assertEquals("Card is required for a transaction.", exception.getMessage());
    }
}
