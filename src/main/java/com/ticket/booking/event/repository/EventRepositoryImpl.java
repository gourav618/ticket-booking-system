package com.ticket.booking.event.repository;

import com.ticket.booking.event.model.Event;
import org.springframework.stereotype.Repository;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

@Repository
public class EventRepositoryImpl implements EventRepository {

    private final Map<Long, Event> events = new ConcurrentHashMap<>();
    private final AtomicLong idGenerator = new AtomicLong(1);

    /**
     * Saves an event to the repository. If the event does not have an ID, a new ID is generated.
     *
     * @param event the event to save
     * @return the saved event with an assigned ID
     */
    @Override
    public Event save(Event event) {
        if (event.getId() == null) {
            event.setId(idGenerator.getAndIncrement());
        }
        events.put(event.getId(), event);
        return event;
    }

    /**
     * Finds an event by its ID.
     *
     * @param eventId the ID of the event to find
     * @return an Optional containing the event if found, or empty if not found
     */
    @Override
    public Optional<Event> findById(Long eventId) {
        return Optional.ofNullable(events.get(eventId));
    }

    /**
     * Retrieves all events stored in the repository.
     *
     * @return a list of all events
     */
    @Override
    public List<Event> findAll() {
        return new ArrayList<>(events.values());
    }

    /**
     * Deletes an event by its ID.
     *
     * @param eventId the ID of the event to delete
     */
    @Override
    public void delete(Long eventId) {
        events.remove(eventId);
    }
}
