package by.northdakota.Entity;

import lombok.*;

import java.time.LocalDateTime;

@AllArgsConstructor
@EqualsAndHashCode
@Getter
@Setter
@ToString
public class Flight {
    private Long id;
    private String flightNumber;
    private LocalDateTime departure;
    private String departureAirportCode;
    private LocalDateTime arrival;
    private String arrivalAirportCode;
    private Long aircraftId;
    private FlightStatus status;
}
