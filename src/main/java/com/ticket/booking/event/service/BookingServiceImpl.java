package com.ticket.booking.event.service;

import com.ticket.booking.event.dto.BookingRequestDto;
import com.ticket.booking.event.dto.BookingResponseDto;
import com.ticket.booking.event.enums.BookingStatus;
import com.ticket.booking.event.exception.BookingException;
import com.ticket.booking.event.exception.DuplicateBookingException;
import com.ticket.booking.event.exception.EventException;
import com.ticket.booking.event.mapper.BookingMapper;
import com.ticket.booking.event.model.Booking;
import com.ticket.booking.event.model.Event;
import com.ticket.booking.event.repository.BookingRepository;
import com.ticket.booking.event.repository.EventRepository;
import com.ticket.booking.event.util.ValidationUtil;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class BookingServiceImpl implements BookingService {

    private final BookingRepository bookingRepository;
    private final EventRepository eventRepository;

    public BookingServiceImpl(BookingRepository bookingRepository, EventRepository eventRepository) {
        this.bookingRepository = bookingRepository;
        this.eventRepository = eventRepository;
    }

    /**
     * Creates a new booking for an event.
     *
     * @param bookingRequestDto The booking request dto.
     * @return The booking response dto.
     * @throws EventException              If the event is not found.
     * @throws DuplicateBookingException   If the user has already booked the event.
     */
    @Override
    public BookingResponseDto createBooking(BookingRequestDto bookingRequestDto) {
        Event event = eventRepository.findById(bookingRequestDto.getEventId())
                .orElseThrow(() -> new EventException("Event not found"));

        //check if booking exist for eventId, userId
        Optional<Booking> existingBooking = bookingRepository.findByEventIdAndUserId(bookingRequestDto.getEventId(), bookingRequestDto.getUserId());
        if (existingBooking.isPresent()) {
            throw new DuplicateBookingException("User has already booked this event");
        }
        //check if enough seats
        ValidationUtil.validateBooking(event, bookingRequestDto.getUserId(), bookingRequestDto.getSeatsBooked());

        // Save booking
        Booking booking = BookingMapper.toEntity(bookingRequestDto);
        Booking savedBooking = bookingRepository.save(booking);

        // Update event seats
        event.setBookedSeats(event.getBookedSeats() + booking.getSeatsBooked());
        eventRepository.save(event);

        return BookingMapper.toResponseDto(savedBooking);
    }

    /**
     * Cancels an existing booking.
     *
     * @param bookingId The ID of the booking to cancel.
     * @throws BookingException If the booking is not found or already canceled.
     * @throws EventException   If the associated event is not found.
     */
    @Override
    public void cancelBooking(Long bookingId) {
        Booking booking = bookingRepository.findById(bookingId)
                .orElseThrow(() -> new BookingException("Booking not found"));

        if (booking.getStatus() == BookingStatus.CANCELED) {
            throw new BookingException("Booking is already canceled");
        }

        bookingRepository.cancel(bookingId);

        // increment cancelled seats back to event
        Event event = eventRepository.findById(booking.getEventId())
                .orElseThrow(() -> new EventException("Event not found"));
        event.setBookedSeats(event.getBookedSeats() - booking.getSeatsBooked());
        eventRepository.save(event);
    }

    /**
     * Retrieves a list of bookings based on optional filters.
     *
     * @param bookingId The ID of the booking to filter by (optional).
     * @param eventId   The ID of the event to filter by (optional).
     * @param userId    The ID of the user to filter by (optional).
     * @return A list of booking response dto.
     */
    @Override
    public List<BookingResponseDto> getBookings(Long bookingId, Long eventId, Long userId) {
        List<Booking> bookings = bookingRepository.findAll(); // In-memory case

        Stream<Booking> stream = bookings.stream()
                .filter(booking -> !booking.getStatus().equals(BookingStatus.CANCELED)); // Only active bookings

        if (bookingId != null) {
            stream = stream.filter(booking -> booking.getId().equals(bookingId));
        }
        if (eventId != null) {
            stream = stream.filter(booking -> booking.getEventId().equals(eventId));
        }
        if (userId != null) {
            stream = stream.filter(booking -> booking.getUserId().equals(userId));
        }

        return stream
                .map(BookingMapper::toResponseDto)
                .collect(Collectors.toList());
    }
}
