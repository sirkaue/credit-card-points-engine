package com.sirkaue.creditcardpointsengine.domain.strategy.points;

import com.sirkaue.creditcardpointsengine.domain.StrategyType;
import com.sirkaue.creditcardpointsengine.domain.Transaction;
import com.sirkaue.creditcardpointsengine.domain.strategy.PointsStrategy;

import java.math.BigDecimal;

public final class TravelPointsStrategy implements PointsStrategy {

    @Override
    public StrategyType getType() {
        return StrategyType.TRAVEL;
    }

    @Override
    public BigDecimal calculatePoints(Transaction tx) {
        return tx.getAmount().multiply(BigDecimal.valueOf(2));
    }
}
