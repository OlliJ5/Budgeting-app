package budjetointisovellus.dao;

import budjetointisovellus.domain.Expense;
import java.sql.SQLException;
import java.util.List;

/**
 *
 * @author ogrousu
 */
public interface ExpenseDao {

    /**
     * Uuden kulun luonti
     * 
     * @param id Budjetin id, johon kulu liittyy
     * @param name Kulun nimi
     * @param price Kulun hinta
     * @return Kulun, joka luodaan
     */
    Expense create(int id, String name, Double price) throws SQLException;

    /**
     * Hakee kaikki kulut, jotka liittyvät tiettyyn budjettiin
     *
     * @param budgetId Budjetin id, jonka kuluja haetaan
     * @return Lista Expense-olioita
     */
    List<Expense> getAllFromABudget(int budgetId) throws SQLException;
    
    /**
     * Poistaa kaikki kulut budjetista
     * 
     * @param budgetId Budjetin id, josta kulut halutaan poistaa
     */
    void deleteExpensesFromBudget(int budgetId) throws SQLException;
    
    /**
     * Tietyn kulun poistaminen
     * 
     * @param budgetId Budjetin id, johon poistettava kulu liittyy
     * @param expense Kulu, joka halutaan poistaa
     */
    void delete(int budgetId, Expense expense) throws SQLException;
}
