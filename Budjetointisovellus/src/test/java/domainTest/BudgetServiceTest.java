package domainTest;

import budjetointisovellus.domain.Budget;
import budjetointisovellus.domain.BudgetService;
import budjetointisovellus.domain.Expense;
import budjetointisovellus.domain.User;
import java.util.ArrayList;
import java.util.List;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class BudgetServiceTest {

    FakeUserDao userDao;
    FakeBudgetDao budgetDao;
    FakeExpenseDao expenseDao;
    BudgetService budgetService;
    User user;

    @Before
    public void setUp() {
        this.userDao = new FakeUserDao();
        this.budgetDao = new FakeBudgetDao();
        this.expenseDao = new FakeExpenseDao();
        this.budgetService = new BudgetService(expenseDao, userDao, budgetDao);
        this.user = new User("username", "name", "password");
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
        assertEquals(this.user, returnedUser);
    }

    @Test
    public void getUserReturnsNullIfNoUserExists() {
        User returnedUser = budgetService.getUser("wrongusername");
        assertEquals(null, returnedUser);
    }

    @Test
    public void createBudgetWorks() {
        Boolean creationWorked = budgetService.createBudget(new Budget("tammikuu", 100), this.user);
        assertTrue(creationWorked);
    }

    @Test
    public void cannotCreateBudgetWithSameName() {
        Boolean creationWorked = budgetService.createBudget(new Budget("lomamatka", 500), this.user);
        assertFalse(creationWorked);
    }

    @Test
    public void findBudgetsWorks() {
        int sizeBeforeAddition = budgetService.findBudgets(this.user).size();
        budgetService.createBudget(new Budget("helmikuu", 100), this.user);
        int sizeAfterAddition = budgetService.findBudgets(this.user).size();

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

    @Test
    public void getBudgetByNameReturnsBudget() {
        Budget budget = new Budget("lomamatka", 500);
        assertEquals(budget, budgetService.getBudgetByName("lomamatka", this.user));
    }

    @Test
    public void budgetDeletionWorks() {
        budgetService.createBudget(new Budget("uusi", 400), this.user);
        int sizeBeforeRemoval = budgetService.findBudgets(this.user).size();
        budgetService.deleteBudget(new Budget("uusi", 400), this.user);
        int sizeAfterRemoval = budgetService.findBudgets(this.user).size();
        assertEquals(sizeBeforeRemoval, sizeAfterRemoval + 1);

    }

    @Test
    public void expenseDeletionWorks() {
        budgetService.createExpense(this.user.getUsername(), "lomamatka", "ruokailu", 20.0);
        int sizeBeforeRemoval = budgetService.findBudgetsExpenses("lomamatka", this.user.getUsername()).size();
        budgetService.deleteExpense(new Budget("lomamata", 500), user, new Expense("ruokailu", 20.0));
        int sizeAfterRemoval = budgetService.findBudgetsExpenses("lomamatka", this.user.getUsername()).size();
        assertEquals(sizeBeforeRemoval, sizeAfterRemoval + 1);
    }

    @Test
    public void totalExpenses() {
        List<Expense> expenses = new ArrayList<>();
        expenses.add(new Expense("ruoka", 15));
        expenses.add(new Expense("olut", 400));

        assertEquals(415, budgetService.totalExpenses(expenses), 0.01);
    }

    @Test
    public void isDoubleReturnsTrueWhenGivenADouble() {
        Boolean returned = budgetService.isDouble("8.5");
        assertTrue(returned);
    }

    @Test
    public void isDoubleReturnsFalseWhenNotGivenDouble() {
        Boolean returned = budgetService.isDouble("notADouble");
        assertFalse(returned);
    }

    @Test
    public void updateBudgetAmountUpdatesAmount() {
        assertTrue(budgetService.updateBudgetAmount(1000.0, user, "lomamatka"));
    }

    @Test
    public void updateBudgetNameUpdatesName() {
        assertTrue(budgetService.updateBudgetName("loma", user, "lomamatka"));
    }

    @Test
    public void deleteUserExpensesDeletesExpenses() {
        assertTrue(budgetService.deleteUserExpenses(user));
    }

    @Test
    public void deleteUserDeletesUser() {
        assertTrue(budgetService.deleteUser(user));
    }

}
