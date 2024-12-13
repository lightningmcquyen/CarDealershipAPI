package controllers;


import dao.VehicleDAO;
import model.Vehicle;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/api/vehicles")
public class VehicleController {

    private final VehicleDAO vehicleDAO;

    public VehicleController(VehicleDAO vehicleDAO) {
        this.vehicleDAO = vehicleDAO;
    }

    @GetMapping("/{vin}")
    public Vehicle findByVin(@PathVariable("vin") int vin) {
        return vehicleDAO.findVehicleByVin(vin);
    }

    @GetMapping
    public List<Vehicle> findAllVehicles() {
        return vehicleDAO.findAllVehicles();
    }

    @PostMapping
    @ResponseStatus(code = HttpStatus.CREATED)
    public void addVehicle(@RequestBody Vehicle vehicle) {
        vehicleDAO.addVehicle(vehicle);
    }

    @DeleteMapping("/{vin}")
    @ResponseStatus(code = HttpStatus.NO_CONTENT)
    public void deleteVehicle(@PathVariable("vin") int vin) {
        vehicleDAO.removeVehicle(vin);
    }

    @GetMapping("/price")
    public List<Vehicle> findByPriceRange(
            @RequestParam("min") double minPrice,
            @RequestParam("max") double maxPrice) {
        return vehicleDAO.findVehicleByPriceRange(minPrice, maxPrice);
    }
}