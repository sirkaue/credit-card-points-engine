package com.sirkaue.creditcardpointsengine.infrastructure.controllers.dtos.request;

public record TransactionRequest(
        String cardId,
        String cardType,
        String owner,
        String currency,
        String amount,
        String timestamp,
        String strategyType
) {}
