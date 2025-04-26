package com.ticket.booking.event.repository;

import com.ticket.booking.event.model.Booking;

import java.util.List;
import java.util.Optional;

public interface BookingRepository {

    Booking save(Booking booking);
    Optional<Booking> findById(Long bookingId);
    Optional<Booking> findByEventIdAndUserId(Long eventId, Long userId);
    List<Booking> findAll();
    void cancel(Long bookingId);
}
