package com.cloudbees.booking.stub;

import com.cloudbees.booking.domain.model.SeatLocation;
import com.cloudbees.booking.domain.model.Section;
import com.cloudbees.booking.domain.model.Station;
import com.cloudbees.booking.domain.model.dto.request.TicketRequest;

public class TicketRequestStub {

  public static TicketRequest createTicketRequest() {
    return createTicketRequest(Section.A, SeatLocation.MIDDLE, 1);
  }

  public static TicketRequest createTicketRequest(Section section, SeatLocation seatLocation,
      int number) {
    return TicketRequest.builder()
        .name("test-firstname")
        .surname("test-surname")
        .email("test@test.com")
        .section(section)
        .number(number)
        .location(seatLocation)
        .departure(Station.London)
        .destination(Station.France)
        .build();
  }

  public static TicketRequest createInsufficientTicketRequest() {
    return TicketRequest.builder()
        .number(-1)
        .build();
  }
}
