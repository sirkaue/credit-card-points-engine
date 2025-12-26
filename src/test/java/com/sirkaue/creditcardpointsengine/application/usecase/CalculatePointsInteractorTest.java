package com.sirkaue.creditcardpointsengine.application.usecase;

import com.sirkaue.creditcardpointsengine.application.gateways.CardTypeStrategyPort;
import com.sirkaue.creditcardpointsengine.application.gateways.ExchangeRatePort;
import com.sirkaue.creditcardpointsengine.application.gateways.PointsStrategyPort;
import com.sirkaue.creditcardpointsengine.domain.Card;
import com.sirkaue.creditcardpointsengine.domain.CardType;
import com.sirkaue.creditcardpointsengine.domain.Currency;
import com.sirkaue.creditcardpointsengine.domain.StrategyType;
import com.sirkaue.creditcardpointsengine.domain.Transaction;
import com.sirkaue.creditcardpointsengine.domain.strategy.CardTypeStrategy;
import com.sirkaue.creditcardpointsengine.domain.strategy.PointsStrategy;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.Instant;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CalculatePointsInteractorTest {

    @Mock
    ExchangeRatePort exchangeRatePort;

    @Mock
    CardTypeStrategyPort cardTypeStrategyPort;

    @Mock
    PointsStrategyPort pointsStrategyPort;

    @InjectMocks
    CalculatePointsInteractor interactor;

    @Test
    void shouldCalculatePointsForUsdTransaction() {
        // Arrange
        Transaction tx = new Transaction(
                new BigDecimal("100"),
                Currency.USD,
                Instant.now(),
                new Card("card-001", CardType.PLATINUM, "Alice"),
                StrategyType.TRAVEL
        );

        PointsStrategy pointsStrategy = mock(PointsStrategy.class);
        when(pointsStrategyPort.getStrategy(StrategyType.TRAVEL)).thenReturn(pointsStrategy);
        when(pointsStrategy.calculatePoints(tx)).thenReturn(new BigDecimal("50"));

        CardTypeStrategy cardStrategy = mock(CardTypeStrategy.class);
        when(cardTypeStrategyPort.getStrategy(CardType.PLATINUM)).thenReturn(cardStrategy);
        when(cardStrategy.applyMultiplier(new BigDecimal("50"))).thenReturn(new BigDecimal("75"));

        // Act
        PointsCalculationResult result = interactor.execute(tx);

        // Assert
        assertEquals(new BigDecimal("100"), result.amountInUsd());
        assertEquals(new BigDecimal("75"), result.points());

        verify(pointsStrategyPort).getStrategy(StrategyType.TRAVEL);
        verify(pointsStrategy).calculatePoints(tx);
        verify(cardTypeStrategyPort).getStrategy(CardType.PLATINUM);
        verify(cardStrategy).applyMultiplier(new BigDecimal("50"));
        verifyNoMoreInteractions(pointsStrategyPort, pointsStrategy, cardTypeStrategyPort, cardStrategy, exchangeRatePort);
    }

    @Test
    void shouldConvertBrlToUsdAndCalculatePoints() {
        // Arrange
        Transaction tx = new Transaction(
                new BigDecimal("500"),
                Currency.BRL,
                Instant.now(),
                new Card("card-002", CardType.GOLD, "Bob"),
                StrategyType.TRAVEL
        );

        when(exchangeRatePort.getBrlPerUsdRate(tx.getDate())).thenReturn(new BigDecimal("5"));

        PointsStrategy pointsStrategy = mock(PointsStrategy.class);
        when(pointsStrategyPort.getStrategy(StrategyType.TRAVEL)).thenReturn(pointsStrategy);
        when(pointsStrategy.calculatePoints(any(Transaction.class))).thenReturn(new BigDecimal("20"));

        CardTypeStrategy cardStrategy = mock(CardTypeStrategy.class);
        when(cardTypeStrategyPort.getStrategy(CardType.GOLD)).thenReturn(cardStrategy);
        when(cardStrategy.applyMultiplier(new BigDecimal("20"))).thenReturn(new BigDecimal("30"));

        // Act
        PointsCalculationResult result = interactor.execute(tx);

        // Assert
        assertTrue(result.amountInUsd().compareTo(new BigDecimal("100")) == 0);
        assertEquals(new BigDecimal("30"), result.points());
        verify(exchangeRatePort).getBrlPerUsdRate(tx.getDate());
    }
}
