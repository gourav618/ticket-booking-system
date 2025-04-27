package com.ticket.booking.event.model;

import com.ticket.booking.event.enums.BookingStatus;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Booking {
    private Long id;
    private Long eventId;
    private Long userId;
    private Integer seatsBooked;
    private BookingStatus status;
}
