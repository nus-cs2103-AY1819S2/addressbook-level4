package seedu.address.model.person;

import java.util.Date;

/**
 * Represents the status of a teeth.
 * Attributes of class is immutable to uphold information integrity.
 */
public class Status {
    private String description;
    private Date dateCreated;

    public Status(String description) {
        this.description = description;
        this.dateCreated = new Date();
    }

    public String getDescription() {
        return description;
    }

    public Date getDateCreated() {
        return dateCreated;
    }
}
