package seedu.finance.model.record;

import static seedu.finance.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Objects;

import seedu.finance.model.category.Category;

/**
 * Represents a Record in the finance tracker.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Record {

    // Identity fields
    private final Name name;

    // Data fields
    private final Amount amount;
    private final Date date;
    private final Description description;
    private final Category category;

    /**
     * Every field must be present and not null.
     */
    public Record(Name name, Amount amount, Date date,
                  Description description, Category category) {
        requireAllNonNull(name, amount, date, category);
        this.name = name;
        this.amount = amount;
        this.date = date;
        this.description = description;
        this.category = category;
    }

    public Name getName() {
        return name;
    }

    public Amount getAmount() {
        return amount;
    }

    public Date getDate() {
        return date;
    }

    public Description getDescription() {
        return description;
    }

    public Category getCategory() {
        return category;
    }

    /**
     * Returns true if both records of the same name have at least one other identity field that is the same.
     * This defines a weaker notion of equality between two records.
     */
    public boolean isSameRecord(Record otherRecord) {
        if (otherRecord == this) {
            return true;
        }

        return otherRecord != null
                && otherRecord.getName().equals(getName())
                && (otherRecord.getAmount().equals(getAmount()) || otherRecord.getDate().equals(getDate()));
    }

    /**
     * Returns true if both records have the same identity and data fields.
     * This defines a stronger notion of equality between two records.
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof Record)) {
            return false;
        }

        Record otherRecord = (Record) other;
        return otherRecord.getName().equals(getName())
                && otherRecord.getAmount().equals(getAmount())
                && otherRecord.getDate().equals(getDate())
                && otherRecord.getDescription().equals(getDescription())
                && otherRecord.getCategory().equals(getCategory());
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(name, amount, date, description, category);
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append(getName())
                .append(" Amount: $")
                .append(getAmount())
                .append(" Date: ")
                .append(getDate())
                .append(" Description: ")
                .append(getDescription())
                .append(" Category: ")
                .append(getCategory());
        return builder.toString();
    }

}
