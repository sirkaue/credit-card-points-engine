package com.sirkaue.creditcardpointsengine.domain.strategy.cardtype;

import com.sirkaue.creditcardpointsengine.domain.CardType;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;

class SilverPointsStrategyTest {

    @Test
    void shouldReturnSilverCardType() {
        SilverPointsStrategy strategy = new SilverPointsStrategy();

        CardType cardType = strategy.getCardType();

        assertEquals(CardType.SILVER, cardType);
    }

    @Test
    void shouldKeepSameValueButScaleToTwoDecimalPlaces() {
        SilverPointsStrategy strategy = new SilverPointsStrategy();
        BigDecimal basePoints = new BigDecimal("10");

        BigDecimal result = strategy.applyMultiplier(basePoints);

        assertEquals(new BigDecimal("10.00"), result);
    }

    @Test
    void shouldRoundHalfUpWhenInputHasMoreThanTwoDecimalPlaces() {
        SilverPointsStrategy strategy = new SilverPointsStrategy();
        BigDecimal basePoints = new BigDecimal("10.335");

        BigDecimal result = strategy.applyMultiplier(basePoints);

        assertEquals(new BigDecimal("10.34"), result);
    }
}
