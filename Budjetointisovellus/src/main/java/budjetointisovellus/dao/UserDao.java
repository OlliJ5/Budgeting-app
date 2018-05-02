package budjetointisovellus.dao;

import budjetointisovellus.domain.User;
import java.sql.SQLException;

/**
 *
 * @author ogrousu
 */
public interface UserDao {

    /**
     * Uuden käyttäjän luominen
     * @param user Käyttäjä-olio, joka halutaan luoda
     * @return Palauttaa luodun käyttäjän
     */
    User create(User user) throws SQLException;

    /**
     * Tarkistaa onko käyttäjätunnus jo olemassa
     * 
     * @param name Käyttäjätunnus
     * @return Palauttaa true, jos käyttäjätunnus on jo olemassa, muuten false
     */
    boolean usernameExists(String name) throws SQLException;

    /**
     * Etsii käyttäjän käyttäjätunnuksen perusteella
     *
     * @param username Käyttäjätunnus
     * @return User-olion, johon parametri liittyy
     */
    User findByUsername(String username) throws SQLException;

    /**
     * Tarkistaa, liittyvätkö käyttäjätunnus ja salasana yhteen
     * 
     * @param name Tarkistettava käyttäjätunnus
     * @param password Tarkistettava salasana
     * @return True, jos käyttäjätunnus ja salasana liittyvät yhteen, muuten false
     */
    boolean usernameAndPasswordCorrect(String name, String password) throws SQLException;
}
