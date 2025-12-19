package com.sirkaue.creditcardpointsengine.application.usecase;

import com.sirkaue.creditcardpointsengine.domain.Transaction;

public interface CalculatePointsUseCase {

    PointsCalculationResult execute(Transaction tx);
}
