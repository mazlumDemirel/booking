package com.cloudbees.booking.domain.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

import com.cloudbees.booking.domain.dao.SeatRepositoryPort;
import com.cloudbees.booking.domain.dao.TicketRepositoryPort;
import com.cloudbees.booking.domain.mapper.TicketMapper;
import com.cloudbees.booking.domain.model.dto.request.TicketRequest;
import com.cloudbees.booking.domain.model.dto.response.PaxDetail;
import com.cloudbees.booking.domain.model.entity.Direction;
import com.cloudbees.booking.domain.model.entity.Passenger;
import com.cloudbees.booking.domain.model.entity.Seat;
import com.cloudbees.booking.domain.model.entity.Ticket;
import com.cloudbees.booking.domain.provider.PriceProvider;
import com.cloudbees.booking.domain.service.impl.TicketServiceImpl;
import com.cloudbees.booking.stub.TicketRequestStub;
import com.cloudbees.booking.stub.TicketStub;
import java.math.BigDecimal;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class TicketServiceTest {

  @Mock
  private PriceProvider priceProvider;
  @Mock
  private TicketRepositoryPort ticketRepositoryPort;
  @Mock
  private SeatRepositoryPort seatRepositoryPort;
  @Mock
  private TicketMapper ticketMapper;

  @InjectMocks
  private TicketServiceImpl ticketService;

  @Test
  void createTicket_withValidRequest_NoThrowsException() {
    //given
    TicketRequest request = TicketRequestStub.createTicketRequest();

    //then
    assertThatCode(() -> ticketService.createTicket(request)).doesNotThrowAnyException();
  }

  @Test
  void saveTicket_withValidTicketRequest_shouldPass() {
    //given
    BigDecimal amount = BigDecimal.valueOf(100.0);
    TicketRequest request = TicketRequestStub.createTicketRequest();
    Ticket ticketBeforeSave = Ticket.builder()
        .id(null)
        .amount(amount)
        .passenger(Passenger.builder()
            .name(request.name())
            .surname(request.surname())
            .email(request.email())
            .build())
        .seat(Seat.builder()
            .number(request.number())
            .location(request.location())
            .section(request.section())
            .build())
        .direction(Direction.builder()
            .departure(request.departure())
            .destination(request.destination())
            .build())
        .build();

    PaxDetail paxDetail = PaxDetail.builder()
        .id(ticketBeforeSave.getId())
        .name(request.name())
        .surname(request.surname())
        .email(request.email())
        .section(request.section())
        .number(request.number())
        .location(request.location())
        .departure(request.departure())
        .destination(request.destination())
        .pricePaid(ticketBeforeSave.getAmount())
        .build();

    when(ticketMapper.mapTicket(request)).thenReturn(ticketBeforeSave);
    when(ticketRepositoryPort.saveTicket(ticketBeforeSave)).thenReturn(ticketBeforeSave);
    when(ticketMapper.mapReceipt(ticketBeforeSave)).thenReturn(paxDetail);

    //when
    PaxDetail response = ticketService.createTicket(request);

    //then
    assertThat(response).isNotNull().hasNoNullFieldsOrPropertiesExcept("id")
        .satisfies(
            ticket -> assertThat(request).usingRecursiveComparison()
                .comparingOnlyFields("name", "surname", "email", "section",
                    "number", "location", "departure", "destination")
                .isEqualTo(ticket)
        )
        .hasFieldOrPropertyWithValue("pricePaid", amount);

    verify(ticketMapper).mapTicket(request);
    verify(ticketRepositoryPort).saveTicket(ticketBeforeSave);
    verifyNoMoreInteractions(priceProvider, ticketMapper, ticketRepositoryPort);
  }

  @Test
  void deleteTicket_withValidTicketId_shouldPass() {
    //given
    Long ticketId = Long.MAX_VALUE;

    //then
    assertThatCode(() -> ticketService.deleteTicket(ticketId)).doesNotThrowAnyException();
    verify(ticketRepositoryPort).deleteTicket(ticketId);
    verifyNoMoreInteractions(priceProvider, ticketMapper, ticketRepositoryPort);
  }

  @Test
  void updateSeat_withValidRequest_shouldPass() {
    //given
    Ticket ticket = TicketStub.createTicket();
    Long ticketId = ticket.getId();

    when(ticketRepositoryPort.getTicket(ticketId)).thenReturn(ticket);

    //then
    assertThatCode(
        () -> ticketService.updateSeat(ticketId, ticket.getSeat())).doesNotThrowAnyException();
    verify(ticketRepositoryPort).updateTicket(ticket);
    verifyNoMoreInteractions(priceProvider, ticketMapper, ticketRepositoryPort);
  }

  @Test
  void getTicket_withValidTicketId_shouldPass() {
    //given
    long ticketId = Long.MAX_VALUE;
    Ticket ticket = TicketStub.createTicket();
    when(ticketRepositoryPort.getTicket(ticketId)).thenReturn(ticket);

    //then
    assertThatCode(() -> ticketService.getTicket(ticketId)).doesNotThrowAnyException();
    verify(ticketRepositoryPort).getTicket(ticketId);
    verify(ticketMapper).mapReceipt(ticket);
    verifyNoMoreInteractions(priceProvider, ticketMapper, ticketRepositoryPort);
  }
}