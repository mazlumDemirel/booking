package com.cloudbees.booking.domain.mapper;

import static org.mapstruct.ReportingPolicy.IGNORE;

import com.cloudbees.booking.domain.model.Section;
import com.cloudbees.booking.domain.model.dto.request.TicketRequest;
import com.cloudbees.booking.domain.model.dto.response.PaxDetail;
import com.cloudbees.booking.domain.model.dto.response.SectionResponse;
import com.cloudbees.booking.domain.model.entity.Ticket;
import com.cloudbees.booking.domain.provider.PriceProvider;
import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.beans.factory.annotation.Autowired;

@Mapper(componentModel = "spring", unmappedTargetPolicy = IGNORE)
public abstract class TicketMapper {

  protected PriceProvider priceProvider;

  @Mapping(source = "name", target = "passenger.name")
  @Mapping(source = "surname", target = "passenger.surname")
  @Mapping(source = "email", target = "passenger.email")
  @Mapping(source = "section", target = "seat.section")
  @Mapping(source = "number", target = "seat.number")
  @Mapping(source = "location", target = "seat.location")
  @Mapping(source = "departure", target = "direction.departure")
  @Mapping(source = "destination", target = "direction.destination")
  @Mapping(target = "amount", expression = "java(priceProvider.getPrice(request.departure(), request.destination()))")
  public abstract Ticket mapTicket(TicketRequest request);

  @Mapping(source = "passenger.name", target = "name")
  @Mapping(source = "passenger.surname", target = "surname")
  @Mapping(source = "passenger.email", target = "email")
  @Mapping(source = "seat.section", target = "section")
  @Mapping(source = "seat.number", target = "number")
  @Mapping(source = "seat.location", target = "location")
  @Mapping(source = "direction.departure", target = "departure")
  @Mapping(source = "direction.destination", target = "destination")
  @Mapping(source = "amount", target = "pricePaid")
  public abstract PaxDetail mapReceipt(Ticket ticket);

  @Mapping(source = "tickets", target = "paxes")
  public abstract SectionResponse mapSectionResponse(Section section, List<Ticket> tickets);

  @Autowired
  protected void setQuestionService(PriceProvider priceProvider) {
    this.priceProvider = priceProvider;
  }
}
