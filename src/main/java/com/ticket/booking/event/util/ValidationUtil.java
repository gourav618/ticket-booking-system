package com.ticket.booking.event.util;

import com.ticket.booking.event.exception.BookingException;
import com.ticket.booking.event.model.Event;

public class ValidationUtil {

    private ValidationUtil(){
    }
    public static void validateBooking(Event event, Long userId, Integer seatsRequested) {
        // check if enough seats available
        if ((event.getTotalSeats() - event.getBookedSeats()) < seatsRequested) {
            throw new BookingException("Not enough seats available");
        }
    }
}
