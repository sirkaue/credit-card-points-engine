package com.sirkaue.creditcardpointsengine.domain.strategy;

import com.sirkaue.creditcardpointsengine.domain.StrategyType;
import com.sirkaue.creditcardpointsengine.domain.Transaction;

import java.math.BigDecimal;

public interface PointsStrategy {

    StrategyType getType();

    BigDecimal calculatePoints(Transaction transactionInUsd);
}
