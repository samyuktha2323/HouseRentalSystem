// RentalService.java
package service;

import model.House;
import model.Tenant;
import model.RentalAgreement;
import exception.HouseNotFoundException;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class RentalService {
    private List<RentalAgreement> agreements;
    private HouseService houseService;

    public RentalService(HouseService houseService) {
        this.agreements = new ArrayList<>();
        this.houseService = houseService;
    }

    public synchronized RentalAgreement bookHouse(String houseId, Tenant tenant, 
            LocalDate startDate, LocalDate endDate, double deposit) throws HouseNotFoundException {
        House house = houseService.findHouseById(houseId);
        
        if (!house.isAvailable()) {
            throw new HouseNotFoundException("House is not available for rent");
        }
        
        RentalAgreement agreement = new RentalAgreement(house, tenant, startDate, endDate, deposit);
        agreements.add(agreement);
        house.setAvailable(false);
        
        return agreement;
    }

    public List<RentalAgreement> getAllAgreements() {
        return new ArrayList<>(agreements);
    }
}