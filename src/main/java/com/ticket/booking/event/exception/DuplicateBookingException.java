package com.ticket.booking.event.exception;

public class DuplicateBookingException extends RuntimeException{

    public DuplicateBookingException(String message) {
        super(message);
    }
}
