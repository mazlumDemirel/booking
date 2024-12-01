package com.cloudbees.booking.domain.service;

import com.cloudbees.booking.domain.model.dto.response.PaxDetail;

public interface ReceiptService {

  PaxDetail getTicket(long id);
}
