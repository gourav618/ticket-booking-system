package com.ticket.booking.event.service;

import com.ticket.booking.event.dto.BookingResponseDto;
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
    private final BookingService bookingService;

    public EventServiceImpl(EventRepository eventRepository, SortStrategyFactory sortStrategyFactory, BookingService bookingService) {
        this.eventRepository = eventRepository;
        this.sortStrategyFactory = sortStrategyFactory;
        this.bookingService = bookingService;
    }

    /**
     * Creates a new event.
     *
     * @param eventRequestDto The event request dto.
     * @return The event response dto.
     */
    @Override
    public EventResponseDto createEvent(EventRequestDto eventRequestDto) {
        Event event = EventMapper.toEntity(eventRequestDto);
        Event savedEvent = eventRepository.save(event);
        return EventMapper.toResponseDto(savedEvent);
    }

    /**
     * Retrieves an event by its ID.
     *
     * @param eventId The ID of the event to retrieve.
     * @return The event response dto.
     * @throws EventException If the event is not found.
     */
    @Override
    public EventResponseDto getEventById(Long eventId) {
        Event event = eventRepository.findById(eventId)
                .orElseThrow(() -> new EventException("Event not found with id: " + eventId));
        return EventMapper.toResponseDto(event);
    }

    /**
     * Retrieves all events, optionally sorted by a specified strategy.
     *
     * @param sortBy The field to sort the events by (optional).
     * @return A list of event response dto.
     */
    @Override
    public List<EventResponseDto> getAllEvents(String sortBy) {
        List<Event> events = eventRepository.findAll();
        SortStrategy sortStrategy = sortStrategyFactory.getStrategy(sortBy);
        List<Event> sortedEvents = sortStrategy.sort(events);
        return sortedEvents.stream()
                .map(EventMapper::toResponseDto)
                .collect(Collectors.toList());
    }

    @Override
    public void deleteEvent(Long eventId) {
        Event event = eventRepository.findById(eventId)
                .orElseThrow(() -> new EventException("Event not found with id: " + eventId));
        //get all the booking fo this event and cancel the booking
        List<BookingResponseDto> bookingsByEventId = bookingService.getBookingsByEventId(eventId);
        bookingsByEventId.forEach(booking -> bookingService.cancelBooking(booking.getId()));
        //delete event
        eventRepository.delete(event.getId());
    }
}
