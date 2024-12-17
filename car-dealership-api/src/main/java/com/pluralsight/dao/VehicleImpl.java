package com.pluralsight.dao;

import com.pluralsight.model.Vehicle;
import org.apache.commons.dbcp2.BasicDataSource;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Component
public class VehicleImpl implements VehicleDAO {
    private final BasicDataSource dataSource;

    public VehicleImpl(BasicDataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public List<Vehicle> findAllVehicles() {
        List<Vehicle> vehicles = new ArrayList<>();
        String query = """
                SELECT VIN, Year, Make, Model, vehicleType, Color, Odometer, Price, Sold
                FROM vehicles
                ORDER BY Price;
                """;

        try (Connection connection = dataSource.getConnection();
             PreparedStatement ps = connection.prepareStatement(query);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                int vin = rs.getInt("VIN");
                int year = rs.getInt("Year");
                String make = rs.getString("Make");
                String model = rs.getString("Model");
                String vehicleType = rs.getString("vehicleType");
                String color = rs.getString("Color");
                int odometer = rs.getInt("Odometer");
                double price = rs.getDouble("Price");
                boolean sold = rs.getBoolean("Sold");

                vehicles.add(new Vehicle(vin, year, make, model, vehicleType, color, odometer, price, sold));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error retrieving all vehicles", e);
        }
        return vehicles;
    }

    @Override
    public List<Vehicle> findVehicleByPriceRange(double minPrice, double maxPrice) {
        List<Vehicle> vehicles = new ArrayList<>();
        String query = """
                SELECT VIN, Year, Make, Model, vehicleType, Color, Odometer, Price, Sold
                FROM vehicles
                WHERE Price BETWEEN ? AND ?
                """;

        try (Connection connection = dataSource.getConnection();
             PreparedStatement ps = connection.prepareStatement(query)) {

            ps.setDouble(1, minPrice);
            ps.setDouble(2, maxPrice);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                vehicles.add(new Vehicle(
                        rs.getInt("VIN"),
                        rs.getInt("Year"),
                        rs.getString("Make"),
                        rs.getString("Model"),
                        rs.getString("vehicleType"),
                        rs.getString("Color"),
                        rs.getInt("Odometer"),
                        rs.getDouble("Price"),
                        rs.getBoolean("Sold")
                ));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error retrieving vehicles by price range", e);
        }
        return vehicles;
    }

    @Override
    public List<Vehicle> findVehicleByMakeModel(String make, String model) {
        List<Vehicle> vehicles = new ArrayList<>();
        String query = """
                SELECT VIN, Year, Make, Model, vehicleType, Color, Odometer, Price, Sold
                FROM vehicles
                WHERE Make = ? AND Model = ?
                """;

        try (Connection connection = dataSource.getConnection();
             PreparedStatement ps = connection.prepareStatement(query)) {

            ps.setString(1, make);
            ps.setString(2, model);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                vehicles.add(new Vehicle(
                        rs.getInt("VIN"),
                        rs.getInt("Year"),
                        rs.getString("Make"),
                        rs.getString("Model"),
                        rs.getString("vehicleType"),
                        rs.getString("Color"),
                        rs.getInt("Odometer"),
                        rs.getDouble("Price"),
                        rs.getBoolean("Sold")
                ));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error retrieving vehicles by make and model", e);
        }
        return vehicles;
    }

    @Override
    public List<Vehicle> findVehicleByYear(int minYear, int maxYear) {
        List<Vehicle> vehicles = new ArrayList<>();
        String query = """
                SELECT VIN, Year, Make, Model, vehicleType, Color, Odometer, Price, Sold
                FROM vehicles
                WHERE Year BETWEEN ? AND ?
                """;

        try (Connection connection = dataSource.getConnection();
             PreparedStatement ps = connection.prepareStatement(query)) {

            ps.setInt(1, minYear);
            ps.setInt(2, maxYear);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                vehicles.add(new Vehicle(
                        rs.getInt("VIN"),
                        rs.getInt("Year"),
                        rs.getString("Make"),
                        rs.getString("Model"),
                        rs.getString("vehicleType"),
                        rs.getString("Color"),
                        rs.getInt("Odometer"),
                        rs.getDouble("Price"),
                        rs.getBoolean("Sold")
                ));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error retrieving vehicles by year range", e);
        }
        return vehicles;
    }

    @Override
    public void addVehicle(Vehicle vehicle) {
        String query = """
                INSERT INTO vehicles (VIN, Year, Make, Model, vehicleType, Color, Odometer, Price, Sold)
                VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)
                """;

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
            throw new RuntimeException("Error adding vehicle", e);
        }
    }

    @Override
    public boolean removeVehicle(int vin) {
        String query = """
                DELETE FROM vehicles WHERE VIN = ?
                """;

        try (Connection connection = dataSource.getConnection();
             PreparedStatement ps = connection.prepareStatement(query)) {

            ps.setInt(1, vin);
            int rowsAffected = ps.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            throw new RuntimeException("Error removing vehicle with VIN " + vin, e);
        }
    }

    @Override
    public boolean updateVehicle(int vin, Vehicle updatedVehicle) {
        String query = """
            UPDATE vehicles
            SET Year = ?, Make = ?, Model = ?, vehicleType = ?, Color = ?, Odometer = ?, Price = ?, Sold = ?
            WHERE VIN = ?
            """;

        try (Connection connection = dataSource.getConnection();
             PreparedStatement ps = connection.prepareStatement(query)) {

            // Set the updated vehicle parameters
            ps.setInt(1, updatedVehicle.getYear());
            ps.setString(2, updatedVehicle.getMake());
            ps.setString(3, updatedVehicle.getModel());
            ps.setString(4, updatedVehicle.getVehicleType());
            ps.setString(5, updatedVehicle.getColor());
            ps.setInt(6, updatedVehicle.getOdometer());
            ps.setDouble(7, updatedVehicle.getPrice());
            ps.setBoolean(8, updatedVehicle.isSold());
            ps.setInt(9, vin); // VIN to identify the vehicle

            // Execute update and check affected rows
            int rowsUpdated = ps.executeUpdate();
            return rowsUpdated > 0; // Return true if at least one row was updated

        } catch (SQLException e) {
            throw new RuntimeException("Error updating vehicle with VIN: " + vin, e);
        }
    }


    @Override
    public List<Vehicle> findVehicleByColor(String color) {
        List<Vehicle> vehicles = new ArrayList<>();
        String query = """
                SELECT VIN, Year, Make, Model, vehicleType, Color, Odometer, Price, Sold
                FROM vehicles
                WHERE Color = ?
                """;

        try (Connection connection = dataSource.getConnection();
             PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setString(1, color);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                int vin = rs.getInt("VIN");
                int year = rs.getInt("Year");
                String make = rs.getString("Make");
                String model = rs.getString("Model");
                String vehicleType = rs.getString("vehicleType");
                int odometer = rs.getInt("Odometer");
                double price = rs.getDouble("Price");
                boolean sold = rs.getBoolean("Sold");

                vehicles.add(new Vehicle(vin, year, make, model, vehicleType, color, odometer, price, sold));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error retrieving vehicles by color", e);
        }
        return vehicles;
    }

    @Override
    public List<Vehicle> findVehicleByMileage(int minMileage, int maxMileage) {
        List<Vehicle> vehicles = new ArrayList<>();
        String query = """
            SELECT VIN, Year, Make, Model, vehicleType, Color, Odometer, Price, Sold
            FROM vehicles
            WHERE Odometer BETWEEN ? AND ?
            """;

        try (Connection connection = dataSource.getConnection();
             PreparedStatement ps = connection.prepareStatement(query)) {

            ps.setInt(1, minMileage);
            ps.setInt(2, maxMileage);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                int vin = rs.getInt("VIN");
                int year = rs.getInt("Year");
                String make = rs.getString("Make");
                String model = rs.getString("Model");
                String vehicleType = rs.getString("vehicleType");
                String color = rs.getString("Color");
                int odometer = rs.getInt("Odometer");
                double price = rs.getDouble("Price");
                boolean sold = rs.getBoolean("Sold");

                vehicles.add(new Vehicle(vin, year, make, model, vehicleType, color, odometer, price, sold));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error retrieving vehicles by mileage range", e);
        }
        return vehicles;
    }

    @Override
    public List<Vehicle> findVehicleByType(String type) {
        List<Vehicle> vehicles = new ArrayList<>();
        String query = """
            SELECT VIN, Year, Make, Model, vehicleType, Color, Odometer, Price, Sold
            FROM vehicles
            WHERE vehicleType = ?
            """;

        try (Connection connection = dataSource.getConnection();
             PreparedStatement ps = connection.prepareStatement(query)) {

            ps.setString(1, type);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                int vin = rs.getInt("VIN");
                int year = rs.getInt("Year");
                String make = rs.getString("Make");
                String model = rs.getString("Model");
                String vehicleType = rs.getString("vehicleType");
                String color = rs.getString("Color");
                int odometer = rs.getInt("Odometer");
                double price = rs.getDouble("Price");
                boolean sold = rs.getBoolean("Sold");

                vehicles.add(new Vehicle(vin, year, make, model, vehicleType, color, odometer, price, sold));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error retrieving vehicles by type", e);
        }
        return vehicles;
    }

    @Override
    public Vehicle findVehicleByVin(int vin) {
        String query = """
            SELECT VIN, Year, Make, Model, vehicleType, Color, Odometer, Price, Sold
            FROM vehicles
            WHERE VIN = ?
            """;

        try (Connection connection = dataSource.getConnection();
             PreparedStatement ps = connection.prepareStatement(query)) {

            ps.setInt(1, vin); // Set the VIN parameter for the query
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                // Extract vehicle data from the result set
                int vehicleVin = rs.getInt("VIN");
                int year = rs.getInt("Year");
                String make = rs.getString("Make");
                String model = rs.getString("Model");
                String vehicleType = rs.getString("vehicleType");
                String color = rs.getString("Color");
                int odometer = rs.getInt("Odometer");
                double price = rs.getDouble("Price");
                boolean sold = rs.getBoolean("Sold");

                // Create and return the Vehicle object
                return new Vehicle(vehicleVin, year, make, model, vehicleType, color, odometer, price, sold);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error finding vehicle with VIN: " + vin, e);
        }

        // If VIN is not found, return null
        return null;
    }

}
