package com.ticket.booking.event.service;

import com.ticket.booking.event.dto.EventRequestDto;
import com.ticket.booking.event.dto.EventResponseDto;

import java.util.List;

public interface EventService {

    EventResponseDto createEvent(EventRequestDto eventRequestDto);
    EventResponseDto getEventById(Long eventId);
    List<EventResponseDto> getAllEvents(String sortBy);

    void deleteEvent(Long eventId);
}
