package seedu.hms.model.booking;

import seedu.hms.model.util.DateRange;

/**
 * Contains the details of every room in the hotel.
 */
public enum RoomType {

    GYM(20, "GYM", 5.0),
    POOL(50, "SWIMMING POOL", 2.5),
    SPA(40, "SPA", 7.0),
    GAMES(100, "GAMES ROOM", 6.0);

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
