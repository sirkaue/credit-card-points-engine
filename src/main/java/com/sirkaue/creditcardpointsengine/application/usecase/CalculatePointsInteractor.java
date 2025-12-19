package com.sirkaue.creditcardpointsengine.application.usecase;

import com.sirkaue.creditcardpointsengine.application.gateways.CardTypeStrategyPort;
import com.sirkaue.creditcardpointsengine.application.gateways.ExchangeRatePort;
import com.sirkaue.creditcardpointsengine.application.gateways.PointsStrategyPort;
import com.sirkaue.creditcardpointsengine.domain.Currency;
import com.sirkaue.creditcardpointsengine.domain.Transaction;
import com.sirkaue.creditcardpointsengine.domain.strategy.CardTypeStrategy;
import com.sirkaue.creditcardpointsengine.domain.strategy.PointsStrategy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.math.RoundingMode;

public final class CalculatePointsInteractor implements CalculatePointsUseCase {

    private static final Logger log = LoggerFactory.getLogger(CalculatePointsInteractor.class);

    private final ExchangeRatePort exchangeRatePort;
    private final CardTypeStrategyPort cardTypeStrategyPort;
    private final PointsStrategyPort pointsStrategyPort;

    public CalculatePointsInteractor(ExchangeRatePort exchangeRatePort, CardTypeStrategyPort cardTypeStrategyPort,
                                     PointsStrategyPort pointsStrategyPort) {
        this.exchangeRatePort = exchangeRatePort;
        this.cardTypeStrategyPort = cardTypeStrategyPort;
        this.pointsStrategyPort = pointsStrategyPort;
    }

    @Override
    public PointsCalculationResult execute(Transaction tx) {
        log.info("Starting points calculation for transaction {}", tx.getId());

        Transaction txInUsd = convertToUsd(tx);

        PointsStrategy pointsStrategy = pointsStrategyPort.getStrategy(txInUsd.getStrategyType());
        BigDecimal basePoints = pointsStrategy.calculatePoints(txInUsd);

        CardTypeStrategy cardStrategy = cardTypeStrategyPort.getStrategy(txInUsd.getCard().getType());
        BigDecimal finalPoints = cardStrategy.applyMultiplier(basePoints);

        log.info("Transaction {} calculated: base={}, final={}", tx.getId(), basePoints, finalPoints);

        return new PointsCalculationResult(txInUsd.getAmount(), finalPoints);
    }

    private Transaction convertToUsd(Transaction tx) {
        if (tx.getCurrency() == Currency.USD) {
            return tx;
        }

        BigDecimal rate = exchangeRatePort.getBrlPerUsdRate(tx.getDate());
        BigDecimal usd = tx.getAmount().divide(rate, 8, RoundingMode.HALF_UP);

        return new Transaction(
                usd,
                Currency.USD,
                tx.getDate(),
                tx.getCard(),
                tx.getStrategyType()
        );
    }
}
