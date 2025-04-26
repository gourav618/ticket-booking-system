package com.ticket.booking.event.dto;

import lombok.Data;

@Data
public class BookingResponseDto {

    private Long id;
    private Long eventId;
    private Long userId;
    private Integer seatsBooked;
}
