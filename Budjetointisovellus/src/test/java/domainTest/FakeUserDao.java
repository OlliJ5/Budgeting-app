package domainTest;

import budjetointisovellus.dao.UserDao;
import budjetointisovellus.domain.User;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class FakeUserDao implements UserDao {

    List<User> users;

    public FakeUserDao() {
        users = new ArrayList<>();
        User user = new User("username", "name", "password");
        users.add(user);
    }

    @Override
    public User create(User user) throws SQLException {
        users.add(user);
        return user;
    }

    @Override
    public boolean usernameExists(String username) throws SQLException {
        for (int i = 0; i < users.size(); i++) {
            if (users.get(i).getUsername().equals(username)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public User findByUsername(String username) throws SQLException {
        for (int i = 0; i < users.size(); i++) {
            if (users.get(i).getUsername().equals(username)) {
                return users.get(i);
            }
        }
        return null;
    }

    @Override
    public boolean usernameAndPasswordCorrect(String name, String password) throws SQLException {
        for (int i = 0; i < users.size(); i++) {
            if (users.get(i).getUsername().equals(name) && users.get(i).getPassword().equals(password)) {
                return true;
            }
        }
        return false;
    }

}
