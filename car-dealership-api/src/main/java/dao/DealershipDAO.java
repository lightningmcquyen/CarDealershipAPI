package dao;


import model.Dealership;
import java.util.List;

public interface DealershipDAO {

    /**
     * Find a dealership by its ID.
     * @param id the ID of the dealership
     * @return the Dealership object
     */
    Dealership findDealershipById(int id);

    /**
     * Find all dealerships.
     * @return a list of all Dealership objects
     */
    List<Dealership> findAllDealerships();

    /**
     * Add a new dealership to the database.
     * @param dealership the Dealership object to add
     */
    void addDealership(Dealership dealership);

    /**
     * Remove a dealership by its ID.
     * @param id the ID of the dealership to remove
     * @return true if the dealership was successfully removed, false otherwise
     */
    boolean removeDealership(int id);
}