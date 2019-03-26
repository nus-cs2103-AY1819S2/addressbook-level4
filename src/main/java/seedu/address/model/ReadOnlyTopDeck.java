package seedu.address.model;

import javafx.beans.Observable;
import javafx.collections.ObservableList;
import seedu.address.model.deck.Deck;

/**
 * Unmodifiable view of TopDeck
 */
public interface ReadOnlyTopDeck extends Observable {
    /**
     * Returns an unmodifiable view of the decks list.
     * This list will not contain any duplicate decks.
     */
    ObservableList<Deck> getDeckList();
}
