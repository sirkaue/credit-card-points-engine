package com.sirkaue.creditcardpointsengine.domain.strategy.cardtype;

import com.sirkaue.creditcardpointsengine.domain.CardType;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

class PlatinumPointsStrategyTest {

    @Test
    void shouldReturnPlatinumCardType() {
        PlatinumPointsStrategy strategy = new PlatinumPointsStrategy();

        CardType cardType = strategy.getCardType();

        assertEquals(CardType.PLATINUM, cardType);
    }

    @Test
    void shouldApplyMultiplierAndBonusCorrectly() {
        PlatinumPointsStrategy strategy = new PlatinumPointsStrategy();
        BigDecimal basePoints = new BigDecimal("100");

        BigDecimal result = strategy.applyMultiplier(basePoints);

        // 100 * 2.0 = 200 + 10 = 210.00
        assertEquals(new BigDecimal("210.00"), result);
    }

    @Test
    void shouldApplyMultiplierBonusAndRoundHalfUp() {
        PlatinumPointsStrategy strategy = new PlatinumPointsStrategy();
        BigDecimal basePoints = new BigDecimal("33.335");

        BigDecimal result = strategy.applyMultiplier(basePoints);

        // 33.335 * 2.0 = 66.67 + 10 = 76.67
        assertEquals(new BigDecimal("76.67"), result);
    }

    @Test
    void shouldAlwaysReturnScaleTwo() {
        PlatinumPointsStrategy strategy = new PlatinumPointsStrategy();
        BigDecimal basePoints = new BigDecimal("1");

        BigDecimal result = strategy.applyMultiplier(basePoints);

        assertEquals(2, result.scale());
    }
}