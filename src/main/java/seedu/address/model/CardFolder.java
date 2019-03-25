package seedu.address.model;

import static java.util.Objects.requireNonNull;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import javafx.beans.InvalidationListener;
import javafx.collections.ObservableList;
import seedu.address.commons.util.InvalidationListenerManager;
import seedu.address.model.card.Card;
import seedu.address.model.card.UniqueCardList;

/**
 * Wraps all data at the address-book level
 * Duplicates are not allowed (by .isSameCard comparison)
 */
public class CardFolder implements ReadOnlyCardFolder {

    private final UniqueCardList cards;
    private String folderName;
    public List<Double> folderScores;
    private final InvalidationListenerManager invalidationListenerManager = new InvalidationListenerManager();

    /*
     * The 'unusual' code block below is an non-static initialization block, sometimes used to avoid duplication
     * between constructors. See https://docs.oracle.com/javase/tutorial/java/javaOO/initial.html
     *
     * Note that non-static init blocks are not recommended to use. There are other ways to avoid duplication
     *   among constructors.
     */
    {
        cards = new UniqueCardList();
    }

    public CardFolder(String folderName) {
        setFolderName(folderName);
        folderScores = new ArrayList<>();
    }

    /**
     * Creates an CardFolder using the Cards in the {@code toBeCopied}
     */
    public CardFolder(ReadOnlyCardFolder toBeCopied) {
        resetData(toBeCopied);
    }

    //// list overwrite operations

    /**
     * Replaces the contents of the card list with {@code cards}.
     * {@code cards} must not contain duplicate cards.
     */
    public void setCards(List<Card> cards) {
        this.cards.setCards(cards);
        indicateModified();
    }

    /**
     * Resets the existing data of this {@code CardFolder} with {@code newData}.
     */
    public void resetData(ReadOnlyCardFolder newData) {
        requireNonNull(newData);

        setCards(newData.getCardList());
        setFolderName(newData.getFolderName());
        setFolderScores(newData.getFolderScores());
    }

    //// card-level operations

    /**
     * Returns true if a card with the same identity as {@code card} exists in the card folder.
     */
    public boolean hasCard(Card card) {
        requireNonNull(card);
        return cards.contains(card);
    }

    /**
     * Adds a card to the card folder.
     * The card must not already exist in the card folder.
     */
    public void addCard(Card p) {
        cards.add(p);
        indicateModified();
    }

    /**
     * Replaces the given card {@code target} in the list with {@code editedCard}.
     * {@code target} must exist in the card folder.
     * The card identity of {@code editedCard} must not be the same as another existing card in the card folder.
     */
    public void setCard(Card target, Card editedCard) {
        requireNonNull(editedCard);

        cards.setCard(target, editedCard);
        indicateModified();
    }

    /**
     * Removes {@code key} from this {@code CardFolder}.
     * {@code key} must exist in the card folder.
     */
    public void removeCard(Card key) {
        cards.remove(key);
        indicateModified();
    }

    public void addFolderScore(Double folderScore) {
        while (folderScores.size() >= 3) {
            folderScores.remove(0);
        }
        folderScores.add(folderScore);
    }

    @Override
    public void addListener(InvalidationListener listener) {
        invalidationListenerManager.addListener(listener);
    }

    @Override
    public void removeListener(InvalidationListener listener) {
        invalidationListenerManager.removeListener(listener);
    }

    /**
     * Notifies listeners that the card folder has been modified.
     */
    protected void indicateModified() {
        invalidationListenerManager.callListeners(this);
    }

    //// util methods

    @Override
    public String toString() {
        return getFolderName();
        // TODO: refine later
    }

    @Override
    public List<Double> getFolderScores() {
        return folderScores;
    }

    /**
     * Sets the folderScores of {@code CardFolder } and overwrites the previous scores.
     * @param folderScores
     */
    public void setFolderScores(List<Double> folderScores) {
        this.folderScores = folderScores;
        indicateModified();
    }

    @Override
    public ObservableList<Card> getCardList() {
        return cards.asUnmodifiableObservableList();
    }

    public void sortCards(Comparator<Card> comparator) {
        cards.sortCards(comparator);
    }

    /**
     * Sets the name of the {@code CardFolder} and overwrites the previous name.
     */
    public void setFolderName(String newFolderName) {
        this.folderName = newFolderName;
        indicateModified();
    }

    @Override
    public String getFolderName() {
        return folderName;
    }

    /**
     * Equivalence of {@code CardFolder} is determined by folderName and cards.
     */
    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof CardFolder // instanceof handles nulls
                && folderName.equals(((CardFolder) other).folderName)
                && cards.equals(((CardFolder) other).cards));
    }

    @Override
    public int hashCode() {
        return folderName.hashCode();
    }
}
