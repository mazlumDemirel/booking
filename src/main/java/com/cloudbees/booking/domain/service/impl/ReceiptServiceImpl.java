package com.cloudbees.booking.domain.service.impl;

import com.cloudbees.booking.domain.dao.TicketRepositoryPort;
import com.cloudbees.booking.domain.mapper.TicketMapper;
import com.cloudbees.booking.domain.model.dto.response.PaxDetail;
import com.cloudbees.booking.domain.service.ReceiptService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ReceiptServiceImpl implements ReceiptService {

  private final TicketRepositoryPort ticketRepositoryPort;
  private final TicketMapper ticketMapper;

  @Override
  public PaxDetail getTicket(long id) {
    return ticketMapper.mapReceipt(ticketRepositoryPort.getTicket(id));
  }
}
