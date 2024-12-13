package model;

public class Vehicle {
    private final int vin;
    private final int year;
    private final String make;
    private final String model;
    private final String vehicleType;
    private final String color;
    private final int odometer;
    private final double price;
    private final boolean sold;

    // Constructor for full initialization
    public Vehicle(int vin, int year, String make, String model, String vehicleType, String color, int odometer, double price, boolean sold) {
        this.vin = vin;
        this.year = year;
        this.make = make;
        this.model = model;
        this.vehicleType = vehicleType;
        this.color = color;
        this.odometer = odometer;
        this.price = price;
        this.sold = sold;
    }

    // Constructor for VIN only
    public Vehicle(int vin) {
        this.vin = vin;
        this.year = 0; // Default value
        this.make = null;
        this.model = null;
        this.vehicleType = null;
        this.color = null;
        this.odometer = 0;
        this.price = 0.0;
        this.sold = false;
    }

    public int getVin() {
        return vin;
    }

    public int getYear() {
        return year;
    }

    public String getMake() {
        return make;
    }

    public String getModel() {
        return model;
    }

    public String getVehicleType() {
        return vehicleType;
    }

    public String getColor() {
        return color;
    }

    public int getOdometer() {
        return odometer;
    }

    public double getPrice() {
        return price;
    }

    public boolean isSold() {
        return sold;
    }

    @Override
    public String toString() {
        return String.format(
                "VIN: %d | Year: %d | Make: %s | Model: %s | Type: %s | Color: %s | Odometer: %d | Price: $%.2f | Sold: %s",
                vin, year, make, model, vehicleType, color, odometer, price, sold ? "Yes" : "No"
        );
    }
}
