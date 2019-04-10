package seedu.address.logic.commands;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalCards.getTypicalDeck;
import static seedu.address.testutil.TypicalDecks.getTypicalTopDeck;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.logic.CommandHistory;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;

public class BackCommandTest {


    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private Model modelCard = new ModelManager(getTypicalTopDeck(), new UserPrefs());
    private Model modelStudy = new ModelManager(getTypicalTopDeck(), new UserPrefs());
    private Model getExpectedModelCard = new ModelManager(getTypicalTopDeck(), new UserPrefs());
    private Model getExpectedModelStudy = new ModelManager(getTypicalTopDeck(), new UserPrefs());
    private CommandHistory commandHistory = new CommandHistory();


    @Before
    public void initializeCardView() {
        modelCard.changeDeck(getTypicalDeck());
        assertTrue(modelCard.isAtCardsView());
        getExpectedModelCard.changeDeck(getTypicalDeck());
        assertTrue(getExpectedModelCard.isAtCardsView());
    }

    @Before
    public void initializeStudyView() {
        modelStudy.studyDeck(getTypicalDeck());
        assertTrue(modelStudy.isAtStudyView());
        getExpectedModelStudy.studyDeck(getTypicalDeck());
        assertTrue(getExpectedModelStudy.isAtStudyView());
    }

    @Test
    public void execute_fromStudy_success() {
        BackCommand backCommand = new BackCommand();
        String expectedMessage = String
                .format(BackCommand.MESSAGE_CLOSE_DECK_SUCCESS, getTypicalDeck().getName());
        CommandResult expectedCommandResult = new UpdatePanelCommandResult(expectedMessage);
        getExpectedModelStudy.goToDecksView();
        assertCommandSuccess(backCommand, modelStudy, commandHistory, expectedCommandResult,
                             getExpectedModelStudy);
    }

    @Test
    public void execute_fromCard_success() {
        BackCommand backCommand = new BackCommand();
        String expectedMessage = String
                .format(BackCommand.MESSAGE_CLOSE_DECK_SUCCESS, getTypicalDeck().getName());
        CommandResult expectedCommandResult = new UpdatePanelCommandResult(expectedMessage);
        getExpectedModelCard.goToDecksView();
        assertCommandSuccess(backCommand, modelCard, commandHistory, expectedCommandResult,
                             getExpectedModelCard);
    }

    @Test
    public void equals() {
        BackCommand backFirstCommand = new BackCommand();

        // same object -> returns true
        assertTrue(backFirstCommand.equals(backFirstCommand));

        // same type -> returns true
        BackCommand backSecondCommand = new BackCommand();
        assertTrue(backFirstCommand.equals(backSecondCommand));

        // different types -> returns false
        assertFalse(backFirstCommand.equals(1));

        // null -> returns false
        assertFalse(backFirstCommand.equals(null));
    }
}
