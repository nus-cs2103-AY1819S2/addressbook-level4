package seedu.address.logic.commands;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.CommandTestUtil.showCardAtIndex;
import static seedu.address.testutil.TypicalCards.getTypicalCardFolders;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_CARD;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_CARD;
import static seedu.address.testutil.TypicalIndexes.INDEX_THIRD_CARD;

import org.junit.Test;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.CommandHistory;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;

/**
 * Contains integration tests (interaction with the Model) for {@code SelectCommand}.
 */
public class SelectCommandTest {
    private Model model = new ModelManager(getTypicalCardFolders(), new UserPrefs());
    private Model expectedModel = new ModelManager(getTypicalCardFolders(), new UserPrefs());
    private CommandHistory commandHistory = new CommandHistory();

    @Test
    public void execute_validIndexUnfilteredList_success() {
        Index lastCardIndex = Index.fromOneBased(model.getFilteredCards().size());

        assertExecutionSuccess(INDEX_FIRST_CARD);
        assertExecutionSuccess(INDEX_THIRD_CARD);
        assertExecutionSuccess(lastCardIndex);
    }

    @Test
    public void execute_invalidIndexUnfilteredList_failure() {
        Index outOfBoundsIndex = Index.fromOneBased(model.getFilteredCards().size() + 1);

        assertExecutionFailure(outOfBoundsIndex, Messages.MESSAGE_INVALID_CARD_DISPLAYED_INDEX);
    }

    @Test
    public void execute_validIndexFilteredList_success() {
        showCardAtIndex(model, INDEX_FIRST_CARD);
        showCardAtIndex(expectedModel, INDEX_FIRST_CARD);

        assertExecutionSuccess(INDEX_FIRST_CARD);
    }

    @Test
    public void execute_invalidIndexFilteredList_failure() {
        showCardAtIndex(model, INDEX_FIRST_CARD);
        showCardAtIndex(expectedModel, INDEX_FIRST_CARD);

        Index outOfBoundsIndex = INDEX_SECOND_CARD;
        // ensures that outOfBoundIndex is still in bounds of folder folder list
        assertTrue(outOfBoundsIndex.getZeroBased() < model.getActiveCardFolder().getCardList().size());

        assertExecutionFailure(outOfBoundsIndex, Messages.MESSAGE_INVALID_CARD_DISPLAYED_INDEX);
    }

    @Test
    public void equals() {
        SelectCommand selectFirstCommand = new SelectCommand(INDEX_FIRST_CARD);
        SelectCommand selectSecondCommand = new SelectCommand(INDEX_SECOND_CARD);

        // same object -> returns true
        assertTrue(selectFirstCommand.equals(selectFirstCommand));

        // same values -> returns true
        SelectCommand selectFirstCommandCopy = new SelectCommand(INDEX_FIRST_CARD);
        assertTrue(selectFirstCommand.equals(selectFirstCommandCopy));

        // different types -> returns false
        assertFalse(selectFirstCommand.equals(1));

        // null -> returns false
        assertFalse(selectFirstCommand.equals(null));

        // different folder -> returns false
        assertFalse(selectFirstCommand.equals(selectSecondCommand));
    }

    /**
     * Executes a {@code SelectCommand} with the given {@code index},
     * and checks that the model's selected folder is set to the folder at {@code index} in the filtered folder list.
     */
    private void assertExecutionSuccess(Index index) {
        SelectCommand selectCommand = new SelectCommand(index);
        String expectedMessage = String.format(SelectCommand.MESSAGE_SELECT_CARD_SUCCESS, index.getOneBased());
        expectedModel.setSelectedCard(model.getFilteredCards().get(index.getZeroBased()));

        assertCommandSuccess(selectCommand, model, commandHistory, expectedMessage, expectedModel);
    }

    /**
     * Executes a {@code SelectCommand} with the given {@code index}, and checks that a {@code CommandException}
     * is thrown with the {@code expectedMessage}.
     */
    private void assertExecutionFailure(Index index, String expectedMessage) {
        SelectCommand selectCommand = new SelectCommand(index);
        assertCommandFailure(selectCommand, model, commandHistory, expectedMessage);
    }
}
