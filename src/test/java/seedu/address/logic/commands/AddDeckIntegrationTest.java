package seedu.address.logic.commands;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalCards.getTypicalTopDeck;
import static seedu.address.commons.core.Messages.MESSAGE_DUPLICATE_DECK;

import org.junit.Before;
import org.junit.Test;

import seedu.address.logic.CommandHistory;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.deck.Card;
import seedu.address.model.deck.Deck;
import seedu.address.testutil.CardBuilder;
import seedu.address.testutil.DeckBuilder;

/**
 * Contains integration tests (interaction with the Model) for {@code AddCommand}.
 */
public class AddDeckIntegrationTest {

    private Model model;
    private CommandHistory commandHistory = new CommandHistory();

    @Before
    public void setUp() {
        model = new ModelManager(getTypicalTopDeck(), new UserPrefs());
    }

    @Test
    public void execute_newDeck_success() {
        Deck validDeck = new DeckBuilder().build();

        Model expectedModel = new ModelManager(model.getTopDeck(), new UserPrefs());
        expectedModel.addDeck(validDeck);
        expectedModel.commitTopDeck();

        assertCommandSuccess(new NewDeckCommand(validDeck), model, commandHistory,
                String.format(NewDeckCommand.MESSAGE_SUCCESS, validDeck), expectedModel);
    }

    @Test
    public void execute_duplicatePerson_throwsCommandException() {
        Deck deckInList = model.getTopDeck().getDeckList().get(0);
        assertCommandFailure(new NewDeckCommand(deckInList), model, commandHistory, MESSAGE_DUPLICATE_DECK);
    }

}
