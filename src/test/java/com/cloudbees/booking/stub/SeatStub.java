package com.cloudbees.booking.stub;

import com.cloudbees.booking.domain.model.SeatLocation;
import com.cloudbees.booking.domain.model.Section;
import com.cloudbees.booking.domain.model.entity.Seat;

public class SeatStub {
  public static Seat createSeat() {
    return Seat.builder().section(Section.B).number(25).id(100L).location(SeatLocation.UPPER_BED)
        .build();
  }
}
