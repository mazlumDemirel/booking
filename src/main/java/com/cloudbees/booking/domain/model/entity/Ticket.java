package com.cloudbees.booking.domain.model.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import java.math.BigDecimal;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Entity
@Data
@NoArgsConstructor(force = true)
public final class Ticket {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private final Long id;
  private final BigDecimal amount;
  @JoinColumn(name = "email", referencedColumnName = "email")
  @ManyToOne
  private final Passenger passenger;
  @JoinColumn(name = "seatId", referencedColumnName = "id")
  @ManyToOne
  private Seat seat;
  @JoinColumn(name = "directionId", referencedColumnName = "id")
  @ManyToOne
  private final Direction direction;

  public Ticket(
      Long id,
      BigDecimal amount,
      Passenger passenger,
      Seat seat,
      Direction direction
  ) {
    this.id = id;
    this.amount = amount;
    this.passenger = passenger;
    this.seat = seat;
    this.direction = direction;
  }

  public void replaceSeat(Seat newSeat) {
    this.seat = newSeat;
  }
}