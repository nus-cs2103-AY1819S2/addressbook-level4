package seedu.address.model;

import java.util.List;

import javafx.beans.Observable;
import javafx.collections.ObservableList;
import seedu.address.model.card.Card;

/**
 * Unmodifiable view of an card folder
 */
public interface ReadOnlyCardFolder extends Observable {

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
}
