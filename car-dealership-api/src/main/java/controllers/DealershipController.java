package controllers;

import dao.DealershipDAO;
import model.Dealership;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/api/dealerships")
public class DealershipController {

    private final DealershipDAO dealershipDAO;

    public DealershipController(DealershipDAO dealershipDAO) {
        this.dealershipDAO = dealershipDAO;
    }

    @GetMapping("/{id}")
    public Dealership findById(@PathVariable("id") int id) {
        return dealershipDAO.findDealershipById(id);
    }

    @GetMapping
    public List<Dealership> findAll() {
        return dealershipDAO.findAllDealerships();
    }

    @PostMapping
    @ResponseStatus(code = HttpStatus.CREATED)
    public void addDealership(@RequestBody Dealership dealership) {
        dealershipDAO.addDealership(dealership);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(code = HttpStatus.NO_CONTENT)
    public void deleteDealership(@PathVariable("id") int id) {
        dealershipDAO.removeDealership(id);
    }
}