package controllers;

import dao.LeaseContractDAO;
import model.LeaseContract;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/lease-contracts")
public class LeaseContractController {

    private final LeaseContractDAO leaseContractDAO;

    @Autowired
    public LeaseContractController(LeaseContractDAO leaseContractDAO) {
        this.leaseContractDAO = leaseContractDAO;
    }

    // GET: Retrieve a lease contract by ID
    @GetMapping("/{id}")
    public LeaseContract getLeaseContractById(@PathVariable int id) {
        return leaseContractDAO.findLeaseContractById(id);
    }

    // POST: Add a new lease contract
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public LeaseContract addLeaseContract(@RequestBody LeaseContract leaseContract) {
        leaseContractDAO.addLeaseContract(leaseContract);
        return leaseContract;
    }
}