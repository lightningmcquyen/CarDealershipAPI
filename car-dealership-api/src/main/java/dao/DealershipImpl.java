package dao;

import model.Dealership;
import org.apache.commons.dbcp2.BasicDataSource;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DealershipImpl implements DealershipDAO {

    private final BasicDataSource dataSource;

    // Constructor for dependency injection of the BasicDataSource
    public DealershipImpl(BasicDataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public Dealership findDealershipById(int id) {
        String query = "SELECT * FROM dealerships WHERE dealership_id = ?";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return new Dealership(
                        rs.getInt("dealership_id"),
                        rs.getString("name"),
                        rs.getString("address"),
                        rs.getString("phone")
                );
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error finding dealership by ID", e);
        }
        return null; // Return null if no dealership is found
    }

    @Override
    public List<Dealership> findAllDealerships() {
        String query = "SELECT * FROM dealerships";
        List<Dealership> dealerships = new ArrayList<>();
        try (Connection connection = dataSource.getConnection();
             PreparedStatement ps = connection.prepareStatement(query);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                Dealership dealership = new Dealership(
                        rs.getInt("dealership_id"),
                        rs.getString("name"),
                        rs.getString("address"),
                        rs.getString("phone")
                );
                dealerships.add(dealership);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error retrieving all dealerships", e);
        }
        return dealerships;
    }

    @Override
    public void addDealership(Dealership dealership) {
        String query = """
                INSERT INTO dealerships (name, address, phone)
                VALUES (?, ?, ?)
                """;
        try (Connection connection = dataSource.getConnection();
             PreparedStatement ps = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, dealership.getName());
            ps.setString(2, dealership.getAddress());
            ps.setString(3, dealership.getPhone());
            ps.executeUpdate();

            // Optional: Retrieve and set the generated ID for the dealership
            ResultSet keys = ps.getGeneratedKeys();
            if (keys.next()) {
                dealership.setId(keys.getInt(1));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error adding dealership", e);
        }
    }

    @Override
    public boolean removeDealership(int id) {
        String query = "DELETE FROM dealerships WHERE dealership_id = ?";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setInt(1, id);
            int rowsAffected = ps.executeUpdate();
            return rowsAffected > 0; // Return true if a row was deleted
        } catch (SQLException e) {
            throw new RuntimeException("Error removing dealership", e);
        }
    }
}