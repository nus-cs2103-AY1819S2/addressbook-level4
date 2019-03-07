package seedu.address.model.patient;

/**
 * Represents a tooth of a Person.
 */
class Tooth {
    private boolean isPresent;
    private boolean onStatus;
    private Status status = new Status();

    Tooth(boolean isPresent) {
        this.isPresent = isPresent;
        this.onStatus = false;
    }

    public void setPresent(boolean present) {
        isPresent = present;
    }

    public boolean isPresent() {
        return isPresent;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public Status getStatus() {
        return status;
    }

    public boolean isOnStatus() {
        return onStatus;
    }

}
