package seedu.address.model.patient;

/**
 * Represents the status of a teeth.
 * Attributes of class is immutable to uphold information integrity.
 */
public class Status {
    private String description;

    Status() {
        this.description = null;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
