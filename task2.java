import java.util.*;
public class HotelReservationSystem 
{
    static Scanner sc= new Scanner(System.in);
    static ArrayList<Room> rooms= new ArrayList<>();
    static ArrayList<Booking> bookings= new ArrayList<>();

    public static void main(String[] args) {
        initializeRooms();

        int choice;
        do {
            System.out.println("\n========== HOTEL RESERVATION SYSTEM ==========");
            System.out.println("1. View Available Rooms");
            System.out.println("2. Make a Reservation");
            System.out.println("3. View Booking Details");
            System.out.println("4. Exit");
            System.out.print("Enter choice: ");
            choice=sc.nextInt();
            sc.nextLine(); 
            switch (choice) {
                case 1:
                    viewAvailableRooms();
                    break;
                case 2:
                    makeReservation();
                    break;
                case 3:
                    viewBookings();
                    break;
                case 4:
                    System.out.println("Thank you for using our reservation system!");
                    break;
                default:
                    System.out.println("Invalid choice. Try again.");
            }
        } while (choice != 4);
    }
    static void initializeRooms() {
        rooms.add(new Room(101, "Deluxe", 8000));
        rooms.add(new Room(102, "Deluxe", 8000));
        rooms.add(new Room(201, "Standard", 5000));
        rooms.add(new Room(202, "Standard", 5000));
        rooms.add(new Room(301, "Suite", 12000));
        rooms.add(new Room(302, "Suite", 12000));
    }
    static void viewAvailableRooms() {
        System.out.println("\nAvailable Rooms:");
        for (Room room : rooms) {
            if (!room.isBooked) {
                System.out.println("Room " + room.roomNumber + " - " + room.category + " - Rs." + room.pricePerNight);
            }
        }
    }
    static void makeReservation() {
        System.out.print("\nEnter customer name: ");
        String name=sc.nextLine();

        System.out.print("Enter contact number: ");
        String contact=sc.nextLine();
        viewAvailableRooms();

        System.out.print("Enter desired room number: ");
        int roomNumber=sc.nextInt();

        Room selectedRoom=null;
        for (Room room : rooms) {
            if (room.roomNumber == roomNumber && !room.isBooked) {
                selectedRoom=room;
                break;
            }
        }

        if (selectedRoom==null) {
            System.out.println("Room not available or invalid number.");
            return;
        }

        System.out.print("Enter number of nights: ");
        int nights=sc.nextInt();
        double totalCost=nights * selectedRoom.pricePerNight;
        System.out.println("Total Cost: Rs." + totalCost);
        System.out.print("Enter payment amount: Rs.");
        double payment=sc.nextDouble();

        if (payment<totalCost) {
            System.out.println("Insufficient payment. Booking failed.");
            return;
        }
        selectedRoom.isBooked=true;

        Booking booking=new Booking(name, contact, selectedRoom, nights);
        bookings.add(booking);
        System.out.println("Booking successful! Room " + selectedRoom.roomNumber + " reserved for " + name + ".");
    }
    static void viewBookings() {
        if (bookings.isEmpty()) {
            System.out.println("No bookings found.");
            return;
        }
        System.out.println("\nBooking Details:");
        for (Booking booking : bookings) {
            System.out.println("Customer: " + booking.customerName);
            System.out.println("Contact: " + booking.contactNumber);
            System.out.println("Room No: " + booking.room.roomNumber + " (" + booking.room.category + ")");
            System.out.println("Nights: " + booking.nights);
            System.out.println("Total Paid: Rs." + booking.totalCost);
            System.out.println("----------------------------");
        }
    }
}
class Room {
    int roomNumber;
    String category;
    double pricePerNight;
    boolean isBooked;
    Room(int roomNumber, String category, double pricePerNight) {
        this.roomNumber=roomNumber;
        this.category=category;
        this.pricePerNight=pricePerNight;
        this.isBooked=false;
    }
}

class Booking {
    String customerName;
    String contactNumber;
    Room room;
    int nights;
    double totalCost;
    Booking(String customerName, String contactNumber, Room room, int nights) {
        this.customerName=customerName;
        this.contactNumber=contactNumber;
        this.room=room;
        this.nights=nights;
        this.totalCost=nights * room.pricePerNight;
    }
}
