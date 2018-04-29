package budjetointisovellus.dao;

import budjetointisovellus.domain.Budget;
import budjetointisovellus.domain.User;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SqlBudgetDao implements BudgetDao {

    private Database database;

    public SqlBudgetDao(Database database) {
        this.database = database;
    }

    @Override
    public Budget create(Budget budget, String username) throws SQLException {
        Connection connection = database.getConnection();
        PreparedStatement statement = connection.prepareStatement("INSERT INTO Budget(user_username, name, amount) VALUES(?, ?, ?);");
        statement.setString(1, username);
        statement.setString(2, budget.getName());
        statement.setDouble(3, budget.getAmount());
        statement.executeUpdate();
        statement.close();
        connection.close();

        return budget;
    }

    @Override
    public int getIdByNameAndUsername(String username, String budgetName) throws SQLException {
        Connection connection = database.getConnection();
        PreparedStatement statement = connection.prepareStatement("SELECT * FROM Budget WHERE user_username = (?) AND name = (?);");
        statement.setString(1, username);
        statement.setString(2, budgetName);
        ResultSet rs = statement.executeQuery();

        int id = rs.getInt("id");
        statement.close();
        rs.close();
        connection.close();
        return id;
    }

    @Override
    public boolean budgetExists(Budget budget, String username) throws SQLException {
        Connection connection = database.getConnection();
        PreparedStatement statement = connection.prepareStatement("SELECT * FROM Budget WHERE user_username = ? AND name = ?;");
        statement.setString(1, username);
        statement.setString(2, budget.getName());
        ResultSet rs = statement.executeQuery();

        if (rs.next()) {
            statement.close();
            rs.close();
            connection.close();
            return true;
        }
        statement.close();
        rs.close();
        connection.close();
        return false;
    }

    @Override
    public List<Budget> findBudgetsByUsername(String username) throws SQLException {
        ArrayList<Budget> budgets = new ArrayList<>();

        Connection connection = database.getConnection();
        PreparedStatement statement = connection.prepareStatement("SELECT * FROM Budget WHERE user_username = ?");
        statement.setString(1, username);

        ResultSet rs = statement.executeQuery();

        while (rs.next()) {
            Budget budget = new Budget(rs.getString("name"), rs.getDouble("amount"));
            budgets.add(budget);
        }

        statement.close();
        rs.close();
        connection.close();

        return budgets;
    }

    @Override
    public Budget findOne(String budgetName, String username) throws SQLException {
        Connection connection = database.getConnection();
        PreparedStatement statement = connection.prepareStatement("SELECT * FROM Budget WHERE user_username = (?) AND name = (?);");
        statement.setString(1, username);
        statement.setString(2, budgetName);

        ResultSet rs = statement.executeQuery();

        if (!rs.next()) {
            return null;
        }

        Budget budget = new Budget(rs.getString("name"), rs.getDouble("amount"));
        statement.close();
        rs.close();
        connection.close();
        return budget;
    }

    @Override
    public void delete(Budget budget, String username) throws SQLException {
        Connection connection = database.getConnection();
        PreparedStatement statement = connection.prepareStatement("DELETE FROM Budget WHERE user_username = (?) AND name = (?);");
        statement.setString(1, username);
        statement.setString(2, budget.getName());
        statement.executeUpdate();
        statement.close();
        connection.close();
    }

}
