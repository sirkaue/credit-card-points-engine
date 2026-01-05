package com.sirkaue.creditcardpointsengine.infrastructure.controllers;

import com.sirkaue.creditcardpointsengine.application.usecase.PointsCalculationResult;
import com.sirkaue.creditcardpointsengine.domain.Currency;
import com.sirkaue.creditcardpointsengine.domain.Transaction;
import com.sirkaue.creditcardpointsengine.infrastructure.controllers.dtos.response.PointsResponse;

import java.math.RoundingMode;

public final class PointsResponseMapper {

    private static final int USD_DECIMAL_PLACES = 2;

    public PointsResponse toResponse(Transaction tx, PointsCalculationResult result) {
        return new PointsResponse(
                tx.getId(),
                tx.getCard().type().name(),
                tx.getStrategyType().name(),
                Currency.USD.name(),
                result.amountInUsd().setScale(USD_DECIMAL_PLACES, RoundingMode.HALF_UP).toString(),
                result.points().longValue()
        );
    }
}
