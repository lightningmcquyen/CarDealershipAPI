package dao;

import model.SalesContract;
import model.Vehicle;
import org.apache.commons.dbcp2.BasicDataSource;
import org.springframework.stereotype.Component;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Component
public class SalesContractImpl implements SalesContractDAO {

    private final BasicDataSource dataSource;

    public SalesContractImpl(BasicDataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public SalesContract findSalesContractById(int id) {
        String query = "SELECT * FROM sales_contracts sc JOIN vehicles v ON sc.vin = v.vin WHERE sc.id = ?";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement ps = connection.prepareStatement(query)) {

            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                // Fetch all fields for Vehicle
                Vehicle vehicle = new Vehicle(
                        rs.getInt("vin"),
                        rs.getInt("year"),
                        rs.getString("make"),
                        rs.getString("model"),
                        rs.getString("vehicleType"),
                        rs.getString("color"),
                        rs.getInt("odometer"),
                        rs.getDouble("price"),
                        rs.getBoolean("sold")
                );

                // Fetch and return SalesContract
                return new SalesContract(
                        rs.getString("dateOfContract"),
                        rs.getString("customerName"),
                        rs.getString("customerEmail"),
                        vehicle,
                        rs.getDouble("originalPrice"),
                        rs.getBoolean("financed")
                );
            } else {
                throw new RuntimeException("SalesContract not found with ID: " + id);
            }

        } catch (SQLException e) {
            throw new RuntimeException("Error finding SalesContract with ID: " + id, e);
        }
    }


    @Override
    public List<SalesContract> findAllSalesContracts() {
        List<SalesContract> contracts = new ArrayList<>();
        String query = """
            SELECT sc.dateOfContract, sc.customerName, sc.customerEmail, 
                   sc.originalPrice, sc.financed, v.vin, v.year, v.make, 
                   v.model, v.vehicleType, v.color, v.odometer, v.price, v.sold
            FROM sales_contracts sc
            JOIN vehicles v ON sc.vin = v.vin
            """;

        try (Connection connection = dataSource.getConnection();
             PreparedStatement ps = connection.prepareStatement(query);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                // Fetch all vehicle fields
                Vehicle vehicle = new Vehicle(
                        rs.getInt("vin"),
                        rs.getInt("year"),
                        rs.getString("make"),
                        rs.getString("model"),
                        rs.getString("vehicleType"),
                        rs.getString("color"),
                        rs.getInt("odometer"),
                        rs.getDouble("price"),
                        rs.getBoolean("sold")
                );

                // Add SalesContract with full Vehicle object
                contracts.add(new SalesContract(
                        rs.getString("dateOfContract"),
                        rs.getString("customerName"),
                        rs.getString("customerEmail"),
                        vehicle,
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