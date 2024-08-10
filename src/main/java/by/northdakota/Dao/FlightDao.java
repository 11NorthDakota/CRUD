package by.northdakota.Dao;

import by.northdakota.Entity.Flight;
import by.northdakota.Entity.FlightStatus;
import by.northdakota.Entity.Ticket;
import by.northdakota.Exception.DaoException;
import by.northdakota.JDBCRunner;
import by.northdakota.Utils.ConnectionManager;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class FlightDao implements Dao<Long, Flight> {
    private final static FlightDao INSTANCE = new FlightDao();
    public static FlightDao getInstance() {
        return INSTANCE;
    }
    private final static String SAVE_SQL = """
            INSERT INTO flight(flight_no,
                   departure_date,
                   departure_airport_code,
                   arrival_date,
                   arrival_airport_code,
                   aircraft_id,
                   status)
            values (?,?,?,?,?,?,?)
            """;
    private final static String DELETE_SQL = """
             DELETE FROM flight
             where id = ?
            """;

    private final static String FIND_ALL_SQL = """
            SELECT  id,
                    flight_no,
                    departure_date,
                    departure_airport_code,
                    arrival_date,arrival_airport_code,
                    aircraft_id,
                    status
            FROM flight;
            """;
    private final static String FIND_BY_ID_SQL = """
            SELECT  flight_no,
                    departure_date,
                    departure_airport_code,
                    arrival_date,arrival_airport_code,
                    aircraft_id,
                    status
            FROM flight
            where id = ?;
            """;

    public final static String UPDATE_SQL = """
            UPDATE flight
            SET     flight_no = ?,
                    departure_date = ?,
                    departure_airport_code = ?,
                    arrival_date = ?,
                    arrival_airport_code = ?,
                    aircraft_id = ?,
                    status = ?
            where id = ?;
            """;

    @Override
    public Optional<Flight> findById(Long id) {
        Flight flight = null;
        try (Connection connection = ConnectionManager.getConnection();
             PreparedStatement statement = connection.prepareStatement(FIND_BY_ID_SQL)) {
            statement.setLong(1, id);
            statement.execute();
            var result = statement.getResultSet();
            if (result.next()) {
                flight = new Flight(
                        result.getLong("id"),
                        result.getString("flight_no"),
                        result.getTimestamp("departure_date").toLocalDateTime(),
                        result.getString("departure_airport_code"),
                        result.getTimestamp("arrival_date").toLocalDateTime(),
                        result.getString("arrival_airport_code"),
                        result.getLong("aircraft_id"),
                        FlightStatus.getFlightStatus(result.getString("status"))
                );
            }

        } catch (SQLException e) {
            throw new DaoException(e);
        }


        return Optional.empty();
    }

    @Override
    public List<Flight> findAll() {
        List<Flight> flights = new ArrayList<>();
        try (Connection connection = ConnectionManager.getConnection();
             PreparedStatement statement = connection.prepareStatement(FIND_ALL_SQL)) {

            var result = statement.executeQuery();
            while (result.next()) {
                flights.add(new Flight(
                        result.getLong("id"),
                        result.getString("flight_no"),
                        result.getTimestamp("departure_date").toLocalDateTime(),
                        result.getString("departure_airport_code"),
                        result.getTimestamp("arrival_date").toLocalDateTime(),
                        result.getString("arrival_airport_code"),
                        result.getLong("aircraft_id"),
                        FlightStatus.getFlightStatus(result.getString("status"))
                ));
            }
            return flights;
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    @Override
    public Flight save(Flight flight) {
        try (Connection connection = ConnectionManager.getConnection();
             PreparedStatement statement = connection.prepareStatement(SAVE_SQL,
                     PreparedStatement.RETURN_GENERATED_KEYS)) {
            statement.setString(1,flight.getFlightNumber());
            statement.setTimestamp(2,Timestamp.valueOf(flight.getDeparture()));
            statement.setString(3,flight.getDepartureAirportCode());
            statement.setTimestamp(4,Timestamp.valueOf(flight.getArrival()));
            statement.setString(5,flight.getArrivalAirportCode());
            statement.setLong(6,flight.getAircraftId());
            statement.setString(7,flight.getStatus().toString());
            statement.executeUpdate();
            var keys = statement.getGeneratedKeys();
            if(keys.next()) {
                flight.setId(keys.getLong(1));
            }
            return flight;
        } catch (SQLException e) {
            throw new DaoException(e);
        }

    }

    @Override
    public boolean update(Flight flight) {
        try (Connection connection = ConnectionManager.getConnection();
             PreparedStatement statement = connection
                     .prepareStatement(UPDATE_SQL)
        ){
            statement.setString(1,flight.getFlightNumber());
            statement.setTimestamp(2,Timestamp.valueOf(flight.getDeparture()));
            statement.setString(3,flight.getDepartureAirportCode());
            statement.setTimestamp(4,Timestamp.valueOf(flight.getArrival()));
            statement.setString(5,flight.getArrivalAirportCode());
            statement.setLong(6,flight.getAircraftId());
            statement.setString(7,flight.getStatus().toString());
            statement.setLong(8,flight.getId());
            var result = statement.executeUpdate();
            return result > 0;
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    @Override
    public boolean delete(Long id) {
        try (Connection connection = ConnectionManager.getConnection();
             PreparedStatement statement = connection
                     .prepareStatement(DELETE_SQL)
        ) {
            statement.setLong(1, id);
            return statement.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    private FlightDao() {
    }

    ;
}
