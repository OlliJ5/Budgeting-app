package domainTest;

import budjetointisovellus.domain.Expense;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

public class ExpenseTest {

    Expense expense;

    @Before
    public void setUp() {
        expense = new Expense("ruokailu", 20.0);
    }

    @Test
    public void constructorSetsCorrectName() {
        assertEquals("ruokailu", expense.getName());
    }

    @Test
    public void constructorSetsCorrectPrice() {
        assertEquals(20.0, this.expense.getPrice(), 0.0001);
    }
}
