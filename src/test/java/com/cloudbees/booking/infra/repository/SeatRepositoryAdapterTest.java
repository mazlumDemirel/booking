package com.cloudbees.booking.infra.repository;

import static org.assertj.core.api.Assertions.assertThat;

import com.cloudbees.booking.BaseSpringTestWithData;
import com.cloudbees.booking.domain.dao.SeatRepositoryPort;
import com.cloudbees.booking.domain.model.entity.Seat;
import com.cloudbees.booking.stub.SeatStub;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

class SeatRepositoryAdapterTest extends BaseSpringTestWithData {

  @Autowired
  private SeatRepositoryPort seatRepositoryPort;

  @Test
  void getSeat_withValidDestinations_shouldPass() {
    //given
    Seat seat = SeatStub.createSeat();

    //when
    Seat foundSeat = seatRepositoryPort.getSeat(seat);

    //then
    assertThat(seat)
        .usingRecursiveComparison()
        .ignoringFields("id")
        .isEqualTo(foundSeat);
  }
}