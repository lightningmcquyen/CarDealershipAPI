package controllers;

import dao.SalesContractDAO;
import model.SalesContract;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/salescontracts")
public class SalesContractController {

    private final SalesContractDAO salesContractDAO;

    @Autowired
    public SalesContractController(SalesContractDAO salesContractDAO) {
        this.salesContractDAO = salesContractDAO;
    }

    // Get all sales contracts
    @GetMapping
    public List<SalesContract> getAllSalesContracts() {
        return salesContractDAO.findAllSalesContracts();
    }

    // Get a sales contract by ID
    @GetMapping("/{id}")
    public SalesContract getSalesContractById(@PathVariable int id) {
        return salesContractDAO.findSalesContractById(id);
    }

    // Add a new sales contract
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void addSalesContract(@RequestBody SalesContract salesContract) {
        salesContractDAO.addSalesContract(salesContract);
    }

    // Delete a sales contract by ID
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteSalesContract(@PathVariable int id) {
        salesContractDAO.removeSalesContract(id);
    }
}