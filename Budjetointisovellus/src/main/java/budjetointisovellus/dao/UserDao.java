
package budjetointisovellus.dao;

import budjetointisovellus.domain.User;
import java.sql.SQLException;


public interface UserDao {
    User create(User user) throws SQLException;
    
}