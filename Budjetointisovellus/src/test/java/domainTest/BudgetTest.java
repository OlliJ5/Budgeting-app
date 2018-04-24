package domainTest;

import budjetointisovellus.domain.Budget;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

public class BudgetTest {

    Budget budget;

    @Before
    public void setUp() {
        budget = new Budget("lomamatka", 400.0);
    }

    @Test
    public void constructorSetsCorrectName() {
        assertEquals("lomamatka", budget.getName());
    }

    @Test
    public void constructorSetsCorrectAmount() {
        assertEquals(400.0, budget.getAmount(), 0.001);
    }
}
