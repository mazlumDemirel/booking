package com.cloudbees.booking.domain.service;

import com.cloudbees.booking.domain.model.dto.request.TicketRequest;
import com.cloudbees.booking.domain.model.dto.response.PaxDetail;
import com.cloudbees.booking.domain.model.entity.Seat;

public interface TicketService {

  PaxDetail createTicket(TicketRequest request);

  void deleteTicket(Long ticketId);

  void updateSeat(Long ticketId, Seat newSeat);

  PaxDetail getTicket(long id);
}
