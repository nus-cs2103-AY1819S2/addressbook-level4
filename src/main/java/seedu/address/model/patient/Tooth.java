package seedu.address.model.patient;

/**
 * Represents a tooth of a Person.
 */
class Tooth {
    private boolean isPresent = true;
    private boolean isOnStatus = false;
    private Status status;

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

    public void setPresent(boolean present) {
        isPresent = present;
    }

    public void setOnStatus(boolean onStatus) {
        isOnStatus = onStatus;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public boolean isPresent() {
        return isPresent;
    }

    public boolean isOnStatus() {
        return isOnStatus;
    }

    public Status getStatus() {
        return status;
    }

}
