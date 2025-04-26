package com.ticket.booking.event.service.factory;

import com.ticket.booking.event.service.strategy.SortByAvailabilityStrategy;
import com.ticket.booking.event.service.strategy.SortByDateStrategy;
import com.ticket.booking.event.service.strategy.SortByLocationStrategy;
import com.ticket.booking.event.service.strategy.SortStrategy;
import org.springframework.stereotype.Component;

@Component
public class SortStrategyFactory {

    private final SortByAvailabilityStrategy sortByAvailabilityStrategy;
    private final SortByDateStrategy sortByDateStrategy;
    private final SortByLocationStrategy sortByLocationStrategy;

    public SortStrategyFactory(SortByAvailabilityStrategy sortByAvailabilityStrategy, SortByDateStrategy sortByDateStrategy, SortByLocationStrategy sortByLocationStrategy) {
        this.sortByAvailabilityStrategy = sortByAvailabilityStrategy;
        this.sortByDateStrategy = sortByDateStrategy;
        this.sortByLocationStrategy = sortByLocationStrategy;
    }

    /**
     * Returns the appropriate sorting strategy based on the provided sort type.
     * If the sort type is null or blank, the default strategy (SortByAvailabilityStrategy) is returned.
     *
     * @param sortBy The type of sorting to be applied (e.g., "availability", "date", "location").
     * @return The corresponding SortStrategy implementation.
     * @throws IllegalArgumentException If the sort type is invalid.
     */
    public SortStrategy getStrategy(String sortBy) {
        if (sortBy == null || sortBy.isBlank()) {
            return sortByAvailabilityStrategy; // Default
        }

        switch (sortBy.toLowerCase()) {
            case "availability":
                return sortByAvailabilityStrategy;
            case "date":
                return sortByDateStrategy;
            case "location":
                return sortByLocationStrategy;
            default:
                throw new IllegalArgumentException("Invalid sort type: " + sortBy);
        }
    }
}
