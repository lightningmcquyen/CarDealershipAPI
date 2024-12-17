package com.pluralsight.dao;

import com.pluralsight.model.Vehicle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Component
public class VehicleImpl implements VehicleDAO {
    private final DataSource dataSource;

    @Autowired
    public VehicleImpl(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    // Helper method to map ResultSet to a Vehicle object
    private Vehicle mapRowToVehicle(ResultSet rs) throws SQLException {
        return new Vehicle(
                rs.getInt("vin"),
                rs.getInt("year"),
                rs.getString("make"),
                rs.getString("com/pluralsight"),
                rs.getString("vehicleType"),
                rs.getString("color"),
                rs.getInt("odometer"),
                rs.getDouble("price"),
                rs.getBoolean("sold")
        );
    }

    @Override
    public List<Vehicle> findVehicleByPriceRange(double minPrice, double maxPrice) {
        List<Vehicle> vehicles = new ArrayList<>();
        String query = "SELECT * FROM vehicles WHERE price BETWEEN ? AND ?";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setDouble(1, minPrice);
            ps.setDouble(2, maxPrice);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                vehicles.add(mapRowToVehicle(rs));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error retrieving vehicles by price range.", e);
        }
        return vehicles;
    }

    @Override
    public List<Vehicle> findVehicleByMakeModel(String make, String model) {
        List<Vehicle> vehicles = new ArrayList<>();
        String query = "SELECT * FROM vehicles WHERE make = ? AND model = ?";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setString(1, make);
            ps.setString(2, model);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                vehicles.add(mapRowToVehicle(rs));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error retrieving vehicles by make and model.", e);
        }
        return vehicles;
    }

    @Override
    public List<Vehicle> findVehicleByYear(int minYear, int maxYear) {
        List<Vehicle> vehicles = new ArrayList<>();
        String query = "SELECT * FROM vehicles WHERE year BETWEEN ? AND ?";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setInt(1, minYear);
            ps.setInt(2, maxYear);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                vehicles.add(mapRowToVehicle(rs));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error retrieving vehicles by year range.", e);
        }
        return vehicles;
    }

    @Override
    public List<Vehicle> findVehicleByColor(String color) {
        List<Vehicle> vehicles = new ArrayList<>();
        String query = "SELECT * FROM vehicles WHERE color = ?";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setString(1, color);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                vehicles.add(mapRowToVehicle(rs));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error retrieving vehicles by color.", e);
        }
        return vehicles;
    }

    @Override
    public List<Vehicle> findVehicleByMileage(int minMiles, int maxMiles) {
        List<Vehicle> vehicles = new ArrayList<>();
        String query = "SELECT * FROM vehicles WHERE odometer BETWEEN ? AND ?";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setInt(1, minMiles);
            ps.setInt(2, maxMiles);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                vehicles.add(mapRowToVehicle(rs));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error retrieving vehicles by mileage.", e);
        }
        return vehicles;
    }

    @Override
    public List<Vehicle> findVehicleByType(String type) {
        List<Vehicle> vehicles = new ArrayList<>();
        String query = "SELECT * FROM vehicles WHERE vehicleType = ?";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setString(1, type);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                vehicles.add(mapRowToVehicle(rs));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error retrieving vehicles by type.", e);
        }
        return vehicles;
    }

    @Override
    public void addVehicle(Vehicle vehicle) {
        String query = "INSERT INTO vehicles (vin, year, make, model, vehicleType, color, odometer, price, sold) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setInt(1, vehicle.getVin());
            ps.setInt(2, vehicle.getYear());
            ps.setString(3, vehicle.getMake());
            ps.setString(4, vehicle.getModel());
            ps.setString(5, vehicle.getVehicleType());
            ps.setString(6, vehicle.getColor());
            ps.setInt(7, vehicle.getOdometer());
            ps.setDouble(8, vehicle.getPrice());
            ps.setBoolean(9, vehicle.isSold());
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Error adding vehicle.", e);
        }
    }

    @Override
    public void updateVehicle(int vin, Vehicle updatedVehicle) {
        String query = """
            UPDATE vehicles 
            SET year = ?, make = ?, model = ?, vehicleType = ?, color = ?, odometer = ?, price = ?, sold = ?
            WHERE vin = ?
            """;

        try (Connection connection = dataSource.getConnection();
             PreparedStatement ps = connection.prepareStatement(query)) {

            ps.setInt(1, updatedVehicle.getYear());
            ps.setString(2, updatedVehicle.getMake());
            ps.setString(3, updatedVehicle.getModel());
            ps.setString(4, updatedVehicle.getVehicleType());
            ps.setString(5, updatedVehicle.getColor());
            ps.setInt(6, updatedVehicle.getOdometer());
            ps.setDouble(7, updatedVehicle.getPrice());
            ps.setBoolean(8, updatedVehicle.isSold());
            ps.setInt(9, vin);

            int rowsUpdated = ps.executeUpdate();
            if (rowsUpdated == 0) {
                throw new RuntimeException("No vehicle found with VIN: " + vin);
            }

        } catch (SQLException e) {
            throw new RuntimeException("Error updating vehicle with VIN: " + vin, e);
        }
    }

    @Override
    public boolean removeVehicle(int vin) {
        String query = "DELETE FROM vehicles WHERE vin = ?";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setInt(1, vin);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new RuntimeException("Error removing vehicle.", e);
        }
    }

//

    @Override
    public List<Vehicle> findAllVehicles() {
        List<Vehicle> vehicles = new ArrayList<>();
        try(Connection connection = dataSource.getConnection()) {
            PreparedStatement findAllVehicles = connection.prepareStatement(""" 
                    SELECT VIN, Year, Make, Model, vehicleType, Color, Odometer, Price, Sold
                    FROM vehicles                
                    ORDER BY price;
                    """);
            ResultSet rs = findAllVehicles.executeQuery();
            while(rs.next()) {
                int vin = rs.getInt("VIN");
                String make = rs.getString("Make");
                String model = rs.getString("Model");
                int year = rs.getInt("Year");
                String color = rs.getString("Color");
                int odometer = rs.getInt("Odometer");
                double price = rs.getDouble("Price");
                boolean sold = rs.getBoolean("Sold");
                String vehicleType = rs.getString("vehicleType");
                vehicles.add(new Vehicle(vin, year, make, model, vehicleType, color, odometer, price, sold));}
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return vehicles;
    }

    @Override
    public Vehicle findVehicleByVin(int vin) {
        String query = "SELECT * FROM vehicles WHERE vin = ?";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement ps = connection.prepareStatement(query)) {

            ps.setInt(1, vin);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return new Vehicle(
                        rs.getInt("vin"),
                        rs.getInt("year"),
                        rs.getString("make"),
                        rs.getString("com/pluralsight"),
                        rs.getString("vehicleType"),
                        rs.getString("color"),
                        rs.getInt("odometer"),
                        rs.getDouble("price"),
                        rs.getBoolean("sold")
                );
            } else {
                throw new RuntimeException("Vehicle not found with VIN: " + vin);
            }

        } catch (SQLException e) {
            throw new RuntimeException("Error finding vehicle with VIN: " + vin, e);
        }
    }
}