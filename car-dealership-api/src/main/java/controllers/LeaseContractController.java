package controllers;

import dao.LeaseContractDAO;
import model.LeaseContract;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/leasecontracts")
public class LeaseContractController {

    private final LeaseContractDAO leaseContractDAO;

    @Autowired
    public LeaseContractController(LeaseContractDAO leaseContractDAO) {
        this.leaseContractDAO = leaseContractDAO;
    }

    // Get all lease contracts
    @GetMapping
    public List<LeaseContract> getAllLeaseContracts() {
        return leaseContractDAO.findAllLeaseContracts();
    }

    // Get a lease contract by ID
    @GetMapping("/{id}")
    public LeaseContract getLeaseContractById(@PathVariable int id) {
        return leaseContractDAO.findLeaseContractById(id);
    }

    // Add a new lease contract
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void addLeaseContract(@RequestBody LeaseContract leaseContract) {
        leaseContractDAO.addLeaseContract(leaseContract);
    }

    // Delete a lease contract by ID
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteLeaseContract(@PathVariable int id) {
        leaseContractDAO.removeLeaseContract(id);
    }
}