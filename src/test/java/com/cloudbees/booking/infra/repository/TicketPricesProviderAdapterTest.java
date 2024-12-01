package com.cloudbees.booking.infra.repository;

import static org.assertj.core.api.Assertions.assertThat;

import com.cloudbees.booking.BaseSpringTestWithData;
import com.cloudbees.booking.domain.dao.TicketPricesProviderPort;
import com.cloudbees.booking.domain.model.Station;
import java.math.BigDecimal;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

class TicketPricesProviderAdapterTest extends BaseSpringTestWithData {

  @Autowired
  private TicketPricesProviderPort ticketPricesProviderPort;

  @Test
  void getPrice_withValidDestinations_shouldPass() {
    //given
    Station from = Station.London;
    Station to = Station.France;
    BigDecimal expectedPrice = BigDecimal.valueOf(2000, 2);

    //when
    BigDecimal foundPrice = ticketPricesProviderPort.getPrice(from, to);

    //then
    assertThat(expectedPrice).isEqualTo(foundPrice);
  }
}