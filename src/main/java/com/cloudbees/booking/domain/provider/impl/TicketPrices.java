package com.cloudbees.booking.domain.provider.impl;

import com.cloudbees.booking.domain.model.Station;
import java.math.BigDecimal;
import java.util.Map;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "ticket")
@Getter
@Setter
public class TicketPrices {

  private Map<Station, Map<Station, BigDecimal>> prices;
}
