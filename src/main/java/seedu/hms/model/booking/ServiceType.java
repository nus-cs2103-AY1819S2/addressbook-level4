package seedu.hms.model.booking;

import seedu.hms.model.util.TimeRange;

/**
 * Contains the details of every service in the hotel.
 */
public enum ServiceType {

    GYM(20, new TimeRange(8, 22), "GYM", 5.0),
    POOL(50, new TimeRange(8, 20), "SWIMMING POOL", 2.5),
    SPA(40, new TimeRange(10, 20), "SPA", 7.0),
    GAMES(100, new TimeRange(10, 18), "GAMES ROOM", 6.0);

    private final int capacity;
    private final TimeRange timing;
    private final String name;
    private final double ratePerHour;

    ServiceType(int capacity, TimeRange timing, String name, double ratePerHour) {
        this.capacity = capacity;
        this.timing = timing;
        this.name = name;
        this.ratePerHour = ratePerHour;
    }

    @Override
    public String toString() {
        return "NAME: " + this.name
            + "TIMING: " + this.timing
            + "RATE: " + this.ratePerHour;
    }

    public int getCapacity() {
        return capacity;
    }

    public TimeRange getTiming() {
        return timing;
    }

    public String getName() {
        return name;
    }

    public double getRatePerHour() {
        return ratePerHour;
    }

    /**
     * Tests whether a {@code serviceTypeString} exist.
     * This test is case Insensitive.
     */
    public static boolean typeExist(String serviceTypeString) {
        for (ServiceType serviceType : values()) {
            if (serviceType.name.equalsIgnoreCase(serviceTypeString)) {
                return true;
            }
        }
        return false;
    }
}
