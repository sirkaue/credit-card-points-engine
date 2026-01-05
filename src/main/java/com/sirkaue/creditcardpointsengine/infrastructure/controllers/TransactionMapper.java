package com.sirkaue.creditcardpointsengine.infrastructure.controllers;

import com.sirkaue.creditcardpointsengine.domain.*;
import com.sirkaue.creditcardpointsengine.infrastructure.controllers.dtos.request.TransactionRequest;

import java.math.BigDecimal;
import java.time.Instant;

public final class TransactionMapper {

    public Transaction toTransaction(TransactionRequest request) {
        CardType cardType = CardType.valueOf(request.cardType().toUpperCase());
        Card card = new Card(request.cardId(), cardType, request.owner());

        StrategyType strategyType = request.strategyType() != null
                ? StrategyType.valueOf(request.strategyType().toUpperCase())
                : StrategyType.DEFAULT;

        return new Transaction(
                new BigDecimal(request.amount()),
                Currency.valueOf(request.currency().toUpperCase()),
                Instant.parse(request.timestamp()),
                card,
                strategyType
        );
    }
}
