package com.sirkaue.creditcardpointsengine.domain.strategy;

import com.sirkaue.creditcardpointsengine.domain.CardType;

import java.math.BigDecimal;

public interface CardTypeStrategy {

    CardType getCardType();

    BigDecimal applyMultiplier(BigDecimal basePoints);
}
