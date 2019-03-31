package seedu.equipment.model.equipment;

import static java.util.Objects.requireNonNull;
import static seedu.equipment.commons.util.AppUtil.checkArgument;

/**
 * Represents a Equipment's equipment in the equipment book.
 * Guarantees: immutable; is valid as declared in {@link #isValidAddress(String)}
 */
public class Address implements Comparable<Address> {

    public static final String MESSAGE_CONSTRAINTS = "Addresses can take any values, and it should not be blank";

    /*
     * The first character of the equipment must not be a whitespace,
     * otherwise " " (a blank string) becomes a valid input.
     */
    public static final String VALIDATION_REGEX = "[^\\s].*";

    public final String value;

    private double latitude;

    private double longitude;

    private boolean hasCoordinate;

    /**
     * Constructs an {@code Address}.
     *
     * @param address A valid equipment.
     */
    public Address(String address) {
        requireNonNull(address);
        checkArgument(isValidAddress(address), MESSAGE_CONSTRAINTS);
        value = address;
        hasCoordinate = false;
    }

    /**
     * Constructs an {@code Address} with known coordinates.
     *
     * @param address A valid equipment.
     * @param latitude the latitude to be set
     * @param longitude the longitude to be set
     */
    public Address(String address, double latitude, double longitude) {
        this(address);
        hasCoordinate = true;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    /**
     * Returns true if a given string is a valid address.
     */
    public static boolean isValidAddress(String test) {
        return test.matches(VALIDATION_REGEX);
    }

    /**
     *  Check whether the address has known coordinates or not.
     * @return true if the address has known coordinates.
     */
    public boolean isHasCoordinate() {
        return hasCoordinate;
    }

    /**
     *  Set the coordinates of the address
     * @param latitude the latitude to be set
     * @param longitude the longitude to be set
     */
    public void setCoordinates(double latitude, double longitude) {
        this.latitude = latitude;
        this.longitude = longitude;
        this.hasCoordinate = true;
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Address // instanceof handles nulls
                && value.equals(((Address) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

    @Override
    public int compareTo(Address otherAddress) {
        return value.compareTo(otherAddress.value);
    }

}
