package com.cloudbees.booking.infra.dao.sql;

import com.cloudbees.booking.domain.model.Section;
import com.cloudbees.booking.domain.model.entity.Ticket;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TicketRepository extends JpaRepository<Ticket, Long> {

  List<Ticket> findBySeatSection(Section section);
}
