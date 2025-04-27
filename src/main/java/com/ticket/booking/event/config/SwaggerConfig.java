package com.ticket.booking.event.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.tags.Tag;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI ticketBookingAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Ticket Booking System API")
                        .description("Backend API for managing events and ticket bookings")
                        .version("1.0"))
                .addTagsItem(new Tag().name("Events").description("Operations related to Events"))
                .addTagsItem(new Tag().name("Bookings").description("Operations related to Bookings"));
    }
}
