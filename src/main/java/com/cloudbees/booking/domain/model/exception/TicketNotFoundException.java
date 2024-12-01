package com.cloudbees.booking.domain.model.exception;

import static com.cloudbees.booking.domain.model.exception.EntityType.TICKET;

public class TicketNotFoundException extends NotFoundException {

  public TicketNotFoundException() {
    super(TICKET);
  }
}
