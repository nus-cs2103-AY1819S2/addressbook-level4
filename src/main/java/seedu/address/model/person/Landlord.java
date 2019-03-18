package seedu.address.model.person;

import java.util.Set;

import seedu.address.model.property.Property;
import seedu.address.model.tag.Tag;

/**
 * Represents a Landlord in the address book.
 */
public class Landlord extends Person {

    //private final Remark remark;
    private final Property property;

    public Landlord(Name name, Phone phone, Email email, Property property) {
        super(name, phone, email);
        this.property = property;
    }

    @Override
    public Address getAddress() {
        return property.getAddress();
    }

    @Override
    public Set<Tag> getTags() {
        return property.getTags();
    }

}
