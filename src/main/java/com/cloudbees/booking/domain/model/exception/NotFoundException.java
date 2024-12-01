package com.cloudbees.booking.domain.model.exception;

public class NotFoundException extends RuntimeException {

  public NotFoundException(EntityType entityType) {
    super(entityType.toString());
  }
}
