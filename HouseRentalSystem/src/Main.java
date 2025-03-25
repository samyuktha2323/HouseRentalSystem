import model.House;
import model.Tenant;
import model.RentalAgreement;
import service.*;
import exception.*;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Scanner;

public class Main {
    private static HouseService houseService;
    private static TenantService tenantService;
    private static RentalService rentalService;
    private static FileService fileService;
    private static Scanner scanner;

    public static void main(String[] args) {
        initializeServices();
        scanner = new Scanner(System.in);

        try {
            loadData();
            showMainMenu();
            saveData();
        } catch (IOException e) {
            System.out.println("An IO error occurred: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("An unexpected error occurred: " + e.getMessage());
        } finally {
            scanner.close();
        }
    }

    private static void initializeServices() {
        houseService = new HouseService();
        tenantService = new TenantService();
        rentalService = new RentalService(houseService);
        fileService = new FileService();
    }

    private static void loadData() throws IOException {
        // Load houses
        List<House> houses = fileService.loadHouses();
        if (houses != null) {
            for (House house : houses) {
                try {
                    houseService.addHouse(house);
                } catch (InvalidInputException e) {
                    System.err.println("Skipping invalid house data: " + house + 
                                    "\nReason: " + e.getMessage());
                }
            }
        }

        // Load tenants
        List<Tenant> tenants = fileService.loadTenants();
        if (tenants != null) {
            for (Tenant tenant : tenants) {
                try {
                    tenantService.registerTenant(tenant);
                } catch (InvalidInputException e) {
                    System.err.println("Skipping invalid tenant data: " + tenant + 
                                     "\nReason: " + e.getMessage());
                }
            }
        }
    }

    private static void saveData() throws IOException {
        fileService.saveHouses(houseService.getAllHouses());
        fileService.saveTenants(tenantService.getAllTenants());
    }

    private static void showMainMenu() {
        while (true) {
            System.out.println("\nHouse Rental Management System");
            System.out.println("1. Manage Houses");
            System.out.println("2. Manage Tenants");
            System.out.println("3. Manage Rentals");
            System.out.println("4. Exit");
            System.out.print("Choose an option: ");

            try {
                int choice = Integer.parseInt(scanner.nextLine());

                switch (choice) {
                    case 1:
                        manageHouses();
                        break;
                    case 2:
                        manageTenants();
                        break;
                    case 3:
                        manageRentals();
                        break;
                    case 4:
                        return;
                    default:
                        System.out.println("Invalid option. Please try again.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid number.");
            }
        }
    }

    private static void manageHouses() {
        while (true) {
            System.out.println("\nHouse Management");
            System.out.println("1. Add House");
            System.out.println("2. Search Houses");
            System.out.println("3. List All Houses");
            System.out.println("4. Back to Main Menu");
            System.out.print("Choose an option: ");

            try {
                int choice = Integer.parseInt(scanner.nextLine());

                switch (choice) {
                    case 1:
                        addHouse();
                        break;
                    case 2:
                        searchHouses();
                        break;
                    case 3:
                        listAllHouses();
                        break;
                    case 4:
                        return;
                    default:
                        System.out.println("Invalid option. Please try again.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid number.");
            }
        }
    }

    private static void addHouse() {
        System.out.println("\nAdd New House");
        System.out.print("Enter House ID: ");
        String id = scanner.nextLine();
        
        System.out.print("Enter Location: ");
        String location = scanner.nextLine();
        
        System.out.print("Enter Price: ");
        try {
            double price = Double.parseDouble(scanner.nextLine());
            
            System.out.print("Enter Number of Bedrooms: ");
            int bedrooms = Integer.parseInt(scanner.nextLine());
            
            System.out.print("Enter Owner Name: ");
            String owner = scanner.nextLine();

            try {
                House house = new House(id, location, price, bedrooms, owner);
                houseService.addHouse(house);
                System.out.println("House added successfully!");
            } catch (InvalidInputException e) {
                System.out.println("Error: " + e.getMessage());
            }
        } catch (NumberFormatException e) {
            System.out.println("Invalid number format. Please enter valid numbers for price and bedrooms.");
        }
    }

    private static void searchHouses() {
        System.out.println("\nSearch Houses");
        System.out.print("Enter Location (leave empty for any): ");
        String location = scanner.nextLine();
        
        System.out.print("Enter Maximum Price: ");
        try {
            double maxPrice = Double.parseDouble(scanner.nextLine());

            List<House> results = houseService.searchHouses(location.isEmpty() ? null : location, maxPrice);
            
            if (results.isEmpty()) {
                System.out.println("No houses found matching your criteria.");
            } else {
                System.out.println("Found " + results.size() + " house(s):");
                results.forEach(System.out::println);
            }
        } catch (NumberFormatException e) {
            System.out.println("Invalid price format. Please enter a valid number.");
        }
    }

    private static void listAllHouses() {
        List<House> houses = houseService.getAllHouses();
        if (houses.isEmpty()) {
            System.out.println("No houses available.");
        } else {
            System.out.println("All Houses:");
            houses.forEach(System.out::println);
        }
    }

    private static void manageTenants() {
        while (true) {
            System.out.println("\nTenant Management");
            System.out.println("1. Register Tenant");
            System.out.println("2. List All Tenants");
            System.out.println("3. Back to Main Menu");
            System.out.print("Choose an option: ");

            try {
                int choice = Integer.parseInt(scanner.nextLine());

                switch (choice) {
                    case 1:
                        registerTenant();
                        break;
                    case 2:
                        listAllTenants();
                        break;
                    case 3:
                        return;
                    default:
                        System.out.println("Invalid option. Please try again.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid number.");
            }
        }
    }

    private static void registerTenant() {
        System.out.println("\nRegister New Tenant");
        System.out.print("Enter Tenant ID: ");
        String id = scanner.nextLine();
        
        System.out.print("Enter Name: ");
        String name = scanner.nextLine();
        
        System.out.print("Enter Contact: ");
        String contact = scanner.nextLine();
        
        System.out.print("Enter Preferred Location: ");
        String preferredLocation = scanner.nextLine();

        try {
            Tenant tenant = new Tenant(id, name, contact, preferredLocation);
            tenantService.registerTenant(tenant);
            System.out.println("Tenant registered successfully!");
        } catch (InvalidInputException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private static void listAllTenants() {
        List<Tenant> tenants = tenantService.getAllTenants();
        if (tenants.isEmpty()) {
            System.out.println("No tenants registered.");
        } else {
            System.out.println("All Tenants:");
            tenants.forEach(System.out::println);
        }
    }

    private static void manageRentals() {
        while (true) {
            System.out.println("\nRental Management");
            System.out.println("1. Book a House");
            System.out.println("2. View All Rental Agreements");
            System.out.println("3. Back to Main Menu");
            System.out.print("Choose an option: ");

            try {
                int choice = Integer.parseInt(scanner.nextLine());

                switch (choice) {
                    case 1:
                        bookHouse();
                        break;
                    case 2:
                        viewRentalAgreements();
                        break;
                    case 3:
                        return;
                    default:
                        System.out.println("Invalid option. Please try again.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid number.");
            }
        }
    }

    private static void bookHouse() {
        System.out.println("\nBook a House");
        listAllHouses();
        
        System.out.print("Enter House ID to book: ");
        String houseId = scanner.nextLine();
        
        listAllTenants();
        System.out.print("Enter Tenant ID: ");
        String tenantId = scanner.nextLine();
        
        try {
            System.out.print("Enter Start Date (YYYY-MM-DD): ");
            LocalDate startDate = LocalDate.parse(scanner.nextLine());
            
            System.out.print("Enter End Date (YYYY-MM-DD): ");
            LocalDate endDate = LocalDate.parse(scanner.nextLine());
            
            System.out.print("Enter Deposit Amount: ");
            double deposit = Double.parseDouble(scanner.nextLine());

            try {
                Tenant tenant = tenantService.getAllTenants().stream()
                    .filter(t -> t.getId().equals(tenantId))
                    .findFirst()
                    .orElseThrow(() -> new Exception("Tenant not found"));
                
                RentalAgreement agreement = rentalService.bookHouse(
                    houseId, tenant, startDate, endDate, deposit);
                
                System.out.println("House booked successfully!");
                System.out.println(agreement);
            } catch (HouseNotFoundException e) {
                System.out.println("Error: " + e.getMessage());
            } catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
            }
        } catch (DateTimeParseException e) {
            System.out.println("Invalid date format. Please use YYYY-MM-DD format.");
        } catch (NumberFormatException e) {
            System.out.println("Invalid deposit amount. Please enter a valid number.");
        }
    }

    private static void viewRentalAgreements() {
        List<RentalAgreement> agreements = rentalService.getAllAgreements();
        if (agreements.isEmpty()) {
            System.out.println("No rental agreements found.");
        } else {
            System.out.println("All Rental Agreements:");
            agreements.forEach(System.out::println);
        }
    }
}