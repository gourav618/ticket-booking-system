package com.ticket.booking.event.controller;

import com.ticket.booking.event.dto.EventRequestDto;
import com.ticket.booking.event.dto.EventResponseDto;
import com.ticket.booking.event.service.EventService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/events")
public class EventController {

    private final EventService eventService;

    public EventController(EventService eventService) {
        this.eventService = eventService;
    }

    /**
     * Creates a new event.
     *
     * @param eventRequestDto the event request dto containing event details
     * @return a ResponseEntity containing the created event response dto
     */
    @PostMapping
    public ResponseEntity<EventResponseDto> createEvent(@RequestBody EventRequestDto eventRequestDto) {
        return ResponseEntity.ok(eventService.createEvent(eventRequestDto));
    }

    /**
     * Retrieves a list of all events, optionally sorted by a specified field.
     *
     * @param sortBy optional parameter to specify sorting strategy: availability, date and location
     * @return a ResponseEntity containing a list of event response dto
     */
    @GetMapping
    public ResponseEntity<List<EventResponseDto>> getAllEvents(@RequestParam(required = false) String sortBy) {
        return ResponseEntity.ok(eventService.getAllEvents(sortBy));
    }

    /**
     * Retrieves a specific event by its ID.
     *
     * @param id the ID of the event to retrieve
     * @return a ResponseEntity containing the event response dto
     */
    @GetMapping("/{id}")
    public ResponseEntity<EventResponseDto> getEvent(@PathVariable Long id) {
        return ResponseEntity.ok(eventService.getEventById(id));
    }
}
