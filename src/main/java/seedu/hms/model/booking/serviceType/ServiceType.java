package seedu.hms.model.booking.serviceType;

import seedu.hms.model.util.TimeRange;

/**
 * Contains the details of every service in the hotel.
 */
public class ServiceType {

    private int capacity;
    private TimeRange timing;
    private String name;
    private double ratePerHour;

    public ServiceType(int capacity, TimeRange timing, String name, double ratePerHour) {
        this.capacity = capacity;
        this.timing = timing;
        this.name = name;
        this.ratePerHour = ratePerHour;
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

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public void setTiming(TimeRange timing) {
        this.timing = timing;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setRatePerHour(double ratePerHour) {
        this.ratePerHour = ratePerHour;
    }

    @Override
    public String toString() {
        return "NAME: " + this.name
            + " TIMING: " + this.timing
            + " RATE: " + this.ratePerHour;
    }

    @Override
    public boolean equals(Object other) {
        if (other instanceof ServiceType) {
            return this == other || this.name.equalsIgnoreCase(((ServiceType) other).getName());
        }
        return false;
    }
}
