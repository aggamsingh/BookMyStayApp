import java.util.HashMap;
import java.util.Map;

public class HotelBookingApp {
    public static void main(String[] args) {
        System.out.println("--- Welcome to Book My Stay - Use Case 3 ---");
        System.out.println("System: Centralized Inventory Management");
        System.out.println("------------------------------------------------------------------");

        // 1. Initialize the Inventory Component (Requirement: Initialize via Constructor)
        RoomInventory inventory = new RoomInventory();

        // 2. Display Initial State
        inventory.displayInventory();

        // 3. Support controlled updates (Requirement: Controlled updates)
        System.out.println("\n--- Action: Booking 1 Suite and 1 Single Room ---");
        inventory.updateAvailability("Suite", -1);
        inventory.updateAvailability("Single", -1);

        // 4. Ensure consistency (Displaying updated state)
        inventory.displayInventory();

        System.out.println("------------------------------------------------------------------");
    }
}

/**
 * Encapsulation of Inventory Logic: 
 * This class manages HOW many rooms are available.
 */
class RoomInventory {
    // Key: Room Type | Value: Count
    private final Map<String, Integer> availabilityMap;

    public RoomInventory() {
        this.availabilityMap = new HashMap<>();
        // Requirement: Registering room types with available counts
        availabilityMap.put("Single", 5);
        availabilityMap.put("Double", 3);
        availabilityMap.put("Suite", 2);
    }

    // Requirement: Support controlled updates
    public void updateAvailability(String roomType, int change) {
        if (availabilityMap.containsKey(roomType)) {
            int currentCount = availabilityMap.get(roomType);
            availabilityMap.put(roomType, currentCount + change);
        } else {
            System.out.println("Error: Room type " + roomType + " not found.");
        }
    }

    // Requirement: Provide methods to retrieve current availability
    public void displayInventory() {
        System.out.println("Current Inventory Status:");
        availabilityMap.forEach((type, count) -> 
            System.out.println("Room Type: " + type + " | Available: " + count));
    }
}

// --- Domain Model (Separation of Concerns: These stay in the domain layer) ---
abstract class Room {
    private String type;
    public Room(String type) { this.type = type; }
    public String getType() { return type; }
}

class SingleRoom extends Room { public SingleRoom() { super("Single"); } }
class DoubleRoom extends Room { public DoubleRoom() { super("Double"); } }
class SuiteRoom extends Room { public SuiteRoom() { super("Suite"); } }