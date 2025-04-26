package com.ticket.booking.event.dto;

import com.ticket.booking.event.enums.City;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class EventResponseDto {

    private Long id;
    private String name;
    private LocalDateTime date;
    private City location;
    private Integer totalSeats;
    private Integer bookedSeats;
}
