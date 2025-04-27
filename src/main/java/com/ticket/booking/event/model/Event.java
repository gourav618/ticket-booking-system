package com.ticket.booking.event.model;

import com.ticket.booking.event.enums.City;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class Event {

    private Long id;
    private String name;
    private LocalDateTime date;
    private City location;
    private Integer totalSeats;
    private Integer bookedSeats;
}
