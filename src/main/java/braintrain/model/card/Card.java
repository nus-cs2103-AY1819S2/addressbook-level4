package braintrain.model.card;

import static braintrain.commons.util.CollectionUtil.requireAllNonNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Represents a flashcard which minimally contains two core fields, Question and Answer.
 * Guarantees: Core fields are present and not null, core and optional field values are validated.
 */
public class Card {
    // Data fields
    private ArrayList<String> cores; // Core fields a Card must have such as Question and Answer.
    private ArrayList<String> optionals; // Optional fields a Card can have such as Hint.

    /**
     * Every field must be present and not null.
     */
    public Card(List<String> cores, List<String> optionals) {
        requireAllNonNull(cores, optionals);
        this.cores.addAll(cores);
        this.optionals.addAll(optionals);
    }

    public ArrayList<String> getCores() {
        return cores;
    }

    public ArrayList<String> getOptionals() {
        return optionals;
    }

    public void setCores(List<String> cores) {
        this.cores.addAll(cores);
    }

    public void setOptionals(List<String> optionals) {
        this.optionals.addAll(optionals);
    }

    /**
     * Returns true if both cards have the same data fields.
     * This defines a stronger notion of equality between two cards.
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
        return otherCard.getCores().equals(getCores())
                && otherCard.getOptionals().equals(getOptionals());
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(cores, optionals);
    }
}
