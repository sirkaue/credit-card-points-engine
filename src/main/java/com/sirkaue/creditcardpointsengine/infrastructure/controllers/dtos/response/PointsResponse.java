package com.sirkaue.creditcardpointsengine.infrastructure.controllers.dtos.response;

public record PointsResponse(
        String transactionId,
        String cardType,
        String strategyType,
        String currency,
        String amountUsd,
        long points
) {}
