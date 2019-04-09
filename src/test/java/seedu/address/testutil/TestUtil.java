package seedu.address.testutil;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import seedu.address.commons.core.index.Index;
import seedu.address.model.CardsView;
import seedu.address.model.DecksView;
import seedu.address.model.deck.Card;
import seedu.address.model.deck.Deck;

/**
 * A utility class for test cases.
 */
public class TestUtil {

    /**
     * Folder used for temp files created during testing. Ignored by Git.
     */
    private static final Path SANDBOX_FOLDER = Paths.get("src", "test", "data", "sandbox");

    /**
     * Appends {@code fileName} to the sandbox folder path and returns the resulting path.
     * Creates the sandbox folder if it doesn't exist.
     */
    public static Path getFilePathInSandboxFolder(String fileName) {
        try {
            Files.createDirectories(SANDBOX_FOLDER);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return SANDBOX_FOLDER.resolve(fileName);
    }

    /**
     * Returns the middle index of the card in the {@code cardsView}'s card list.
     */
    public static Index getMidIndex(CardsView cardsView) {
        return Index.fromOneBased(cardsView.getFilteredList().size() / 2);
    }

    /**
     * Returns the last index of the card in the {@code cardsView}'s card list.
     */
    public static Index getLastIndex(CardsView cardsView) {
        return Index.fromOneBased(cardsView.getFilteredList().size());
    }

    /**
     * Returns the card in the {@code cardsView}'s card list at {@code index}.
     */
    public static Card getCard(CardsView cardsView, Index index) {
        return cardsView.getFilteredList().get(index.getZeroBased());
    }

    /**
     * Returns the deck in the {@code deckView}'s deck list at {@code index}.
     */
    public static Deck getDeck(DecksView decksView, Index index) {
        return decksView.getFilteredList().get(index.getZeroBased());
    }

    /**
     * Returns the last index of the deck in the {@code model}'s deck list.
     */
    public static Index getLastIndexDeck(DecksView decksView) {
        return Index.fromOneBased(decksView.getFilteredList().size());
    }

    /**
     * Returns the middle index of the deck in the {@code model}'s deck list.
     */
    public static Index getMidIndexDeck(DecksView decksView) {
        return Index.fromOneBased(decksView.getFilteredList().size() / 2);
    }
}


