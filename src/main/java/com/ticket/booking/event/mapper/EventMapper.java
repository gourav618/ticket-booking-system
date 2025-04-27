package com.ticket.booking.event.mapper;

import com.ticket.booking.event.dto.EventRequestDto;
import com.ticket.booking.event.dto.EventResponseDto;
import com.ticket.booking.event.model.Event;

public class EventMapper {

    private EventMapper() {
    }

    public static Event toEntity(EventRequestDto dto) {
        return Event.builder().name(dto.getName())
                .date(dto.getDate())
                .location(dto.getLocation())
                .totalSeats(dto.getTotalSeats())
                .bookedSeats(0)
                .build();
    }

    public static EventResponseDto toResponseDto(Event event) {
        return EventResponseDto.builder().id(event.getId())
                .name(event.getName())
                .date(event.getDate())
                .location(event.getLocation())
                .totalSeats(event.getTotalSeats())
                .bookedSeats(event.getBookedSeats())
                .build();
    }
}
