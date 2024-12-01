package com.cloudbees.booking.infra.dao.sql;

import com.cloudbees.booking.domain.model.entity.Passenger;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PassengerRepository extends JpaRepository<Passenger, String> {

  Optional<Passenger> findByEmail(String email);
}
