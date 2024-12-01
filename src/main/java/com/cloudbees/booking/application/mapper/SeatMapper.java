package com.cloudbees.booking.application.mapper;

import static org.mapstruct.ReportingPolicy.IGNORE;

import com.cloudbees.booking.domain.model.dto.request.SeatRequest;
import com.cloudbees.booking.domain.model.entity.Seat;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", unmappedTargetPolicy = IGNORE)
public interface SeatMapper {

  Seat mapSeat(SeatRequest request);
}
