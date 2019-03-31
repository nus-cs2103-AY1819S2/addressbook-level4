package seedu.address.testutil;

import seedu.address.model.PinBook;
import seedu.address.model.person.Person;

/**
 * A utility class to help with building Pinbook objects.
 * Example usage: <br>
 *     {@code PinBook ab = new PinBookBuilder().withPerson("Irwin", "Jimmy").build();}
 */
public class PinBookBuilder {

    private PinBook pinBook;

    public PinBookBuilder() {
        pinBook = new PinBook();
    }

    public PinBookBuilder(PinBook pinBook) {
        this.pinBook = pinBook;
    }

    /**
     * Adds a new {@code Person} to the {@code PinBook} that we are building.
     */
    public PinBookBuilder withPerson(Person person) {
        pinBook.addPerson(person);
        return this;
    }

    public PinBook build() {
        return pinBook;
    }
}
