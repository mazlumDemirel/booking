package com.cloudbees.booking.domain.service;

import static org.assertj.core.api.Assertions.assertThatCode;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

import com.cloudbees.booking.domain.dao.TicketRepositoryPort;
import com.cloudbees.booking.domain.mapper.TicketMapper;
import com.cloudbees.booking.domain.model.Section;
import com.cloudbees.booking.domain.model.entity.Ticket;
import com.cloudbees.booking.domain.service.impl.PassengerServiceImpl;
import com.cloudbees.booking.stub.TicketStub;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class PassengerServiceTest {

  @Mock
  private TicketRepositoryPort ticketRepositoryPort;
  @Mock
  private TicketMapper ticketMapper;

  @InjectMocks
  private PassengerServiceImpl passengerService;

  @Test
  void getPassengersBySection_withValidRequest_shouldPass() {
    //given
    Section section = Section.B;
    Ticket ticket1 = TicketStub.createTicket("test_1@test.com");
    Ticket ticket2 = TicketStub.createTicket("test_2@test.com");
    Ticket ticket3 = TicketStub.createTicket("test_3@test.com");
    when(ticketRepositoryPort.getPassengersBySection(section))
        .thenReturn(List.of(ticket1, ticket2, ticket3));

    //then
    assertThatCode(
        () -> passengerService.getPassengersBySection(section)).doesNotThrowAnyException();
    verify(ticketMapper).mapSectionResponse(section, List.of(ticket1, ticket2, ticket3));
    verifyNoMoreInteractions(ticketMapper, ticketRepositoryPort);
  }
}