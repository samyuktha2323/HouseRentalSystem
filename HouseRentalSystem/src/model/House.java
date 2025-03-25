// House.java
package model;

import java.io.Serializable;

public class House implements Serializable {
    private String id;
    private String location;
    private String owner;
    private double price;
    private int bedrooms;
    private boolean isAvailable;

    public House(String id, String location, double price, int bedrooms, String owner) {
        this.id = id;
        this.location = location;
        this.price = price;
        this.bedrooms = bedrooms;
        this.owner = owner;
        this.isAvailable = true;
    }

    public String getId() { return id; }
    public String getLocation() { return location; }
    public double getPrice() { return price; }
    public int getBedrooms() { return bedrooms; }
    public String getOwner() { return owner; }
    public boolean isAvailable() { return isAvailable; }
    
    public void setAvailable(boolean available) { isAvailable = available; }

    @Override
    public String toString() {
        return String.format("[%s, %s, %.2f, %d, %s, %s]", 
            id, location, price, bedrooms, owner, isAvailable ? "Available" : "Rented");
    }
}