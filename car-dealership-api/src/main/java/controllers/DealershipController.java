package controllers;

import dao.DealershipDAO;
import model.Dealership;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/dealerships")
public class DealershipController {

    private final DealershipDAO dealershipDAO;

    @Autowired
    public DealershipController(DealershipDAO dealershipDAO) {
        this.dealershipDAO = dealershipDAO;
    }

    // Get all dealerships
    @GetMapping
    public List<Dealership> getAllDealerships() {
        return dealershipDAO.findAllDealerships();
    }

    // Get a dealership by ID
    @GetMapping("/{id}")
    public Dealership getDealershipById(@PathVariable int id) {
        return dealershipDAO.findDealershipById(id);
    }

    // Add a new dealership
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void addDealership(@RequestBody Dealership dealership) {
        dealershipDAO.addDealership(dealership);
    }

    // Delete a dealership by ID
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteDealership(@PathVariable int id) {
        dealershipDAO.removeDealership(id);
    }
}