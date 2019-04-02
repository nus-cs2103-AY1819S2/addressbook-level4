package seedu.address.logic;

import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static seedu.address.testutil.TypicalCards.ADDITION;
import static seedu.address.testutil.TypicalCards.SUBTRACTION;
import static seedu.address.testutil.TypicalDecks.DECK_A;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.model.deck.Card;
import seedu.address.model.deck.Deck;
import seedu.address.testutil.CardBuilder;
import seedu.address.testutil.DeckBuilder;

public class DeckShufflerTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private Deck deck = new DeckBuilder(DECK_A).build();
    private DeckShuffler deckShuffler;
    private Card firstCard;
    private Card secondCard;

    @Before
    public void setUp() {
        Card addition = new CardBuilder(ADDITION).build();
        Card subtraction = new CardBuilder(SUBTRACTION).build();
        deck.addCard(addition);
        deck.addCard(subtraction);
        deckShuffler = new DeckShuffler(deck);
    }

    @Test
    public void constructor() {
        assertEquals(deck, deckShuffler.getDeck());
    }

    @Test
    public void generateNextCard() {
        firstCard = deckShuffler.generateCard();
        secondCard = deckShuffler.generateCard();
        assertNotEquals(firstCard, secondCard);
    }

    @Test
    public void generateMoreCards() {
        for (int i = 0; i < 5; i++) {
            Card card = deckShuffler.generateCard();
            assertNotNull(card);
        }
    }

}
