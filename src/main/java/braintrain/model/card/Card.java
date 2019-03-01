package braintrain.model.card;

import static braintrain.commons.util.CollectionUtil.requireAllNonNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Represents a flash card which minimally contains two core fields, Question and Answer.
 * Guarantees: Core fields are present and not null, core and optional field values are validated.
 */
public class Card {
    // Identity fields
    private int hashCode; // Used for identification purposes.
    // Two cards with the same sets of cores and optionals will have the same hash code.

    // Data fields
    private ArrayList<String> cores; // Core fields a card must have, such as Question and Answer
    private ArrayList<String> optionals; // Optional fields a card can have, such as Hint

    /**
     * Creates a {@code Card} which represents a flash card.
     *
     * @param cores core fields a {@code Card} must have, such as Question and Answer
     * @param optionals optional fields a {@code Card} can have, such as Hint
     */
    public Card(List<String> cores, List<String> optionals) {
        requireAllNonNull(cores, optionals);
        this.cores = new ArrayList<>();
        this.optionals = new ArrayList<>();

        this.cores.addAll(cores);
        this.optionals.addAll(optionals);
        hashCode = generateHashCode();
    }

    /**
     * Returns the list of cores. Cores are fields a {@code Card} must have, such as Question and Answer.
     *
     * @return the list of cores
     */
    public ArrayList<String> getCores() {
        return cores;
    }

    /**
     * Returns the list of optionals. Optional are fields a {@code Card} can have, such as Hint.
     *
     * @return the list of optionals
     */
    public ArrayList<String> getOptionals() {
        return optionals;
    }

    /**
     * Replaces the core list with the specified list of cores.
     *
     * @param newCores the new list of cores
     */
    public void setCores(List<String> newCores) {
        this.cores.clear();
        this.cores.addAll(newCores);
        hashCode = generateHashCode();
    }

    /**
     * Replaces the optional list with the specified list of optionals.
     *
     * @param newOptionals the new list of optionals
     */
    public void setOptionals(List<String> newOptionals) {
        this.optionals.clear();
        this.optionals.addAll(newOptionals);
        hashCode = generateHashCode();
    }

    /**
     * Returns the core at the specified position in the core list.
     *
     * @param index index of the core to return
     * @return the core at the specified position in the core list
     */
    public String getCore(int index) {
        return cores.get(index);
    }

    /**
     * Returns the optional at the specified position in the optional list.
     *
     * @param index index of the optional to return
     * @return the optional at the specified position in the optional list
     */
    public String getOptional(int index) {
        return optionals.get(index);
    }

    /**
     * Replaces the core at the specified position in the core list with the specified core.
     *
     * @param index index of the core to replace
     * @param newCore core to be stored at the specified position in the core list
     */
    public void setCore(int index, String newCore) {
        cores.set(index, newCore);
        hashCode = generateHashCode();
    }

    /**
     * Replaces the optional at the specified position in the optional list with the specified optional.
     *
     * @param index index of the optional to replace
     * @param newOptional optional to be stored at the specified position in the optional list
     */
    public void setOptional(int index, String newOptional) {
        optionals.set(index, newOptional);
        hashCode = generateHashCode();
    }

    /**
     * Returns true if both {@code Card} objects have the same set and order of cores and optionals.
     * See {@link #generateHashCode()}. This defines a strong notion of equality between two {@code Card} objects.
     *
     * @param other object to be compared for equality with this {@code Card}
     * @return true if the specified object is a {@code Card} identical to this {@code Card}
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof Card)) {
            return false;
        }

        Card otherCard = (Card) other;
        return otherCard.hashCode() == this.hashCode();
    }

    /**
     * Generates a hash code using cores and optionals for equality purposes. See {@link #equals(Object)}.
     * Two {@code Card} objects with the same set and order of cores and optionals will have the same hash code.
     *
     * If two {@code Card} objects have the same set of cores and optionals, but their cores or optionals are ordered
     * differently, they will have different hash codes. For example: Given card1's cores:
     * [What is the capital of Japan?, Tokyo] and card2's cores: Tokyo, What is the capital of Japan?],
     * card1 and card2 will have different hash codes and card1.equals(card2) will return false.
     *
     * @return hash code generated from cores and optionals
     */
    private int generateHashCode() {
        return Objects.hash(cores, optionals);
    }

    @Override
    public int hashCode() {
        return hashCode;
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append("Hash Code: ")
                .append(hashCode())
                .append(", Cores: ")
                .append(getCores())
                .append(", Optionals: ")
                .append(getOptionals());
        return builder.toString();
    }
}
