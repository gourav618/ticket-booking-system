package com.ticket.booking.event.repository;

import com.ticket.booking.event.model.Event;

import java.util.List;
import java.util.Optional;

public interface EventRepository {

    Event save(Event event);
    Optional<Event> findById(Long eventId);
    List<Event> findAll();
    void delete(Long eventId);

}
