package com.cloudbees.booking.infra.dao.sql;

import static org.assertj.core.api.Assertions.assertThat;

import com.cloudbees.booking.domain.model.entity.Passenger;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
class PassengerRepositoryTest {

  @Autowired
  private PassengerRepository passengerRepository;

  @Test
  void shouldFindPassenger_withGivenEmail() {
    //given
    Passenger passenger = Passenger.builder()
        .email("mazlummerdandemirel@gmail.com")
        .name("Mazlum")
        .surname("Demirel")
        .build();

    Passenger savedPassenger = passengerRepository.saveAndFlush(passenger);

    //when
    Passenger foundPassenger = passengerRepository.findByEmail(passenger.getEmail()).orElseThrow();

    //then
    assertThat(foundPassenger).usingRecursiveComparison()
        .isEqualTo(savedPassenger)
        .isEqualTo(passenger);
  }
}