package seedu.address.logic;

import java.util.Collections;
import java.util.Iterator;
import java.util.Objects;

import javafx.collections.ObservableList;
import seedu.address.model.deck.Card;
import seedu.address.model.deck.Deck;

/**
 * Shuffles the deck for study session.
 */
public class DeckShuffler {

    private Deck deck;
    private Iterator<Card> it;
    private ObservableList<Card> cards;
    private int itCounter;

    public DeckShuffler(Deck deck) {
        this.deck = deck;
        Deck shuffledDeck = new Deck(deck);
        cards = shuffledDeck.getCards().internalList;
        shuffleCards();
        itCounter = 0;
    }

    /**
     * Gets the activeDeck in deckShuffler
     */
    public Deck getDeck() {
        return deck;
    }

    /**
     * Gets the iterator in deckShuffler
     */
    public Iterator<Card> getIt() {
        return it;
    }

    /**
     * Reshuffles cards once again when end of deck is reached.
     */
    private void shuffleCards() {
        Collections.shuffle(cards);
        it = cards.iterator();
    }

    /**
     * Returns a card from the shuffled deck.
     */
    public Card generateCard() {
        itCounter++;
        if (it.hasNext()) {
            return it.next();
        } else {
            shuffleCards();
            return it.next();
        }
    }

    @Override
    public boolean equals(Object obj) {
        // short circuit if same object
        if (obj == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(obj instanceof DeckShuffler)) {
            return false;
        }
        // state check
        DeckShuffler other = (DeckShuffler) obj;
        return Objects.equals(deck, other.deck)
                && itCounter == other.itCounter;
    }

    @Override
    public int hashCode() {
        return Objects.hash(deck, itCounter);
    }
}
