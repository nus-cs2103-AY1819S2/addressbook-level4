package seedu.address.model.patient;

import java.util.Set;

import seedu.address.model.person.Address;
import seedu.address.model.person.Email;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.model.tag.Tag;

/**
 * Represents a patient which is extend from the Person class.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Patient extends Person {
    private Teeth teeth;

    /**
     * Every field must be present and not null.
     */
    public Patient(Name name, Phone phone, Email email, Address address, Set<Tag> tags) {
        super(name, phone, email, address, tags);
    }

    public Patient(Name name, Phone phone, Email email, Address address, Set<Tag> tags,
                  Person personToCopy, int copyCount) {
        super(name, phone, email, address, tags, personToCopy, copyCount);
    }

    public Teeth getTeeth() {
        return teeth;
    }
}
