package com.pluralsight.controllers;

import com.pluralsight.dao.VehicleDAO;
import com.pluralsight.model.Vehicle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
//@RequestMapping("/vehicles")
public class VehicleController {

    private final VehicleDAO vehicleDAO;

    @Autowired
    public VehicleController(VehicleDAO vehicleDAO) {
        this.vehicleDAO = vehicleDAO;
    }

    // GET: Retrieve vehicles by price range
    @GetMapping("/by-price")
    public List<Vehicle> findByPriceRange(@RequestParam double minPrice, @RequestParam double maxPrice) {
        return vehicleDAO.findVehicleByPriceRange(minPrice, maxPrice);
    }

    // GET: Retrieve vehicles by make and model
    @GetMapping("/by-make-model")
    public List<Vehicle> findByMakeAndModel(@RequestParam String make, @RequestParam String model) {
        return vehicleDAO.findVehicleByMakeModel(make, model);
    }

    // GET: Retrieve vehicles by year range
    @GetMapping("/by-year")
    public List<Vehicle> findByYearRange(@RequestParam int minYear, @RequestParam int maxYear) {
        return vehicleDAO.findVehicleByYear(minYear, maxYear);
    }

    // GET: Retrieve vehicles by color
    @GetMapping("/by-color")
    public List<Vehicle> findByColor(@RequestParam String color) {
        return vehicleDAO.findVehicleByColor(color);
    }

    // GET: Retrieve vehicles by mileage range
    @GetMapping("/by-mileage")
    public List<Vehicle> findByMileageRange(@RequestParam int minMiles, @RequestParam int maxMiles) {
        return vehicleDAO.findVehicleByMileage(minMiles, maxMiles);
    }

    // GET: Retrieve vehicles by type
    @GetMapping("/by-type")
    public List<Vehicle> findByType(@RequestParam String type) {
        return vehicleDAO.findVehicleByType(type);
    }

    // GET: Retrieve all vehicles
    @RequestMapping (path="/vehicles", method = RequestMethod.GET)
    public List<Vehicle> findAllVehicles() {

        return vehicleDAO.findAllVehicles();
    }

    // POST: Add a new vehicle
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Vehicle addVehicle(@RequestBody Vehicle vehicle) {
        vehicleDAO.addVehicle(vehicle);
        return vehicle;
    }

    // PUT: Update an existing vehicle
    @PutMapping("/{vin}")
    @ResponseStatus(HttpStatus.OK)
    public Vehicle updateVehicle(@PathVariable int vin, @RequestBody Vehicle updatedVehicle) {
        Vehicle existingVehicle = vehicleDAO.findVehicleByVin(vin);
        if (existingVehicle == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Vehicle with VIN " + vin + " not found.");
        }

        // Update the vehicle data
        vehicleDAO.updateVehicle(vin, updatedVehicle);
        return updatedVehicle;
    }


    // DELETE: Remove a vehicle by VIN
    @DeleteMapping("/{vin}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteVehicle(@PathVariable int vin) {
        boolean removed = vehicleDAO.removeVehicle(vin);
        if (!removed) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Vehicle with VIN " + vin + " not found.");
        }
    }
}