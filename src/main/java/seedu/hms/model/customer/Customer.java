package seedu.hms.model.customer;

import static seedu.hms.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Collections;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import seedu.hms.model.tag.Tag;

/**
 * Represents a Customer in the hms book.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Customer {

    // Identity fields
    private final Name name;
    private final seedu.hms.model.customer.Phone phone;
    private final Email email;
    private final seedu.hms.model.customer.IdentificationNo idnum;

    // Data fields
    private final Address address;
    private final Set<Tag> tags = new HashSet<>();

    /**
     * Every field must be present and not null.
     */
    public Customer(Name name, seedu.hms.model.customer.Phone phone, Email email, IdentificationNo idnum,
                    Address address, Set<Tag> tags) {
        requireAllNonNull(name, phone, email, idnum, address, tags);
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.idnum = idnum;
        this.address = address;
        this.tags.addAll(tags);
    }

    public Name getName() {
        return name;
    }

    public seedu.hms.model.customer.Phone getPhone() {
        return phone;
    }

    public Email getEmail() {
        return email;
    }

    public seedu.hms.model.customer.IdentificationNo getIdNum() {
        return idnum;
    }

    public Address getAddress() {
        return address;
    }

    /**
     * Returns an immutable tag set, which throws {@code UnsupportedOperationException}
     * if modification is attempted.
     */
    public Set<Tag> getTags() {
        return Collections.unmodifiableSet(tags);
    }

    public boolean isVip() {
        return this.tags.stream().anyMatch(Tag::isVipTag);
    }

    /**
     * Returns true if both customers of the same name have at least one other identity field that is the same.
     * This defines a weaker notion of equality between two customers.
     */
    public boolean isSameCustomer(Customer otherCustomer) {
        if (otherCustomer == this) {
            return true;
        }

        return otherCustomer != null
            && otherCustomer.getName().equals(getName())
            && (otherCustomer.getPhone().equals(getPhone()) || otherCustomer.getEmail().equals(getEmail())
            || otherCustomer.getIdNum().equals(getIdNum()));
    }

    /**
     * Returns true if both customers have the same identity and data fields.
     * This defines a stronger notion of equality between two customers.
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof Customer)) {
            return false;
        }

        Customer otherCustomer = (Customer) other;
        return otherCustomer.getName().equals(getName())
            && otherCustomer.getPhone().equals(getPhone())
            && otherCustomer.getEmail().equals(getEmail())
            && otherCustomer.getIdNum().equals(getIdNum())
            && otherCustomer.getAddress().equals(getAddress())
            && otherCustomer.getTags().equals(getTags());
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(name, phone, email, idnum, address, tags);
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append(getName())
            .append(" Phone: ")
            .append(getPhone())
            .append(" Email: ")
            .append(getEmail())
            .append(" Idnum: ")
            .append(getIdNum())
            .append(" Address: ")
            .append(getAddress())
            .append(" VIP Status: ")
            .append(isVip())
            .append(" Tags: ");
        getTags().forEach(builder::append);
        return builder.toString();
    }

}
