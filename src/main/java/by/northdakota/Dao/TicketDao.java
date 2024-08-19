package by.northdakota.Dao;

import by.northdakota.Entity.Ticket;
import by.northdakota.Exception.DaoException;
import by.northdakota.Utils.ConnectionManager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class TicketDao implements Dao<Long,Ticket>{
    private final static TicketDao INSTANCE = new TicketDao();

    private final static String SAVE_SQL = """
            INSERT INTO ticket(passenger_no, passenger_name, flight_id, seat_no, cost)
            values (?,?,?,?,?)
            """;
    private final static String DELETE_SQL = """
             DELETE FROM ticket
             where id = ?
            """;

    private final static String FIND_ALL_SQL = """
            SELECT id,passenger_no,passenger_name,flight_id,seat_no,cost
            FROM ticket;
            """;
    private final static String FIND_BY_ID_SQL = """
            SELECT id,passenger_no,passenger_name,flight_id,seat_no,cost
            FROM ticket
            where id = ?;
            """;

    private final static String UPDATE_SQL = """
            UPDATE ticket
            SET passenger_no = ?,
                passenger_name = ?,
                flight_id = ?,
                seat_no = ?,
                cost = ?
            where id = ?;
            """;

    private final static String FIND_BY_FLIGHT_SQL = """
            select t.id,t.passenger_no,t.passenger_name,t.flight_id,t.seat_no,t.cost
            from ticket as t
            join flight as f on f.id = t.flight_id
            where f.id = ?;
            """;

    public static TicketDao getInstance() {
        return INSTANCE;
    }
    @Override
    public Ticket save(Ticket ticket) {
        try (Connection connection = ConnectionManager.getConnection();
             PreparedStatement statement = connection
                     .prepareStatement(SAVE_SQL, PreparedStatement.RETURN_GENERATED_KEYS)
        ) {
            statement.setString(1, ticket.getPassenger_no());
            statement.setString(2, ticket.getPassenger_name());
            statement.setLong(3, ticket.getFlightId());
            statement.setString(4, ticket.getSeatNo());
            statement.setBigDecimal(5, ticket.getCost());
            statement.executeUpdate();
            var keys = statement.getGeneratedKeys();
            if (keys.next()) {
                ticket.setId(keys.getLong(1));
            }
            return ticket;
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }
    @Override
    public boolean delete(Long id) {
        try (Connection connection = ConnectionManager.getConnection();
             PreparedStatement statement = connection
                     .prepareStatement(DELETE_SQL, PreparedStatement.RETURN_GENERATED_KEYS)
        ) {
            statement.setLong(1, id);
            return statement.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    public List<Ticket> findAllByFlightId(Long flightId) {
        List<Ticket> tickets = new ArrayList<>();
        try (Connection connection = ConnectionManager.getConnection();
             PreparedStatement statement = connection
                     .prepareStatement(FIND_BY_FLIGHT_SQL)
        ) {
            statement.setLong(1, flightId);
            var result = statement.executeQuery();
            while(result.next()){
                tickets.add(
                        buildTicket(result)
                );
            }
            return tickets;
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    @Override
    public boolean update(Ticket ticket) {
        try (Connection connection = ConnectionManager.getConnection();
             PreparedStatement statement = connection
                     .prepareStatement(UPDATE_SQL)
        ) {
            statement.setString(1, ticket.getPassenger_no());
            statement.setString(2, ticket.getPassenger_name());
            statement.setLong(3, ticket.getFlightId());
            statement.setString(4, ticket.getSeatNo());
            statement.setBigDecimal(5, ticket.getCost());
            statement.setLong(6, ticket.getId());
            return statement.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }
    @Override
    public List<Ticket> findAll() {
        List<Ticket> tickets = new ArrayList<>();
        try (Connection connection = ConnectionManager.getConnection();
             PreparedStatement statement = connection
                     .prepareStatement(FIND_ALL_SQL)
        ) {

            var result = statement.executeQuery();
            while(result.next()) {
                tickets.add(buildTicket(result));
            };
            return tickets;
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }
    @Override
    public Optional<Ticket> findById(Long id) {
        Ticket ticket = null;
        try (Connection connection = ConnectionManager.getConnection();
             PreparedStatement statement = connection
                     .prepareStatement(FIND_BY_ID_SQL)
        ) {
            statement.setLong(1,id);
            var result = statement.executeQuery();
            if(result.next()) {
                ticket = buildTicket(result);
            }
            return Optional.ofNullable(ticket);
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    private static Ticket buildTicket(ResultSet result) throws SQLException {
        return new Ticket(
                result.getLong("id"),
                result.getString("passenger_no"),
                result.getString("passenger_name"),
                result.getLong("flight_id"),
                result.getString("seat_no"),
                result.getBigDecimal("cost")
        );
    }

    private TicketDao() {

    }

    ;
}
