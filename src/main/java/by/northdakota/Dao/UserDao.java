package by.northdakota.Dao;

import by.northdakota.Entity.Gender;
import by.northdakota.Entity.Role;
import by.northdakota.Entity.User;
import by.northdakota.Exception.DaoException;
import by.northdakota.Utils.ConnectionManager;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
@NoArgsConstructor(access = AccessLevel.PRIVATE )
public class UserDao implements Dao<Long, User> {
    private static final UserDao INSTANCE = new UserDao();

    private static final String SAVE_SQL = """
            insert into users(name, birthday,email,password,role,gender)
            values (?,?,?,?,?,?);
            """;
    private static final String FIND_BY_ID_SQL = """
            select name, birthday, email, password, role, gender
            from users
            where id = ?;
            """;
    private static final String FIND_ALL_SQL = """
            select name, birthday, email, password, role, gender
            from users;
            """;
    private static final String UPDATE_SQL = """
            UPDATE users
            SET name = ?,
                birthday = ?,
                email = ?,
                password = ?,
                role = ?,
                gender = ?
            where id = ?;
            """;
    private static final String DELETE_SQL = """
            DELETE from users
            where id = ?
            """;

    public static UserDao getInstance() {
        return INSTANCE;
    }

    @Override
    public Optional<User> findById(Long id) {
        User user = null;
        try (Connection connection = ConnectionManager.getConnection();
             PreparedStatement ps = connection.prepareStatement(FIND_BY_ID_SQL);
        ) {
            ps.setLong(1, id);
            var res = ps.executeQuery();
            while (res.next()) {
                user = buildUser(res);
            }
            return Optional.ofNullable(user);
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    @Override
    public List<User> findAll() {
        List<User> users = new ArrayList<>();
        try (Connection connection = ConnectionManager.getConnection();
             PreparedStatement ps = connection.prepareStatement(FIND_ALL_SQL);
        ) {
            var result = ps.executeQuery();
            while (result.next()) {
                users.add(buildUser(result));
            }
            return users;
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }


    @Override
    public User save(User user) {
        try (
                Connection connection = ConnectionManager.getConnection();
                PreparedStatement ps = connection.prepareStatement(SAVE_SQL, PreparedStatement.RETURN_GENERATED_KEYS);
        ) {
            ps.setString(1, user.getName());
            ps.setDate(2, Date.valueOf(user.getBirthday()));
            ps.setString(3, user.getEmail());
            ps.setString(4, user.getPassword());
            ps.setString(5, user.getRole().toString());
            ps.setString(6, user.getGender().toString());
            ps.executeUpdate();
            var res = ps.getGeneratedKeys();
            while (res.next()) {
                user.setId(res.getLong(1));
            }
            return user;
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    @Override
    public boolean update(User user) {
        try (
                Connection connection = ConnectionManager.getConnection();
                PreparedStatement ps = connection.prepareStatement(UPDATE_SQL);
        ) {
            ps.setString(1, user.getName());
            ps.setDate(2, Date.valueOf(user.getBirthday()));
            ps.setString(3, user.getEmail());
            ps.setString(4, user.getPassword());
            ps.setString(5, user.getRole().toString());
            ps.setString(6, user.getGender().toString());
            ps.setLong(7, user.getId());
            var res = ps.executeUpdate();
            return res > 0;
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    @Override
    public boolean delete(Long id) {
        try (
                Connection connection = ConnectionManager.getConnection();
                PreparedStatement ps = connection.prepareStatement(DELETE_SQL);
        ) {
            ps.setLong(1, id);
            var res = ps.executeUpdate();
            return res > 0;
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }


    private static User buildUser(ResultSet result) throws SQLException {
        return new User(
                result.getLong("id"),
                result.getString("name"),
                result.getDate("date").toLocalDate(),
                result.getString("email"),
                result.getString("password"),
                Role.findRole(result.getString("role")),
                Gender.findGender(result.getString("gender"))
        );
    }
}
