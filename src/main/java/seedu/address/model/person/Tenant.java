package seedu.address.model.person;

import seedu.address.model.property.Property;

/**
 * Represents a Tenant in the address book.
 */
public class Tenant extends Person {

    //private final Remark remark;
    private final Property property;

    public Tenant(Name name, Phone phone, Email email) {
        super(name, phone, email);
        this.property = null;
    }
}
