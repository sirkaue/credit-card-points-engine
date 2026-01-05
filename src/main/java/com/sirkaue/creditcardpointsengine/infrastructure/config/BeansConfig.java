package com.sirkaue.creditcardpointsengine.infrastructure.config;

import com.sirkaue.creditcardpointsengine.application.gateways.CardTypeStrategyPort;
import com.sirkaue.creditcardpointsengine.application.gateways.PointsStrategyPort;
import com.sirkaue.creditcardpointsengine.domain.CardType;
import com.sirkaue.creditcardpointsengine.domain.StrategyType;
import com.sirkaue.creditcardpointsengine.domain.strategy.CardTypeStrategy;
import com.sirkaue.creditcardpointsengine.domain.strategy.PointsStrategy;
import com.sirkaue.creditcardpointsengine.domain.strategy.cardtype.GoldPointsStrategy;
import com.sirkaue.creditcardpointsengine.domain.strategy.cardtype.PlatinumPointsStrategy;
import com.sirkaue.creditcardpointsengine.domain.strategy.cardtype.SilverPointsStrategy;
import com.sirkaue.creditcardpointsengine.domain.strategy.points.CashbackPointsStrategy;
import com.sirkaue.creditcardpointsengine.domain.strategy.points.DefaultPointsStrategy;
import com.sirkaue.creditcardpointsengine.domain.strategy.points.TravelPointsStrategy;
import com.sirkaue.creditcardpointsengine.infrastructure.controllers.PointsResponseMapper;
import com.sirkaue.creditcardpointsengine.infrastructure.controllers.TransactionMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Configuration
public class BeansConfig {

    // Points Strategies
    @Bean
    public PointsStrategy defaultPointsStrategy() {
        return new DefaultPointsStrategy();
    }

    @Bean
    public PointsStrategy travelPointsStrategy() {
        return new TravelPointsStrategy();
    }

    @Bean
    public PointsStrategy cashbackPointsStrategy() {
        return new CashbackPointsStrategy();
    }

    @Bean
    public PointsStrategyPort pointsStrategyPort(List<PointsStrategy> strategies) {
        Map<StrategyType, PointsStrategy> strategyMap = strategies.stream()
                .collect(Collectors.toMap(
                        PointsStrategy::getType,
                        Function.identity()));

        return strategyType -> {
            PointsStrategy strategy = strategyMap.get(strategyType);

            if (strategy == null) {
                throw new RuntimeException("No PointsStrategy for " + strategyType);
            }

            return strategy;
        };
    }

    // Card Type Strategies
    @Bean
    public CardTypeStrategy silverPointsStrategy() {
        return new SilverPointsStrategy();
    }

    @Bean
    public CardTypeStrategy goldPointsStrategy() {
        return new GoldPointsStrategy();
    }

    @Bean
    public CardTypeStrategy platinumPointsStrategy() {
        return new PlatinumPointsStrategy();
    }

    @Bean
    public CardTypeStrategyPort cardTypeStrategyPort(List<CardTypeStrategy> strategies) {
        Map<CardType, CardTypeStrategy> strategyMap = strategies.stream()
                .collect(Collectors.toMap(
                        CardTypeStrategy::getCardType,
                        Function.identity()));

        return cardType -> {
            CardTypeStrategy strategy = strategyMap.get(cardType);

            if (strategy == null) {
                throw new RuntimeException("No CardTypeStrategy for " + cardType);
            }

            return strategy;
        };
    }

    // Mappers
    @Bean
    public TransactionMapper transactionMapper() {
        return new TransactionMapper();
    }

    @Bean
    public PointsResponseMapper pointsResponseMapper() {
        return new PointsResponseMapper();
    }
}
