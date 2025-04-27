package com.ticket.booking.event.mapper;

import com.ticket.booking.event.dto.BookingRequestDto;
import com.ticket.booking.event.dto.BookingResponseDto;
import com.ticket.booking.event.enums.BookingStatus;
import com.ticket.booking.event.model.Booking;

public class BookingMapper {

    private BookingMapper() {
    }

    public static Booking toEntity(BookingRequestDto dto) {
        return Booking.builder()
                .eventId(dto.getEventId())
                .userId(dto.getUserId())
                .seatsBooked(dto.getSeatsBooked())
                .status(BookingStatus.ACTIVE)
                .build();
    }

    public static BookingResponseDto toResponseDto(Booking booking) {
        return BookingResponseDto.builder()
                .id(booking.getId())
                .eventId(booking.getEventId())
                .userId(booking.getUserId())
                .seatsBooked(booking.getSeatsBooked())
                .build();
    }
}
