package domainTest;

import budjetointisovellus.dao.BudgetDao;
import budjetointisovellus.domain.Budget;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class FakeBudgetDao implements BudgetDao {

    List<Budget> budgets;

    public FakeBudgetDao() {
        this.budgets = new ArrayList<>();
        Budget budget = new Budget("lomamatka", 500);
        this.budgets.add(budget);
    }

    @Override
    public Budget create(Budget budget, String user) throws SQLException {
        budgets.add(budget);
        return budget;
    }

    @Override
    public List<Budget> findBudgetsByUsername(String username) throws SQLException {
        return budgets;
    }

    @Override
    public boolean budgetExists(Budget budget, String username) throws SQLException {
        for (Budget b : budgets) {
            if (b.getName().equals(budget.getName())) {
                return true;
            }
        }
        return false;
    }

    @Override
    public int getIdByNameAndUsername(String username, String budgetName) throws SQLException {
        for (int i = 0; i < budgets.size(); i++) {
            if (budgets.get(i).getName().equals(budgetName)) {
                return i + 1;
            }
        }
        return 0;
    }

    @Override
    public Budget findOne(String budgetName, String username) throws SQLException {
        for(int i = 0; i < budgets.size(); i++) {
            if(budgets.get(i).getName().equals(budgetName)) {
                Budget budget = new Budget(budgets.get(i).getName(), budgets.get(i).getAmount());
                return budget;
            }
        }
        return null;
    }

    @Override
    public void delete(Budget budget, String username) throws SQLException {
        
    }

}
