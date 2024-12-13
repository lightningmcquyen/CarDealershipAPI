package controllers;

import dao.LeaseContractDAO;
import model.LeaseContract;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/api/lease-contracts")
public class LeaseContractController {

    private final LeaseContractDAO leaseContractDAO;

    public LeaseContractController(LeaseContractDAO leaseContractDAO) {
        this.leaseContractDAO = leaseContractDAO;
    }

    @GetMapping("/{id}")
    public LeaseContract findById(@PathVariable("id") int id) {
        return leaseContractDAO.findLeaseContractById(id);
    }

    @GetMapping
    public List<LeaseContract> findAll() {
        return leaseContractDAO.findAllLeaseContracts();
    }

    @PostMapping
    @ResponseStatus(code = HttpStatus.CREATED)
    public void addLeaseContract(@RequestBody LeaseContract leaseContract) {
        leaseContractDAO.addLeaseContract(leaseContract);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(code = HttpStatus.NO_CONTENT)
    public void deleteLeaseContract(@PathVariable("id") int id) {
        leaseContractDAO.removeLeaseContract(id);
    }
}