package seedu.address.model.card;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Represents a flash card which minimally contains {@value #MIN_CORE_COUNT} {@link java.lang.String} objects in
 * {@link #cores} and 0 or more {@link java.lang.String} objects in {@link #optionals}.
 *
 * <br><br>A basic {@code Card} will have 2 {@link java.lang.String} objects in {@link #cores} representing
 * Question and Answer, and optionally have 1 {@link java.lang.String} object in {@link #optionals} representing
 * Hint.
 */
public class Card {
    // Class fields
    /**
     * A {@code Card} requires at least {@value #MIN_CORE_COUNT} cores to be testable (e.g. Question and Answer).
     */
    public static final int MIN_CORE_COUNT = 2;

    // Identity fields
    /**
     * Two {@code Card} objects with the same sets and order of {@link #cores} and {@link #optionals} will have
     * the same {@code hashCode}. See {@link #generateHashCode()}.
     */
    private int hashCode;

    // Data fields
    /**
     * Cores are fields a {@code Card} must have. A {@code Card} requires at least {@value #MIN_CORE_COUNT} cores
     * to be testable (e.g. Question and Answer).
     */
    private ArrayList<String> cores;
    /**
     * Optionals are fields a {@code Card} can have. A {@code Card} can have 0 or more optionals (e.g. Hint)
     */
    private ArrayList<String> optionals;

    /**
     * Creates a {@code Card} which represents a flash card.
     *
     * @param cores {@link #cores} a {@code Card} must have.
     * @param optionals {@link #optionals} a {@code Card} can have.
     */
    public Card(List<String> cores, List<String> optionals) {
        requireAllNonNull(cores, optionals);

        this.cores = new ArrayList<>();
        this.cores.addAll(cores);

        this.optionals = new ArrayList<>();
        this.optionals.addAll(optionals);

        hashCode = generateHashCode();
    }

    /**
     * Creates a {@code Card} which represents a flash card.
     *
     * @param cores {@link #cores} a {@code Card} must have.
     */
    public Card(List<String> cores) {
        requireAllNonNull(cores);

        this.cores = new ArrayList<>();
        this.cores.addAll(cores);

        this.optionals = new ArrayList<>();

        hashCode = generateHashCode();
    }

    /**
     * Returns the list of {@link #cores}.
     *
     * @return the list of {@link #cores}
     */
    public ArrayList<String> getCores() {
        return cores;
    }

    /**
     * Returns the list of {@link #optionals}.
     *
     * @return the list of {@link #optionals}
     */
    public ArrayList<String> getOptionals() {
        return optionals;
    }

    /**
     * Replaces the existing list in {@link #cores} with newCores.
     *
     * @param newCores the new list of {@link #cores}
     */
    public void setCores(List<String> newCores) {
        requireAllNonNull(newCores);
        this.cores.clear();
        this.cores.addAll(newCores);
        hashCode = generateHashCode();
    }

    /**
     * Replaces the existing list in {@link #optionals} with newOptionals.
     *
     * @param newOptionals the new list of {@link #optionals}
     */
    public void setOptionals(List<String> newOptionals) {
        requireAllNonNull(newOptionals);
        this.optionals.clear();
        this.optionals.addAll(newOptionals);
        hashCode = generateHashCode();
    }

    /**
     * Returns the core at the specified position in {@link #cores}.
     *
     * @param index index of the core to return
     * @return the core at the specified position in {@link #cores}
     */
    public String getCore(int index) {
        return cores.get(index);
    }

    /**
     * Returns the optional at the specified position in {@link #optionals}.
     *
     * @param index index of the optional to return
     * @return the optional at the specified position in {@link #optionals}
     */
    public String getOptional(int index) {
        return optionals.get(index);
    }

    /**
     * Replaces the core at the specified position in {@link #cores} with newCore.
     *
     * @param index index of the core to replace
     * @param newCore core to be stored at the specified position in {@link #cores}
     */
    public void setCore(int index, String newCore) {
        cores.set(index, newCore);
        hashCode = generateHashCode();
    }

    /**
     * Replaces the optional at the specified position in {@link #optionals} with newOptional.
     *
     * @param index index of the optional to replace
     * @param newOptional optional to be stored at the specified position in {@link #optionals}
     */
    public void setOptional(int index, String newOptional) {
        optionals.set(index, newOptional);
        hashCode = generateHashCode();
    }

    /**
     * Generates a hash code using {@link #cores} and {@link #optionals} as input.
     * Two {@code Card} objects with the <b>same set and order</b> of {@link #cores} and {@link #optionals}
     * will have the same hash code.
     *
     * @return {@link #hashCode} generated using {@link #cores} and {@link #optionals} as input
     */
    private int generateHashCode() {
        return Objects.hash(cores, optionals);
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append("Cores: ")
                .append(getCores())
                .append("\nOptionals: ")
                .append(getOptionals());
        return builder.toString();
    }

    /**
     * Returns a printable format of the Card which is displayed to the user.
     *
     * @return the printable format of the Card.
     */
    public String toPrint() {
        final StringBuilder builder = new StringBuilder();
        builder.append(getCores()).append(getOptionals());

        return builder.toString();
    }

    @Override
    public int hashCode() {
        return hashCode;
    }

    /**
     * Returns true if both are {@code Card} objects, and are the same object or have the same {@link #hashCode}.
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
}
