package com.cloudbees.booking.domain.model.dto.response;

import com.cloudbees.booking.domain.model.SeatLocation;

public record PaxResponse(String name, String surname, String email, int seatNumber,
                          SeatLocation location) {

}
