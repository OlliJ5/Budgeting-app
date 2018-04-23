package budjetointisovellus.dao;

import budjetointisovellus.domain.Budget;
import budjetointisovellus.domain.Expense;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class SqlExpenseDao implements ExpenseDao {

    private Database database;

    public SqlExpenseDao(Database database) {
        this.database = database;
    }
    
    @Override
    public Expense create(int id, String name, Double price) throws SQLException{
        Connection connection = database.getConnection();
        PreparedStatement statement = connection.prepareStatement("INSERT INTO Expense(budget_id, name, price) VALUES(?, ?, ?);");
        statement.setInt(1, id);
        statement.setString(2, name);
        statement.setDouble(3, price);
        statement.executeUpdate();
        statement.close();
        connection.close();

        Expense expense = new Expense(name, price);
        return expense;
    }
}
