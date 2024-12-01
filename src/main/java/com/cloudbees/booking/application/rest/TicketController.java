package com.cloudbees.booking.application.rest;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

import com.cloudbees.booking.application.mapper.SeatMapper;
import com.cloudbees.booking.domain.model.Section;
import com.cloudbees.booking.domain.model.dto.request.SeatRequest;
import com.cloudbees.booking.domain.model.dto.request.TicketRequest;
import com.cloudbees.booking.domain.model.dto.response.PaxDetail;
import com.cloudbees.booking.domain.model.dto.response.SectionResponse;
import com.cloudbees.booking.domain.service.PassengerService;
import com.cloudbees.booking.domain.service.TicketService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/tickets")
@RequiredArgsConstructor
@Validated
public class TicketController {

  private final PassengerService passengerService;
  private final TicketService ticketService;
  private final SeatMapper seatMapper;

  @PostMapping(consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
  @ResponseStatus(HttpStatus.CREATED)
  public PaxDetail createTicket(@Valid @RequestBody TicketRequest request) {
    return ticketService.createTicket(request);
  }

  @GetMapping(value = "/{id}", produces = APPLICATION_JSON_VALUE)
  @ResponseStatus(HttpStatus.OK)
  public PaxDetail getReceipt(@PathVariable Long id) {
    return ticketService.getTicket(id);
  }

  @PutMapping(value = "/{id}", consumes = APPLICATION_JSON_VALUE)
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void updateTicket(@PathVariable Long id, @Valid @RequestBody SeatRequest request) {
    ticketService.updateSeat(id, seatMapper.mapSeat(request));
  }

  @DeleteMapping(value = "/{id}")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void deleteTicket(@PathVariable Long id) {
    ticketService.deleteTicket(id);
  }

  @GetMapping(produces = APPLICATION_JSON_VALUE)
  @ResponseStatus(HttpStatus.OK)
  public SectionResponse getPassengersBySection(@RequestParam Section section) {
    return passengerService.getPassengersBySection(section);
  }
}
