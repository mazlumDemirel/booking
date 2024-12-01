package com.cloudbees.booking.domain.model.dto.request;

import com.cloudbees.booking.domain.model.SeatLocation;
import com.cloudbees.booking.domain.model.Section;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Builder;

@Builder
public record SeatRequest(
    @Positive
    int number,
    @NotNull
    Section section,
    @NotNull
    SeatLocation location) {

}
