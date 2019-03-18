package seedu.address.model;

import static java.util.Objects.requireNonNull;

import java.util.List;

import javafx.beans.InvalidationListener;
import javafx.collections.ObservableList;
import seedu.address.commons.util.InvalidationListenerManager;
import seedu.address.model.deck.Card;
import seedu.address.model.deck.Deck;
import seedu.address.model.deck.UniqueCardList;
import seedu.address.model.deck.UniqueDeckList;
import seedu.address.model.deck.exceptions.DuplicateDeckException;

/**
 * Wraps all data at the TopDeck level
 * Duplicates are not allowed (by .isSamePerson comparison)
 */
public class TopDeck implements ReadOnlyTopDeck {
    private final UniqueDeckList decks;
    private final InvalidationListenerManager invalidationListenerManager = new InvalidationListenerManager();

    /*
     * The 'unusual' code block below is an non-static initialization block, sometimes used to avoid duplication
     * between constructors. See https://docs.oracle.com/javase/tutorial/java/javaOO/initial.html
     *
     * Note that non-static init blocks are not recommended to use. There are other ways to avoid duplication
     *   among constructors.
     */
    {
        decks = new UniqueDeckList();
    }

    public TopDeck() {}

    /**
     * Creates an AddressBook using the Persons in the {@code toBeCopied}
     */
    public TopDeck(ReadOnlyTopDeck toBeCopied) {
        this();
        resetData(toBeCopied);
    }

    //// list overwrite operations

    /**
     * Replaces the contents of the deck list with {@code decks}.
     * {@code decks} must not contain duplicate cards.
     */
    public void setDecks(List<Deck> decks) {
        this.decks.setDecks(decks);
        indicateModified();
    }

    /**
     * Resets the existing data of this {@code TopDeck} with {@code newData}.
     */
    public void resetData(ReadOnlyTopDeck newData) {
        requireNonNull(newData);

        setDecks(newData.getDeckList());
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
     * Notifies listeners that the address book has been modified.
     */
    protected void indicateModified() {
        invalidationListenerManager.callListeners(this);
    }

    //// deck operations

    /**
     * Adds a deck to the TopDeck.
     * The deck must not already exist in the TopDeck.
     */
    public void addDeck(Deck deck) throws DuplicateDeckException {
        decks.add(deck);
        indicateModified();
    }

    /**
     * Returns true if a deck with the same identity as {@code deck} exists in Anakin.
     */
    public boolean hasDeck(Deck deck) {
        requireNonNull(deck);
        return decks.contains(deck);
    }


    //// util methods

    @Override
    public String toString() {
        return decks.asUnmodifiableObservableList().size() + " decks";
        // TODO: refine later
    }

    @Override
    public ObservableList<Deck> getDeckList() {
        return decks.asUnmodifiableObservableList();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof TopDeck // instanceof handles nulls
                && decks.equals(((TopDeck) other).decks));
    }

    @Override
    public int hashCode() {
        return decks.hashCode();
    }
}
