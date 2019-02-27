package seedu.address.model.person;

/**
 * Represents a tooth of a Person.
 */
public class Tooth {
    private boolean isPresent = true;
    private boolean isOnStatus = false;
    private Status status;

    public Tooth() { }

    public Tooth(Status status) {
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
