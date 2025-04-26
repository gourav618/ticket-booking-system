package com.ticket.booking.event.mapper;

import com.ticket.booking.event.dto.BookingRequestDto;
import com.ticket.booking.event.dto.BookingResponseDto;
import com.ticket.booking.event.model.Booking;

public class BookingMapper {

    private BookingMapper() {
    }

    public static Booking toEntity(BookingRequestDto dto) {
        Booking booking = new Booking();
        booking.setEventId(dto.getEventId());
        booking.setUserId(dto.getUserId());
        booking.setSeatsBooked(dto.getSeatsBooked());
        return booking;
    }

    public static BookingResponseDto toResponseDto(Booking booking) {
        BookingResponseDto dto = new BookingResponseDto();
        dto.setId(booking.getId());
        dto.setEventId(booking.getEventId());
        dto.setUserId(booking.getUserId());
        dto.setSeatsBooked(booking.getSeatsBooked());
        return dto;
    }
}
