// Tenant.java
package model;

import java.io.Serializable;

public class Tenant implements Serializable {
    private String id;
    private String name;
    private String contact;
    private String preferredLocation;

    public Tenant(String id, String name, String contact, String preferredLocation) {
        this.id = id;
        this.name = name;
        this.contact = contact;
        this.preferredLocation = preferredLocation;
    }

    // Getters and Setters
    public String getId() { return id; }
    public String getName() { return name; }
    public String getContact() { return contact; }
    public String getPreferredLocation() { return preferredLocation; }

    @Override
    public String toString() {
        return String.format("[%s, %s, %s, %s]", 
            id, name, contact, preferredLocation);
    }
}
