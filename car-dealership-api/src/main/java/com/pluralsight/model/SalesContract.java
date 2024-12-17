package com.pluralsight.model;

public class SalesContract extends Contract {
    private final double originalPrice;
    private final boolean financed;

    public SalesContract(String dateOfContract, String customerName, String customerEmail, Vehicle vehicle, double originalPrice, boolean financed) {
        super(dateOfContract, customerName, customerEmail, vehicle);
        this.originalPrice = originalPrice;
        this.financed = financed;
    }

    @Override
    public double getTotalPrice() {
        double salesTax = originalPrice * 0.05; // 5% sales tax
        double recordingFee = 100.00; // Fixed recording fee
        double processingFee = originalPrice < 10000 ? 295.00 : 495.00; // Conditional processing fee
        return originalPrice + salesTax + recordingFee + processingFee;
    }

    @Override
    public double getMonthlyPayment() {
        if (!financed) return 0; // No monthly payment if not financed
        double totalPrice = getTotalPrice();
        double interestRate = originalPrice >= 10000 ? 0.0425 : 0.0525; // Interest rate based on price
        int months = originalPrice >= 10000 ? 48 : 24; // Financing term based on price
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
        return getTotalPrice(); // Or another meaningful calculation
    }
}