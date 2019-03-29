package seedu.address.model.nextofkin;

import static java.util.Objects.requireNonNull;

import java.util.Set;

import seedu.address.model.person.Address;
import seedu.address.model.person.Email;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.model.tag.Tag;

/**
 * Represents a NextOfKin which extends the Person class.
 * Guarantees: Details are present and not null, field values are validated, imutable.
 */
public class NextOfKin extends Person {
    private NextOfKinRelation kinRelation;

    public NextOfKin(Name kinName, Phone kinPhone, Email email, Address kinAddr,
                     Set<Tag> tags, NextOfKinRelation relationship) {
        super(kinName, kinPhone, email, kinAddr, tags);
        requireNonNull(relationship);
        this.kinRelation = relationship;
    }

    public NextOfKinRelation getKinRelation() {
        return kinRelation;
    }

    @Override
    public boolean equals(Object other) {
        return super.equals(other) && this.getKinRelation().equals(((NextOfKin) other).getKinRelation());
    }
}
