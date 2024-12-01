package com.cloudbees.booking.domain.model.exception;

import static com.cloudbees.booking.domain.model.exception.EntityType.DIRECTION;

public class DirectionNotFoundException extends NotFoundException {

  public DirectionNotFoundException() {
    super(DIRECTION);
  }
}
