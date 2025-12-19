package com.sirkaue.creditcardpointsengine.application.usecase;

import java.math.BigDecimal;

public record PointsCalculationResult(
        BigDecimal amountInUsd,
        BigDecimal points
) {
}
