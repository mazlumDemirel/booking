package com.cloudbees.booking.domain.dao;

import com.cloudbees.booking.domain.model.entity.Seat;

public interface SeatRepositoryPort {

  Seat getSeat(Seat seat);
}
