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

    // Get all vehicles
    @GetMapping
    public List<Vehicle> getAllVehicles() {
        return vehicleDAO.findAllVehicles();
    }

    // Get a vehicle by VIN
    @GetMapping("/{vin}")
    public Vehicle getVehicleByVin(@PathVariable int vin) {
        return vehicleDAO.findVehicleByVin(vin);
    }

    // Add a new vehicle
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void addVehicle(@RequestBody Vehicle vehicle) {
        vehicleDAO.addVehicle(vehicle);
    }

    // Delete a vehicle by VIN
    @DeleteMapping("/{vin}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteVehicle(@PathVariable int vin) {
        vehicleDAO.removeVehicle(vin);
    }
}