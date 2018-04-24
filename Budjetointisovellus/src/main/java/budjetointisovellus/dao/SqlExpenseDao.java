package budjetointisovellus.dao;

import budjetointisovellus.domain.Budget;
import budjetointisovellus.domain.Expense;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SqlExpenseDao implements ExpenseDao {

    private Database database;

    public SqlExpenseDao(Database database) {
        this.database = database;
    }

    @Override
    public Expense create(int id, String name, Double price) throws SQLException {
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

    @Override
    public List<Expense> getAllFromABudget(int budgetId) throws SQLException {
        ArrayList<Expense> expenses = new ArrayList<>();

        Connection connection = database.getConnection();
        PreparedStatement statement = connection.prepareStatement("SELECT * FROM Expense WHERE budget_id = ?;");
        statement.setInt(1, budgetId);
        ResultSet rs = statement.executeQuery();

        while (rs.next()) {
            Expense expense = new Expense(rs.getString("name"), rs.getDouble("price"));
            expenses.add(expense);
        }

        statement.close();
        rs.close();
        connection.close();
        return expenses;
    }
}
