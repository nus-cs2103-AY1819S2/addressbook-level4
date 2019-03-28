package seedu.address.model;

import java.util.List;

import javafx.beans.Observable;
import javafx.collections.ObservableList;
import seedu.address.model.card.Card;

/**
 * Unmodifiable view of an card folder
 */
public interface ReadOnlyCardFolder extends Observable {

    String MESSAGE_CONSTRAINTS = "Folder name can take any values, and should not be blank";

    /** Maximum number of scores to keep in list */
    int MAX_NUM_FOLDER_SCORES = 10;

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
     * Returns a list of folder scores
     */
    List<Double> getFolderScores();

    /**
     * Returns true if passed in list of cards has the same cards as the folder.
     * Used to test equality between a folder's contents over time, since folder
     * equality is enforced via folder name.
     */
    boolean hasSameCards(ObservableList<Card> otherCardList);
}
