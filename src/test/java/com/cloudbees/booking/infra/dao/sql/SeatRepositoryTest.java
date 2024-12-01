package com.cloudbees.booking.infra.dao.sql;

import static org.assertj.core.api.Assertions.assertThat;

import com.cloudbees.booking.domain.model.SeatLocation;
import com.cloudbees.booking.domain.model.Section;
import com.cloudbees.booking.domain.model.entity.Seat;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
class SeatRepositoryTest {

  @Autowired
  private SeatRepository seatRepository;

  @Test
  void shouldFindSeatPersistentDirection_withGivenSeatNumberSectionAndLocation() {
    //given
    Seat seat = Seat.builder()
        .id(20L)
        .section(Section.B)
        .number(11)
        .location(SeatLocation.UPPER_BED)
        .build();

    Seat savedSeat = seatRepository.saveAndFlush(seat);

    //when
    Seat foundSeat = seatRepository.findBySectionAndNumberAndLocation(
        seat.getSection(), seat.getNumber(), seat.getLocation()).orElseThrow();

    //then
    assertThat(foundSeat).usingRecursiveComparison()
        .isEqualTo(savedSeat)
        .isEqualTo(seat);
  }
}