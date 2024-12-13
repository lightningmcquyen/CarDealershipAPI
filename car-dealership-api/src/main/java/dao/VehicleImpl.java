package dao;

import model.Vehicle;
import org.springframework.stereotype.Component;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Component
public class VehicleImpl implements VehicleDAO {
    private final DataSource dataSource;

    public VehicleImpl(DataSource dataSource) {
        this.dataSource = dataSource;
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
                vehicles.add(createVehicleFromResultSet(rs));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error finding vehicles by price range", e);
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
                vehicles.add(createVehicleFromResultSet(rs));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error finding vehicles by make and model", e);
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
                vehicles.add(createVehicleFromResultSet(rs));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error finding vehicles by year range", e);
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
                vehicles.add(createVehicleFromResultSet(rs));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error finding vehicles by color", e);
        }
        return vehicles;
    }

    @Override
    public List<Vehicle> findVehicleByMileage(int minMileage, int maxMileage) {
        List<Vehicle> vehicles = new ArrayList<>();
        String query = "SELECT * FROM vehicles WHERE odometer BETWEEN ? AND ?";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setInt(1, minMileage);
            ps.setInt(2, maxMileage);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                vehicles.add(createVehicleFromResultSet(rs));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error finding vehicles by mileage range", e);
        }
        return vehicles;
    }

    @Override
    public List<Vehicle> findVehicleByType(String vehicleType) {
        List<Vehicle> vehicles = new ArrayList<>();
        String query = "SELECT * FROM vehicles WHERE vehicleType = ?";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setString(1, vehicleType);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                vehicles.add(createVehicleFromResultSet(rs));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error finding vehicles by type", e);
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
            throw new RuntimeException("Error adding vehicle", e);
        }
    }

    @Override
    public boolean removeVehicle(int vin) {
        String query = "DELETE FROM vehicles WHERE vin = ?";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setInt(1, vin);
            int rowsAffected = ps.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            throw new RuntimeException("Error removing vehicle", e);
        }
    }

    @Override
    public List<Vehicle> findAllVehicles() {
        List<Vehicle> vehicles = new ArrayList<>();
        String query = "SELECT * FROM vehicles";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement ps = connection.prepareStatement(query);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                vehicles.add(createVehicleFromResultSet(rs));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error finding all vehicles", e);
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
                return createVehicleFromResultSet(rs);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error finding vehicle by VIN", e);
        }
        return null;
    }

    private Vehicle createVehicleFromResultSet(ResultSet rs) throws SQLException {
        int vin = rs.getInt("vin");
        int year = rs.getInt("year");
        String make = rs.getString("make");
        String model = rs.getString("model");
        String vehicleType = rs.getString("vehicleType");
        String color = rs.getString("color");
        int odometer = rs.getInt("odometer");
        double price = rs.getDouble("price");
        boolean sold = rs.getBoolean("sold");
        return new Vehicle(vin, year, make, model, vehicleType, color, odometer, price, sold);
    }
}