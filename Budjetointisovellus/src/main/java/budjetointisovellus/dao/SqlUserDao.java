package budjetointisovellus.dao;

import budjetointisovellus.domain.User;
import java.sql.*;
import org.mindrot.jbcrypt.*;

/**
 *
 * @author ogrousu
 */
public class SqlUserDao implements UserDao {

    private Database database;

    /**
     *
     * @param database Database-olio, jota halutaan käyttää tiedon talletukseen
     */
    public SqlUserDao(Database database) {
        this.database = database;
    }

    @Override
    public User create(User user) throws SQLException {

        Connection connection = database.getConnection();
        PreparedStatement stmt = connection.prepareStatement("INSERT INTO USER(username, name, password) VALUES (?, ?, ?);");
        stmt.setString(1, user.getUsername());
        stmt.setString(2, user.getName());
        stmt.setString(3, user.getPassword());
        stmt.executeUpdate();
        stmt.close();

        connection.close();
        return user;

    }

    @Override
    public boolean usernameExists(String username) throws SQLException {
        Connection connection = database.getConnection();
        PreparedStatement statement = connection.prepareStatement("SELECT * FROM User Where username = ?;");
        statement.setString(1, username);
        ResultSet rs = statement.executeQuery();

        if (rs.next()) {
            statement.close();
            rs.close();
            connection.close();
            return true;
        }
        statement.close();
        rs.close();
        connection.close();
        return false;
    }

    @Override
    public User findByUsername(String username) throws SQLException {
        Connection connection = database.getConnection();
        PreparedStatement stmt = connection.prepareStatement("SELECT * FROM User Where username = ?;");
        stmt.setString(1, username);
        ResultSet rs = stmt.executeQuery();

        if (!rs.next()) {
            return null;
        }

        User user = new User(rs.getString("username"), rs.getString("name"), rs.getString("password"));

        stmt.close();
        rs.close();
        connection.close();
        return user;
    }

    @Override
    public boolean usernameAndPasswordCorrect(String username, String password) throws SQLException {
        Connection connection = database.getConnection();
        PreparedStatement statement = connection.prepareStatement("SELECT * FROM User Where username = ?;");
        statement.setString(1, username);
        ResultSet rs = statement.executeQuery();

        if (!rs.next()) {
            statement.close();
            rs.close();
            connection.close();
            return false;
        }

        User user = new User(rs.getString("username"), rs.getString("name"), rs.getString("password"));
        statement.close();
        rs.close();
        connection.close();

        if (BCrypt.checkpw(password, user.getPassword())) {
            return true;
        }

        return false;

    }

    @Override
    public User delete(User user) throws SQLException {
        Connection connection = database.getConnection();
        PreparedStatement statement = connection.prepareStatement("DELETE FROM User WHERE username = ?;");
        statement.setString(1, user.getUsername());
        statement.executeUpdate();
        statement.close();
        connection.close();
        return user;
    }

}
