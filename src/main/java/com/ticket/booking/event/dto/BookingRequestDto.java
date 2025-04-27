package com.ticket.booking.event.dto;

import lombok.*;

@Data
@Builder
public class BookingRequestDto {

    private Long eventId;
    private Long userId;
    private Integer seatsBooked;
}
