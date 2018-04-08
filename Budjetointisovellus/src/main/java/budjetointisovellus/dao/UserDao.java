
package budjetointisovellus.dao;

import budjetointisovellus.domain.User;
import java.sql.SQLException;


public interface UserDao {
    User create(User user) throws SQLException;
    boolean usernameExists(String name) throws SQLException;
    User findByUsername(String username) throws SQLException;
}
