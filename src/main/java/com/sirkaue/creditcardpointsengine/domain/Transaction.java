package com.sirkaue.creditcardpointsengine.domain;

import com.sirkaue.creditcardpointsengine.domain.exception.InvalidTransactionException;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.Objects;
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
        this.amount = validateAmount(amount);
        this.currency = Objects.requireNonNull(currency, "Currency is required.");
        this.date = Objects.requireNonNull(date, "Transaction timestamp is required.");
        this.card = Objects.requireNonNull(card, "Card is required for a transaction.");
        this.strategyType = Objects.requireNonNullElse(strategyType, StrategyType.DEFAULT);
    }

    private BigDecimal validateAmount(BigDecimal amount) {
        Objects.requireNonNull(amount, "Transaction amount is required.");

        if (amount.compareTo(BigDecimal.ZERO) < 0) {
            throw new InvalidTransactionException("Transaction amount cannot be negative.");
        }

        return amount;
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
