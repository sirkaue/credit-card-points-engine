package com.sirkaue.creditcardpointsengine.application.gateways;

import java.math.BigDecimal;
import java.time.Instant;

public interface ExchangeRatePort {

    BigDecimal getBrlPerUsdRate(Instant timestamp);
}
