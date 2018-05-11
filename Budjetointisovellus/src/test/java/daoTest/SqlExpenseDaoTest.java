package daoTest;

import budjetointisovellus.dao.Database;
import budjetointisovellus.dao.ExpenseDao;
import budjetointisovellus.dao.SqlExpenseDao;
import budjetointisovellus.domain.Expense;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.List;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;


/**
 *
 * @author ogrousu
 */
public class SqlExpenseDaoTest {

    Database database;
    ExpenseDao expenseDao;

    @Before
    public void setUp() throws Exception {
        database = new Database("jdbc:sqlite:test.db");
        expenseDao = new SqlExpenseDao(database);
        Connection connection = database.getConnection();

        PreparedStatement statement = connection.prepareStatement("INSERT INTO Expense(budget_id, name, price) VALUES(?, ?, ?);");
        statement.setInt(1, 1);
        statement.setString(2, "ruokailu");
        statement.setDouble(3, 15.50);
        statement.executeUpdate();
        statement.close();
        connection.close();
    }

    @Test
    public void creationWorks() throws Exception {
        Expense expense = expenseDao.create(1, "lennot", 400.0);
        List<Expense> expenses = expenseDao.getAllFromABudget(1);
        assertEquals(expense, expenses.get(1));
    }
    
    @Test
    public void getAllFrombudget() throws Exception {
        List<Expense> expenses = expenseDao.getAllFromABudget(1);
        assertEquals(1, expenses.size());
    }
    
    @Test
    public void deletionWorks() throws Exception {
        expenseDao.delete(1, new Expense("ruokailu", 15.50));
        assertTrue(expenseDao.getAllFromABudget(1).isEmpty());
    }
    
    @Test
    public void deleteAllExpensesFromBudgetWorks() throws Exception {
        expenseDao.create(1, "lennot", 500.0);
        expenseDao.deleteExpensesFromBudget(1);
        assertTrue(expenseDao.getAllFromABudget(1).isEmpty());
    }

    @After
    public void tearDown() throws Exception {
        Connection connection = database.getConnection();
        PreparedStatement statement = connection.prepareStatement("DROP TABLE Expense;");
        statement.execute();
        statement.close();
        connection.close();
    }
}
