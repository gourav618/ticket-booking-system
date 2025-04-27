package com.ticket.booking.event.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class BookingResponseDto {

    private Long id;
    private Long eventId;
    private Long userId;
    private Integer seatsBooked;
}
