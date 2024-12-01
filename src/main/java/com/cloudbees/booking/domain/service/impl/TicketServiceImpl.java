package com.cloudbees.booking.domain.service.impl;

import com.cloudbees.booking.domain.dao.SeatRepositoryPort;
import com.cloudbees.booking.domain.dao.TicketRepositoryPort;
import com.cloudbees.booking.domain.mapper.TicketMapper;
import com.cloudbees.booking.domain.model.dto.request.TicketRequest;
import com.cloudbees.booking.domain.model.dto.response.PaxDetail;
import com.cloudbees.booking.domain.model.entity.Seat;
import com.cloudbees.booking.domain.model.entity.Ticket;
import com.cloudbees.booking.domain.service.TicketService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TicketServiceImpl implements TicketService {

  private final TicketRepositoryPort ticketRepositoryPort;
  private final TicketMapper ticketMapper;
  private final SeatRepositoryPort seatRepositoryPort;

  @Override
  public PaxDetail createTicket(TicketRequest request) {
    return ticketMapper.mapReceipt(
        ticketRepositoryPort.saveTicket(ticketMapper.mapTicket(request)));
  }

  @Override
  public void deleteTicket(Long ticketId) {
    ticketRepositoryPort.deleteTicket(ticketId);
  }

  @Override
  public void updateSeat(Long ticketId, Seat newSeat) {
    Ticket ticket = ticketRepositoryPort.getTicket(ticketId);
    Seat seat = seatRepositoryPort.getSeat(newSeat);
    ticket.replaceSeat(seat);
    ticketRepositoryPort.updateTicket(ticket);
  }

  @Override
  public PaxDetail getTicket(long id) {
    return ticketMapper.mapReceipt(ticketRepositoryPort.getTicket(id));
  }
}
