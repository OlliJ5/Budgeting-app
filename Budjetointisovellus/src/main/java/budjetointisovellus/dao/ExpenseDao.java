
package budjetointisovellus.dao;

import budjetointisovellus.domain.Expense;
import java.sql.SQLException;


public interface ExpenseDao {
    Expense create(int id, String name, Double price) throws SQLException;
}
