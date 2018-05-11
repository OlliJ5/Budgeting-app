package budjetointisovellus.dao;

import budjetointisovellus.domain.Budget;
import budjetointisovellus.domain.User;
import java.sql.SQLException;
import java.util.List;

/**
 *
 * @author ogrousu
 */
public interface BudgetDao {

    /**
     * Budjetin luonti
     *
     * @param budget budjetti, joka halutaan luoda
     * @param user käyttäjätunnus, johon budjetti halutaan yhdistää
     * @return budjetti, joka luodaan
     */
    Budget create(Budget budget, String user) throws SQLException;

    /**
     * Tarkistetaan, onko budjetti olemassa
     *
     * @param budget budjetti, jonka olemassaolo halutaan tarkistaa
     * @param username käyttäjä, johon budjetti liittyy
     * @return true, jos budjetti on olemassa, muuten false
     */
    boolean budgetExists(Budget budget, String username) throws SQLException;

    /**
     * Hakee listan budjeteista, jotka liittyvät tiettyyn käyttäjätunnukseen
     *
     * @param username käyttäjätunnus, jonka budjetteja halutaan etsiä
     * @return Käyttäjätunnukseen liittyvät budjetit
     */
    List<Budget> findBudgetsByUsername(String username) throws SQLException;

    /**
     * Hakee id:n, joka liittyy tiettyyn budjettiin nimen ja käyttäjätunnuksen
     * perusteella
     *
     * @param username Käyttäjätunnus, johon budjetti liittyy
     * @param budgetName Budjetin nimi
     * @return id:n joka liittyy tiettyyn budjettiin
     */
    int getIdByNameAndUsername(String username, String budgetName) throws SQLException;

    /**
     * Hakee tietyn budjetin, nimen ja käyttäjätunnuksen perusteella
     *
     * @param budgetName budjetin nimi
     * @param username käyttäjätunnus, johon budjetti liittyy
     * @return Budget-olio, johon paremetrit liittyvät
     */
    Budget findOne(String budgetName, String username) throws SQLException;

    /**
     * Budjetin poisto
     *
     * @param budget poistettava budjetti
     * @param username käyttäjätunnus, johon budjetti liittyy
     */
    void delete(Budget budget, String username) throws SQLException;

    /**
     * Käyttäjän budjettien poisto
     *
     * @param user käyttäjä, jonka budjetit halutaan poistaa
     */
    void deleteBudgetsOfUser(User user) throws SQLException;

    /**
     * Budjetin nimen muuttaminen
     *
     * @param id budjetin, jonka nimenä halutaan muuttaa, id
     * @param name uusi nimi budjetille
     * @throws SQLException
     */
    void updateBudgetName(int id, String name) throws SQLException;

    /**
     * Budjetin määrän muuttaminen
     *
     * @param id budjetin, jonka määrää halutaan muuttaa, id
     * @param amount uusi määrä budjetille
     * @throws SQLException
     */
    void updateBudgetAmount(int id, Double amount) throws SQLException;
}
