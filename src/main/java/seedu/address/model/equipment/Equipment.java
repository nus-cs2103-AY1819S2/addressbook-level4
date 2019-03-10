package seedu.address.model.equipment;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Collections;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import seedu.address.model.tag.Tag;

/**
 * Represents a Equipment in the address book.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Equipment {

    // Identity fields
    private final Name name;
    private final Phone phone;
    private final Email email;
    private final SerialNumber serialNumber;

    // Data fields
    private final Address address;
    private final Set<Tag> tags = new HashSet<>();

    /**
     * Every field must be present and not null.
     */
    public Equipment(Name name, Phone phone, Email email, Address address, SerialNumber serialNumber, Set<Tag> tags) {
        requireAllNonNull(name, phone, email, address, tags);
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.serialNumber = serialNumber;
        this.address = address;
        this.tags.addAll(tags);
    }

    public Name getName() {
        return name;
    }

    public Phone getPhone() {
        return phone;
    }

    public Email getEmail() {
        return email;
    }

    public SerialNumber getSerialNumber() {
        return serialNumber;
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

    /**
     * Returns true if both equipments of the same name have at least one other identity field that is the same.
     * This defines a weaker notion of equality between two equipments.
     */
    public boolean isSameEquipment(Equipment otherEquipment) {
        if (otherEquipment == this) {
            return true;
        }

        return otherEquipment != null
                && otherEquipment.getName().equals(getName())
                && (otherEquipment.getPhone().equals(getPhone()) || otherEquipment.getEmail()
                    .equals(getEmail()) || otherEquipment.getSerialNumber().equals(getSerialNumber()));
    }

    /**
     * Returns true if both persons have the same identity and data fields.
     * This defines a stronger notion of equality between two persons.
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof Equipment)) {
            return false;
        }

        Equipment otherEquipment = (Equipment) other;
        return otherEquipment.getName().equals(getName())
                && otherEquipment.getPhone().equals(getPhone())
                && otherEquipment.getEmail().equals(getEmail())
                && otherEquipment.getAddress().equals(getAddress())
                && otherEquipment.getSerialNumber().equals(getSerialNumber())
                && otherEquipment.getTags().equals(getTags());
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(name, phone, email, address, serialNumber, tags);
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append(getName())
                .append(" Phone: ")
                .append(getPhone())
                .append(" Email: ")
                .append(getEmail())
                .append(" Address: ")
                .append(getAddress())
                .append(" Serial Number: ")
                .append(getSerialNumber())
                .append(" Tags: ");
        getTags().forEach(builder::append);
        return builder.toString();
    }

}
