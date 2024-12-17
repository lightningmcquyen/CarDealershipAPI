package com.pluralsight.model;

public class LeaseContract extends Contract {
    private final double originalPrice;
    private final boolean financed;

    public LeaseContract(String dateOfContract, String customerName, String customerEmail, Vehicle vehicle, double originalPrice, boolean financed) {
        super(dateOfContract, customerName, customerEmail, vehicle);
        this.originalPrice = originalPrice;
        this.financed = financed;
    }

    @Override
    public double getTotalPrice() {
        double expectedEndingValue = originalPrice * 0.50; // 50% of original price
        double leaseFee = originalPrice * 0.07; // 7% lease fee
        return originalPrice + leaseFee + expectedEndingValue;
    }

    @Override
    public double getMonthlyPayment() {
        if (!financed) return 0;
        double totalPrice = getTotalPrice();
        double interestRate = 0.04; // Fixed interest rate for leases
        int months = 36; // Lease duration in months
        return (totalPrice * interestRate / 12) / (1 - Math.pow(1 + interestRate / 12, -months));
    }

    public boolean isFinanced() {
        return financed;
    }

    public double getOriginalPrice() {
        return originalPrice;
    }

    public int getVin() {
        return getVehicleSold().getVin(); // Using getVehicleSold() inherited from Contract
    }

    // Implement the abstract method 'value' from Contract
    @Override
    public double value() {
        // Return some meaningful value; here, we use the total price as the contract value
        return getTotalPrice();
    }
}