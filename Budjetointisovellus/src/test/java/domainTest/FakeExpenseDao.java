package domainTest;

import budjetointisovellus.dao.ExpenseDao;
import budjetointisovellus.domain.Expense;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class FakeExpenseDao implements ExpenseDao {

    List<Expense> expenses;

    public FakeExpenseDao() {
        this.expenses = new ArrayList<>();
        Expense expense = new Expense("opiskelijalounas", 2.60);
        expenses.add(expense);
    }

    @Override
    public Expense create(int id, String name, Double price) throws SQLException {
        Expense expense = new Expense(name, price);
        this.expenses.add(expense);
        return expense;
    }

    @Override
    public List<Expense> getAllFromABudget(int budgetId) throws SQLException {
        return expenses;
    }

    @Override
    public void deleteExpensesFromBudget(int budgetId) throws SQLException {
        this.expenses.clear();
    }

    @Override
    public void delete(int budgetId, Expense expense) throws SQLException {
        for(int i = 0; i < expenses.size(); i++) {
            if(expenses.get(i).getName().equals(expense.getName())) {
                expenses.remove(i);
            }
        }
    }

}
