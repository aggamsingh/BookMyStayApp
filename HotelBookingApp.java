/**
 * Hotel Booking Management System
 * Current Stage: Use Case 2 - Basic Room Types & Static Availability
 */
public class HotelBookingApp {

    public static void main(String[] args) {
        System.out.println("--- Welcome to Book My Stay - Hotel Booking Management System ---");
        System.out.println("System initialized successfully.");
        System.out.println("------------------------------------------------------------------");

        // 1. Initialize Room Objects (Polymorphism)
        // We use the parent type 'Room' to refer to specific room instances
        Room single = new SingleRoom();
        Room doubleRoom = new DoubleRoom();
        Room suite = new SuiteRoom();

        // 2. Static Availability Representation 
        // Note: Using simple variables highlights why we'll need Collections later
        int singleAvailable = 5;
        int doubleAvailable = 3;
        int suiteAvailable = 2;

        // 3. Display Room details and availability
        System.out.println("CURRENT ROOM INVENTORY:");
        displayRoomStatus(single, singleAvailable);
        displayRoomStatus(doubleRoom, doubleAvailable);
        displayRoomStatus(suite, suiteAvailable);

        System.out.println("------------------------------------------------------------------");
        System.out.println("Application terminated.");
    }

    // Static helper method to keep the main method clean
    private static void displayRoomStatus(Room room, int count) {
        System.out.println(room.getRoomDetails() + " | Available: " + count);
    }
}

// --- DOMAIN MODEL ---

/**
 * Abstract Class: Defines the blueprint for all room types.
 * Demonstrates: Abstraction and Encapsulation.
 */
abstract class Room {
    private String type;
    private int beds;
    private double pricePerNight;

    public Room(String type, int beds, double pricePerNight) {
        this.type = type;
        this.beds = beds;
        this.pricePerNight = pricePerNight;
    }

    // Encapsulation: Accessing private data through a public method
    public String getRoomDetails() {
        return String.format("Type: %-10s | Beds: %d | Price: $%.2f", type, beds, pricePerNight);
    }
}

/**
 * Inheritance: SingleRoom, DoubleRoom, and SuiteRoom 
 * specialize the abstract Room class.
 */
class SingleRoom extends Room {
    public SingleRoom() {
        super("Single", 1, 100.0);
    }
}

class DoubleRoom extends Room {
    public DoubleRoom() {
        super("Double", 2, 180.0);
    }
}

class SuiteRoom extends Room {
    public SuiteRoom() {
        super("Suite", 4, 350.0);
    }
}