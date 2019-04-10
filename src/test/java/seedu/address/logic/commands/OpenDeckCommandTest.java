package seedu.address.logic.commands;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.CommandTestUtil.showDeckAtIndex;
import static seedu.address.testutil.TypicalCards.getTypicalDeck;
import static seedu.address.testutil.TypicalDecks.getTypicalTopDeck;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_DECK;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_DECK;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.DecksView;
import seedu.address.logic.StudyView;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;

public class OpenDeckCommandTest {

    private static final CommandHistory EMPTY_COMMAND_HISTORY = new CommandHistory();

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private Model modelDeck = new ModelManager(getTypicalTopDeck(), new UserPrefs());
    private Model modelStudy = new ModelManager(getTypicalTopDeck(), new UserPrefs());
    private Model expectedModelDeck = new ModelManager(getTypicalTopDeck(), new UserPrefs());
    private Model getExpectedModelStudy = new ModelManager(getTypicalTopDeck(), new UserPrefs());
    private CommandHistory commandHistory = new CommandHistory();
    private DecksView decksView;

    @Before
    public void initializeDecksView() {
        assertTrue(modelDeck.isAtDecksView());
        decksView = (DecksView) modelDeck.getViewState();
    }

    @Before
    public void initializeStudyView() {
        modelStudy.studyDeck(getTypicalDeck());
        assertTrue(modelStudy.isAtStudyView());
        getExpectedModelStudy.studyDeck(getTypicalDeck());
        assertTrue(getExpectedModelStudy.isAtStudyView());
    }

    @Test
    public void execute_validIndexUnfilteredList_success() {
        Index lastPersonIndex = Index.fromOneBased(decksView.getFilteredList().size());
        assertExecutionSuccessIndex(INDEX_FIRST_DECK);
        assertExecutionSuccessIndex(lastPersonIndex);
    }

    @Test
    public void execute_invalidIndexUnfilteredList_failure() {
        Index outOfBoundsIndex = Index.fromOneBased(decksView.getFilteredList().size() + 1);
        assertExecutionFailure(outOfBoundsIndex, Messages.MESSAGE_INVALID_DISPLAYED_INDEX);
    }

    @Test
    public void execute_validIndexFilteredList_success() {
        showDeckAtIndex(modelDeck, INDEX_FIRST_DECK);
        showDeckAtIndex(expectedModelDeck, INDEX_FIRST_DECK);
        assertExecutionSuccessIndex(INDEX_FIRST_DECK);
    }

    @Test
    public void execute_invalidIndexFilteredList_failure() {
        showDeckAtIndex(modelDeck, INDEX_FIRST_DECK);
        showDeckAtIndex(expectedModelDeck, INDEX_FIRST_DECK);

        Index outOfBoundsIndex = INDEX_SECOND_DECK;
        // ensures that outOfBoundIndex is still in bounds of TopDeck list
        assertTrue(outOfBoundsIndex.getZeroBased() < modelDeck.getTopDeck().getDeckList().get(0)
                                                              .getCards().internalList.size());

        assertExecutionFailure(outOfBoundsIndex, Messages.MESSAGE_INVALID_DISPLAYED_INDEX);
    }

    @Test
    public void execute_validDeck_success() {
        OpenDeckCommand openDeckCommand = new OpenDeckCommand(getTypicalDeck());
        String expectedMessage = String
                .format(OpenDeckCommand.MESSAGE_OPEN_DECK_SUCCESS, getTypicalDeck().getName());
        CommandResult expectedCommandResult = new UpdatePanelCommandResult(expectedMessage);
        expectedModelDeck.changeDeck(getTypicalDeck());
        assertCommandSuccess(openDeckCommand, modelDeck, commandHistory, expectedCommandResult,
                             expectedModelDeck);
    }

    @Test
    public void equals() {
        OpenDeckCommand openFirstCommand = new OpenDeckCommand(INDEX_FIRST_DECK, decksView);
        OpenDeckCommand openSecondCommand = new OpenDeckCommand(INDEX_SECOND_DECK, decksView);

        // same object -> returns true
        assertTrue(openFirstCommand.equals(openFirstCommand));

        // same values -> returns true
        OpenDeckCommand openFirstCommandCopy = new OpenDeckCommand(INDEX_FIRST_DECK, decksView);
        assertTrue(openFirstCommand.equals(openFirstCommandCopy));

        // different types -> returns false
        assertFalse(openFirstCommand.equals(1));

        // null -> returns false
        assertFalse(openFirstCommand.equals(null));

        // different card -> returns false
        assertFalse(openFirstCommand.equals(openSecondCommand));
    }

    /**
     * Executes a {@code StudyCommand} with the given {@code index},
     * and checks that the modelDeck's viewstate is changed to studyView with active deck being
     * the deck with given Index
     */
    private void assertExecutionSuccessIndex(Index index) {
        OpenDeckCommand openDeckCommand = new OpenDeckCommand(index, decksView);
        String expectedMessage = String.format(OpenDeckCommand.MESSAGE_OPEN_DECK_SUCCESS,
                                               decksView.getFilteredList().get(index.getZeroBased())
                                                        .getName());
        CommandResult expectedCommandResult = new UpdatePanelCommandResult(expectedMessage);
        expectedModelDeck.changeDeck(decksView.getFilteredList().get(index.getZeroBased()));
        assertCommandSuccess(openDeckCommand, modelDeck, commandHistory, expectedCommandResult,
                             expectedModelDeck);
    }

    /**
     * Executes a {@code StudyDeckCommand} with the given {@code index}, and checks that a {@code
     * CommandException}
     * is thrown with the {@code expectedMessage}.
     */
    private void assertExecutionFailure(Index index, String expectedMessage) {
        StudyDeckCommand studyDeckCommand = new StudyDeckCommand(decksView, index);
        assertCommandFailure(studyDeckCommand, modelDeck, commandHistory, expectedMessage);
    }
}
