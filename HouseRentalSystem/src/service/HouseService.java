// HouseService.java
package service;

import model.House;
import exception.HouseNotFoundException;
import exception.InvalidInputException;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class HouseService {
    private List<House> houses;

    public HouseService() {
        this.houses = new ArrayList<>();
    }

    public void addHouse(House house) throws InvalidInputException {
        if (house.getPrice() <= 0) {
            throw new InvalidInputException("Price must be positive");
        }
        if (house.getBedrooms() <= 0) {
            throw new InvalidInputException("Bedrooms must be positive");
        }
        houses.add(house);
    }

    public List<House> searchHouses(String location, double maxPrice) {
        return houses.stream()
            .filter(h -> h.isAvailable() && 
                        (location == null || h.getLocation().equalsIgnoreCase(location)) && 
                        h.getPrice() <= maxPrice)
            .collect(Collectors.toList());
    }

    public House findHouseById(String id) throws HouseNotFoundException {
        return houses.stream()
            .filter(h -> h.getId().equals(id))
            .findFirst()
            .orElseThrow(() -> new HouseNotFoundException("House not found with ID: " + id));
    }

    public List<House> getAllHouses() {
        return new ArrayList<>(houses);
    }
}