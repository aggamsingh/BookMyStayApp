import java.util.*;

public class HotelBookingApp {
    public static void main(String[] args) {
        System.out.println("--- Use Case 6: Reservation Confirmation & Room Allocation ---");

        // 1. Setup State
        RoomInventory inventory = new RoomInventory();
        Queue<ReservationRequest> bookingQueue = new LinkedList<>();
        AllocationService allocationService = new AllocationService(inventory);

        // 2. Add Requests to Queue (FIFO)
        bookingQueue.add(new ReservationRequest("Alice", "Suite"));
        bookingQueue.add(new ReservationRequest("Bob", "Suite"));
        bookingQueue.add(new ReservationRequest("Charlie", "Suite")); // Only 2 Suites exist!

        // 3. Process the Queue
        System.out.println("Processing Booking Requests...\n");
        while (!bookingQueue.isEmpty()) {
            ReservationRequest request = bookingQueue.poll();
            allocationService.processRequest(request);
        }

        System.out.println("\nFinal Inventory Status:");
        inventory.displayInventory();
    }
}

/**
 * Allocation Service: Ensures uniqueness using a Set.
 * Prevents double-booking and updates inventory atomically.
 */
class AllocationService {
    private final RoomInventory inventory;
    // Requirement: Set to store unique allocated Room IDs
    private final Set<String> allocatedRoomIds = new HashSet<>();

    public AllocationService(RoomInventory inventory) {
        this.inventory = inventory;
    }

    public void processRequest(ReservationRequest request) {
        String type = request.getRoomType();
        
        if (inventory.getRoomCount(type) > 0) {
            // Requirement: Generate a Unique Room ID (e.g., SUITE-101)
            String roomId = type.toUpperCase() + "-" + (100 + new Random().nextInt(900));
            
            // Uniqueness Enforcement
            if (!allocatedRoomIds.contains(roomId)) {
                allocatedRoomIds.add(roomId);
                inventory.updateAvailability(type, -1);
                System.out.println("CONFIRMED: " + request.getGuestName() + " assigned to " + roomId);
            }
        } else {
            System.out.println("REJECTED: No " + type + "s available for " + request.getGuestName());
        }
    }
}

class RoomInventory {
    private final Map<String, Integer> availabilityMap = new HashMap<>();
    public RoomInventory() {
        availabilityMap.put("Single", 5);
        availabilityMap.put("Double", 3);
        availabilityMap.put("Suite", 2); // Limited supply to test rejection logic
    }
    public int getRoomCount(String type) { return availabilityMap.getOrDefault(type, 0); }
    public void updateAvailability(String type, int change) {
        availabilityMap.put(type, availabilityMap.get(type) + change);
    }
    public void displayInventory() {
        availabilityMap.forEach((k, v) -> System.out.println(k + ": " + v));
    }
}

class ReservationRequest {
    private final String guestName;
    private final String roomType;
    public ReservationRequest(String g, String r) { this.guestName = g; this.roomType = r; }
    public String getGuestName() { return guestName; }
    public String getRoomType() { return roomType; }
}