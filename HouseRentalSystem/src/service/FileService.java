// FileService.java
package service;

import model.House;
import model.Tenant;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class FileService {
    private static final String HOUSES_FILE = "houses.txt";
    private static final String TENANTS_FILE = "tenants.txt";

    public void saveHouses(List<House> houses) throws IOException {
        try (PrintWriter writer = new PrintWriter(new FileWriter(HOUSES_FILE))) {
            for (House house : houses) {
                writer.println(String.format("%s,%s,%.2f,%d,%s,%b",
                    house.getId(), house.getLocation(), house.getPrice(),
                    house.getBedrooms(), house.getOwner(), house.isAvailable()));
            }
        }
    }

    public List<House> loadHouses() throws IOException {
        List<House> houses = new ArrayList<>();
        File file = new File(HOUSES_FILE);
        
        if (!file.exists()) {
            return houses;
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 6) {
                    House house = new House(
                        parts[0], parts[1], 
                        Double.parseDouble(parts[2]), 
                        Integer.parseInt(parts[3]), 
                        parts[4]);
                    house.setAvailable(Boolean.parseBoolean(parts[5]));
                    houses.add(house);
                }
            }
        }
        return houses;
    }

    public void saveTenants(List<Tenant> tenants) throws IOException {
        try (PrintWriter writer = new PrintWriter(new FileWriter(TENANTS_FILE))) {
            for (Tenant tenant : tenants) {
                writer.println(String.format("%s,%s,%s,%s",
                    tenant.getId(), tenant.getName(),
                    tenant.getContact(), tenant.getPreferredLocation()));
            }
        }
    }

    public List<Tenant> loadTenants() throws IOException {
        List<Tenant> tenants = new ArrayList<>();
        File file = new File(TENANTS_FILE);
        
        if (!file.exists()) {
            return tenants;
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 4) {
                    tenants.add(new Tenant(
                        parts[0], parts[1], parts[2], parts[3]));
                }
            }
        }
        return tenants;
    }
}