package seedu.address.model.prescription;

/**
 * Represents description of a prescription in the address book.
 */
public class Description {

    public static final String MESSAGE_CONSTRAINTS =
            "Description should be short";

    private String description;

    public Description(String description) {

        this.description = description;
    }

    @Override
    public String toString() {

        return this.description;
    }

    public String getDescription() {

        return description;
    }

    @Override
    public boolean equals(Object other) {
        return other instanceof Description
                && this.description.equals(((Description) other).description);
    }

    @Override
    public int hashCode() {

        return description.hashCode();
    }
}
