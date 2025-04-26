package com.ticket.booking.event.mapper;

import com.ticket.booking.event.dto.EventRequestDto;
import com.ticket.booking.event.dto.EventResponseDto;
import com.ticket.booking.event.model.Event;

public class EventMapper {

    private EventMapper() {
    }

    public static Event toEntity(EventRequestDto dto) {
        Event event = new Event();
        event.setName(dto.getName());
        event.setDate(dto.getDate());
        event.setLocation(dto.getLocation());
        event.setTotalSeats(dto.getTotalSeats());
        return event;
    }

    public static EventResponseDto toResponseDto(Event event) {
        EventResponseDto dto = new EventResponseDto();
        dto.setId(event.getId());
        dto.setName(event.getName());
        dto.setDate(event.getDate());
        dto.setLocation(event.getLocation());
        dto.setTotalSeats(event.getTotalSeats());
        dto.setBookedSeats(event.getBookedSeats());
        return dto;
    }
}
