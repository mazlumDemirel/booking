package com.cloudbees.booking.domain.model.dto.response;

import com.cloudbees.booking.domain.model.Section;
import java.util.List;
import lombok.Builder;

@Builder
public record SectionResponse(Section section, List<PaxDetail> paxes) {

}
