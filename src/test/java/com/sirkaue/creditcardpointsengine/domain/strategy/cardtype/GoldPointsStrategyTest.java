package com.sirkaue.creditcardpointsengine.domain.strategy.cardtype;

import com.sirkaue.creditcardpointsengine.domain.CardType;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;

class GoldPointsStrategyTest {

    @Test
    void shouldReturnGoldCardType() {
        GoldPointsStrategy strategy = new GoldPointsStrategy();

        CardType cardType = strategy.getCardType();

        assertEquals(CardType.GOLD, cardType);
    }

    @Test
    void shouldApplyMultiplierAndRoundHalfUpWithTwoDecimalPlaces() {
        GoldPointsStrategy strategy = new GoldPointsStrategy();
        BigDecimal basePoints = new BigDecimal("10");

        BigDecimal result = strategy.applyMultiplier(basePoints);

        assertEquals(new BigDecimal("15.00"), result);
    }

    @Test
    void shouldRoundCorrectlyWhenResultHasMoreThanTwoDecimalPlaces() {
        GoldPointsStrategy strategy = new GoldPointsStrategy();
        BigDecimal basePoints = new BigDecimal("10.333");

        BigDecimal result = strategy.applyMultiplier(basePoints);

        assertEquals(new BigDecimal("15.50"), result);
    }
}
