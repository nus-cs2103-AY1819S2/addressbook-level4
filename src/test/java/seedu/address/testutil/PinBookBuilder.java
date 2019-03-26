package seedu.address.testutil;

import seedu.address.model.PinBook;
import seedu.address.model.person.Person;

/**
 * A utility class to help with building Archivebook objects.
 * Example usage: <br>
 *     {@code ArchiveBook ab = new ArchiveBookBuilder().withPerson("John", "Doe").build();}
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
     * Adds a new {@code Person} to the {@code ArchiveBook} that we are building.
     */
    public PinBookBuilder withPerson(Person person) {
        pinBook.addPerson(person);
        return this;
    }

    public PinBook build() {
        return pinBook;
    }
}
