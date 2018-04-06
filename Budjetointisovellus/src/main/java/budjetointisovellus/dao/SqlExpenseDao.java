package budjetointisovellus.dao;

public class SqlExpenseDao implements ExpenseDao {

    private Database database;

    public SqlExpenseDao(Database database) {
        this.database = database;
    }
}
