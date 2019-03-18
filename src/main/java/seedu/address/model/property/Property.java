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
    private final Price salePrice;
    private final Price rentPrice;
    private final Set<Tag> tags = new HashSet<>();

    public Property(Address address, Price salePrice, Price rentPrice, Set<Tag> tags) {
        requireAllNonNull(address);
        this.address = address;
        this.salePrice = salePrice;
        this.rentPrice = rentPrice;
        this.tags.addAll(tags);
    }

    public Address getAddress() {
        return address;
    }

    public Price getSalePrice() {
        return salePrice;
    }

    public Price getRentPrice() {
        return rentPrice;
    }

    /**
     * Returns an immutable tag set, which throws {@code UnsupportedOperationException}
     * if modification is attempted.
     */
    public Set<Tag> getTags() {
        return Collections.unmodifiableSet(tags);
    }
}
