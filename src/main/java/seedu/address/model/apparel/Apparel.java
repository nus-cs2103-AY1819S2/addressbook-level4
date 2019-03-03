package seedu.address.model.apparel;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Objects;

/**
 * Represents a Person in the address book.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Apparel {

    // Identity fields
    private final Name name;
    private final Color color;
    private final Type type;

    // Status fields
    private boolean available;
    private int usageCount;

    /**
     * Every field must be present and not null.
     */
    public Apparel(Name name, Color color, Type type) {
        requireAllNonNull(name, color, type);
        this.name = name;
        this.color = color;
        this.type = type;
        this.available = true;
        this.usageCount = 0;
    }

    public Name getName() {
        return name;
    }

    public Color getColor() {
        return color;
    }

    public Type getType() {
        return type;
    }

    public boolean isAvailable() {
        return available;
    }

    public int getUsageCount() {
        return usageCount;
    }

    /**
     * Returns true if both apparels of the same name have at least one other identity field that is the same.
     * This defines a weaker notion of equality between two apparels.
     */
    public boolean isSameApparel(Apparel otherApparel) {
        if (otherApparel == this) {
            return true;
        }

        return otherApparel != null
                && otherApparel.getName().equals(getName())
                && (otherApparel.getColor().equals(getColor()) || otherApparel.getType().equals(getType()));
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

        if (!(other instanceof Apparel)) {
            return false;
        }

        Apparel otherApparel = (Apparel) other;
        return otherApparel.getName().equals(getName())
                && otherApparel.getColor().equals(getColor())
                && otherApparel.getType().equals(getType());
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(name, color, type);
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append(getName())
                .append(" Color: ")
                .append(getColor())
                .append(" Type: ")
                .append(getType())
                .append(" Available: ")
                .append(isAvailable())
                .append(" Usage-count: ")
                .append(getUsageCount());

        return builder.toString();
    }

}
