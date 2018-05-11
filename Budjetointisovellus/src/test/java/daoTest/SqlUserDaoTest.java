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
import org.mindrot.jbcrypt.BCrypt;

public class SqlUserDaoTest {

    Database database;
    UserDao userDao;

    @Before
    public void setUp() throws Exception {
        database = new Database("jdbc:sqlite:test.db");
        userDao = new SqlUserDao(database);
        Connection connection = database.getConnection();

        String pwHash = BCrypt.hashpw("testPassword", BCrypt.gensalt());

        PreparedStatement stmt = connection.prepareStatement("INSERT INTO User(username, name, password) VALUES (?, ?, ?);");
        stmt.setString(1, "testUsername");
        stmt.setString(2, "testName");
        stmt.setString(3, pwHash);
        stmt.executeUpdate();
        stmt.close();
        connection.close();
    }

    @Test
    public void findsUserByUsername() throws Exception {
        assertEquals("testUsername", userDao.findByUsername("testUsername").getUsername());
    }

    @Test
    public void findByUsernameReturnsNullIfUserDoesNotExist() throws Exception {
        assertEquals(null, userDao.findByUsername("wrong"));
    }

    @Test
    public void userCreationWorks() throws Exception {
        User user = new User("newUsername", "newName", "newPassword");
        userDao.create(user);
        assertEquals(user, userDao.findByUsername("newUsername"));
    }

    @Test
    public void findsExistingUsername() throws Exception {
        assertTrue(userDao.usernameExists("testUsername"));
    }

    @Test
    public void returnsFalseWhenUsernameDoesNotExist() throws Exception {
        assertFalse(userDao.usernameExists("wrong"));
    }

    @Test
    public void deletionWorks() throws Exception {
        User user = new User("testUsername", "testName", "testPassword");
        userDao.delete(user);
        assertFalse(userDao.usernameExists("testUsername"));
    }

    @Test
    public void usernameAndPasswordCorrectReturnsTrueWhenCorrect() throws Exception {
        assertTrue(userDao.usernameAndPasswordCorrect("testUsername", "testPassword"));
    }

    @Test
    public void usernameAndPasswordCorrectReturnFalseWhenFalse() throws Exception {
        assertFalse(userDao.usernameAndPasswordCorrect("wrong", "false"));
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
