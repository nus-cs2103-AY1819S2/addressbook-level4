package seedu.address.model.person;

/**
 * Represents a Buyer in the address book.
 */
public class Buyer extends Person {

    public static final String CUSTOMER_TYPE_BUYER = "buyer";

    public Buyer(Name name, Phone phone, Email email, Remark remark) {
        super(name, phone, email, remark);
    }

    /**
     * Returns true if both buyers have the same identity and data fields.
     * This defines a stronger notion of equality between two buyers.
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof Buyer)) {
            return false;
        }

        Buyer otherBuyer = (Buyer) other;
        return otherBuyer.getName().equals(getName())
                && otherBuyer.getPhone().equals(getPhone())
                && otherBuyer.getEmail().equals(getEmail())
                && otherBuyer.getRemark().equals(getRemark());
    }

}
