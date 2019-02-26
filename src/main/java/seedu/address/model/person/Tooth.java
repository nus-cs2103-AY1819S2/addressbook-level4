package seedu.address.model.person;

import seedu.address.model.person.exceptions.Status;

/**
 * Represents a tooth of a Person.
 */
public class Tooth {
    private boolean isPresent;
    private boolean isOnStatus;
    private Status status;

    public Tooth() {
        this.isOnStatus = false;
    }

    public Tooth(Status status) {
        this.isPresent = true;
        this.isOnStatus = true;
        this.status = status;
    }

    public Tooth(boolean isPresent, boolean hasStatus) {
        this.isPresent = isPresent;
        this.isOnStatus = hasStatus;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public Status getStatus() {
        return status;
    }

    public boolean isPresent() {
        return isPresent;
    }

    public boolean isStatus() {
        return isOnStatus;
    }
}
