package com.cloudbees.booking.stub;

import com.cloudbees.booking.domain.model.SeatLocation;
import com.cloudbees.booking.domain.model.Section;
import com.cloudbees.booking.domain.model.Station;
import com.cloudbees.booking.domain.model.entity.Direction;
import com.cloudbees.booking.domain.model.entity.Passenger;
import com.cloudbees.booking.domain.model.entity.Seat;
import com.cloudbees.booking.domain.model.entity.Ticket;
import java.math.BigDecimal;

public class TicketStub {

  public static Ticket createTicket() {
    return createTicket(BigDecimal.valueOf(2000, 2));
  }

  public static Ticket createTicket(BigDecimal amount) {
    return createTicket(Section.A, SeatLocation.UPPER_BED, 1, amount);
  }

  public static Ticket createTicket(Section section, SeatLocation seatLocation,
      int number, BigDecimal amount) {
    return Ticket.builder()
        .passenger(Passenger.builder().name("test-firstname")
            .surname("test-surname")
            .email("test@test.com")
            .build())
        .seat(Seat.builder()
            .id(1L)
            .section(section)
            .number(number)
            .location(seatLocation)
            .build())
        .direction(Direction
            .builder()
            .id(1L)
            .departure(Station.London)
            .destination(Station.France)
            .price(BigDecimal.valueOf(2000, 2))
            .build())
        .amount(amount)
        .id(1L)
        .build();
  }

  public static Ticket createTicket(Direction direction) {
    return Ticket.builder()
        .passenger(Passenger.builder().name("test-firstname")
            .surname("test-surname")
            .email("test@test.com")
            .build())
        .seat(Seat.builder()
            .id(1L)
            .section(Section.A)
            .number(1)
            .location(SeatLocation.UPPER_BED)
            .build())
        .direction(direction)
        .amount(BigDecimal.valueOf(2000, 2))
        .id(1L)
        .build();
  }

  public static Ticket createTicket(String userEmail) {
    return Ticket.builder()
        .passenger(Passenger.builder().name("test-firstname")
            .surname("test-surname")
            .email(userEmail)
            .build())
        .seat(Seat.builder()
            .id(1L)
            .section(Section.A)
            .number(1)
            .location(SeatLocation.UPPER_BED)
            .build())
        .direction(Direction
            .builder()
            .id(1L)
            .departure(Station.London)
            .destination(Station.France)
            .price(BigDecimal.valueOf(2000, 2))
            .build())
        .amount(BigDecimal.valueOf(2000, 2))
        .id(1L)
        .build();
  }
}
