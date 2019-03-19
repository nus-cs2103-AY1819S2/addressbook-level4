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

    private final Address address;
    private final Price sellingPrice;
    private final Price rentalPrice;
    private final Set<Tag> tags = new HashSet<>();

    public Property(String propertyType, Address address, Price price, Set<Tag> tags) {
        requireAllNonNull(address);
        this.address = address;
        if (propertyType.equals("selling")) {
            this.sellingPrice = price;
            rentalPrice = new Price("0");
        } else {
            this.rentalPrice = price;
            sellingPrice = new Price("0");
        }
        this.tags.addAll(tags);
    }

    public Address getAddress() {
        return address;
    }

    public Price getSellingPrice() {
        return sellingPrice;
    }

    public Price getRentalPrice() {
        return rentalPrice;
    }

    /**
     * Returns an immutable tag set, which throws {@code UnsupportedOperationException}
     * if modification is attempted.
     */
    public Set<Tag> getTags() {
        return Collections.unmodifiableSet(tags);
    }
}
