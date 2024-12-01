package com.cloudbees.booking.infra.repository;

import com.cloudbees.booking.domain.model.Station;
import com.cloudbees.booking.domain.model.exception.DirectionNotFoundException;
import com.cloudbees.booking.domain.dao.TicketPricesProviderPort;
import com.cloudbees.booking.infra.dao.sql.DirectionRepository;
import java.math.BigDecimal;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TicketPricesProviderAdapter implements TicketPricesProviderPort {

  private final DirectionRepository directionRepository;

  @Override
  public BigDecimal getPrice(Station from, Station to) {
    return directionRepository.findByDepartureAndDestination(from, to)
        .orElseThrow(DirectionNotFoundException::new).getPrice();
  }
}
