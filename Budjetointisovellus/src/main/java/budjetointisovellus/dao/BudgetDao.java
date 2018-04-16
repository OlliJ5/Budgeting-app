package budjetointisovellus.dao;

import budjetointisovellus.domain.Budget;
import java.sql.SQLException;
import java.util.List;

public interface BudgetDao {

    Budget create(Budget budget, String user) throws SQLException;

    List<Budget> findBudgetsByUsername(String username) throws SQLException;
}
