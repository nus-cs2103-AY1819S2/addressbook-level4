package seedu.address.testutil;

import seedu.address.model.ArchiveBook;
import seedu.address.model.person.Person;

/**
 * A utility class to help with building Archivebook objects.
 * Example usage: <br>
 *     {@code ArchiveBook ab = new ArchiveBookBuilder().withPerson("John", "Doe").build();}
 */
public class ArchiveBookBuilder {

    private ArchiveBook archiveBook;

    public ArchiveBookBuilder() {
        archiveBook = new ArchiveBook();
    }

    public ArchiveBookBuilder(ArchiveBook archiveBook) {
        this.archiveBook = archiveBook;
    }

    /**
     * Adds a new {@code Person} to the {@code ArchiveBook} that we are building.
     */
    public ArchiveBookBuilder withPerson(Person person) {
        archiveBook.addPerson(person);
        return this;
    }

    public ArchiveBook build() {
        return archiveBook;
    }
}
