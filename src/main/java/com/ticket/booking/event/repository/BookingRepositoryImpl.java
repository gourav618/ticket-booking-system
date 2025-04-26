package com.ticket.booking.event.repository;

import com.ticket.booking.event.enums.BookingStatus;
import com.ticket.booking.event.exception.DuplicateBookingException;
import com.ticket.booking.event.model.Booking;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

@Repository
public class BookingRepositoryImpl implements BookingRepository {

    private final Map<Long, Booking> bookings = new ConcurrentHashMap<>();
    private final AtomicLong idGenerator = new AtomicLong(1);

    /**
     * Saves a booking to the repository. If the booking does not have an ID, a new ID is generated.
     *
     * @param booking the booking to save
     * @return the saved booking with an assigned ID
     */
    @Override
    public Booking save(Booking booking) {
        if (booking.getId() == null) {
            booking.setId(idGenerator.getAndIncrement());
        }
        bookings.put(booking.getId(), booking);
        return booking;
    }

    /**
     * Finds a booking by its ID.
     *
     * @param bookingId the ID of the booking to find
     * @return an Optional containing the booking if found, or empty if not found
     */
    @Override
    public Optional<Booking> findById(Long bookingId) {
        return Optional.ofNullable(bookings.get(bookingId));
    }

    /**
     * Finds a booking by event ID and user ID, excluding canceled bookings.
     *
     * @param eventId the ID of the event
     * @param userId the ID of the user
     * @return an Optional containing the booking if found, or empty if not found
     */
    @Override
    public Optional<Booking> findByEventIdAndUserId(Long eventId, Long userId) {
        return bookings.values().stream()
                .filter(booking ->
                        booking.getUserId().equals(userId) &&
                                booking.getEventId().equals(eventId) &&
                                !booking.getStatus().equals(BookingStatus.CANCELED)
                )
                .findFirst();
    }

    /**
     * Retrieves all bookings, excluding canceled bookings.
     *
     * @return a list of all active bookings
     */
    @Override
    public List<Booking> findAll() {
        return bookings.values().stream()
                .filter(b -> b.getStatus() != BookingStatus.CANCELED)
                .collect(Collectors.toList());
    }

    /**
     * Cancels a booking by its ID. If the booking exists, its status is set to CANCELED.
     *
     * @param bookingId the ID of the booking to cancel
     */
    @Override
    public void cancel(Long bookingId) {
        Booking booking = bookings.get(bookingId);
        if (booking != null) {
            booking.setStatus(BookingStatus.CANCELED);
        }
    }
}
