package com.cloudbees.booking.domain.service.impl;

import com.cloudbees.booking.domain.mapper.TicketMapper;
import com.cloudbees.booking.domain.model.Section;
import com.cloudbees.booking.domain.model.dto.response.SectionResponse;
import com.cloudbees.booking.domain.dao.TicketRepositoryPort;
import com.cloudbees.booking.domain.service.PassengerService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PassengerServiceImpl implements PassengerService {

  private final TicketRepositoryPort ticketRepositoryPort;
  private final TicketMapper ticketMapper;

  @Override
  public SectionResponse getPassengersBySection(Section section) {
    return ticketMapper.mapSectionResponse(section, ticketRepositoryPort.getPassengersBySection(section));
  }
}
