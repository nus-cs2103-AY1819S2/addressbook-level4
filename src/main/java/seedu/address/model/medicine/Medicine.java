package seedu.address.model.medicine;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
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
public class Medicine {

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
    public Medicine(Name name, Quantity quantity, Expiry expiry, Company company, Set<Tag> tags,
            Map<BatchNumber, Batch> batches) {
        requireAllNonNull(name, quantity, expiry, company, tags);
        this.name = name;
        this.totalQuantity = quantity;
        this.nextExpiry = expiry;
        this.company = company;
        this.tags.addAll(tags);
        this.batches.putAll(batches);
    }

    public Name getName() {
        return name;
    }

    public Quantity getTotalQuantity() {
        return totalQuantity;
    }

    public Expiry getNextExpiry() {
        return nextExpiry;
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
     * Returns a String array containing the information of the various variables of the medicine.
     * This method is mainly used to support the export command to export the current list in the GUI to
     * a csv file.
     * @return A string array containing the medicine information.
     */
    public String[] toStringArray() {
        final StringBuilder builder = new StringBuilder();
        String delimiter = "|";
        String[] result;
        builder.append(getName())
                .append(delimiter)
                .append(getTotalQuantity())
                .append(delimiter)
                .append(getNextExpiry())
                .append(delimiter)
                .append(getCompany())
                .append(delimiter);
        Iterator iterator = getTags().iterator();
        while (iterator.hasNext()) {
            Tag current = (Tag) iterator.next();
            String formattedCurrentTagString = current.toStringUpperCase();
            builder.append(formattedCurrentTagString);
            builder.append(' ');
        }
        result = builder.toString().split("\\|");
        return result;
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
                && otherMedicine.getTotalQuantity().equals(getTotalQuantity())
                && otherMedicine.getNextExpiry().equals(getNextExpiry())
                && otherMedicine.getCompany().equals(getCompany())
                && otherMedicine.getTags().equals(getTags())
                && otherMedicine.getBatches().equals(getBatches());
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(name, totalQuantity, nextExpiry, company, tags, batches);
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
