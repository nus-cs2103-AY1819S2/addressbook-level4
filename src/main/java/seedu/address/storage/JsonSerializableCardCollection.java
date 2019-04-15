package seedu.address.storage;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.CardCollection;
import seedu.address.model.ReadOnlyCardCollection;
import seedu.address.model.flashcard.Flashcard;

/**
 * An Immutable CardCollection that is serializable to JSON format.
 */
@JsonRootName(value = "cardcollection")
class JsonSerializableCardCollection {

    public static final String MESSAGE_DUPLICATE_FLASHCARD = "Flashcards list contains duplicate flashcard(s).";

    private final List<JsonAdaptedFlashcard> flashcards = new ArrayList<>();

    /**
     * Constructs a {@code JsonSerializableCardCollection} with the given flashcards.
     */
    @JsonCreator
    public JsonSerializableCardCollection(@JsonProperty("flashcards") List<JsonAdaptedFlashcard> flashcards) {
        this.flashcards.addAll(flashcards);
    }

    /**
     * Converts a given {@code ReadOnlyCardCollection} into this class for Jackson use.
     *
     * @param source future changes to this will not affect the created {@code JsonSerializableCardCollection}.
     */
    public JsonSerializableCardCollection(ReadOnlyCardCollection source) {
        flashcards.addAll(source.getFlashcardList().stream().map(JsonAdaptedFlashcard::new)
            .collect(Collectors.toList()));
    }

    /**
     * Converts this card collection into the model's {@code CardCollection} object.
     *
     * @throws IllegalValueException if there were any data constraints violated.
     */
    public CardCollection toModelType() throws IllegalValueException {
        CardCollection cardCollection = new CardCollection();
        for (JsonAdaptedFlashcard jsonAdaptedFlashcard : flashcards) {
            Flashcard flashcard = jsonAdaptedFlashcard.toModelType();
            if (cardCollection.hasFlashcard(flashcard)) {
                throw new IllegalValueException(MESSAGE_DUPLICATE_FLASHCARD);
            }
            cardCollection.addFlashcard(flashcard);
        }
        return cardCollection;
    }

}
