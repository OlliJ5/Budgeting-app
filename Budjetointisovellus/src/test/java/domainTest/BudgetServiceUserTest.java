package domainTest;

import budjetointisovellus.domain.Budget;
import budjetointisovellus.domain.BudgetService;
import budjetointisovellus.domain.Expense;
import budjetointisovellus.domain.User;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class BudgetServiceUserTest {

    FakeUserDao userDao;
    FakeBudgetDao budgetDao;
    FakeExpenseDao expenseDao;
    BudgetService budgetService;

    @Before
    public void setUp() {
        this.userDao = new FakeUserDao();
        this.budgetDao = new FakeBudgetDao();
        this.expenseDao = new FakeExpenseDao();
        this.budgetService = new BudgetService(expenseDao, userDao, budgetDao);
    }

    @Test
    public void createUserWorks() {
        Boolean creationWorked = budgetService.createUser("uusi", "olli", "salainen");
        assertTrue(creationWorked);
    }

    @Test
    public void cannotCreateUserWithTheSameUsername() {
        Boolean creationWorked = budgetService.createUser("username", "olli", "salainen");
        assertFalse(creationWorked);
    }

    @Test
    public void loginWorks() {
        Boolean loginWorked = budgetService.login("username", "password");
        assertTrue(loginWorked);
    }

    @Test
    public void cannotLoginWithUnknownUsername() {
        Boolean loginWorked = budgetService.login("notworking", "salainen");
        assertFalse(loginWorked);
    }

    @Test
    public void cannotLoginWithWrongPassWord() {
        Boolean loginWorked = budgetService.login("username", "wrong");
        assertFalse(loginWorked);
    }

    @Test
    public void getUserWorks() {
        User returnedUser = budgetService.getUser("username");
        User user = new User("username", "name", "password");
        assertEquals(user, returnedUser);
    }

    @Test
    public void getUserReturnsNullIfNoUserExists() {
        User returnedUser = budgetService.getUser("wrongusername");
        assertEquals(null, returnedUser);
    }

    @Test
    public void createBudgetWorks() {
        Boolean creationWorked = budgetService.createBudget(new Budget("tammikuu", 100), new User("username", "name", "pword"));
        assertTrue(creationWorked);
    }

    @Test
    public void cannotCreateBudgetWithSameName() {
        Boolean creationWorked = budgetService.createBudget(new Budget("lomamatka", 500), new User("username", "name", "pword"));
    }

    @Test
    public void findBudgetsWorks() {
        int sizeBeforeAddition = budgetService.findBudgets(new User("username", "name", "pword")).size();
        budgetService.createBudget(new Budget("helmikuu", 100), new User("username", "name", "pword"));
        int sizeAfterAddition = budgetService.findBudgets(new User("username", "name", "pword")).size();

        assertEquals(sizeBeforeAddition + 1, sizeAfterAddition);
    }

    @Test
    public void createExpenseWorks() {
        Boolean creationWorked = budgetService.createExpense("username", "budgetname", "ruokailu", 15.0);
        assertTrue(creationWorked);
    }

    @Test
    public void findBudgetsExpenses() {
        int sizeBeforeAddition = budgetService.findBudgetsExpenses("lomamatka", "username").size();
        budgetService.createExpense("username", "budgetName", "ruokaostokset", 20.0);
        int sizeAfterAddition = budgetService.findBudgetsExpenses("lomamatka", "username").size();
        assertEquals(sizeBeforeAddition + 1, sizeAfterAddition);
    }

}
