package daoTest;

import budjetointisovellus.dao.BudgetDao;
import budjetointisovellus.dao.Database;
import budjetointisovellus.dao.SqlBudgetDao;
import budjetointisovellus.domain.Budget;
import budjetointisovellus.domain.User;
import java.sql.Connection;
import java.sql.PreparedStatement;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class SqlBudgetDaoTest {

    Database database;
    BudgetDao budgetDao;
    User user;

    @Before
    public void setUp() throws Exception {
        database = new Database("jdbc:sqlite:test.db");
        budgetDao = new SqlBudgetDao(database);
        user = new User("testUsername", "testName", "testPassword");
        Connection connection = database.getConnection();

        PreparedStatement stmt = connection.prepareStatement("INSERT INTO Budget(user_username, name, amount) VALUES (?, ?, ?);");
        stmt.setString(1, "testUsername");
        stmt.setString(2, "testName");
        stmt.setDouble(3, 400.0);
        stmt.executeUpdate();
        stmt.close();
        connection.close();
    }

    @Test
    public void budgetCreationWorks() throws Exception {
        Budget budget = new Budget("lomamatka", 700);
        assertEquals(budget, budgetDao.create(budget, user.getUsername()));
    }

    @Test
    public void getIdByNameAndUsername() throws Exception {
        boolean isGreaterThanZero = budgetDao.getIdByNameAndUsername("testUsername", "testName") > 0;
        assertTrue(isGreaterThanZero);
    }

    @Test
    public void returnsTrueIfBudgetExists() throws Exception {
        assertTrue(budgetDao.budgetExists(new Budget("testName", 400.0), "testUsername"));
    }

    @Test
    public void returnsFalseIfBudgetDoesNotExist() throws Exception {
        assertFalse(budgetDao.budgetExists(new Budget("wrong", 0), "testUsername"));
    }

    @Test
    public void findBudgetByUsernameWorks() throws Exception {
        int sizeBeforeAddition = budgetDao.findBudgetsByUsername(user.getUsername()).size();
        budgetDao.create(new Budget("jotain", 300), user.getUsername());
        int sizeAfterAddition = budgetDao.findBudgetsByUsername(user.getUsername()).size();
        assertEquals(sizeBeforeAddition, sizeAfterAddition - 1);
    }

    @Test
    public void findOneReturnsCorrectBudget() throws Exception {
        Budget budget = new Budget("testName", 400.0);
        assertEquals(budget, budgetDao.findOne("testName", user.getUsername()));
    }

    @Test
    public void findOneReturnsNullIfDoesNotExist() throws Exception {
        assertEquals(null, budgetDao.findOne("wrong", user.getUsername()));
    }

    @Test
    public void deleteWorks() throws Exception {
        budgetDao.create(new Budget("new", 0), user.getUsername());
        int sizeBeforeRemoval = budgetDao.findBudgetsByUsername(user.getUsername()).size();
        budgetDao.delete(new Budget("new", 0), user.getUsername());
        int sizeAfterRemoval = budgetDao.findBudgetsByUsername(user.getUsername()).size();
        assertEquals(sizeBeforeRemoval, sizeAfterRemoval + 1);
    }

    @After
    public void tearDown() throws Exception {
        Connection connection = database.getConnection();
        PreparedStatement statement = connection.prepareStatement("DROP TABLE Budget;");
        statement.execute();
        statement.close();
        connection.close();
    }
}
