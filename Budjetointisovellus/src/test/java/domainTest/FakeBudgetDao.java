
package domainTest;

import budjetointisovellus.dao.BudgetDao;
import budjetointisovellus.domain.Budget;
import java.sql.SQLException;
import java.util.List;


public class FakeBudgetDao implements BudgetDao{

    @Override
    public Budget create(Budget budget, String user) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<Budget> findBudgetsByUsername(String username) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
