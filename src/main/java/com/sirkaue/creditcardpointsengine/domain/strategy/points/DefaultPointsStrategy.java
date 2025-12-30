package com.sirkaue.creditcardpointsengine.domain.strategy.points;

import com.sirkaue.creditcardpointsengine.domain.StrategyType;
import com.sirkaue.creditcardpointsengine.domain.Transaction;
import com.sirkaue.creditcardpointsengine.domain.strategy.PointsStrategy;

import java.math.BigDecimal;

public final class DefaultPointsStrategy implements PointsStrategy {

    @Override
    public StrategyType getType() {
        return StrategyType.DEFAULT;
    }

    @Override
    public BigDecimal calculatePoints(Transaction tx) {
        return tx.getAmount();
    }
}
