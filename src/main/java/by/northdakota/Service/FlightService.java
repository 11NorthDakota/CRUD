package by.northdakota.Service;

import by.northdakota.Dao.FlightDao;
import by.northdakota.Dto.FlightDto;

import java.util.List;
import java.util.stream.Collectors;

public class FlightService {
    private final static FlightService INSTANCE = new FlightService();
    private final FlightDao flightDao = FlightDao.getInstance();

    public static FlightService getInstance() {
        return INSTANCE;
    }

    public List<FlightDto> findAll(){
        return flightDao.findAll().stream().map(
                flight->
                        new FlightDto(
                                flight.getId(),
                                "%s - %s - %s".formatted(
                                        flight.getArrivalAirportCode(),
                                        flight.getDepartureAirportCode(),
                                        flight.getStatus()
                                )
                        )
        ).collect(Collectors.toList());
    }

    private FlightService() {}
}
