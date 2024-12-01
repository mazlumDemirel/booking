package com.cloudbees.booking.infra.dao.sql;

import com.cloudbees.booking.domain.model.SeatLocation;
import com.cloudbees.booking.domain.model.Section;
import com.cloudbees.booking.domain.model.entity.Seat;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SeatRepository extends JpaRepository<Seat, Long> {

  Optional<Seat> findBySectionAndNumberAndLocation(Section section, int number,
      SeatLocation location);
}
