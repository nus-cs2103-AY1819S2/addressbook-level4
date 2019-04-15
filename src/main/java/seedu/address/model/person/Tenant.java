package seedu.address.model.person;

/**
 * Represents a Tenant in the address book.
 */
public class Tenant extends Person {

    public static final String CUSTOMER_TYPE_TENANT = "tenant";

    public Tenant(Name name, Phone phone, Email email, Remark remark) {
        super(name, phone, email, remark);
    }

    /**
     * Returns true if both tenants have the same identity and data fields.
     * This defines a stronger notion of equality between two tenants.
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof Tenant)) {
            return false;
        }

        Tenant otherTenant = (Tenant) other;
        return otherTenant.getName().equals(getName())
                && otherTenant.getPhone().equals(getPhone())
                && otherTenant.getEmail().equals(getEmail())
                && otherTenant.getRemark().equals(getRemark());
    }
}
