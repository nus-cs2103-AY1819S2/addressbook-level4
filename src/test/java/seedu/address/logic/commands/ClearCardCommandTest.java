package seedu.address.logic.commands;

import static seedu.address.logic.commands.CommandTestUtil.VALID_DECK_A;
import static seedu.address.logic.commands.CommandTestUtil.assertUpdateCommandSuccess;
import static seedu.address.testutil.TypicalCards.getTypicalDeck;
import static seedu.address.testutil.TypicalCards.getTypicalTopDeck;

import org.junit.Test;

import seedu.address.logic.CardsView;
import seedu.address.logic.CommandHistory;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.deck.Deck;

public class ClearCardCommandTest {
    private CommandHistory commandHistory = new CommandHistory();

    @Test
    public void execute_emptyDeck_success() {
        Model model = new ModelManager();
        model.addDeck(VALID_DECK_A);
        model.changeDeck(VALID_DECK_A);
        Model expectedModel = new ModelManager();
        expectedModel.addDeck(VALID_DECK_A);
        expectedModel.changeDeck(VALID_DECK_A);
        expectedModel.commitTopDeck();

        assertUpdateCommandSuccess(new ClearCardCommand((CardsView) model.getViewState()), model,
            commandHistory, ClearCardCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void execute_nonEmptyDeck_success() {
        Model model = new ModelManager(getTypicalTopDeck(), new UserPrefs());
        model.changeDeck(getTypicalDeck());
        Model expectedModel = new ModelManager(getTypicalTopDeck(), new UserPrefs());
        expectedModel.setDeck(getTypicalDeck(), new Deck(getTypicalDeck().getName()));
        expectedModel.commitTopDeck();

        assertUpdateCommandSuccess(new ClearCardCommand((CardsView) model.getViewState()), model, commandHistory,
            ClearCardCommand.MESSAGE_SUCCESS, expectedModel);
    }

}
