package com.ticket.booking.event.controller;

import com.ticket.booking.event.dto.EventRequestDto;
import com.ticket.booking.event.dto.EventResponseDto;
import com.ticket.booking.event.service.EventService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Events", description = "Operations related to Events")
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
    @Operation(summary = "create event")
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
    @Operation(summary = "get all events with sortBy options Availability, date and location")
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
    @Operation(summary = "get event with eventId")
    @GetMapping("/{id}")
    public ResponseEntity<EventResponseDto> getEvent(@PathVariable Long id) {
        return ResponseEntity.ok(eventService.getEventById(id));
    }

    @Operation(summary = "delete event with eventId")
    @DeleteMapping("/{id}")
    public void deleteEvent(@PathVariable Long id) {
        eventService.deleteEvent(id);
    }
}
