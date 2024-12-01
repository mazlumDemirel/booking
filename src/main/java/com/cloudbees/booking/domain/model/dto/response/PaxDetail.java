package com.cloudbees.booking.domain.model.dto.response;

import com.cloudbees.booking.domain.model.SeatLocation;
import com.cloudbees.booking.domain.model.Section;
import com.cloudbees.booking.domain.model.Station;
import java.math.BigDecimal;
import lombok.Builder;

@Builder
public record PaxDetail(Long id,
                        String name,
                        String surname,
                        String email,
                        Section section,
                        int number,
                        SeatLocation location,
                        Station departure,
                        Station destination,
                        BigDecimal pricePaid) {

}
