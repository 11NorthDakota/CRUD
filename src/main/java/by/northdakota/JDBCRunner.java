package by.northdakota;

import by.northdakota.Dao.FlightDao;
import by.northdakota.Dao.TicketDao;
import by.northdakota.Entity.Flight;
import by.northdakota.Entity.Ticket;
import by.northdakota.Utils.ConnectionManager;

import java.math.BigDecimal;
import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class JDBCRunner {

    public static List<Long> getTicketsByFlightId(Long flightId) {
        String sqlSelect = """
                select * from ticket
                where flight_id = %s
                """.formatted(flightId);

        List<Long> tickets = new ArrayList<>();

        try (Connection connection = ConnectionManager.getConnection();
             Statement statement = connection.createStatement()
        ) {
            statement.executeQuery(sqlSelect);
            ResultSet resultSet = statement.getResultSet();
            while (resultSet.next()) {
                tickets.add(resultSet.getLong("id"));
            }
        } catch (SQLException ex) {
            System.out.println(ex.getErrorCode());
        }
        return tickets;
    }

    public static List<Long> getFlightBetween(LocalDateTime start, LocalDateTime end) {
        List<Long> flight = new ArrayList<>();
        String sqlSelect = """
                   select * from flight
                   where departure_date between ? and ?
                """;
        try (Connection connection = ConnectionManager.getConnection();
             PreparedStatement statement = connection.prepareStatement(sqlSelect);
        ) {
            statement.setTimestamp(1, Timestamp.valueOf(start));
            statement.setTimestamp(2, Timestamp.valueOf(end));
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                flight.add(resultSet.getLong("id"));
            }
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
        return flight;
    }

    public static void main(String[] args) {
        var flightDao = FlightDao.getInstance();
        var flights = flightDao.findAll();
        for(Flight x:flights){
            System.out.println(x);
        }

    }
}
