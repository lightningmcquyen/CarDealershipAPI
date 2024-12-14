package controllers;

import dao.VehicleDAO;
import model.Vehicle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/vehicles")
public class VehicleController {

    private final VehicleDAO vehicleDAO;

    @Autowired
    public VehicleController(VehicleDAO vehicleDAO) {
        this.vehicleDAO = vehicleDAO;
    }

    // Get vehicles with query string filtering
    @GetMapping
    public List<Vehicle> getVehicles(
            @RequestParam(required = false) Double minPrice,
            @RequestParam(required = false) Double maxPrice,
            @RequestParam(required = false) String make,
            @RequestParam(required = false) String model,
            @RequestParam(required = false) Integer minYear,
            @RequestParam(required = false) Integer maxYear,
            @RequestParam(required = false) String color,
            @RequestParam(required = false) Integer minMiles,
            @RequestParam(required = false) Integer maxMiles,
            @RequestParam(required = false) String type) {

        // Handle different query parameters
        if (minPrice != null && maxPrice != null) {
            return vehicleDAO.findVehicleByPriceRange(minPrice, maxPrice);
        } else if (make != null && model != null) {
            return vehicleDAO.findVehicleByMakeModel(make, model);
        } else if (minYear != null && maxYear != null) {
            return vehicleDAO.findVehicleByYear(minYear, maxYear);
        } else if (color != null) {
            return vehicleDAO.findVehicleByColor(color);
        } else if (minMiles != null && maxMiles != null) {
            return vehicleDAO.findVehicleByMileage(minMiles, maxMiles);
        } else if (type != null) {
            return vehicleDAO.findVehicleByType(type);
        }

        // Default: return all vehicles
        return vehicleDAO.findAllVehicles();
    }

    // Add a new vehicle
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void addVehicle(@RequestBody Vehicle vehicle) {
        vehicleDAO.addVehicle(vehicle);
    }

    // Update a vehicle
    @PutMapping("/{vin}")
    @ResponseStatus(HttpStatus.OK)
    public void updateVehicle(@PathVariable int vin, @RequestBody Vehicle updatedVehicle) {
        Vehicle existingVehicle = vehicleDAO.findVehicleByVin(vin);

        if (existingVehicle != null) {
            vehicleDAO.updateVehicle(vin, updatedVehicle); // Update the vehicle in DAO
        } else {
            throw new RuntimeException("Vehicle with VIN " + vin + " not found.");
        }
    }

    // Delete a vehicle
    @DeleteMapping("/{vin}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteVehicle(@PathVariable int vin) {
        vehicleDAO.removeVehicle(vin);
    }
}