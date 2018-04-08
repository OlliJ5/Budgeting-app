
package budjetointisovellus.domain;

import budjetointisovellus.dao.ExpenseDao;
import budjetointisovellus.dao.UserDao;
import budjetointisovellus.domain.User;
import java.sql.SQLException;


public class BudgetService {
    private ExpenseDao expenseDao;
    private UserDao userDao;

    public BudgetService(ExpenseDao expenseDao, UserDao userDao) {
        this.expenseDao = expenseDao;
        this.userDao = userDao;
    }
    
    public boolean createUser(String username, String name, double budget){
        
        try {
            if(userDao.usernameExists(name)) {
                return false;
            }
        } catch(Exception e) {
            return false;
        }
        
        User user = new User(username, name, budget);
        
        try {
            userDao.create(user);
        } catch(Exception e) {
            System.out.println("Virhe: " + e);
            return false;
        }
        return true;
    }
    
    public boolean login(String username) {
        try {
            if(userDao.usernameExists(username)) {
                return true;
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        return false;
    }
    

}
