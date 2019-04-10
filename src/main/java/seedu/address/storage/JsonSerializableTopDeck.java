package seedu.address.storage;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.ReadOnlyTopDeck;
import seedu.address.model.TopDeck;
import seedu.address.model.deck.Deck;

/**
 * An Immutable TopDeck that is serializable to JSON format.
 */
@JsonRootName(value = "topdeck")
public class JsonSerializableTopDeck {

    public static final String MESSAGE_DUPLICATE_DECK = "Decks list contains duplicate deck(s).";
    public static final String MESSAGE_DUPLICATE_CARD = "Cards list contains duplicate cards(s).";

    private final List<JsonAdaptedDeck> decks = new ArrayList<>();

    /**
     * Constructs a {@code JsonSerializableTopDeck} with the given decks.
     */
    @JsonCreator
    public JsonSerializableTopDeck(@JsonProperty("decks") List<JsonAdaptedDeck> decks) {
        this.decks.addAll(decks);
    }

    /**
     * Converts a given {@code ReadOnlyTopDeck} into this class for Jackson use.
     *
     * @param source future changes to this will not affect the created {@code JsonSerializableTopDeck}.
     */
    public JsonSerializableTopDeck(ReadOnlyTopDeck source) {
        decks.addAll(source.getDeckList().stream().map(JsonAdaptedDeck::new).collect(Collectors.toList()));
    }

    /**
     * Converts this serializable deck into the model's {@code TopDeck} object.
     *
     * @throws IllegalValueException if there were any data constraints violated.
     */
    public TopDeck toModelType() throws IllegalValueException {
        TopDeck topDeck = new TopDeck();

        for (JsonAdaptedDeck jsonAdaptedDeck : decks) {
            Deck deck = jsonAdaptedDeck.toModelType();
            if (topDeck.hasDeck(deck)) {
                throw new IllegalValueException(MESSAGE_DUPLICATE_DECK);
            }
            topDeck.addDeck(deck);
        }
        return topDeck;
    }

}
