package domainTest;

import budjetointisovellus.domain.BudgetService;
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
        assertEquals(true, creationWorked);
    }
    
    @Test
    public void loginWorks() {
        Boolean loginWorked = budgetService.login("username", "password");
        assertEquals(true, loginWorked);
    }
    
    @Test
    public void getUserWorks() {
        User returnedUser = budgetService.getUser("username");
        User user = new User("username", "name", "password");
        assertEquals(user, returnedUser);
    }

}
