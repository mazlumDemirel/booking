package com.cloudbees.booking.domain.model.dto.request;

import com.cloudbees.booking.domain.model.SeatLocation;
import com.cloudbees.booking.domain.model.Section;
import com.cloudbees.booking.domain.model.Station;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Builder;

@Builder
public record TicketRequest(@NotEmpty String name,
                            @NotEmpty String surname,
                            @NotEmpty @Email String email,
                            @NotNull Section section,
                            @Positive int number,
                            @NotNull SeatLocation location,
                            @NotNull Station departure,
                            @NotNull Station destination) {

}
