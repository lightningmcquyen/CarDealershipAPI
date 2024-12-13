package dao;

import model.LeaseContract;
import java.util.List;

public interface LeaseContractDAO {
    void addLeaseContract(LeaseContract leaseContract);
    List<LeaseContract> findAllLeaseContracts();
    LeaseContract findLeaseContractById(int id);
    boolean removeLeaseContract(int id);
}
