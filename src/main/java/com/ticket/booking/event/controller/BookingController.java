package com.ticket.booking.event.controller;

import com.ticket.booking.event.dto.BookingRequestDto;
import com.ticket.booking.event.dto.BookingResponseDto;
import com.ticket.booking.event.service.BookingService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/bookings")
public class BookingController {

    private final BookingService bookingService;

    public BookingController(BookingService bookingService) {
        this.bookingService = bookingService;
    }

    /**
     * Creates a new booking.
     *
     * @param bookingRequestDto the booking request data transfer object containing booking details
     * @return a ResponseEntity containing the created booking response data transfer object
     */
    @PostMapping
    public ResponseEntity<BookingResponseDto> createBooking(@RequestBody BookingRequestDto bookingRequestDto) {
        return ResponseEntity.ok(bookingService.createBooking(bookingRequestDto));
    }

    /**
     * Retrieves a list of bookings based on optional filters.
     *
     * @param bookingId optional filter for booking ID
     * @param eventId optional filter for event ID
     * @param userId optional filter for user ID
     * @return a ResponseEntity containing a list of booking response data transfer objects
     */
    @GetMapping
    public ResponseEntity<List<BookingResponseDto>> getBookings(
            @RequestParam(required = false) Long bookingId,
            @RequestParam(required = false) Long eventId,
            @RequestParam(required = false) Long userId) {

        List<BookingResponseDto> bookings = bookingService.getBookings(bookingId, eventId, userId);
        return ResponseEntity.ok(bookings);
    }

    /**
     * Cancels a booking by its ID.
     *
     * @param id the ID of the booking to cancel
     */
    @DeleteMapping("/{id}")
    public void cancelBooking(@PathVariable Long id) {
        bookingService.cancelBooking(id);
    }
}
