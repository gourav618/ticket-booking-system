package com.ticket.booking.event.service;

import com.ticket.booking.event.dto.BookingRequestDto;
import com.ticket.booking.event.dto.BookingResponseDto;

import java.util.List;

public interface BookingService {

    BookingResponseDto createBooking(BookingRequestDto bookingRequestDto);
    void cancelBooking(Long bookingId);

    List<BookingResponseDto> getBookings(Long bookingId, Long eventId, Long userId);
    List<BookingResponseDto> getBookingsByEventId(Long eventId);
}
