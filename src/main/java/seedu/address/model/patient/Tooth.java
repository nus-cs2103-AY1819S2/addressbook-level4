package seedu.address.model.patient;

/**
 * Represents a tooth of a Person.
 */
class Tooth {
    private boolean isPresent = true;
    private boolean isOnStatus = false;
    private Status status;

    Tooth() { }

    Tooth(boolean isPresent) {
        this.isPresent = isPresent;
    }

    Tooth(Status status) {
        this.isOnStatus = true;
        this.status = status;
    }

    Tooth(boolean isPresent, boolean hasStatus) {
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
