package budjetointisovellus.dao;

import budjetointisovellus.domain.User;
import java.sql.*;

public class SqlUserDao implements UserDao {

    private Database database;

    public SqlUserDao(Database database) {
        this.database = database;
    }

    @Override
    public User create(User user) throws SQLException {
        Connection connection = database.getConnection();
        PreparedStatement statement = connection.prepareStatement("SELECT * FROM USER WHERE username = ?;");
        statement.setString(1, user.getUsername());
        ResultSet rs = statement.executeQuery();
        if(!rs.next()) {
            statement.close();
            rs.close();
            
            PreparedStatement stmt = connection.prepareStatement("INSERT INTO USER(username, name, budget) VALUES (?, ?, ?);");
            stmt.setString(1, user.getUsername());
            stmt.setString(2, user.getName());
            stmt.setDouble(3, user.getBudget());
            stmt.executeUpdate();
            stmt.close();
            
        }
        connection.close();
        return new User(user.getUsername(), user.getName(), user.getBudget());
          
    }

}