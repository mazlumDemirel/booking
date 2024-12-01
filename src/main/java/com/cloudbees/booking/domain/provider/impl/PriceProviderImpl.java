package com.cloudbees.booking.domain.provider.impl;

import com.cloudbees.booking.domain.model.Station;
import com.cloudbees.booking.domain.provider.PriceProvider;
import com.cloudbees.booking.domain.dao.TicketPricesProviderPort;
import java.math.BigDecimal;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PriceProviderImpl implements PriceProvider {

  private final TicketPricesProviderPort providerPort;

  @Override
  public BigDecimal getPrice(Station from, Station to) {
    return providerPort.getPrice(from, to);
  }
}
