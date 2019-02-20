package seedu.address.model.medicine;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Collections;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import seedu.address.model.tag.Tag;

/**
 * Represents a Medicine in the inventory.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Medicine {

    // Identity fields
    private final Name name;
    private final Phone phone;
    private final Email email;

    // Data fields
    private final Company company;
    private final Set<Tag> tags = new HashSet<>();

    /**
     * Every field must be present and not null.
     */
    public Medicine(Name name, Phone phone, Email email, Company company, Set<Tag> tags) {
        requireAllNonNull(name, phone, email, company, tags);
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.company = company;
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

    public Company getCompany() {
        return company;
    }

    /**
     * Returns an immutable tag set, which throws {@code UnsupportedOperationException}
     * if modification is attempted.
     */
    public Set<Tag> getTags() {
        return Collections.unmodifiableSet(tags);
    }

    /**
     * Returns true if both medicines of the same name have at least one other identity field that is the same.
     * This defines a weaker notion of equality between two medicines.
     */
    public boolean isSameMedicine(Medicine otherMedicine) {
        if (otherMedicine == this) {
            return true;
        }

        return otherMedicine != null
                && otherMedicine.getName().equals(getName())
                && (otherMedicine.getPhone().equals(getPhone()) || otherMedicine.getEmail().equals(getEmail()));
    }

    /**
     * Returns true if both medicines have the same identity and data fields.
     * This defines a stronger notion of equality between two medicines.
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof Medicine)) {
            return false;
        }

        Medicine otherMedicine = (Medicine) other;
        return otherMedicine.getName().equals(getName())
                && otherMedicine.getPhone().equals(getPhone())
                && otherMedicine.getEmail().equals(getEmail())
                && otherMedicine.getCompany().equals(getCompany())
                && otherMedicine.getTags().equals(getTags());
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(name, phone, email, company, tags);
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append(getName())
                .append(" Phone: ")
                .append(getPhone())
                .append(" Email: ")
                .append(getEmail())
                .append(" Company: ")
                .append(getCompany())
                .append(" Tags: ");
        getTags().forEach(builder::append);
        return builder.toString();
    }

}
