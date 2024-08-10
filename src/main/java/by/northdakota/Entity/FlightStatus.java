package by.northdakota.Entity;

public enum FlightStatus {
    ARRIVED,
    SCHEDULED,
    CANCELLED,
    DEPARTED;

    public static FlightStatus getFlightStatus(String status) {
        return switch (status) {
            case "ARRIVED" -> ARRIVED;
            case "SCHEDULED" -> SCHEDULED;
            case "DEPARTED" -> DEPARTED;
            default -> CANCELLED;
        };
    }
}
