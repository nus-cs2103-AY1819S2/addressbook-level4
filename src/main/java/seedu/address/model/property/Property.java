package seedu.address.model.property;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import seedu.address.model.person.Address;
import seedu.address.model.tag.Tag;

/**
 * Represents a Property in the address book.
 */
public class Property {

    private final PropertyType propertyType;
    private final Address address;
    private final Price price;
    private final Set<Tag> tags = new HashSet<>();

    public Property(PropertyType propertyType, Address address, Price price, Set<Tag> tags) {
        requireAllNonNull(propertyType, address, price);
        this.propertyType = propertyType;
        this.address = address;
        this.price = price;
        this.tags.addAll(tags);
    }

    public Address getAddress() {
        return address;
    }

    public Price getPrice() {
        return price;
    }

    public PropertyType getPropertyType() {
        return propertyType;
    }

    /**
     * Returns an immutable tag set, which throws {@code UnsupportedOperationException}
     * if modification is attempted.
     */
    public Set<Tag> getTags() {
        return Collections.unmodifiableSet(tags);
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append("Property Type: ")
                .append(getPropertyType())
                .append(" Address: ")
                .append(getAddress())
                .append(" Price: ")
                .append(getPrice())
                .append(" Tags: ");
        getTags().forEach(builder::append);
        return builder.toString();
    }

    /**
     * Returns true if both properties have the same data.
     * This defines a stronger notion of equality between two properties.
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof Property)) {
            return false;
        }

        Property otherProperty = (Property) other;
        return otherProperty.getPropertyType().equals(getPropertyType())
                && otherProperty.getAddress().equals(getAddress())
                && otherProperty.getPrice().equals(getPrice());
    }
}
