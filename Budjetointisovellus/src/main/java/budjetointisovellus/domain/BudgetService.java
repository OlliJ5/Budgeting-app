
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
    
    public boolean createUser(String username, String name, String password){
        
        try {
            if(userDao.usernameExists(username)) {
                return false;
            }
        } catch(Exception e) {
            return false;
        }
        
        User user = new User(username, name, password);
        
        try {
            userDao.create(user);
        } catch(Exception e) {
            System.out.println("Virhe: " + e);
            return false;
        }
        return true;
    }
    
    public boolean login(String username, String password) {
        try {
            return userDao.usernameAndPasswordCorrect(username, password);
        } catch (Exception e) {
            System.out.println(e);
        }
        return false;
    }
    
    public User getUser(String username) {
        try {
            return userDao.findByUsername(username);
        } catch (Exception e){
            System.out.println(e);
        }
        return null;
    }
    

}
