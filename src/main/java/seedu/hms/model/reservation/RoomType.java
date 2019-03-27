package seedu.hms.model.reservation;

/**
 * Contains the details of every room in the hotel.
 */
public enum RoomType {

    SINGLE(20, "SINGLE ROOM", 500.0),
    DOUBLE(50, "DOUBLE ROOM", 250.0),
    DELUXE(40, "DELUXE ROOM", 700.0),
    SUITE(10, "FAMILY SUITE", 6000.0);

    private final int numberOfRooms;
    private final String name;
    private final double ratePerDay;

    RoomType(int numberOfRooms, String name, double ratePerDay) {
        this.numberOfRooms = numberOfRooms;
        this.name = name;
        this.ratePerDay = ratePerDay;
    }

    @Override
    public String toString() {
        return "NAME: " + this.name
            + "RATE: " + this.ratePerDay;
    }

    public int getNumberOfRooms() {
        return numberOfRooms;
    }

    public String getName() {
        return name;
    }

    public double getRatePerDay() {
        return ratePerDay;
    }
}
