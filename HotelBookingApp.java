import java.util.*;

public class HotelBookingApp {
    public static void main(String[] args) {
        System.out.println("--- Welcome to Book My Stay - Use Case 5 ---");
        System.out.println("System: Booking Request Queue (FIFO)");
        System.out.println("------------------------------------------------------------------");

        // 1. Initialize the Queue (FIFO structure)
        Queue<ReservationRequest> bookingQueue = new LinkedList<>();

        // 2. Simulate incoming requests (Arrival order matters!)
        System.out.println("Receiving incoming booking requests...");
        bookingQueue.add(new ReservationRequest("Alice", "Suite"));
        bookingQueue.add(new ReservationRequest("Bob", "Single"));
        bookingQueue.add(new ReservationRequest("Charlie", "Suite"));

        // 3. Display the Queue state
        System.out.println("\nRequests currently waiting in Queue:");
        for (ReservationRequest request : bookingQueue) {
            System.out.println(request);
        }

        // 4. Prepare for processing (Next Use Case preview)
        System.out.println("\nTotal requests to be processed: " + bookingQueue.size());
        System.out.println("First person in line: " + bookingQueue.peek().getGuestName());

        System.out.println("------------------------------------------------------------------");
    }
}

/**
 * Reservation Request: Represents a guest's intent to book.
 */
class ReservationRequest {
    private final String guestName;
    private final String roomType;
    private final long timestamp;

    public ReservationRequest(String guestName, String roomType) {
        this.guestName = guestName;
        this.roomType = roomType;
        this.timestamp = System.currentTimeMillis();
    }

    public String getGuestName() { return guestName; }

    @Override
    public String toString() {
        return String.format("Guest: %-8s | Room: %-10s | Status: QUEUED", guestName, roomType);
    }
}

// --- (Keep your Room, SingleRoom, etc. classes below as before) ---