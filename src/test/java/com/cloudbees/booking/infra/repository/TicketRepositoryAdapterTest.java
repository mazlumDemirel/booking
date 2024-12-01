package com.cloudbees.booking.infra.repository;

import static com.cloudbees.booking.domain.model.SeatLocation.LOWER_BED;
import static com.cloudbees.booking.domain.model.SeatLocation.MIDDLE;
import static com.cloudbees.booking.domain.model.SeatLocation.UPPER_BED;
import static com.cloudbees.booking.domain.model.SeatLocation.WINDOW;
import static com.cloudbees.booking.domain.model.exception.EntityType.DIRECTION;
import static com.cloudbees.booking.domain.model.exception.EntityType.SEAT;
import static com.cloudbees.booking.domain.model.exception.EntityType.TICKET;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;

import com.cloudbees.booking.BaseSpringTestWithData;
import com.cloudbees.booking.domain.dao.TicketRepositoryPort;
import com.cloudbees.booking.domain.model.SeatLocation;
import com.cloudbees.booking.domain.model.Section;
import com.cloudbees.booking.domain.model.Station;
import com.cloudbees.booking.domain.model.entity.Direction;
import com.cloudbees.booking.domain.model.entity.Seat;
import com.cloudbees.booking.domain.model.entity.Ticket;
import com.cloudbees.booking.domain.model.exception.DirectionNotFoundException;
import com.cloudbees.booking.domain.model.exception.SeatNotFoundException;
import com.cloudbees.booking.domain.model.exception.TicketNotFoundException;
import com.cloudbees.booking.infra.dao.sql.DirectionRepository;
import com.cloudbees.booking.infra.dao.sql.PassengerRepository;
import com.cloudbees.booking.infra.dao.sql.SeatRepository;
import com.cloudbees.booking.infra.dao.sql.TicketRepository;
import com.cloudbees.booking.stub.TicketStub;
import java.math.BigDecimal;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

class TicketRepositoryAdapterTest extends BaseSpringTestWithData {

  @Autowired
  private TicketRepository ticketRepository;
  @Autowired
  private TicketRepositoryPort ticketRepositoryPort;
  @Autowired
  private SeatRepository seatRepository;
  @Autowired
  private DirectionRepository directionRepository;
  @Autowired
  private PassengerRepository passengerRepository;

  @BeforeEach
  void setUp() {
    ticketRepository.deleteAll();
  }

  @Test
  void saveTicket_withAValidTicketRequest_createsANewTicket() {
    //given
    Ticket ticketToSave = TicketStub.createTicket();

    //when
    Ticket savedTicket = ticketRepositoryPort.saveTicket(ticketToSave);
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
    Ticket ticketToSave = TicketStub.createTicket(Direction
        .builder()
        .id(1L)
        .departure(null)
        .destination(Station.France)
        .price(BigDecimal.valueOf(2000, 2))
        .build());

    //then
    assertThatCode(() -> ticketRepositoryPort.saveTicket(ticketToSave)).isInstanceOf(
        DirectionNotFoundException.class).hasMessage(DIRECTION.name());
  }

  @Test
  void saveTicket_withIncorrectSeatInformation_throwsNotFoundException() {
    //given
    Ticket ticketToSave = TicketStub
        .createTicket(Section.B, SeatLocation.LOWER_BED, 100, BigDecimal.ONE);

    //then
    assertThatCode(() -> ticketRepositoryPort.saveTicket(ticketToSave)).isInstanceOf(
        SeatNotFoundException.class).hasMessage(SEAT.name());
  }

  @Test
  void getTicket_withId_returnsExpectedTicket() {
    //given
    Ticket ticketToSave = TicketStub.createTicket();
    seatRepository.saveAndFlush(ticketToSave.getSeat());
    directionRepository.saveAndFlush(ticketToSave.getDirection());

    Ticket savedTicket = ticketRepositoryPort.saveTicket(ticketToSave);

    //when
    Ticket foundTicket = ticketRepositoryPort.getTicket(savedTicket.getId());

    //then
    assertThat(foundTicket).isNotNull().usingRecursiveComparison()
        .isEqualTo(savedTicket);
  }

  @Test
  void deleteTicket_withAValidTicketId_deletesTicket() {
    //given
    Ticket ticketToSave = TicketStub.createTicket();
    Ticket savedTicket = ticketRepositoryPort.saveTicket(ticketToSave);

    Long savedTicketId = savedTicket.getId();

    assertThatCode(() -> ticketRepositoryPort.getTicket(savedTicketId))
        .doesNotThrowAnyException();

    //when
    ticketRepositoryPort.deleteTicket(savedTicketId);

    //then
    assertThatCode(() -> ticketRepositoryPort.getTicket(savedTicketId))
        .isInstanceOf(TicketNotFoundException.class).hasMessage(TICKET.name());

    assertThat(seatRepository.findById(savedTicket.getSeat().getId())).isPresent();
    assertThat(directionRepository.findById(savedTicket.getDirection().getId())).isPresent();
    assertThat(passengerRepository.findByEmail(savedTicket.getPassenger().getEmail())).isPresent();
  }

  @Test
  void deleteTicket_withAnInvalidTicketId_doesNotEffectAnything() {
    //given
    Ticket ticketToSave = TicketStub.createTicket();
    Ticket savedTicket = ticketRepositoryPort.saveTicket(ticketToSave);

    //when
    ticketRepositoryPort.deleteTicket(savedTicket.getId() + 1);

    //then
    assertThat(ticketRepositoryPort.getTicket(savedTicket.getId())).isNotNull();
    assertThat(seatRepository.findById(savedTicket.getSeat().getId())).isPresent();
    assertThat(directionRepository.findById(savedTicket.getDirection().getId())).isPresent();
    assertThat(passengerRepository.findByEmail(savedTicket.getPassenger().getEmail())).isPresent();
  }

  @Test
  void updateTicketSetNewSeat_withAValidTicketIdAndSeatInfo_updatesTicket() {
    //given
    Ticket ticketToSave = TicketStub.createTicket();
    Ticket savedTicket = ticketRepositoryPort.saveTicket(ticketToSave);
    Seat seatBeforeChange = savedTicket.getSeat();
    Seat seat = seatRepository.findBySectionAndNumberAndLocation(Section.B, 14, MIDDLE)
        .orElseThrow();

    savedTicket.replaceSeat(seat);

    //when
    ticketRepositoryPort.updateTicket(savedTicket);

    //then
    assertThat(ticketRepositoryPort.getTicket(savedTicket.getId()).getSeat())
        .usingRecursiveComparison()
        .isNotEqualTo(seatBeforeChange);
  }

  @Test
  void getPassengers_bySelectedSection_shouldReturnSectionRelatedPaxes() {
    //given
    Ticket ticketToSave1 = TicketStub.createTicket("test_1@test.com");
    ticketToSave1.replaceSeat(
        seatRepository.findBySectionAndNumberAndLocation(Section.A, 1, UPPER_BED)
            .orElseThrow());
    Ticket ticketAfterSave1 = ticketRepositoryPort.saveTicket(ticketToSave1);
    Ticket ticketToSave2 = TicketStub.createTicket("test_2@test.com");
    ticketToSave2.replaceSeat(
        seatRepository.findBySectionAndNumberAndLocation(Section.B, 4, LOWER_BED)
            .orElseThrow());
    Ticket ticketAfterSave2 = ticketRepositoryPort.saveTicket(ticketToSave2);
    Ticket ticketToSave3 = TicketStub.createTicket("test_3@test.com");
    ticketToSave3.replaceSeat(
        seatRepository.findBySectionAndNumberAndLocation(Section.B, 13, WINDOW)
            .orElseThrow());
    Ticket ticketAfterSave3 = ticketRepositoryPort.saveTicket(ticketToSave3);
    Ticket ticketToSave4 = TicketStub.createTicket("test_4@test.com");
    ticketToSave4.replaceSeat(
        seatRepository.findBySectionAndNumberAndLocation(Section.B, 14, MIDDLE)
            .orElseThrow());
    Ticket ticketAfterSave4 = ticketRepositoryPort.saveTicket(ticketToSave4);

    //then
    assertThat(ticketRepositoryPort.getPassengersBySection(Section.B)).hasSize(3)
        .containsAll(List.of(ticketAfterSave2, ticketAfterSave3, ticketAfterSave4));

    //then
    assertThat(ticketRepositoryPort.getPassengersBySection(Section.A)).hasSize(1)
        .containsOnly(ticketAfterSave1);
  }
}