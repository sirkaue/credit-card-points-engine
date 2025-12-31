package com.sirkaue.creditcardpointsengine.domain.strategy.points;

import com.sirkaue.creditcardpointsengine.domain.StrategyType;
import com.sirkaue.creditcardpointsengine.domain.Transaction;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class DefaultPointsStrategyTest {

    @Test
    void shouldReturnDefaultStrategyType() {
        DefaultPointsStrategy strategy = new DefaultPointsStrategy();

        StrategyType result = strategy.getType();

        assertEquals(StrategyType.DEFAULT, result);
    }

    @Test
    void shouldReturnTransactionAmountAsPoints() {
        DefaultPointsStrategy strategy = new DefaultPointsStrategy();

        Transaction transaction = mock(Transaction.class);
        when(transaction.getAmount()).thenReturn(new BigDecimal("123.45"));

        BigDecimal points = strategy.calculatePoints(transaction);

        assertEquals(0, points.compareTo(new BigDecimal("123.45")));
    }
}
