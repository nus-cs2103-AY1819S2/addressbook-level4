package seedu.address.model;

import javafx.beans.Observable;
import javafx.collections.ObservableList;
import seedu.address.model.card.Card;

/**
 * Unmodifiable view of an folder folder
 */
public interface ReadOnlyCardFolder extends Observable {

    /**
     * Returns an unmodifiable view of the cards list.
     * This list will not contain any duplicate cards.
     */
    ObservableList<Card> getCardList();

    /**
     * Returns the name of the folder.
     */
    String getFolderName();

    /**
     * Returns true if passed in list of cards has the same cards as the folder.
     * Used to test equality between a folder's contents over time, since folder
     * equality is enforced via folder name.
     */
    boolean hasSameCards(ObservableList<Card> otherCardList);
}
