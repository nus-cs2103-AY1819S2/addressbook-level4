package seedu.address.model.medicine;

import static seedu.address.commons.util.AppUtil.checkArgument;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.function.Predicate;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import seedu.address.model.tag.Tag;

/**
 * Represents a Medicine in the inventory.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Medicine implements Comparable<Medicine> {
    // Limits
    public static final int MAX_SIZE_TAG = 10;
    public static final int MAX_SIZE_BATCH = 100000;
    public static final String MESSAGE_CONSTRAINTS_TAGS = "Too many tags. Max number of tags: " + MAX_SIZE_TAG;
    public static final String MESSAGE_CONSTRAINTS_BATCHES = "Too many batches. Max number of batches: "
            + MAX_SIZE_BATCH;

    // Identity fields
    private final Name name;
    private final Company company;

    // Data fields
    private final Quantity totalQuantity;
    private final Expiry nextExpiry;
    private final Set<Tag> tags = new HashSet<>();
    private final Map<BatchNumber, Batch> batches = new HashMap<>();

    /**
     * Every field must be present and not null.
     */
    public Medicine(Name name, Company company, Quantity quantity, Expiry expiry, Set<Tag> tags,
            Map<BatchNumber, Batch> batches) {
        requireAllNonNull(name, company, quantity, expiry, tags, batches);
        checkArgument(isWithinLimits(tags, MAX_SIZE_TAG), MESSAGE_CONSTRAINTS_TAGS);
        checkArgument(isWithinLimits(batches.keySet(), MAX_SIZE_BATCH), MESSAGE_CONSTRAINTS_BATCHES);
        this.name = name;
        this.company = company;
        this.totalQuantity = quantity;
        this.nextExpiry = expiry;
        this.tags.addAll(tags);
        this.batches.putAll(batches);
    }

    private boolean isWithinLimits(Set<?> set, int limit) {
        return set.size() <= limit;
    }

    public Name getName() {
        return name;
    }

    public Company getCompany() {
        return company;
    }

    public Quantity getTotalQuantity() {
        return totalQuantity;
    }

    public Expiry getNextExpiry() {
        return nextExpiry;
    }

    /**
     * Returns an immutable tag set, which throws {@code UnsupportedOperationException}
     * if modification is attempted.
     */
    public Set<Tag> getTags() {
        return Collections.unmodifiableSet(tags);
    }

    /**
     * Returns an immutable batch map, which throws {@code UnsupportedOperationException}
     * if modification is attempted.
     */
    public Map<BatchNumber, Batch> getBatches() {
        return Collections.unmodifiableMap(batches);
    }

    public FilteredList<Batch> getFilteredBatch(Predicate<Batch> predicate) {
        ObservableList<Batch> batches = FXCollections.observableArrayList(getBatches().values());
        return batches.filtered(predicate);
    }

    @Override
    public int compareTo(Medicine other) {
        if (getName().compareTo(other.getName()) == 0) {
            return getCompany().compareTo(other.getCompany());
        }
        return getName().compareTo(other.getName());
    }

    /**
     * Returns true if both medicines of the same name were purchased from the same company.
     * This defines a weaker notion of equality between two medicines.
     */
    public boolean isSameMedicine(Medicine otherMedicine) {
        if (otherMedicine == this) {
            return true;
        }

        return otherMedicine != null
                && otherMedicine.getName().equals(getName())
                && (otherMedicine.getCompany().equals(getCompany()));
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
                && otherMedicine.getCompany().equals(getCompany())
                && otherMedicine.getTotalQuantity().equals(getTotalQuantity())
                && otherMedicine.getNextExpiry().equals(getNextExpiry())
                && otherMedicine.getTags().equals(getTags())
                && otherMedicine.getBatches().equals(getBatches());
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(name, company, totalQuantity, nextExpiry, tags, batches);
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append(getName())
                .append(" Company: ")
                .append(getCompany())
                .append(" Total quantity: ")
                .append(getTotalQuantity())
                .append(" Next expiry: ")
                .append(getNextExpiry())
                .append(" Tags: ");
        getTags().forEach(builder::append);
        builder.append(" Batches: ").append(getBatches().size());

        return builder.toString();
    }
}
