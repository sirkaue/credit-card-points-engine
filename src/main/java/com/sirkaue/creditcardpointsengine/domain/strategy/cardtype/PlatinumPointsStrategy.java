package com.sirkaue.creditcardpointsengine.domain.strategy.cardtype;

import com.sirkaue.creditcardpointsengine.domain.CardType;
import com.sirkaue.creditcardpointsengine.domain.strategy.CardTypeStrategy;

import java.math.BigDecimal;
import java.math.RoundingMode;

public final class PlatinumPointsStrategy implements CardTypeStrategy {

    private static final BigDecimal MULTIPLIER = new BigDecimal("2.0");
    private static final BigDecimal BONUS = BigDecimal.TEN;

    @Override
    public CardType getCardType() {
        return CardType.PLATINUM;
    }

    @Override
    public BigDecimal applyMultiplier(BigDecimal basePoints) {
        BigDecimal points = basePoints.multiply(MULTIPLIER);
        // Platinum tem bônus fixo de 10 points por transação
        points = points.add(BONUS);
        return points.setScale(2, RoundingMode.HALF_UP);
    }
}
