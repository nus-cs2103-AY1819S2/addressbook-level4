package seedu.address.model.project;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Contains information about a user story/project task status.
 */

public class Status {


    public static final String MESSAGE_CONSTRAINTS = "Statuses can only be set to ongoing, complete or on hold";
    public static final String STATUS_COMPLETE = "complete";
    public static final String STATUS_ONGOING = "ongoing";
    public static final String STATUS_ON_HOLD = "on hold";

    private String status;

    /**
     * Default constructor.
     */
    public Status() {
        this.status = STATUS_ONGOING;
    }

    /**
     * Constructor for Status specifying status.
     */
    public Status(String status) {
        requireNonNull(status);
        checkArgument(isValidStatus(status), MESSAGE_CONSTRAINTS);
        this.status = status.toLowerCase();
    }

    public String getStatus() {
        return this.status;
    }

    /**
     * Returns true if given string is a valid user story status
     */
    public static boolean isValidStatus(String status) {
        return status.equalsIgnoreCase(STATUS_COMPLETE)
                || status.equalsIgnoreCase(STATUS_ON_HOLD)
                || status.equalsIgnoreCase(STATUS_ONGOING);
    }

    public boolean isComplete() {
        return this.status.equals(STATUS_COMPLETE);
    }

    /**
     * Returns a clone of this Status object.
     */
    public Status clone() {
        return new Status(this.status);
    }

    @Override
    public String toString() {
        return this.status;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Status // instanceof handles nulls
                && status.equals(((Status) other).status)); // state check
    }

    @Override
    public int hashCode() {
        return status.hashCode();
    }
}

