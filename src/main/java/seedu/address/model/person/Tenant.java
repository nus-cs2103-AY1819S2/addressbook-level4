package seedu.address.model.person;

/**
 * Represents a Tenant in the address book.
 */
public class Tenant extends Person {

    //private final Remark remark;
    ///private final Property property;

    public Tenant(Name name, Phone phone, Email email) {
        super(name, phone, email);
    }
}
