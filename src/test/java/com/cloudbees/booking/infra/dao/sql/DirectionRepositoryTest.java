package com.cloudbees.booking.infra.dao.sql;

import static org.assertj.core.api.Assertions.assertThat;

import com.cloudbees.booking.domain.model.Station;
import com.cloudbees.booking.domain.model.entity.Direction;
import java.math.BigDecimal;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
class DirectionRepositoryTest {

  @Autowired
  private DirectionRepository directionRepository;

  @Test
  void shouldFindByDepartureAndDestinationPersistentDirection_withGivenDestinations() {
    //given
    Direction direction = Direction.builder()
        .id(10L)
        .departure(Station.France)
        .destination(Station.London)
        .price(BigDecimal.valueOf(2000, 2))
        .build();

    Direction savedDirection = directionRepository.saveAndFlush(direction);

    //when
    Direction foundDirection = directionRepository.findByDepartureAndDestination(
        direction.getDeparture(), direction.getDestination()).orElseThrow();

    //then
    assertThat(foundDirection).usingRecursiveComparison()
        .isEqualTo(savedDirection)
        .isEqualTo(direction);
  }
}