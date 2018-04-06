
package budjetointisovellus.domain;

import budjetointisovellus.dao.ExpenseDao;
import budjetointisovellus.dao.UserDao;
import budjetointisovellus.domain.User;


public class BudgetService {
    private ExpenseDao expenseDao;
    private UserDao userDao;

    public BudgetService(ExpenseDao expenseDao, UserDao userDao) {
        this.expenseDao = expenseDao;
        this.userDao = userDao;
    }
    
    public boolean createUser(String username, String name, double budget) {
        User user = new User(username, name, budget);

        try {
            userDao.create(user);
        } catch(Exception e) {
            System.out.println("Virhe: " + e);
            return false;
        }
        return true;
    }
}
