package com.pluralsight.controllers;

import com.pluralsight.dao.SalesContractDAO;
import com.pluralsight.model.SalesContract;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/sales-contracts")
public class SalesContractController {

    private final SalesContractDAO salesContractDAO;

    @Autowired
    public SalesContractController(SalesContractDAO salesContractDAO) {
        this.salesContractDAO = salesContractDAO;
    }

    // GET: Retrieve a sales contract by ID
    @GetMapping("/{id}")
    public SalesContract getSalesContractById(@PathVariable int id) {
        return salesContractDAO.findSalesContractById(id);
    }

    // POST: Add a new sales contract
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public SalesContract addSalesContract(@RequestBody SalesContract salesContract) {
        salesContractDAO.addSalesContract(salesContract);
        return salesContract;
    }
}