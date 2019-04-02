package seedu.address.model.person;

import java.util.Set;

import seedu.address.model.property.Price;
import seedu.address.model.property.Property;
import seedu.address.model.tag.Tag;

/**
 * Represents a Seller in the address book.
 */
public class Seller extends Person {

    //private final Remark remark;
    private final Property property;

    public Seller(Name name, Phone phone, Email email, Remark remark, Property property) {
        super(name, phone, email, remark);
        this.property = property;
    }

    public Address getAddress() {
        return property.getAddress();
    }

    public Set<Tag> getTags() {
        return property.getTags();
    }

    public Price getSellingPrice() {
        return property.getSellingPrice();
    }

    public Property getProperty() {
        return property;
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
                .append(" Selling Price: ")
                .append(getSellingPrice())
                .append(" Tags: ");
        getTags().forEach(builder::append);
        return builder.toString();
    }

}
