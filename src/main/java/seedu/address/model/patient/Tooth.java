package seedu.address.model.patient;

/**
 * Represents a tooth of a Person.
 */
public class Tooth {
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

    /**
     * Checks if the status is valid.
     * @param status the test to check.
     * @return true if valid, false otherwise.
     */
    public static boolean isValidStatus(int status) {
        return status == 0 || status == 1 || status == 2;
    }

    /**
     * Sets the status of the tooth based on integers.
     * 0 - Present, healthy.
     * 1 - Problematic.
     * 2 - Absent.
     * @param status the integer representing different statuses.
     */
    public void setTo(int status) {
        switch (status) {
        case 0:
            setPresent();
            break;
        case 1:
            setStatus();
            break;
        case 2:
            setAbsent();
            break;
        default:
        }
    }

    public Status getStatus() {
        return status;
    }

}
