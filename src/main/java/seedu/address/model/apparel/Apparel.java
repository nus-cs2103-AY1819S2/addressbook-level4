package seedu.address.model.apparel;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Objects;

/**
 * Represents a Apparel in the address book.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Apparel {

    // Identity fields
    private Name name;
    private Color color;
    private ClothingType clothingType;

    // Status fields
    private boolean available;
    private int usageCount;
    private int timesWorn; // before washing

    public Apparel() {};

    /**
     * Every field must be present and not null.
     */
    public Apparel(Name name, Color color, ClothingType clothingType) {
        requireAllNonNull(name, color, clothingType);
        this.name = name;
        this.color = color;
        this.clothingType = clothingType;
        this.available = true;
        this.usageCount = 0;
        this.timesWorn = 0;
    }

    public Apparel(Name name, Color color, ClothingType clothingType, boolean available) {
        requireAllNonNull(name, color, clothingType);
        this.name = name;
        this.color = color;
        this.clothingType = clothingType;
        this.available = available;
        this.usageCount = 0;
        this.timesWorn = 0;
    }

    public Apparel(Name name, Color color, ClothingType clothingType, boolean available, int usageCount) {
        requireAllNonNull(name, color, clothingType);
        this.name = name;
        this.color = color;
        this.clothingType = clothingType;
        this.available = available;
        this.usageCount = usageCount;
        this.timesWorn = 0;
    }

    public Apparel(Apparel other) {
        this.name = other.name;
        this.color = other.color;
        this.clothingType = other.clothingType;
        this.available = other.available;
        this.usageCount = other.usageCount;
        this.timesWorn = other.timesWorn;
    }

    public Name getName() {
        return name;
    }

    public Color getColor() {
        return color;
    }

    public ClothingType getClothingType() {
        return clothingType;
    }

    public String getAvailabilityStatus() {
        if (isAvailable()) {
            return "Available";
        } else {
            return "Unavailable";
        }
    }

    public String getWornStatus() {
        if (isAvailable()) {
            return "Clean";
        } else {
            return "Worn";
        }
    }

    public boolean isAvailable() {
        return available;
    }

    public int getUsageCount() {
        return usageCount;
    }

    /**
     * Increases use count, times worn count and set availability status to false/dirty
     * Returns altered (@code apparel)
     */
    public Apparel use() {
        available = false;
        usageCount++;
        timesWorn++;
        return this;
    }

    public void dirty() {
        available = false;
    }

    public Apparel setWorn() {
        available = false;
        return this;
    }

    public Apparel setWashed() {
        available = true;
        timesWorn = 0; // reset when washed
        return this;
    }

    public void wash() {
        available = true;
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
                && otherApparel.getColor().equals(getColor())
                && otherApparel.getClothingType().equals(getClothingType());
    }

    /**
     * Returns true if both apparels have the same identity and data fields.
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
                && otherApparel.getClothingType().equals(getClothingType());
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(name, color, clothingType);
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append(getName())
                .append(" Color: ")
                .append(getColor())
                .append(" Type: ")
                .append(getClothingType())
                .append(" Status: ")
                .append(getWornStatus())
                .append(" Usage-count: ")
                .append(getUsageCount());

        return builder.toString();
    }

}
