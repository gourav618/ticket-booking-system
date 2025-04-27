package com.ticket.booking.event.service;

import com.ticket.booking.event.dto.BookingRequestDto;
import com.ticket.booking.event.dto.BookingResponseDto;
import com.ticket.booking.event.enums.BookingStatus;
import com.ticket.booking.event.exception.BookingException;
import com.ticket.booking.event.exception.DuplicateBookingException;
import com.ticket.booking.event.exception.EventException;
import com.ticket.booking.event.model.Booking;
import com.ticket.booking.event.model.Event;
import com.ticket.booking.event.repository.BookingRepository;
import com.ticket.booking.event.repository.EventRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class BookingServiceImplTest {


    @Mock
    private BookingRepository bookingRepository;

    @Mock
    private EventRepository eventRepository;

    @InjectMocks
    private BookingServiceImpl bookingService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void createsBookingSuccessfully() {
        BookingRequestDto bookingRequestDto = BookingRequestDto.builder().eventId(1L).userId(1L).seatsBooked(2).build();

        Event event = Event.builder().id(1L).totalSeats(100).bookedSeats(1).build();

        Booking booking = Booking.builder().id(1L).eventId(1L).seatsBooked(2).build();

        BookingResponseDto bookingResponseDto = BookingResponseDto.builder().id(1L).build();

        when(eventRepository.findById(1L)).thenReturn(Optional.of(event));
        when(bookingRepository.findByEventIdAndUserId(1L, 1L)).thenReturn(Optional.empty());
        when(bookingRepository.save(any(Booking.class))).thenReturn(booking);
        when(eventRepository.save(any(Event.class))).thenReturn(event);

        BookingResponseDto result = bookingService.createBooking(bookingRequestDto);

        verify(eventRepository, times(1)).findById(1L);
        verify(bookingRepository, times(1)).findByEventIdAndUserId(1L, 1L);
        verify(bookingRepository, times(1)).save(any(Booking.class));
        verify(eventRepository, times(1)).save(any(Event.class));

        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals(3, event.getBookedSeats());

    }

    @Test
    void createsBookingThrowsEventNotFoundException() {
        BookingRequestDto bookingRequestDto = BookingRequestDto.builder().eventId(1L).userId(1L).seatsBooked(2).build();

        when(eventRepository.findById(1L)).thenReturn(Optional.empty());

        EventException exception = assertThrows(EventException.class, () ->
                bookingService.createBooking(bookingRequestDto)
        );

        assertEquals("Event not found", exception.getMessage());
        verify(eventRepository, times(1)).findById(1L);
        verifyNoInteractions(bookingRepository);
    }

    @Test
    void createsBookingThrowsDuplicateBookingException() {
        BookingRequestDto bookingRequestDto = BookingRequestDto.builder().eventId(1L).userId(1L).seatsBooked(2).build();

        Event event = Event.builder().id(1L).totalSeats(100).bookedSeats(1).build();;

        when(eventRepository.findById(1L)).thenReturn(Optional.of(event));
        when(bookingRepository.findByEventIdAndUserId(1L, 1L)).thenReturn(Optional.of(Booking.builder().build()));

        DuplicateBookingException exception = assertThrows(DuplicateBookingException.class, () ->
                bookingService.createBooking(bookingRequestDto)
        );

        assertEquals("User has already booked this event", exception.getMessage());
        verify(eventRepository, times(1)).findById(1L);
        verify(bookingRepository, times(1)).findByEventIdAndUserId(1L, 1L);
    }

    @Test
    void cancelsBookingSuccessfully() {
        Booking booking = Booking.builder().id(1L).eventId(1L).seatsBooked(2).status(BookingStatus.ACTIVE).build();

        Event event = Event.builder().id(1L).bookedSeats(10).build();

        when(bookingRepository.findById(1L)).thenReturn(Optional.of(booking));
        when(eventRepository.findById(1L)).thenReturn(Optional.of(event));

        bookingService.cancelBooking(1L);

        verify(bookingRepository, times(1)).cancel(1L);
        verify(eventRepository, times(1)).save(event);
        assertEquals(8, event.getBookedSeats());
    }

    @Test
    void cancelsBookingThrowsBookingNotFoundException() {
        when(bookingRepository.findById(1L)).thenReturn(Optional.empty());

        BookingException exception = assertThrows(BookingException.class, () ->
                bookingService.cancelBooking(1L)
        );

        assertEquals("Booking not found", exception.getMessage());
        verify(bookingRepository, times(1)).findById(1L);
        verifyNoInteractions(eventRepository);
    }

    @Test
    void cancelsBookingThrowsAlreadyCanceledException() {
        Booking booking = Booking.builder().id(1L).status(BookingStatus.CANCELED).build();

        when(bookingRepository.findById(1L)).thenReturn(Optional.of(booking));

        BookingException exception = assertThrows(BookingException.class, () ->
                bookingService.cancelBooking(1L)
        );

        assertEquals("Booking is already canceled", exception.getMessage());
        verify(bookingRepository, times(1)).findById(1L);
        verifyNoInteractions(eventRepository);
    }

}