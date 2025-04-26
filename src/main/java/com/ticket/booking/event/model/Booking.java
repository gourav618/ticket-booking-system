package com.ticket.booking.event.model;

import com.ticket.booking.event.enums.BookingStatus;
import lombok.Data;

@Data
public class Booking {
    private Long id;
    private Long eventId;
    private Long userId;
    private Integer seatsBooked;
    private BookingStatus status = BookingStatus.ACTIVE;
}
