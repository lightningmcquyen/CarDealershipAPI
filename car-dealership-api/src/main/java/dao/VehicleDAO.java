package dao;

import model.Vehicle;
import java.util.List;

public interface VehicleDAO {
    List<Vehicle> findVehicleByPriceRange(double minPrice, double maxPrice);
    List<Vehicle> findVehicleByMakeModel(String make, String model);
    List<Vehicle> findVehicleByYear(int minYear, int maxYear);
    List<Vehicle> findVehicleByColor(String color);
    List<Vehicle> findVehicleByMileage(int minMileage, int maxMileage);
    List<Vehicle> findVehicleByType(String vehicleType);
    void addVehicle(Vehicle vehicle);
    void updateVehicle(int vin, Vehicle updatedVehicle);
    boolean removeVehicle(int vin);
    List<Vehicle> findAllVehicles();
    Vehicle findVehicleByVin(int vin);
}