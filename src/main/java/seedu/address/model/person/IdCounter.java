package seedu.address.model.person;

import static java.util.Objects.requireNonNull;

/**
 * Represents the id counter in the address book.
 */
public class IdCounter {

    private int value;

    public IdCounter() {
        value = 0;
    }

    public IdCounter(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public void setIdCounter(IdCounter replacement) {
        requireNonNull(replacement);
        value = replacement.value;
    }
}
