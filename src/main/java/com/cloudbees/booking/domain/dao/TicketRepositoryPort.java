package com.cloudbees.booking.domain.dao;

import com.cloudbees.booking.domain.model.Section;
import com.cloudbees.booking.domain.model.entity.Ticket;
import java.util.List;

public interface TicketRepositoryPort {

  Ticket saveTicket(Ticket ticket);

  Ticket getTicket(long id);

  void deleteTicket(Long ticketId);

  void updateTicket(Ticket ticket);

  List<Ticket> getPassengersBySection(Section section);
}
