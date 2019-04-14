package seedu.hms.model.reservation.roomType;


/**
 * Contains the details of every room in the hotel.
 */
public class RoomType {

    private int numberOfRooms;
    private String name;
    private double ratePerDay;

    public RoomType(int numberOfRooms, String name, double ratePerDay) {
        this.numberOfRooms = numberOfRooms;
        this.name = name;
        this.ratePerDay = ratePerDay;
    }

    @Override
    public String toString() {
        return "NAME: " + this.name
            + " RATE: " + this.ratePerDay;
    }

    @Override
    public boolean equals(Object other) {
        if (other instanceof RoomType) {
            return this == other || this.name.equalsIgnoreCase(((RoomType) other).getName());
        }
        return false;
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

    public void setNumberOfRooms(int numberOfRooms) {
        this.numberOfRooms = numberOfRooms;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setRatePerDay(double ratePerDay) {
        this.ratePerDay = ratePerDay;
    }

}
