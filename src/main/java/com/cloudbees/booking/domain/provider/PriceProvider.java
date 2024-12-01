package com.cloudbees.booking.domain.provider;

import com.cloudbees.booking.domain.model.Station;
import java.math.BigDecimal;

public interface PriceProvider {

  BigDecimal getPrice(Station from, Station to);
}
