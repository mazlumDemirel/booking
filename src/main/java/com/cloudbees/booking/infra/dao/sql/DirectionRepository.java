package com.cloudbees.booking.infra.dao.sql;

import com.cloudbees.booking.domain.model.Station;
import com.cloudbees.booking.domain.model.entity.Direction;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DirectionRepository extends JpaRepository<Direction, Long> {

  Optional<Direction> findByDepartureAndDestination(Station departure, Station destination);
}
