package seedu.address.model.person;

/**
 * Represents a tooth of a Person.
 */
public class Tooth {
    private boolean isPresent;
    private boolean hasStatus;

    public Tooth() {
        this.hasStatus = false;
    }

    public Tooth(boolean hasStatus) {
        this.isPresent = true;
        this.hasStatus = hasStatus;
    }

    public Tooth(boolean isPresent, boolean hasStatus) {
        this.isPresent = isPresent;
        this.hasStatus = hasStatus;
    }

    public boolean isPresent() {
        return isPresent;
    }

    public boolean hasStatus() {
        return hasStatus;
    }
}
