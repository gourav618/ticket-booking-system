package com.ticket.booking.event.service;

import com.ticket.booking.event.dto.EventRequestDto;
import com.ticket.booking.event.dto.EventResponseDto;
import com.ticket.booking.event.enums.City;
import com.ticket.booking.event.exception.EventException;
import com.ticket.booking.event.model.Event;
import com.ticket.booking.event.repository.EventRepository;
import com.ticket.booking.event.service.factory.SortStrategyFactory;
import com.ticket.booking.event.service.strategy.SortStrategy;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class EventServiceImplTest {

    @Mock
    private EventRepository eventRepository;

    @Mock
    private SortStrategyFactory sortStrategyFactory;

    @InjectMocks
    private EventServiceImpl eventServiceImpl;

    public EventServiceImplTest() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void shouldCreateEventSuccessfully() {
        EventRequestDto eventRequestDto = EventRequestDto.builder().name("Conference").date(LocalDateTime.of(2023, 10, 20, 0, 0))
                .location(City.Bangalore).totalSeats(100).build();
        Event savedEvent = Event.builder().id(1L).name("Conference").date(LocalDateTime.of(2023, 10, 20, 0, 0))
                .location(City.Bangalore).totalSeats(100).bookedSeats(0).build();

        when(eventRepository.save(any())).thenReturn(savedEvent);

        EventResponseDto result = eventServiceImpl.createEvent(eventRequestDto);

        assertNotNull(result);
        assertEquals(savedEvent.getId(), result.getId());
        assertEquals(savedEvent.getName(), result.getName());
        assertEquals(savedEvent.getDate(), result.getDate());
    }

    @Test
    void shouldRetrieveEventByIdSuccessfully() {
        Event event = Event.builder().id(1L).name("Seminar").date(LocalDateTime.of(2023, 11, 20, 0, 0)).location(City.Bangalore).totalSeats(100).bookedSeats(0).build();
        when(eventRepository.findById(1L)).thenReturn(Optional.of(event));

        EventResponseDto result = eventServiceImpl.getEventById(1L);

        assertNotNull(result);
        assertEquals(event.getId(), result.getId());
        assertEquals(event.getName(), result.getName());
        assertEquals(event.getDate(), result.getDate());
    }

    @Test
    void shouldThrowExceptionWhenEventNotFoundById() {
        when(eventRepository.findById(99L)).thenReturn(Optional.empty());

        assertThrows(EventException.class, () -> eventServiceImpl.getEventById(99L));
    }

    @Test
    void shouldRetrieveAllEventsSortedSuccessfully() {
        Event event1 = Event.builder().id(1L).name("Workshop").date(LocalDateTime.of(2023, 9, 15, 0, 0)).location(City.Bangalore).totalSeats(100).bookedSeats(0).build();
        Event event2 = Event.builder().id(2L).name("Conference").date(LocalDateTime.of(2023, 10, 20, 0, 0)).location(City.Bangalore).totalSeats(100).bookedSeats(0).build();
        List<Event> events = Arrays.asList(event1, event2);

        SortStrategy sortStrategy = mock(SortStrategy.class);
        when(eventRepository.findAll()).thenReturn(events);
        when(sortStrategyFactory.getStrategy("name")).thenReturn(sortStrategy);
        when(sortStrategy.sort(events)).thenReturn(Arrays.asList(event2, event1));

        List<EventResponseDto> result = eventServiceImpl.getAllEvents("name");

        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals(event2.getName(), result.get(0).getName());
        assertEquals(event1.getName(), result.get(1).getName());
    }

    @Test
    void shouldRetrieveAllEventsWithoutSorting() {
        Event event1 = Event.builder().id(1L).name("Workshop").date(LocalDateTime.of(2023, 9, 15, 0, 0)).location(City.Bangalore).totalSeats(100).bookedSeats(0).build();
        Event event2 = Event.builder().id(2L).name("Conference").date(LocalDateTime.of(2023, 10, 20, 0, 0)).location(City.Bangalore).totalSeats(100).bookedSeats(0).build();
        List<Event> events = Arrays.asList(event1, event2);

        SortStrategy sortStrategy = mock(SortStrategy.class);
        when(eventRepository.findAll()).thenReturn(events);
        when(sortStrategyFactory.getStrategy(null)).thenReturn(sortStrategy);
        when(sortStrategy.sort(events)).thenReturn(events);

        List<EventResponseDto> result = eventServiceImpl.getAllEvents(null);

        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals(event1.getName(), result.get(0).getName());
        assertEquals(event2.getName(), result.get(1).getName());
    }
}