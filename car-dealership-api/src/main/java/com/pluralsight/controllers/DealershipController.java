package com.pluralsight.controllers;

import com.pluralsight.dao.DealershipDAO;
import com.pluralsight.model.Dealership;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/api/dealerships")
public class DealershipController {

    private final DealershipDAO dealershipDAO;

    @Autowired
    public DealershipController(DealershipDAO dealershipDAO) {
        this.dealershipDAO = dealershipDAO;
    }

    // GET: Retrieve all dealerships
    @GetMapping
    public List<Dealership> getAllDealerships() {
        return dealershipDAO.findAllDealerships();
    }

    // GET: Retrieve a dealership by ID
    @GetMapping("/{id}")
    public Dealership getDealershipById(@PathVariable int id) {
        return dealershipDAO.findDealershipById(id);
    }

    // POST: Add a new dealership
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Dealership addDealership(@RequestBody Dealership dealership) {
        dealershipDAO.addDealership(dealership);
        return dealership;
    }

    // DELETE: Remove a dealership by ID
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteDealership(@PathVariable int id) {
        boolean removed = dealershipDAO.removeDealership(id);
        if (!removed) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Dealership with ID " + id + " not found.");
        }
    }
}