package seedu.address.model.person;

import java.util.Set;

import seedu.address.model.property.Price;
import seedu.address.model.property.Property;
import seedu.address.model.tag.Tag;

/**
 * Represents a Landlord in the address book.
 */
public class Landlord extends Person {

    //private final Remark remark;
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
        return property.getRentalPrice();
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
                && otherLandlord.getAddress().equals(getAddress())
                && otherLandlord.getRentalPrice().equals(getRentalPrice())
                && otherLandlord.getTags().equals(getTags());
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
                .append(" Address: ")
                .append(getAddress())
                .append(" Rental Price: ")
                .append(getRentalPrice())
                .append(" Tags: ");
        getTags().forEach(builder::append);
        return builder.toString();
    }

}
