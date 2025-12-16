package com.sirkaue.creditcardpointsengine.domain;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

public final class Transaction {

    private final String id = UUID.randomUUID().toString();
    private final BigDecimal amount;
    private final Currency currency;
    private final Instant date;
    private final Card card;
    private final StrategyType strategyType;

    public Transaction(BigDecimal amount, Currency currency, Instant date, Card card) {
        this(amount, currency, date, card, StrategyType.DEFAULT);
    }

    public Transaction(BigDecimal amount, Currency currency, Instant date, Card card, StrategyType strategyType) {
        this.amount = validateValue(amount);
        this.currency = validateCurrency(currency);
        this.date = validateDate(date);
        this.card = validateCard(card);
        this.strategyType = strategyType != null ? strategyType : StrategyType.DEFAULT;
    }

    private BigDecimal validateValue(BigDecimal amount) {
        if (amount == null) {
            throw new IllegalArgumentException("Transaction amount is required.");
        }
        if (amount.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("Transaction amount cannot be negative.");
        }
        return amount;
    }

    private Currency validateCurrency(Currency currency) {
        if (currency == null) {
            throw new IllegalArgumentException("Currency is required.");
        }
        return currency;
    }

    private Instant validateDate(Instant date) {
        if (date == null) {
            throw new IllegalArgumentException("Transaction timestamp is required.");
        }
        return date;
    }

    private Card validateCard(Card card) {
        if (card == null) {
            throw new IllegalArgumentException("Card is required for a transaction.");
        }
        return card;
    }

    public String getId() {
        return id;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public Currency getCurrency() {
        return currency;
    }

    public Instant getDate() {
        return date;
    }

    public Card getCard() {
        return card;
    }

    public StrategyType getStrategyType() {
        return strategyType;
    }
}
