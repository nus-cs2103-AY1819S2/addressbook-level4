package seedu.address.testutil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.address.model.card.Card;

/**
 * A utility class to help with building {@link Card} objects.
 */
public class CardBuilder {

    public static final List<String> DEFAULT_CORE = List.of("Belgium", "Brussels");
    public static final List<String> DEFAULT_OPTIONAL = List.of("B");
    public static final int DEFAULT_HASHCODE = 1;

    private List<String> cores;
    private List<String> optionals;

    public CardBuilder() {
        cores = new ArrayList<>();
        optionals = new ArrayList<>();
        cores.addAll(DEFAULT_CORE);
        optionals.addAll(DEFAULT_OPTIONAL);
    }

    /**
     * Initializes the CardBuilder with the data of {@code cardToCopy}.
     */
    public CardBuilder(Card cardToCopy) {
        cores = cardToCopy.getCores();
        optionals = cardToCopy.getOptionals();
    }

    /**
     * Parses the {@code cores} into a {@code List<cores>} and set it to the {@code Card} which we are building.
     */
    public CardBuilder withCores(String ... cores) {
        this.cores = Arrays.asList(cores);
        return this;
    }

    /**
     * Parses the {@code optionals} into a {@code List<optionals>} and set it to the {@code Card} which we are building.
     */
    public CardBuilder withOptionals(String ... optionals) {
        this.optionals = Arrays.asList(optionals);
        return this;
    }

    /**
     * Builds and returns a {@link Card}.
     * @return a {@link Card}.
     */
    public Card build() {
        if (optionals.isEmpty()) {
            return new Card(cores);
        } else {
            return new Card(cores, optionals);
        }
    }
}
