package com.ticket.booking.event.dto;

import lombok.*;

@Data
public class BookingRequestDto {

    private Long eventId;
    private Long userId;
    private Integer seatsBooked;
}
