package daoTest;

import budjetointisovellus.dao.Database;
import budjetointisovellus.dao.SqlUserDao;
import budjetointisovellus.dao.UserDao;
import budjetointisovellus.domain.User;
import java.sql.Connection;
import java.sql.PreparedStatement;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class SqlUserDaoTest {

    Database database;
    UserDao userDao;

    @Before
    public void setUp() throws Exception {
        database = new Database("jdbc:sqlite:test.db");
        userDao = new SqlUserDao(database);
        Connection connection = database.getConnection();

        PreparedStatement stmt = connection.prepareStatement("INSERT INTO User(username, name, password) VALUES (?, ?, ?);");
        stmt.setString(1, "testUsername");
        stmt.setString(2, "testName");
        stmt.setString(3, "testPassword");
        stmt.executeUpdate();
        stmt.close();
        connection.close();
    }

    @Test
    public void findsUserByUsername() throws Exception {
        User user = new User("testUsername", "testName", "testPassword");

        assertEquals(user, userDao.findByUsername("testUsername"));
    }

    @Test
    public void userCreationWorks() throws Exception {
        User user = new User("newUsername", "newName", "newPassword");
        assertEquals(user, userDao.create(user));
    }

    @Test
    public void findsExistingUsername() throws Exception {
        assertTrue(userDao.usernameExists("testUsername"));
    }

    @Test
    public void returnsFalseWhenUsernameDoesNotExist() throws Exception {
        assertFalse(userDao.usernameExists("wrong"));
    }

    @After
    public void tearDown() throws Exception {
        Connection connection = database.getConnection();
        PreparedStatement statement = connection.prepareStatement("DROP TABLE User;");
        statement.execute();
        statement.close();
        connection.close();
    }

}
