import java.util.*;

public class HotelBookingApp {
    public static void main(String[] args) {
        System.out.println("--- Use Case 8: Booking History & Reporting ---");

        // 1. Setup Services
        RoomInventory inventory = new RoomInventory();
        BookingHistory history = new BookingHistory();
        AllocationService allocationService = new AllocationService(inventory, history);

        // 2. Simulate successful bookings
        allocationService.processRequest(new ReservationRequest("Alice", "Suite"));
        allocationService.processRequest(new ReservationRequest("Bob", "Single"));

        // 3. Generate Reporting (The new feature)
        System.out.println("\n--- Generating Operational Report ---");
        history.displayHistory();

        System.out.println("------------------------------------------------------------------");
    }
}

/**
 * Booking History: Maintains a record of confirmed reservations.
 * Uses a List to preserve the chronological order of transactions.
 */
class BookingHistory {
    private final List<String> confirmedBookings = new ArrayList<>();

    public void addRecord(String guestName, String roomId) {
        String timestamp = new java.util.Date().toString();
        confirmedBookings.add(String.format("[%s] Guest: %-8s | Assigned: %s", timestamp, guestName, roomId));
    }

    public void displayHistory() {
        if (confirmedBookings.isEmpty()) {
            System.out.println("No booking history found.");
            return;
        }
        System.out.println("HISTORICAL BOOKING LOG:");
        confirmedBookings.forEach(System.out.println);
    }
}

/**
 * Updated Allocation Service: Now communicates with History Service.
 */
class AllocationService {
    private final RoomInventory inventory;
    private final BookingHistory history;
    private final Set<String> allocatedRoomIds = new HashSet<>();

    public AllocationService(RoomInventory inventory, BookingHistory history) {
        this.inventory = inventory;
        this.history = history;
    }

    public void processRequest(ReservationRequest request) {
        String type = request.getRoomType();
        if (inventory.getRoomCount(type) > 0) {
            String roomId = type.toUpperCase() + "-" + (100 + new Random().nextInt(900));
            
            if (allocatedRoomIds.add(roomId)) { // add returns true if ID is unique
                inventory.updateAvailability(type, -1);
                System.out.println("CONFIRMED: " + request.getGuestName() + " assigned to " + roomId);
                
                // NEW: Record to history
                history.addRecord(request.getGuestName(), roomId);
            }
        } else {
            System.out.println("REJECTED: No " + type + "s available for " + request.getGuestName());
        }
    }
}

// ... (Keep existing RoomInventory and ReservationRequest classes below) ...