package by.northdakota.Service;

import by.northdakota.Dao.TicketDao;
import by.northdakota.Dto.TicketDto;

import java.util.List;
import java.util.stream.Collectors;

public class TicketService {
    private final static TicketService INSTANCE = new TicketService();

    private final TicketDao ticketDao = TicketDao.getInstance();

    public static TicketService getInstance() {
        return INSTANCE;
    }

    public List<TicketDto> findAllByTicketId(Long flightId) {
        return ticketDao.findAllByFlightId(flightId).stream().map(
                flight ->  new TicketDto(
                        flight.getId(),
                        flight.getFlightId(),
                        flight.getSeatNo()
                )

        ).collect(Collectors.toList());
    }

    private TicketService() {};
}
