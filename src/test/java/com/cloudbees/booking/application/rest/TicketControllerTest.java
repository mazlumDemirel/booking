package com.cloudbees.booking.application.rest;

import static java.nio.charset.StandardCharsets.UTF_8;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.when;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.cloudbees.booking.application.mapper.SeatMapper;
import com.cloudbees.booking.domain.model.SeatLocation;
import com.cloudbees.booking.domain.model.Section;
import com.cloudbees.booking.domain.model.Station;
import com.cloudbees.booking.domain.model.dto.request.SeatRequest;
import com.cloudbees.booking.domain.model.dto.request.TicketRequest;
import com.cloudbees.booking.domain.model.dto.response.PaxDetail;
import com.cloudbees.booking.domain.model.dto.response.SectionResponse;
import com.cloudbees.booking.domain.model.exception.SeatNotFoundException;
import com.cloudbees.booking.domain.model.exception.TicketNotFoundException;
import com.cloudbees.booking.domain.service.PassengerService;
import com.cloudbees.booking.domain.service.TicketService;
import com.cloudbees.booking.stub.TicketRequestStub;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(controllers = {TicketController.class})
class TicketControllerTest {

  @Autowired
  private MockMvc mockMvc;

  @MockBean
  private PassengerService passengerService;
  @MockBean
  private TicketService ticketService;
  @MockBean
  private SeatMapper seatMapper;
  @Autowired
  private ObjectMapper objectMapper;

  @Test
  void createTicket_withInsufficientTicketRequest_shouldFail() throws Exception {
    //given
    TicketRequest ticketRequest = TicketRequestStub.createInsufficientTicketRequest();

    //then
    this.mockMvc
        .perform(
            post("/tickets")
                .contentType(APPLICATION_JSON_VALUE)
                .accept(APPLICATION_JSON_VALUE)
                .characterEncoding(UTF_8)
                .content(objectMapper.writeValueAsString(ticketRequest))
        )
        .andExpect(status().isBadRequest())
        .andExpect(jsonPath("$.messages", hasSize(8)))
        .andExpect(jsonPath("$.messages[0]").value("departure"))
        .andExpect(jsonPath("$.messages[1]").value("destination"))
        .andExpect(jsonPath("$.messages[2]").value("email"))
        .andExpect(jsonPath("$.messages[3]").value("location"))
        .andExpect(jsonPath("$.messages[4]").value("name"))
        .andExpect(jsonPath("$.messages[5]").value("number"))
        .andExpect(jsonPath("$.messages[6]").value("section"))
        .andExpect(jsonPath("$.messages[7]").value("surname"));
  }

  @Test
  void createTicket_withInsufficientSeatInfo_shouldFail() throws Exception {
    //given
    TicketRequest ticketRequest = TicketRequestStub.createTicketRequest();
    when(ticketService.createTicket(
        argThat(request ->
            Objects.equals(request.name(), ticketRequest.name())
                && Objects.equals(request.surname(), ticketRequest.surname())
                && Objects.equals(request.email(), ticketRequest.email())
                && request.section() == ticketRequest.section()
                && request.number() == ticketRequest.number()
                && request.location() == ticketRequest.location()
                && request.departure() == ticketRequest.departure()
                && request.destination() == ticketRequest.destination()
        )))
        .thenThrow(SeatNotFoundException.class);

    //then
    this.mockMvc
        .perform(
            post("/tickets")
                .contentType(APPLICATION_JSON_VALUE)
                .accept(APPLICATION_JSON_VALUE)
                .characterEncoding(UTF_8)
                .content(objectMapper.writeValueAsString(ticketRequest))
        )
        .andExpect(status().isNotFound());
  }

  @Test
  void createTicket_withValidTicketRequest_shouldPass() throws Exception {
    //given
    TicketRequest ticketRequest = TicketRequestStub.createTicketRequest();
    PaxDetail expectedPaxDetail = PaxDetail.builder()
        .id(Long.MAX_VALUE)
        .name(ticketRequest.name())
        .surname(ticketRequest.surname())
        .email(ticketRequest.email())
        .section(ticketRequest.section())
        .number(ticketRequest.number())
        .location(ticketRequest.location())
        .departure(ticketRequest.departure())
        .destination(ticketRequest.destination())
        .pricePaid(BigDecimal.valueOf(20000, 2))
        .build();
    when(ticketService.createTicket(
        argThat(request ->
            Objects.equals(request.name(), ticketRequest.name())
                && Objects.equals(request.surname(), ticketRequest.surname())
                && Objects.equals(request.email(), ticketRequest.email())
                && request.section() == ticketRequest.section()
                && request.number() == ticketRequest.number()
                && request.location() == ticketRequest.location()
                && request.departure() == ticketRequest.departure()
                && request.destination() == ticketRequest.destination()
        )))
        .thenReturn(expectedPaxDetail);

    //then
    this.mockMvc
        .perform(
            post("/tickets")
                .contentType(APPLICATION_JSON_VALUE)
                .accept(APPLICATION_JSON_VALUE)
                .characterEncoding(UTF_8)
                .content(objectMapper.writeValueAsString(ticketRequest))
        )
        .andExpect(status().isCreated())
        .andExpect(jsonPath("$.id").value(expectedPaxDetail.id()))
        .andExpect(jsonPath("$.name").value(expectedPaxDetail.name()))
        .andExpect(jsonPath("$.surname").value(expectedPaxDetail.surname()))
        .andExpect(jsonPath("$.email").value(expectedPaxDetail.email()))
        .andExpect(jsonPath("$.section").value(expectedPaxDetail.section().name()))
        .andExpect(jsonPath("$.number").value(expectedPaxDetail.number()))
        .andExpect(jsonPath("$.location").value(expectedPaxDetail.location().name()))
        .andExpect(jsonPath("$.departure").value(expectedPaxDetail.departure().name()))
        .andExpect(jsonPath("$.destination").value(expectedPaxDetail.destination().name()))
        .andExpect(jsonPath("$.pricePaid", is(expectedPaxDetail.pricePaid().doubleValue())));
  }

  @Test
  void updateTicket_withInsufficientSeatRequest_shouldFail() throws Exception {
    //given
    SeatRequest seatRequest = SeatRequest.builder().build();
    Long id = Long.MAX_VALUE;

    //then
    this.mockMvc
        .perform(
            put("/tickets/{id}", id)
                .contentType(APPLICATION_JSON_VALUE)
                .accept(APPLICATION_JSON_VALUE)
                .characterEncoding(UTF_8)
                .content(objectMapper.writeValueAsString(seatRequest))
        )
        .andExpect(status().isBadRequest())
        .andExpect(jsonPath("$.messages", hasSize(3)))
        .andExpect(jsonPath("$.messages[0]").value("location"))
        .andExpect(jsonPath("$.messages[1]").value("number"))
        .andExpect(jsonPath("$.messages[2]").value("section"));
  }

  @Test
  void updateTicket_withValidSeatRequest_shouldPass() throws Exception {
    //given
    SeatRequest seatRequest = SeatRequest.builder()
        .location(SeatLocation.WINDOW)
        .number(1)
        .section(Section.A)
        .build();

    Long id = Long.MAX_VALUE;

    //then
    this.mockMvc
        .perform(
            put("/tickets/{id}", id)
                .contentType(APPLICATION_JSON_VALUE)
                .accept(APPLICATION_JSON_VALUE)
                .characterEncoding(UTF_8)
                .content(objectMapper.writeValueAsString(seatRequest))
        )
        .andExpect(status().isNoContent());
  }

  @Test
  void getReceipt_withExistentTicketId_shouldPass() throws Exception {
    //given
    long ticketId = Long.MAX_VALUE;
    TicketRequest ticketRequest = TicketRequestStub.createTicketRequest();
    PaxDetail expectedPaxDetail = PaxDetail.builder()
        .id(Long.MAX_VALUE)
        .name(ticketRequest.name())
        .surname(ticketRequest.surname())
        .email(ticketRequest.email())
        .section(ticketRequest.section())
        .number(ticketRequest.number())
        .location(ticketRequest.location())
        .departure(ticketRequest.departure())
        .destination(ticketRequest.destination())
        .pricePaid(BigDecimal.valueOf(20000, 2))
        .build();

    when(ticketService.getTicket(ticketId)).thenReturn(expectedPaxDetail);

    //then
    this.mockMvc
        .perform(
            get("/tickets/{id}", ticketId)
                .contentType(APPLICATION_JSON_VALUE)
        )
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.id").value(expectedPaxDetail.id()))
        .andExpect(jsonPath("$.name").value(expectedPaxDetail.name()))
        .andExpect(jsonPath("$.surname").value(expectedPaxDetail.surname()))
        .andExpect(jsonPath("$.email").value(expectedPaxDetail.email()))
        .andExpect(jsonPath("$.section").value(expectedPaxDetail.section().name()))
        .andExpect(jsonPath("$.number").value(expectedPaxDetail.number()))
        .andExpect(jsonPath("$.location").value(expectedPaxDetail.location().name()))
        .andExpect(jsonPath("$.departure").value(expectedPaxDetail.departure().name()))
        .andExpect(jsonPath("$.destination").value(expectedPaxDetail.destination().name()))
        .andExpect(jsonPath("$.pricePaid", is(expectedPaxDetail.pricePaid().doubleValue())));
  }

  @Test
  void getReceipt_withInExistentTicketId_shouldFail() throws Exception {
    //given
    long ticketId = Long.MAX_VALUE;

    when(ticketService.getTicket(ticketId)).thenThrow(TicketNotFoundException.class);

    //then
    this.mockMvc
        .perform(
            get("/tickets/{id}", ticketId)
                .contentType(APPLICATION_JSON_VALUE)
        )
        .andExpect(status().isNotFound());
  }

  @Test
  void deleteTicket_withValidTicketId_shouldPass() throws Exception {
    //given
    long ticketId = Long.MAX_VALUE;

    //then
    this.mockMvc
        .perform(
            delete("/tickets/{id}", ticketId)
        )
        .andExpect(status().isNoContent());
  }

  @Test
  void getPassengersBySection_shouldPass() throws Exception {
    //given
    Section selectedSection = Section.A;

    PaxDetail expectedPaxDetail = PaxDetail.builder()
        .id(Long.MAX_VALUE)
        .name("Mazlum")
        .surname("Demirel")
        .email("mazlummerdandemirel@gmail.com")
        .section(selectedSection)
        .number(1)
        .location(SeatLocation.MIDDLE)
        .departure(Station.France)
        .destination(Station.Spain)
        .pricePaid(BigDecimal.valueOf(200000, 2))
        .build();

    SectionResponse expectedSectionResponse = SectionResponse.builder().section(selectedSection)
        .paxes(List.of(expectedPaxDetail)).build();
    when(passengerService.getPassengersBySection(selectedSection))
        .thenReturn(expectedSectionResponse);

    //then
    this.mockMvc
        .perform(
            get("/tickets").param("section", selectedSection.name())
        )
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.section").value(selectedSection.name()))
        .andExpect(jsonPath("$.paxes", hasSize(1)))
        .andExpect(jsonPath("$.paxes[0].id").value(expectedPaxDetail.id()))
        .andExpect(jsonPath("$.paxes[0].name").value(expectedPaxDetail.name()))
        .andExpect(jsonPath("$.paxes[0].surname").value(expectedPaxDetail.surname()))
        .andExpect(jsonPath("$.paxes[0].email").value(expectedPaxDetail.email()))
        .andExpect(jsonPath("$.paxes[0].section").value(expectedPaxDetail.section().name()))
        .andExpect(jsonPath("$.paxes[0].number").value(expectedPaxDetail.number()))
        .andExpect(jsonPath("$.paxes[0].location").value(expectedPaxDetail.location().name()))
        .andExpect(jsonPath("$.paxes[0].departure").value(expectedPaxDetail.departure().name()))
        .andExpect(jsonPath("$.paxes[0].destination").value(expectedPaxDetail.destination().name()))
        .andExpect(
            jsonPath("$.paxes[0].pricePaid", is(expectedPaxDetail.pricePaid().doubleValue())));
  }
}