package budjetointisovellus.dao;

import budjetointisovellus.domain.Expense;
import java.sql.SQLException;
import java.util.List;

public interface ExpenseDao {

    Expense create(int id, String name, Double price) throws SQLException;

    List<Expense> getAllFromABudget(int budgetId) throws SQLException;
}
