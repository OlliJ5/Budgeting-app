package budjetointisovellus.domain;

import budjetointisovellus.dao.BudgetDao;
import budjetointisovellus.dao.ExpenseDao;
import budjetointisovellus.dao.UserDao;
import java.util.ArrayList;
import java.util.List;
import org.mindrot.jbcrypt.*;

public class BudgetService {

    private ExpenseDao expenseDao;
    private UserDao userDao;
    private BudgetDao budgetDao;

    public BudgetService(ExpenseDao expenseDao, UserDao userDao, BudgetDao budgetDao) {
        this.expenseDao = expenseDao;
        this.userDao = userDao;
        this.budgetDao = budgetDao;
    }

    public boolean createUser(String username, String name, String password) {

        try {
            if (userDao.usernameExists(username)) {
                return false;
            }
        } catch (Exception e) {
            return false;
        }
        String pwHash = BCrypt.hashpw(password, BCrypt.gensalt());
        User user = new User(username, name, pwHash);

        try {
            userDao.create(user);
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    public boolean login(String username, String password) {
        try {
            if (userDao.usernameAndPasswordCorrect(username, password)) {
                return true;
            }
        } catch (Exception e) {
        }
        return false;
    }

    public User getUser(String username) {
        try {
            return userDao.findByUsername(username);
        } catch (Exception e) {
        }
        return null;
    }

    public boolean createBudget(Budget budget, User user) {
        try {
            if (budgetDao.budgetExists(budget, user.getUsername())) {
                return false;
            }
        } catch (Exception e) {
            return false;
        }

        try {
            budgetDao.create(budget, user.getUsername());
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public boolean createExpense(String username, String budgetName, String expenseName, Double price) {
        try {
            int id = budgetDao.getIdByNameAndUsername(username, budgetName);
            expenseDao.create(id, expenseName, price);
        } catch (Exception e) {
            return false;
        }

        return true;
    }

    public List<Expense> findBudgetsExpenses(String budgetName, String username) {
        List<Expense> expenses = new ArrayList<>();
        try {
            int id = budgetDao.getIdByNameAndUsername(username, budgetName);
            expenses = expenseDao.getAllFromABudget(id);
        } catch (Exception e) {
        }
        return expenses;
    }

    public List<Budget> findBudgets(User user) {
        List<Budget> budgets = new ArrayList<>();
        try {
            budgets = budgetDao.findBudgetsByUsername(user.getUsername());
        } catch (Exception e) {
        }
        return budgets;
    }

    public Budget getBudgetByName(String budgetName, User user) {
        try {
            return budgetDao.findOne(budgetName, user.getUsername());
        } catch (Exception e) {
        }
        return null;
    }

    public boolean deleteBudget(Budget budget, User user) {
        try {
            int id = budgetDao.getIdByNameAndUsername(user.getUsername(), budget.getName());
            System.out.println("id: " +id);
            budgetDao.delete(budget, user.getUsername());
            expenseDao.deleteExpensesFromBudget(id);
            return true;
        } catch (Exception e) {
        }
        return false;
    }
    
    public boolean deleteExpense(Budget budget, User user, Expense expense) {
        try {
            int id = budgetDao.getIdByNameAndUsername(user.getUsername(), budget.getName());
            System.out.println("id: " + id);
            expenseDao.delete(id, expense);
            return true;
        }catch (Exception e) {
            System.out.println("Virhe: " + e);
        }
        return false;
    }

}
