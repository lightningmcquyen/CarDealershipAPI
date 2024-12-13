package dao;

import model.SalesContract;
import model.Vehicle;
import org.apache.commons.dbcp2.BasicDataSource;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SalesContractImpl implements SalesContractDAO {

    private final BasicDataSource dataSource;

    public SalesContractImpl(BasicDataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public SalesContract findSalesContractById(int id) {
        String query = "SELECT * FROM sales_contracts WHERE id = ?";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement ps = connection.prepareStatement(query)) {

            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return new SalesContract(
                        rs.getString("dateOfContract"),
                        rs.getString("customerName"),
                        rs.getString("customerEmail"),
                        new Vehicle(rs.getInt("vin")), // Update to match your Vehicle constructor
                        rs.getDouble("originalPrice"),
                        rs.getBoolean("financed")
                );
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error retrieving SalesContract by ID", e);
        }
        return null;
    }

    @Override
    public List<SalesContract> findAllSalesContracts() {
        List<SalesContract> contracts = new ArrayList<>();
        String query = "SELECT * FROM sales_contracts";

        try (Connection connection = dataSource.getConnection();
             PreparedStatement ps = connection.prepareStatement(query);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                contracts.add(new SalesContract(
                        rs.getString("dateOfContract"),
                        rs.getString("customerName"),
                        rs.getString("customerEmail"),
                        new Vehicle(rs.getInt("vin")), // Update to match your Vehicle constructor
                        rs.getDouble("originalPrice"),
                        rs.getBoolean("financed")
                ));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error retrieving all SalesContracts", e);
        }

        return contracts;
    }

    @Override
    public void addSalesContract(SalesContract salesContract) {
        String query = """
                INSERT INTO sales_contracts (dateOfContract, customerName, customerEmail, vin, originalPrice, financed)
                VALUES (?, ?, ?, ?, ?, ?)
                """;

        try (Connection connection = dataSource.getConnection();
             PreparedStatement ps = connection.prepareStatement(query)) {

            ps.setString(1, salesContract.getDateOfContract());
            ps.setString(2, salesContract.getCustomerName());
            ps.setString(3, salesContract.getCustomerEmail());
            ps.setInt(4, salesContract.getVin());
            ps.setDouble(5, salesContract.getOriginalPrice());
            ps.setBoolean(6, salesContract.isFinanced());

            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Error adding SalesContract", e);
        }
    }

    @Override
    public boolean removeSalesContract(int id) {
        String query = "DELETE FROM sales_contracts WHERE id = ?";

        try (Connection connection = dataSource.getConnection();
             PreparedStatement ps = connection.prepareStatement(query)) {

            ps.setInt(1, id);
            int rowsAffected = ps.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            throw new RuntimeException("Error removing SalesContract", e);
        }
    }
}