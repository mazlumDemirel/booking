package com.cloudbees.booking.infra.repository;

import static com.cloudbees.booking.domain.model.exception.EntityType.DIRECTION;
import static com.cloudbees.booking.domain.model.exception.EntityType.SEAT;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;

import com.cloudbees.booking.BaseSpringTestWithNoData;
import com.cloudbees.booking.domain.dao.TicketRepositoryPort;
import com.cloudbees.booking.domain.model.SeatLocation;
import com.cloudbees.booking.domain.model.Section;
import com.cloudbees.booking.domain.model.entity.Ticket;
import com.cloudbees.booking.domain.model.exception.DirectionNotFoundException;
import com.cloudbees.booking.domain.model.exception.SeatNotFoundException;
import com.cloudbees.booking.infra.dao.sql.DirectionRepository;
import com.cloudbees.booking.infra.dao.sql.SeatRepository;
import com.cloudbees.booking.stub.TicketStub;
import java.math.BigDecimal;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

class TicketRepositoryImplTest extends BaseSpringTestWithNoData {

  @Autowired
  private TicketRepositoryPort ticketRepository;
  @Autowired
  private SeatRepository seatRepository;
  @Autowired
  private DirectionRepository directionRepository;

  @Test
  void saveTicket_withAValidTicketRequest_createsANewTicket() {
    //given
    Ticket ticketToSave = TicketStub.createTicket();
    seatRepository.saveAndFlush(ticketToSave.getSeat());
    directionRepository.saveAndFlush(ticketToSave.getDirection());
    //when
    Ticket savedTicket = ticketRepository.saveTicket(ticketToSave);
    //then
    assertThat(savedTicket)
        .isNotNull()
        .usingRecursiveComparison()
        .ignoringFields("id")
        .isEqualTo(ticketToSave);
    assertThat(savedTicket.getId())
        .isNotNull().isPositive();
  }

  @Test
  void saveTicket_withIncorrectDirectionInformation_throwsNotFoundException() {
    //given
    Ticket ticketToSave = TicketStub.createTicket();
    seatRepository.saveAndFlush(ticketToSave.getSeat());

    //then
    assertThatCode(() -> ticketRepository.saveTicket(ticketToSave)).isInstanceOf(
        DirectionNotFoundException.class).hasMessage(DIRECTION.name());
  }

  @Test
  void saveTicket_withIncorrectSeatInformation_throwsNotFoundException() {
    //given
    Ticket ticketToSave = TicketStub
        .createTicket(Section.B, SeatLocation.LOWER_BED, 100, BigDecimal.ONE);

    //then
    assertThatCode(() -> ticketRepository.saveTicket(ticketToSave)).isInstanceOf(
        SeatNotFoundException.class).hasMessage(SEAT.name());
  }
}