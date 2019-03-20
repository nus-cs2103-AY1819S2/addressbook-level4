package seedu.address.model.patient;

/**
 * Represents a tooth of a Person.
 */
class Tooth {
    private boolean isPresent;
    private boolean onStatus;
    private Status status = new Status();

    Tooth() {
        this.isPresent = true;
        this.onStatus = false;
    }

    public boolean isOnStatus() {
        return onStatus;
    }

    public boolean isPresent() {
        return isPresent;
    }

    public void setPresent() {
        this.isPresent = true;
        this.onStatus = false;
        this.status = null;
    }

    public void setAbsent() {
        this.isPresent = false;
        this.onStatus = false;
        this.status = null;
    }

    public void setStatus() {
        this.isPresent = true;
        this.onStatus = true;
        this.status = null;
    }

    public void setStatus(Status status) {
        this.isPresent = true;
        this.onStatus = true;
        this.status = status;
    }

    public Status getStatus() {
        return status;
    }

}
