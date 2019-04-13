package seedu.address.model.battleship;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import seedu.address.commons.core.LogsCenter;

/**
 * Represents the orientation of a ship on the map.
 */
public class Orientation {
    public static final String MESSAGE_CONSTRAINTS =
            "Orientation should either be horizontal or vertical, case-insensitive";

    // The first character of the address must not be a whitespace,
    // otherwise " " (a blank string) becomes a valid input.
    private static final String VALIDATION_REGEX = "^(horizontal|vertical|h|v)$";
    private static final Pattern VALIDATION_PATTERN = Pattern.compile(
            VALIDATION_REGEX,
            Pattern.CASE_INSENSITIVE);

    public final OrientationType orientation;
    private final Logger logger = LogsCenter.getLogger(Orientation.class);

    /**
     * Constructs a {@code Name}.
     *
     * @param orientation A valid name.
     */
    public Orientation(String orientation) {
        requireNonNull(orientation);
        checkArgument(isValidOrientation(orientation), MESSAGE_CONSTRAINTS);

        if (orientation.toLowerCase().equals("h")
                || orientation.toLowerCase().equals("horizontal")) {
            this.orientation = OrientationType.HORIZONTAL;
        } else if (orientation.toLowerCase().equals("v")
                || orientation.toLowerCase().equals("vertical")) {
            this.orientation = OrientationType.VERTICAL;
        } else {
            this.orientation = OrientationType.ERROR;
        }

        logger.info("ORIENTATION INSTANCE CREATED.");
    }

    /**
     * Returns true if a given string is a valid name.
     *
     * @param test input string to be tested.
     * @return boolean of whether the given string is a valid orientation.
     */
    public static boolean isValidOrientation(String test) {
        Matcher matcher = VALIDATION_PATTERN.matcher(test);
        return matcher.find();
    }

    /**
     * Checks if orientation is left.
     *
     * @return boolean of whether the orientation is left.
     */
    public boolean isHorizontal() {
        return this.orientation == OrientationType.HORIZONTAL;
    }

    /**
     * Checks if orientation is right.
     *
     * @return boolean of whether the orientation is right.
     */
    public boolean isVertical() {
        return this.orientation == OrientationType.VERTICAL;
    }

    /**
     * Returns string of orientation.
     *
     * @return string of orientation.
     */
    @Override
    public String toString() {
        return this.orientation.toString();
    }

    /**
     * Checks if two given Orientation objects are equal by checking the enum value.
     *
     * @param other object to be compared with.
     * @return boolean of whether objects are equal.
     */
    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Orientation // instanceof handles nulls
                && orientation.equals(((Orientation) other).orientation)); // state check
    }

    /**
     * Hashes the object.
     *
     * @return {@code int} of orientation hashcode.
     */
    @Override
    public int hashCode() {
        return orientation.hashCode();
    }

    /**
     * Enumeration for internal use.
     */
    private enum OrientationType {
        HORIZONTAL("horizontal"),
        VERTICAL("vertical"),
        ERROR("error");

        private final String orientationDescription;

        /**
         * Default constructor method.
         *
         * @param value orientation string.
         */
        OrientationType(String value) {
            orientationDescription = value;
        }

        /**
         * Returns orientation as a lowercase string.
         *
         * @return orientation as a lowercase string.
         */
        @Override
        public String toString() {
            return this.orientationDescription;
        }

    };

}
