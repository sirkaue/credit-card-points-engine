package com.sirkaue.creditcardpointsengine.infrastructure.config;

import com.sirkaue.creditcardpointsengine.application.gateways.CardTypeStrategyPort;
import com.sirkaue.creditcardpointsengine.application.gateways.PointsStrategyPort;
import com.sirkaue.creditcardpointsengine.application.usecase.CalculatePointsInteractor;
import com.sirkaue.creditcardpointsengine.application.usecase.CalculatePointsUseCase;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class UseCaseConfig {

    @Bean
    public CalculatePointsUseCase calculatePointsUseCase(
            CardTypeStrategyPort cardTypeStrategyPort,
            PointsStrategyPort pointsStrategyPort) {
        return new CalculatePointsInteractor(cardTypeStrategyPort, pointsStrategyPort);
    }
}
