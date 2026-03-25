import java.util.*;

public class HotelBookingApp {
    public static void main(String[] args) {
        System.out.println("--- Use Case 7: Add-On Service Selection ---");

        // 1. Setup Data Structures
        AddOnManager addOnManager = new AddOnManager();

        // 2. Define Available Services
        Service breakfast = new Service("Breakfast", 15.0);
        Service spa = new Service("Spa Access", 50.0);
        Service wifi = new Service("Premium WiFi", 10.0);

        // 3. Associate services with a Reservation ID (e.g., SUITE-402 from UC6)
        String resId = "SUITE-402";
        System.out.println("Adding services for Reservation: " + resId);
        
        addOnManager.addServiceToReservation(resId, breakfast);
        addOnManager.addServiceToReservation(resId, spa);

        // 4. Display Final Bill/Details
        addOnManager.displayReservationSummary(resId);
        
        System.out.println("------------------------------------------------------------------");
        System.out.println("Project Milestone Complete: Core Data Structures Applied.");
    }
}

/**
 * Service Model: Represents an individual optional offering.
 */
class Service {
    private String name;
    private double price;

    public Service(String name, double price) {
        this.name = name;
        this.price = price;
    }

    public String getName() { return name; }
    public double getPrice() { return price; }
}

/**
 * Add-On Manager: Manages the One-to-Many mapping.
 * Uses Map<String, List<Service>> to link one ID to multiple offerings.
 */
class AddOnManager {
    private final Map<Map<String, List<Service>>, List<Service>> hiddenMap; // Visualizing complexity
    private final Map<String, List<Service>> serviceMapping = new HashMap<>();

    public void addServiceToReservation(String resId, Service service) {
        // computeIfAbsent creates a new List if the ID is seen for the first time
        serviceMapping.computeIfAbsent(resId, k -> new ArrayList<>()).add(service);
        System.out.println("Attached: " + service.getName());
    }

    public void displayReservationSummary(String resId) {
        List<Service> services = serviceMapping.getOrDefault(resId, Collections.emptyList());
        double total = 0;

        System.out.println("\nSummary for " + resId + ":");
        for (Service s : services) {
            System.out.println("- " + s.getName() + " ($" + s.getPrice() + ")");
            total += s.getPrice();
        }
        System.out.println("Total Add-On Cost: $" + total);
    }
}