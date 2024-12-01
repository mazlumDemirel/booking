package com.cloudbees.booking.application.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import com.cloudbees.booking.domain.model.SeatLocation;
import com.cloudbees.booking.domain.model.Section;
import com.cloudbees.booking.domain.model.dto.request.SeatRequest;
import com.cloudbees.booking.domain.model.entity.Seat;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class SeatMapperTest {

  @Autowired
  private SeatMapper seatMapper;

  @Test
  void mapSeat_withValidSeatRequest_mapsRequestToASeat() {
    //given
    SeatRequest request = SeatRequest.builder().number(1).section(Section.A)
        .location(SeatLocation.WINDOW).build();

    //when
    Seat seat = seatMapper.mapSeat(request);

    //then
    assertThat(request)
        .usingRecursiveComparison()
        .ignoringFields("id")
        .isEqualTo(seat);
  }
}