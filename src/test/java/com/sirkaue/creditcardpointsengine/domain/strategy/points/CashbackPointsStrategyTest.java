package com.sirkaue.creditcardpointsengine.domain.strategy.points;

import com.sirkaue.creditcardpointsengine.domain.StrategyType;
import com.sirkaue.creditcardpointsengine.domain.Transaction;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class CashbackPointsStrategyTest {

    @Test
    void shouldReturnCashbackStrategyType() {
        CashbackPointsStrategy strategy = new CashbackPointsStrategy();

        StrategyType result = strategy.getType();

        assertEquals(StrategyType.CASHBACK, result);
    }

    @Test
    void shouldCalculateHalfOfTransactionAmountAsPoints() {
        CashbackPointsStrategy strategy = new CashbackPointsStrategy();

        Transaction transaction = mock(Transaction.class);
        when(transaction.getAmount()).thenReturn(new BigDecimal("100.00"));

        BigDecimal points = strategy.calculatePoints(transaction);

        assertEquals(0, points.compareTo(new BigDecimal("50.00")));
    }
}