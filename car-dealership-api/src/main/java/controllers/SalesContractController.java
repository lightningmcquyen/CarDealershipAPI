package controllers;

import dao.SalesContractDAO;
import model.SalesContract;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping(path = "/api/sales-contracts")
public class SalesContractController {

    private final SalesContractDAO salesContractDAO;

    public SalesContractController(SalesContractDAO salesContractDAO) {
        this.salesContractDAO = salesContractDAO;
    }

    @GetMapping("/{id}")
    public SalesContract findById(@PathVariable("id") int id) {
        return salesContractDAO.findSalesContractById(id);
    }

    @GetMapping
    public List<SalesContract> findAll() {
        return salesContractDAO.findAllSalesContracts();
    }

    @PostMapping
    @ResponseStatus(code = HttpStatus.CREATED)
    public void addSalesContract(@RequestBody SalesContract salesContract) {
        salesContractDAO.addSalesContract(salesContract);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(code = HttpStatus.NO_CONTENT)
    public void deleteSalesContract(@PathVariable("id") int id) {
        salesContractDAO.removeSalesContract(id);
    }
}