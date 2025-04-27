package com.ticket.booking.event.dto;

import com.ticket.booking.event.enums.City;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class EventRequestDto {

    private String name;
    private LocalDateTime date;
    private City location;
    private Integer totalSeats;
}
