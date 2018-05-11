package budjetointisovellus.dao;

import java.sql.*;

/**
 * Luokka esittää tietokantaa
 *
 * @author ogrousu
 */
public class Database {

    private String databaseAddress;

    /**
     * Konstruktori luo tietokannan ja taulut, jos ne eivät ole jo olemassa
     *
     * @param databaseAddress tietokannan polku
     */
    public Database(String databaseAddress) throws ClassNotFoundException {
        this.databaseAddress = databaseAddress;

        try {
            Connection connection = DriverManager.getConnection(databaseAddress);
            Statement statement = connection.createStatement();
            statement.execute("CREATE TABLE IF NOT EXISTS User(id integer PRIMARY KEY, username varchar(200), name varchar(200), password varchar(60));");
            statement.execute("CREATE TABLE IF NOT EXISTS Budget(id integer PRIMARY KEY, user_username varchar(200), name varchar(200), amount float, FOREIGN KEY (user_username) REFERENCES User(username));");
            statement.execute("CREATE TABLE IF NOT EXISTS Expense(id integer PRIMARY KEY, budget_id integer, name varchar(200), price float, FOREIGN KEY (budget_id) REFERENCES Budget(id));");

        } catch (Exception e) {

        }

    }

    /**
     * Yhteyden otto tietokantaan
     *
     * @return Connection olio
     */
    public Connection getConnection() throws SQLException {
        return DriverManager.getConnection(databaseAddress);
    }

}
