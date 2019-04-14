package seedu.address.model.deck.exceptions;

import static seedu.address.testutil.TypicalDecks.DECK_A;

import org.junit.Test;

import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.testutil.DeckBuilder;


public class EmptyDeckExceptionTest {

    @Test(expected = EmptyDeckException.class)
    public void errorTest() {
        Model model = new ModelManager();
        model.studyDeck(new DeckBuilder(DECK_A).build());
    }
}
