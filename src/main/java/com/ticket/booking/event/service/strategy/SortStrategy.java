package com.ticket.booking.event.service.strategy;

import com.ticket.booking.event.model.Event;

import java.util.List;

public interface SortStrategy {

    List<Event> sort(List<Event> events);
}
