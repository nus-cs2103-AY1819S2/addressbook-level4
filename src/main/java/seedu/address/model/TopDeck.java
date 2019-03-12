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
    private final UniqueCardList cards;
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
        cards = new UniqueCardList();
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
     * Replaces the contents of the card list with {@code cards}.
     * {@code cards} must not contain duplicate cards.
     */
    public void setCards(List<Card> cards) {
        this.cards.setCards(cards);
        indicateModified();
    }

    /**
     * Resets the existing data of this {@code TopDeck} with {@code newData}.
     */
    public void resetData(ReadOnlyTopDeck newData) {
        requireNonNull(newData);

        setCards(newData.getCardList());
    }

    //// card-level operations

    /**
     * Returns true if another card with the same question as {@code card} exists in the deck.
     */
    public boolean hasCard(Card card) {
        requireNonNull(card);
        return cards.contains(card);
    }

    /**
     * Adds a card to the deck.
     * The card must not already exist in the deck.
     */
    public void addCard(Card card) {
        cards.add(card);
        indicateModified();
    }

    /**
     * Replaces the given card {@code target} in the list with {@code editedCard}.
     * {@code target} must exist in the deck.
     * The card identity of {@code editedCard} must not be the same as another existing card in the deck.
     */
    public void setCard(Card target, Card editedCard) {
        requireNonNull(editedCard);

        cards.setCard(target, editedCard);
        indicateModified();
    }

    /**
     * Removes {@code key} from this {@code TopDeck}.
     * {@code key} must exist in the deck.
     */
    public void removeCard(Card key) {
        cards.remove(key);
        indicateModified();
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
    }

    //// util methods

    @Override
    public String toString() {
        return cards.asUnmodifiableObservableList().size() + " persons";
        // TODO: refine later
    }

    @Override
    public ObservableList<Deck> getDeckList() {
        return decks.asUnmodifiableObservableList();
    }

    @Override
    public ObservableList<Card> getCardList() {
        return cards.asUnmodifiableObservableList();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof TopDeck // instanceof handles nulls
                && cards.equals(((TopDeck) other).cards));
    }

    @Override
    public int hashCode() {
        return cards.hashCode();
    }
}
