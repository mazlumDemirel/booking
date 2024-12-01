package com.cloudbees.booking.infra.repository;

import com.cloudbees.booking.domain.dao.SeatRepositoryPort;
import com.cloudbees.booking.domain.model.entity.Seat;
import com.cloudbees.booking.domain.model.exception.SeatNotFoundException;
import com.cloudbees.booking.infra.dao.sql.SeatRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class SeatRepositoryAdapter implements SeatRepositoryPort {

  private final SeatRepository seatRepository;

  @Override
  public Seat getSeat(Seat seat) {
    return seatRepository
        .findBySectionAndNumberAndLocation(seat.getSection(), seat.getNumber(),
            seat.getLocation())
        .orElseThrow(SeatNotFoundException::new);
  }
}
