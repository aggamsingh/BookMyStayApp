import java.util.HashMap;
import java.util.Map;

public class HotelBookingApp {
    public static void main(String[] args) {
        System.out.println("--- Welcome to Book My Stay - Use Case 4 ---");
        System.out.println("System: Room Search & Availability Check");
        System.out.println("------------------------------------------------------------------");

        // 1. Setup Inventory (State Holder)
        RoomInventory inventory = new RoomInventory();
        
        // 2. Setup Domain Objects
        Room[] rooms = { new SingleRoom(), new DoubleRoom(), new SuiteRoom() };

        // 3. Initialize Search Service (Read-Only Logic)
        SearchService searchService = new SearchService(inventory);

        // 4. Simulate Guest Search
        System.out.println("Guest is searching for available rooms...");
        searchService.displayAvailableRooms(rooms);

        // 5. Simulate a booking to show dynamic search updates
        System.out.println("\n--- Admin Action: Setting Suite availability to 0 ---");
        inventory.setAvailability("Suite", 0);

        System.out.println("\nGuest searches again (Suites should be filtered out):");
        searchService.displayAvailableRooms(rooms);

        System.out.println("------------------------------------------------------------------");
    }
}

/**
 * Search Service: Handles read-only access to inventory.
 * Reinforces: Separation of Concerns & Validation Logic.
 */
class SearchService {
    private final RoomInventory inventory;

    public SearchService(RoomInventory inventory) {
        this.inventory = inventory;
    }

    public void displayAvailableRooms(Room[] rooms) {
        boolean found = false;
        for (Room room : rooms) {
            int count = inventory.getRoomCount(room.getType());
            
            // Validation Logic: Display only if availability > 0
            if (count > 0) {
                System.out.println("[AVAILABLE] " + room.getRoomDetails() + " | Stock: " + count);
                found = true;
            }
        }
        if (!found) System.out.println("No rooms currently available.");
    }
}

/**
 * Room Inventory: Centralized State Management.
 */
class RoomInventory {
    private final Map<String, Integer> availabilityMap = new HashMap<>();

    public RoomInventory() {
        availabilityMap.put("Single", 5);
        availabilityMap.put("Double", 3);
        availabilityMap.put("Suite", 2);
    }

    // Controlled getter for Search Service
    public int getRoomCount(String roomType) {
        return availabilityMap.getOrDefault(roomType, 0);
    }

    // Controlled setter for administrative changes
    public void setAvailability(String roomType, int count) {
        availabilityMap.put(roomType, count);
    }
}

// --- Domain Model (Abstract & Concrete Classes) ---
abstract class Room {
    private String type;
    private double price;

    public Room(String type, double price) {
        this.type = type;
        this.price = price;
    }

    public String getType() { return type; }
    public String getRoomDetails() {
        return String.format("Type: %-10s | Price: $%.2f", type, price);
    }
}

class SingleRoom extends Room { public SingleRoom() { super("Single", 100.0); } }
class DoubleRoom extends Room { public DoubleRoom() { super("Double", 180.0); } }
class SuiteRoom extends Room { public SuiteRoom() { super("Suite", 350.0); } }