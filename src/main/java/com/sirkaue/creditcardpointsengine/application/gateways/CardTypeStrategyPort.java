package com.sirkaue.creditcardpointsengine.application.gateways;

import com.sirkaue.creditcardpointsengine.domain.CardType;
import com.sirkaue.creditcardpointsengine.domain.strategy.CardTypeStrategy;

public interface CardTypeStrategyPort {
    
    CardTypeStrategy getStrategy(CardType cardType);
}
