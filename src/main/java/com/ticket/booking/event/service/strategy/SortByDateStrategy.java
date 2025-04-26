package com.ticket.booking.event.service.strategy;

import com.ticket.booking.event.model.Event;
import org.springframework.stereotype.Component;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class SortByDateStrategy implements SortStrategy{
    @Override
    public List<Event> sort(List<Event> events) {
        return events.stream()
                .sorted(Comparator.comparing(Event::getDate))
                .collect(Collectors.toList());
    }
}
