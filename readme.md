# Ticket Booking System

This is a Spring Boot-based backend API for managing events and ticket bookings. It provides endpoints for creating, retrieving, updating, and deleting events and bookings, along with sorting and filtering capabilities.

## Features

- **Event Management**: Create, retrieve, delete, and list events with optional sorting by availability, date, or location.
- **Booking Management**: Create, retrieve, and cancel bookings with optional filters for booking ID, event ID, and user ID.
- **Swagger Integration**: API documentation is available via Swagger UI.

## Project Structure

- `src/main/java/com/ticket/booking/event/model/`: Contains the data models for the application.
- `src/main/java/com/ticket/booking/event/service/`: Contains service interfaces and implementations for business logic.
- `src/main/java/com/ticket/booking/event/controller/`: Contains REST controllers for handling API requests.
- `src/main/java/com/ticket/booking/event/repository/`: Contains repository interface and implementations for database layer.
- `src/main/java/com/ticket/booking/event/config/`: Contains configuration files, including Swagger configuration.
- `src/main/java/com/ticket/booking/event/exception/`: Contains global exceptions.

## Endpoints

### Event Endpoints

- **Create Event**: `POST /events`
- **Get All Events**: `GET /events?sortBy={field}`
- **Get Event by ID**: `GET /events/{id}`
- **Delete Event**: `DELETE /events/{id}`

### Booking Endpoints

- **Create/Update Booking**: `POST /bookings`
- **Get All Bookings**: `GET /bookings?bookingId={id}&eventId={id}&userId={id}`
- **Cancel Booking**: `DELETE /bookings/{id}`

## Setup Instructions

1. Clone the repository:
   ```bash
   git clone https://github.com/gourav-aphinity/ticket-booking-system.git
   cd ticket-booking-system

2. Build the project using Maven:  
   ```bash
   mvn clean instal
  
3. Run the application:  
   ```bash
   mvn spring-boot:run
   
4. Access the Swagger UI for API documentation:
   ```bash
   http://localhost:8080/swagger-ui.html
   