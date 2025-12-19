package com.sirkaue.creditcardpointsengine.application.gateways;

import com.sirkaue.creditcardpointsengine.domain.StrategyType;
import com.sirkaue.creditcardpointsengine.domain.strategy.PointsStrategy;

public interface PointsStrategyPort {
    
    PointsStrategy getStrategy(StrategyType strategyType);
}
