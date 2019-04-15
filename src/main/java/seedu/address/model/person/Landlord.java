package seedu.address.model.person;

import java.util.Set;

import seedu.address.model.property.Price;
import seedu.address.model.property.Property;
import seedu.address.model.tag.Tag;

/**
 * Represents a Landlord in the address book.
 */
public class Landlord extends Person {

    public static final String CUSTOMER_TYPE_LANDLORD = "landlord";
    private final Property property;

    public Landlord(Name name, Phone phone, Email email, Remark remark, Property property) {
        super(name, phone, email, remark);
        this.property = property;
    }

    public Address getAddress() {
        return property.getAddress();
    }

    public Set<Tag> getTags() {
        return property.getTags();
    }

    public Price getRentalPrice() {
        return property.getPrice();
    }

    public Property getProperty() {
        return property;
    }

    /**
     * Returns true if both landlords have the same identity and data fields.
     * This defines a stronger notion of equality between two landlords.
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof Landlord)) {
            return false;
        }

        Landlord otherLandlord = (Landlord) other;
        return otherLandlord.getName().equals(getName())
                && otherLandlord.getPhone().equals(getPhone())
                && otherLandlord.getEmail().equals(getEmail())
                && otherLandlord.getRemark().equals(getRemark())
                && otherLandlord.getProperty().equals(getProperty());
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append(getName())
                .append(" Phone: ")
                .append(getPhone())
                .append(" Email: ")
                .append(getEmail())
                .append(" Remark: ")
                .append(getRemark())
                .append(" ")
                .append(getProperty());
        return builder.toString();
    }

}
