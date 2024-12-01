package com.cloudbees.booking.domain.model.exception;

import static com.cloudbees.booking.domain.model.exception.EntityType.SEAT;

public class SeatNotFoundException extends NotFoundException {

  public SeatNotFoundException() {
    super(SEAT);
  }
}
