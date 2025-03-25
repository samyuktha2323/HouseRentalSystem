// RentalAgreement.java
package model;

import java.time.LocalDate;

public class RentalAgreement {
    private House house;
    private Tenant tenant;
    private LocalDate startDate;
    private LocalDate endDate;
    private double deposit;

    public RentalAgreement(House house, Tenant tenant, LocalDate startDate, 
                          LocalDate endDate, double deposit) {
        this.house = house;
        this.tenant = tenant;
        this.startDate = startDate;
        this.endDate = endDate;
        this.deposit = deposit;
    }

    // Getters
    public House getHouse() { return house; }
    public Tenant getTenant() { return tenant; }
    public LocalDate getStartDate() { return startDate; }
    public LocalDate getEndDate() { return endDate; }
    public double getDeposit() { return deposit; }

    @Override
    public String toString() {
        return String.format("Rental Agreement:\nHouse: %s\nTenant: %s\n" +
                           "Period: %s to %s\nDeposit: %.2f",
            house, tenant, startDate, endDate, deposit);
    }
}
