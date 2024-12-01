package com.cloudbees.booking.domain.dao;

import com.cloudbees.booking.domain.model.Station;
import java.math.BigDecimal;

public interface TicketPricesProviderPort {

  BigDecimal getPrice(Station from, Station to);
}
