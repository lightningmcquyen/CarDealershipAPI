package dao;

import model.LeaseContract;
import model.Vehicle;
import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class LeaseContractImpl implements LeaseContractDAO {
    private final DataSource dataSource;

    public LeaseContractImpl(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public void addLeaseContract(LeaseContract leaseContract) {
        String query = """
                INSERT INTO lease_contracts (dateOfContract, customerName, customerEmail, vin, originalPrice, financed)
                VALUES (?, ?, ?, ?, ?, ?)
                """;

        try (Connection connection = dataSource.getConnection();
             PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setString(1, leaseContract.getDateOfContract());
            ps.setString(2, leaseContract.getCustomerName());
            ps.setString(3, leaseContract.getCustomerEmail());
            ps.setInt(4, leaseContract.getVin());
            ps.setDouble(5, leaseContract.getOriginalPrice());
            ps.setBoolean(6, leaseContract.isFinanced());

            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Error adding lease contract", e);
        }
    }

    @Override
    public List<LeaseContract> findAllLeaseContracts() {
        List<LeaseContract> leaseContracts = new ArrayList<>();
        String query = """
                SELECT * FROM lease_contracts
                JOIN vehicles ON lease_contracts.vin = vehicles.vin
                """;

        try (Connection connection = dataSource.getConnection();
             PreparedStatement ps = connection.prepareStatement(query);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                LeaseContract leaseContract = createLeaseContractFromResultSet(rs);
                leaseContracts.add(leaseContract);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error finding all lease contracts", e);
        }

        return leaseContracts;
    }

    @Override
    public LeaseContract findLeaseContractById(int id) {
        String query = """
                SELECT * FROM lease_contracts
                JOIN vehicles ON lease_contracts.vin = vehicles.vin
                WHERE lease_contracts.id = ?
                """;

        try (Connection connection = dataSource.getConnection();
             PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return createLeaseContractFromResultSet(rs);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error finding lease contract by ID", e);
        }

        return null;
    }

    @Override
    public boolean removeLeaseContract(int id) {
        String query = """
                DELETE FROM lease_contracts
                WHERE id = ?
                """;

        try (Connection connection = dataSource.getConnection();
             PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setInt(1, id);
            int rowsAffected = ps.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            throw new RuntimeException("Error removing lease contract", e);
        }
    }

    private LeaseContract createLeaseContractFromResultSet(ResultSet rs) throws SQLException {
        int id = rs.getInt("id");
        String dateOfContract = rs.getString("dateOfContract");
        String customerName = rs.getString("customerName");
        String customerEmail = rs.getString("customerEmail");
        int vin = rs.getInt("vin");
        double originalPrice = rs.getDouble("originalPrice");
        boolean financed = rs.getBoolean("financed");

        Vehicle vehicle = new Vehicle(
                vin,
                rs.getInt("year"),
                rs.getString("make"),
                rs.getString("model"),
                rs.getString("vehicleType"),
                rs.getString("color"),
                rs.getInt("odometer"),
                rs.getDouble("price"),
                rs.getBoolean("sold")
        );

        return new LeaseContract(dateOfContract, customerName, customerEmail, vehicle, originalPrice, financed);
    }
}
