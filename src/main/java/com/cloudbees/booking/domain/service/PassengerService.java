package com.cloudbees.booking.domain.service;

import com.cloudbees.booking.domain.model.Section;
import com.cloudbees.booking.domain.model.dto.response.SectionResponse;

public interface PassengerService {

  SectionResponse getPassengersBySection(Section section);
}
