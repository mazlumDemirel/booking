package com.cloudbees.booking.infra.repository;

import com.cloudbees.booking.domain.dao.TicketRepositoryPort;
import com.cloudbees.booking.domain.model.Section;
import com.cloudbees.booking.domain.model.entity.Direction;
import com.cloudbees.booking.domain.model.entity.Passenger;
import com.cloudbees.booking.domain.model.entity.Seat;
import com.cloudbees.booking.domain.model.entity.Ticket;
import com.cloudbees.booking.domain.model.exception.DirectionNotFoundException;
import com.cloudbees.booking.domain.model.exception.SeatNotFoundException;
import com.cloudbees.booking.domain.model.exception.TicketNotFoundException;
import com.cloudbees.booking.infra.dao.sql.DirectionRepository;
import com.cloudbees.booking.infra.dao.sql.PassengerRepository;
import com.cloudbees.booking.infra.dao.sql.SeatRepository;
import com.cloudbees.booking.infra.dao.sql.TicketRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TicketRepositoryAdapter implements TicketRepositoryPort {

  private final TicketRepository ticketRepository;
  private final DirectionRepository directionRepository;
  private final SeatRepository seatRepository;
  private final PassengerRepository passengerRepository;

  @Override
  public Ticket saveTicket(Ticket ticket) {
    Passenger passenger = passengerRepository.findByEmail(ticket.getPassenger().getEmail())
        .orElse(passengerRepository.save(ticket.getPassenger()));
    Seat seat = seatRepository.findBySectionAndNumberAndLocation(ticket.getSeat().getSection(),
            ticket.getSeat().getNumber(), ticket.getSeat().getLocation())
        .orElseThrow(SeatNotFoundException::new);
    Direction direction = directionRepository.findByDepartureAndDestination(
        ticket.getDirection().getDeparture(),
        ticket.getDirection().getDestination()).orElseThrow(DirectionNotFoundException::new);
    return ticketRepository.save(Ticket.builder()
        .seat(seat)
        .amount(ticket.getAmount())
        .direction(direction)
        .passenger(passenger)
        .build());
  }

  @Override
  public Ticket getTicket(long id) {
    return ticketRepository.findById(id).orElseThrow(TicketNotFoundException::new);
  }

  @Override
  public void deleteTicket(Long id) {
    ticketRepository.deleteById(id);
  }

  @Override
  public void updateTicket(Ticket ticket) {
    ticketRepository.saveAndFlush(ticket);
  }

  @Override
  public List<Ticket> getPassengersBySection(Section section) {
    return ticketRepository.findBySeatSection(section);
  }
}
