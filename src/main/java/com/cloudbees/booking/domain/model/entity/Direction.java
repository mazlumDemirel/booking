package com.cloudbees.booking.domain.model.entity;

import com.cloudbees.booking.domain.model.Station;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import java.math.BigDecimal;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@Entity
@NoArgsConstructor(force = true)
public final class Direction {

  @Id
  private final Long id;
  @Enumerated(EnumType.STRING)
  private final Station departure;
  @Enumerated(EnumType.STRING)
  private final Station destination;
  private final BigDecimal price;

  public Direction(Long id, Station departure, Station destination, BigDecimal price) {
    this.id = id;
    this.departure = departure;
    this.destination = destination;
    this.price = price;
  }
}
