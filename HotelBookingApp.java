import java.util.*;

public class HotelBookingApp {
    public static void main(String[] args) {
        System.out.println("--- Use Case 9: Error Handling & Validation ---");

        RoomInventory inventory = new RoomInventory();
        BookingValidator validator = new BookingValidator(inventory);

        // 1. Simulate a VALID request
        processBooking("Alice", "Single", validator);

        // 2. Simulate an INVALID request (Room type doesn't exist)
        processBooking("Bob", "Penthouse", validator);

        // 3. Simulate an INVALID request (Sold out)
        // Let's pretend we manually set suites to 0
        inventory.updateAvailability("Suite", -2); 
        processBooking("Charlie", "Suite", validator);

        System.out.println("\nSystem remains stable after handling errors.");
    }

    private static void processBooking(String guest, String type, BookingValidator validator) {
        try {
            validator.validate(guest, type);
            System.out.println("SUCCESS: Request for " + guest + " is valid.");
        } catch (InvalidBookingException e) {
            System.err.println("VALIDATION FAILED: " + e.getMessage());
        }
    }
}

/**
 * Custom Exception: Provides domain-specific error reporting.
 */
class InvalidBookingException extends Exception {
    public InvalidBookingException(String message) {
        super(message);
    }
}

/**
 * Booking Validator: Checks for invalid inputs and system constraints.
 * Implements a Fall-Fast design.
 */
class BookingValidator {
    private final RoomInventory inventory;

    public BookingValidator(RoomInventory inventory) {
        this.inventory = inventory;
    }

    public void validate(String guestName, String roomType) throws InvalidBookingException {
        // Check for empty inputs
        if (guestName == null || guestName.trim().isEmpty()) {
            throw new InvalidBookingException("Guest name cannot be empty.");
        }

        // Check if room type exists in inventory
        if (!inventory.hasRoomType(roomType)) {
            throw new InvalidBookingException("Room type '" + roomType + "' does not exist.");
        }

        // Check for availability (Guarding System State)
        if (inventory.getRoomCount(roomType) <= 0) {
            throw new InvalidBookingException("Room type '" + roomType + "' is currently sold out.");
        }
    }
}

/**
 * Updated RoomInventory with helper for validation.
 */
class RoomInventory {
    private final Map<String, Integer> availabilityMap = new HashMap<>();

    public RoomInventory() {
        availabilityMap.put("Single", 5);
        availabilityMap.put("Double", 3);
        availabilityMap.put("Suite", 2);
    }

    public boolean hasRoomType(String type) { return availabilityMap.containsKey(type); }
    public int getRoomCount(String type) { return availabilityMap.getOrDefault(type, 0); }
    public void updateAvailability(String type, int change) {
        availabilityMap.put(type, availabilityMap.get(type) + change);
    }
}