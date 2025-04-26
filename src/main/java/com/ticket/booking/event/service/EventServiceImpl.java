package com.ticket.booking.event.service;

import com.ticket.booking.event.dto.EventRequestDto;
import com.ticket.booking.event.dto.EventResponseDto;
import com.ticket.booking.event.exception.EventException;
import com.ticket.booking.event.mapper.EventMapper;
import com.ticket.booking.event.model.Event;
import com.ticket.booking.event.repository.EventRepository;
import com.ticket.booking.event.service.factory.SortStrategyFactory;
import com.ticket.booking.event.service.strategy.SortStrategy;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class EventServiceImpl implements EventService {

    private final EventRepository eventRepository;
    private final SortStrategyFactory sortStrategyFactory;

    public EventServiceImpl(EventRepository eventRepository, SortStrategyFactory sortStrategyFactory) {
        this.eventRepository = eventRepository;
        this.sortStrategyFactory = sortStrategyFactory;
    }

    @Override
    public EventResponseDto createEvent(EventRequestDto eventRequestDto) {
        Event event = EventMapper.toEntity(eventRequestDto);
        Event savedEvent = eventRepository.save(event);
        return EventMapper.toResponseDto(savedEvent);
    }

    @Override
    public EventResponseDto getEventById(Long eventId) {
        Event event = eventRepository.findById(eventId)
                .orElseThrow(() -> new EventException("Event not found with id: " + eventId));
        return EventMapper.toResponseDto(event);
    }

    @Override
    public List<EventResponseDto> getAllEvents(String sortBy) {
        List<Event> events = eventRepository.findAll();
        SortStrategy sortStrategy = sortStrategyFactory.getStrategy(sortBy);
        List<Event> sortedEvents = sortStrategy.sort(events);
        return sortedEvents.stream()
                .map(EventMapper::toResponseDto)
                .collect(Collectors.toList());
    }
}
