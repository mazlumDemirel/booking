package com.cloudbees.booking.domain.model.entity;

import com.cloudbees.booking.domain.model.SeatLocation;
import com.cloudbees.booking.domain.model.Section;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@Entity
@NoArgsConstructor(force = true)
public final class Seat {

  @Id
  private final Long id;
  @Enumerated(EnumType.STRING)
  private final Section section;
  private final int number;
  @Enumerated(EnumType.STRING)
  private final SeatLocation location;

  public Seat(Long id, Section section, int number, SeatLocation location) {
    this.id = id;
    this.section = section;
    this.number = number;
    this.location = location;
  }
}
