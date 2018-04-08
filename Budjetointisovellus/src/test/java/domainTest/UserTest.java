package domainTest;

import budjetointisovellus.domain.User;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;


public class UserTest {

    User user;

    @Before
    public void setUp() {
        user = new User("ogrousu", "Olli", 100.0);
    }
    
    @Test
    public void constructorSetsCorrectUsername() {
        assertEquals("ogrousu", user.getUsername());
    }

    @Test
    public void constructorSetsCorrectName() {
        assertEquals("Olli", user.getName());
    }
    
    @Test
    public void constructorSetsCorretBudget() {
        assertEquals(100.0, user.getBudget(), 0.01);
    }
    
}
