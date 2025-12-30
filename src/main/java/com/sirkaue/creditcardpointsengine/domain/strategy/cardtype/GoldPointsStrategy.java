package com.sirkaue.creditcardpointsengine.domain.strategy.cardtype;

import com.sirkaue.creditcardpointsengine.domain.CardType;
import com.sirkaue.creditcardpointsengine.domain.strategy.CardTypeStrategy;

import java.math.BigDecimal;
import java.math.RoundingMode;

public final class GoldPointsStrategy implements CardTypeStrategy {

    private static final BigDecimal MULTIPLIER = new BigDecimal("1.5");

    @Override
    public CardType getCardType() {
        return CardType.GOLD;
    }

    @Override
    public BigDecimal applyMultiplier(BigDecimal basePoints) {
        return basePoints.multiply(MULTIPLIER)
                .setScale(2, RoundingMode.HALF_UP);
    }
}
