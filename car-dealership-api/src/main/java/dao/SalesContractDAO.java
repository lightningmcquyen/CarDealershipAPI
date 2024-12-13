package dao;

import model.SalesContract;

import java.util.List;

public interface SalesContractDAO {

    // Find a SalesContract by ID
    SalesContract findSalesContractById(int id);

    // Retrieve all SalesContracts
    List<SalesContract> findAllSalesContracts();

    // Add a new SalesContract
    void addSalesContract(SalesContract salesContract);

    // Remove a SalesContract by ID
    boolean removeSalesContract(int id);
}