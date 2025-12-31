package com.sirkaue.creditcardpointsengine.domain.strategy.points;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;

import com.sirkaue.creditcardpointsengine.domain.StrategyType;
import com.sirkaue.creditcardpointsengine.domain.Transaction;
import org.junit.jupiter.api.Test;

class TravelPointsStrategyTest {

    @Test
    void shouldReturnTravelStrategyType() {
        TravelPointsStrategy strategy = new TravelPointsStrategy();

        StrategyType result = strategy.getType();

        assertEquals(StrategyType.TRAVEL, result);
    }

    @Test
    void shouldDoubleTransactionAmountAsPoints() {
        TravelPointsStrategy strategy = new TravelPointsStrategy();

        Transaction transaction = mock(Transaction.class);
        when(transaction.getAmount()).thenReturn(new BigDecimal("75.50"));

        BigDecimal points = strategy.calculatePoints(transaction);

        assertEquals(0, points.compareTo(new BigDecimal("151.00")));
    }
}
