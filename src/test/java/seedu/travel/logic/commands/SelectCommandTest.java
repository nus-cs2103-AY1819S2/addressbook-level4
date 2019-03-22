package seedu.travel.logic.commands;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.travel.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.travel.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.travel.logic.commands.CommandTestUtil.showPlaceAtIndex;
import static seedu.travel.testutil.TypicalIndexes.INDEX_FIRST_PLACE;
import static seedu.travel.testutil.TypicalIndexes.INDEX_SECOND_PLACE;
import static seedu.travel.testutil.TypicalIndexes.INDEX_THIRD_PLACE;
import static seedu.travel.testutil.TypicalPlaces.getTypicalTravelBuddy;

import org.junit.Test;

import seedu.travel.commons.core.Messages;
import seedu.travel.commons.core.index.Index;
import seedu.travel.logic.CommandHistory;
import seedu.travel.model.Model;
import seedu.travel.model.ModelManager;
import seedu.travel.model.UserPrefs;

/**
 * Contains integration tests (interaction with the Model) for {@code SelectCommand}.
 */
public class SelectCommandTest {
    private Model model = new ModelManager(getTypicalTravelBuddy(), new UserPrefs());
    private Model expectedModel = new ModelManager(getTypicalTravelBuddy(), new UserPrefs());
    private CommandHistory commandHistory = new CommandHistory();

    @Test
    public void execute_validIndexUnfilteredList_success() {
        Index lastPlaceIndex = Index.fromOneBased(model.getFilteredPlaceList().size());

        assertExecutionSuccess(INDEX_FIRST_PLACE);
        assertExecutionSuccess(INDEX_THIRD_PLACE);
        assertExecutionSuccess(lastPlaceIndex);
    }

    @Test
    public void execute_invalidIndexUnfilteredList_failure() {
        Index outOfBoundsIndex = Index.fromOneBased(model.getFilteredPlaceList().size() + 1);

        assertExecutionFailure(outOfBoundsIndex, Messages.MESSAGE_INVALID_PLACE_DISPLAYED_INDEX);
    }

    @Test
    public void execute_validIndexFilteredList_success() {
        showPlaceAtIndex(model, INDEX_FIRST_PLACE);
        showPlaceAtIndex(expectedModel, INDEX_FIRST_PLACE);

        assertExecutionSuccess(INDEX_FIRST_PLACE);
    }

    @Test
    public void execute_invalidIndexFilteredList_failure() {
        showPlaceAtIndex(model, INDEX_FIRST_PLACE);
        showPlaceAtIndex(expectedModel, INDEX_FIRST_PLACE);

        Index outOfBoundsIndex = INDEX_SECOND_PLACE;
        // ensures that outOfBoundIndex is still in bounds of travel book list
        assertTrue(outOfBoundsIndex.getZeroBased() < model.getTravelBuddy().getPlaceList().size());

        assertExecutionFailure(outOfBoundsIndex, Messages.MESSAGE_INVALID_PLACE_DISPLAYED_INDEX);
    }

    @Test
    public void equals() {
        SelectCommand selectFirstCommand = new SelectCommand(INDEX_FIRST_PLACE);
        SelectCommand selectSecondCommand = new SelectCommand(INDEX_SECOND_PLACE);

        // same object -> returns true
        assertTrue(selectFirstCommand.equals(selectFirstCommand));

        // same values -> returns true
        SelectCommand selectFirstCommandCopy = new SelectCommand(INDEX_FIRST_PLACE);
        assertTrue(selectFirstCommand.equals(selectFirstCommandCopy));

        // different types -> returns false
        assertFalse(selectFirstCommand.equals(1));

        // null -> returns false
        assertFalse(selectFirstCommand.equals(null));

        // different place -> returns false
        assertFalse(selectFirstCommand.equals(selectSecondCommand));
    }

    /**
     * Executes a {@code SelectCommand} with the given {@code index},
     * and checks that the model's selected place is set to the place at {@code index} in the filtered place list.
     */
    private void assertExecutionSuccess(Index index) {
        SelectCommand selectCommand = new SelectCommand(index);
        String expectedMessage = String.format(SelectCommand.MESSAGE_SELECT_PLACE_SUCCESS, index.getOneBased());
        expectedModel.setSelectedPlace(model.getFilteredPlaceList().get(index.getZeroBased()));

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
