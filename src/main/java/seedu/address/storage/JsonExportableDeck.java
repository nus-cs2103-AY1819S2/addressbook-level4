package seedu.address.storage;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.deck.Card;
import seedu.address.model.deck.Deck;
import seedu.address.model.deck.Name;

/**
 * Exportable version of deck.
 */
@JsonRootName(value = "deck")
public class JsonExportableDeck {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Deck's %s field is missing!";

    @JsonProperty(required = true)
    private String name;
    @JsonProperty(required = true)
    private List<JsonAdaptedCard> cards;


    /**
     * Creates an empty JsonExportableDeck
     * This empty constructor is required for marshalling.
     */

    public JsonExportableDeck() {
        name = "Generic Deck Name";
        cards = new ArrayList<>();
    }

    /**
     * Creates an JsonExportableDeck with the specified name and Card list
     * This empty constructor is required for marshalling.
     */
    public JsonExportableDeck(String strName, List<JsonAdaptedCard> cardsList) {
        this();
        name = strName;
        cards = cardsList;
    }

    /**
     * Converts a given Deck into this class for JAXB use.
     *
     * @param source future changes to this will not affect the created JsonExportableDeck
     */
    public JsonExportableDeck(Deck source) {
        this();
        name = source.getName().fullName;
        cards = source.getCards().internalList.stream()
                .map(JsonAdaptedCard::new)
                .collect(Collectors.toList());
    }

    /**
     * Converts this JsonExportableDeck into the model's {@code Deck} object.
     *
     * @throws IllegalValueException if there were any data constraints violated or missing values.
     */

    public Deck toModelType() throws IllegalValueException {
        final List<Card> deckCards = new ArrayList<>();
        for (JsonAdaptedCard card : cards) {
            deckCards.add(card.toModelType());
        }

        if (name == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Name.class.getSimpleName()));
        }
        if (!Name.isValidName(name)) {
            throw new IllegalValueException(Name.MESSAGE_NAME);
        }
        final Name deckName = new Name(name);
        return new Deck(deckName, deckCards);
    }

    public String getName() {
        return name;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof JsonExportableDeck)) {
            return false;
        }

        JsonExportableDeck otherDeck = (JsonExportableDeck) other;
        return Objects.equals(name, otherDeck.name)
                && cards.equals(otherDeck.cards);
    }
}

