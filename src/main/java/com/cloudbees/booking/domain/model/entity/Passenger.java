package com.cloudbees.booking.domain.model.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@Entity
@NoArgsConstructor(force = true)
public final class Passenger {

  @Id
  private final String email;
  private final String name;
  private final String surname;

  public Passenger(String email, String name, String surname) {
    this.email = email;
    this.name = name;
    this.surname = surname;
  }
}
