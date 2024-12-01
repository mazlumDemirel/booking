package com.cloudbees.booking.domain.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import com.cloudbees.booking.domain.model.SeatLocation;
import com.cloudbees.booking.domain.model.Section;
import com.cloudbees.booking.domain.model.dto.request.TicketRequest;
import com.cloudbees.booking.domain.model.dto.response.PaxDetail;
import com.cloudbees.booking.domain.model.dto.response.SectionResponse;
import com.cloudbees.booking.domain.model.entity.Ticket;
import com.cloudbees.booking.domain.dao.TicketPricesProviderPort;
import com.cloudbees.booking.stub.TicketRequestStub;
import com.cloudbees.booking.stub.TicketStub;
import java.math.BigDecimal;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;

@SpringBootTest
@Sql(scripts = "classpath:/static/db/test-direction-data.sql")
class TicketMapperTest {

  @Autowired
  private TicketMapper ticketMapper;

  @Autowired
  private TicketPricesProviderPort pricesProviderPort;

  @Test
  void mapTicket_withGivenTicketRequest_shouldMapAsExpected() {
    //given
    Section section = Section.A;
    SeatLocation seatLocation = SeatLocation.AISLE;
    int number = 1;
    TicketRequest request = TicketRequestStub.createTicketRequest(section, seatLocation, number);
    BigDecimal amount = pricesProviderPort.getPrice(request.departure(), request.destination());

    //when
    Ticket ticketUnderTest = ticketMapper.mapTicket(request);

    //then
    assertThat(ticketUnderTest).isNotNull().hasNoNullFieldsOrPropertiesExcept("id")
        .satisfies(
            ticket -> {
              assertThat(request).usingRecursiveComparison()
                  .comparingOnlyFields("name", "surname", "email")
                  .isEqualTo(ticket.getPassenger());
              assertThat(request).usingRecursiveComparison()
                  .comparingOnlyFields("section", "number", "location")
                  .isEqualTo(ticket.getSeat());
              assertThat(request).usingRecursiveComparison()
                  .comparingOnlyFields("departure", "destination")
                  .isEqualTo(ticket.getDirection());
            }
        )
        .hasFieldOrPropertyWithValue("amount", amount);
  }

  @Test
  void mapReceipt_withGivenTicket_shouldMapAsExpected() {
    //given
    Ticket ticket = TicketStub.createTicket();

    //when
    PaxDetail paxDetailUnderTest = ticketMapper.mapReceipt(ticket);

    //then
    assertThat(paxDetailUnderTest).isNotNull().hasNoNullFieldsOrPropertiesExcept("id")
        .satisfies(
            paxDetail -> {
              assertThat(paxDetail).usingRecursiveComparison()
                  .comparingOnlyFields("name", "surname", "email")
                  .isEqualTo(ticket.getPassenger());
              assertThat(paxDetail).usingRecursiveComparison()
                  .comparingOnlyFields("section", "number", "location")
                  .isEqualTo(ticket.getSeat());
              assertThat(paxDetail).usingRecursiveComparison()
                  .comparingOnlyFields("departure", "destination")
                  .isEqualTo(ticket.getDirection());
            }
        )
        .hasFieldOrPropertyWithValue("pricePaid", ticket.getAmount());
  }

  @Test
  void mapSectionResponse_withGivenTickets_shouldMapAsExpected() {
    //given
    Ticket ticket = TicketStub.createTicket();

    //when
    SectionResponse sectionResponseUnderTest = ticketMapper
        .mapSectionResponse(ticket.getSeat().getSection(), List.of(ticket));

    //then
    assertThat(sectionResponseUnderTest)
        .isNotNull()
        .hasFieldOrPropertyWithValue("section", ticket.getSeat().getSection())
        .satisfies(sectionResponse -> {
          assertThat(sectionResponse.paxes()).hasSize(1).first().usingRecursiveComparison()
              .comparingOnlyFields("name", "surname", "email")
              .isEqualTo(ticket.getPassenger());
          assertThat(sectionResponse.paxes()).hasSize(1).first().usingRecursiveComparison()
              .comparingOnlyFields("section", "number", "location")
              .isEqualTo(ticket.getSeat());
          assertThat(sectionResponse.paxes()).hasSize(1).first().usingRecursiveComparison()
              .comparingOnlyFields("departure", "destination")
              .isEqualTo(ticket.getDirection());
        });
  }
}