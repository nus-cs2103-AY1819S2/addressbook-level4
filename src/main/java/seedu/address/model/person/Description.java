package seedu.address.model.person;

import static java.util.Objects.requireNonNull;

/**
 * Represents a Expense's description in address book
 * Guarantees: immutable; is always valid
 */
public class Description {
    public final String value;

    public Description(String description) {
        requireNonNull(description);
        this.value = description;
    }

    @Override
    public boolean equals(Object other) {
        return other == this || (other instanceof Description
                && value.equals(((Description) other).value));
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

    @Override
    public String toString() {
        return value;
    }
}
