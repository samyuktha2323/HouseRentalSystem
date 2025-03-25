// TenantService.java
package service;

import model.Tenant;
import exception.InvalidInputException;
import java.util.stream.Collectors;
import java.util.ArrayList;
import java.util.List;

public class TenantService {
    private List<Tenant> tenants;

    public TenantService() {
        this.tenants = new ArrayList<>();
    }

    public void registerTenant(Tenant tenant) throws InvalidInputException {
        if (tenant.getName() == null || tenant.getName().trim().isEmpty()) {
            throw new InvalidInputException("Tenant name cannot be empty");
        }
        tenants.add(tenant);
    }

    public List<Tenant> findTenantsByPreferredLocation(String location) {
        return tenants.stream()
            .filter(t -> t.getPreferredLocation().equalsIgnoreCase(location))
            .collect(Collectors.toList());
    }

    public List<Tenant> getAllTenants() {
        return new ArrayList<>(tenants);
    }
}