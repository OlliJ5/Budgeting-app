package domainTest;

import budjetointisovellus.domain.User;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

public class UserTest {

    User user;

    @Before
    public void setUp() {
        user = new User("ogrousu", "Olli", "salasana");
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
    public void constructorSetCorrectPassword() {
        assertEquals("salasana", user.getPassword());
    }

    @Test
    public void setNameWorks() {
        user.setName("Olli Rousu");
        assertEquals("Olli Rousu", user.getName());
    }

    @Test
    public void setUsernameWorks() {
        user.setUsername("ollirousu");
        assertEquals("ollirousu", user.getUsername());
    }

    @Test
    public void setPasswordWorkd() {
        user.setPassword("salainensalasana");
        assertEquals("salainensalasana", user.getPassword());
    }

}
