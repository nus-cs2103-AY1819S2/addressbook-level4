package seedu.address.logic.commands;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.CommandTestUtil.showMedicineAtIndex;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_MEDICINE;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_MEDICINE;
import static seedu.address.testutil.TypicalIndexes.INDEX_THIRD_MEDICINE;
import static seedu.address.testutil.TypicalMedicines.getTypicalInventory;

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
    private Model model = new ModelManager(getTypicalInventory(), new UserPrefs());
    private Model expectedModel = new ModelManager(getTypicalInventory(), new UserPrefs());
    private CommandHistory commandHistory = new CommandHistory();

    @Test
    public void execute_validIndexUnfilteredList_success() {
        Index lastMedicineIndex = Index.fromOneBased(model.getFilteredMedicineList().size());

        assertExecutionSuccess(INDEX_FIRST_MEDICINE);
        assertExecutionSuccess(INDEX_THIRD_MEDICINE);
        assertExecutionSuccess(lastMedicineIndex);
    }

    @Test
    public void execute_invalidIndexUnfilteredList_failure() {
        Index outOfBoundsIndex = Index.fromOneBased(model.getFilteredMedicineList().size() + 1);

        assertExecutionFailure(outOfBoundsIndex, Messages.MESSAGE_INVALID_MEDICINE_DISPLAYED_INDEX);
    }

    @Test
    public void execute_validIndexFilteredList_success() {
        showMedicineAtIndex(model, INDEX_FIRST_MEDICINE);
        showMedicineAtIndex(expectedModel, INDEX_FIRST_MEDICINE);

        assertExecutionSuccess(INDEX_FIRST_MEDICINE);
    }

    @Test
    public void execute_invalidIndexFilteredList_failure() {
        showMedicineAtIndex(model, INDEX_FIRST_MEDICINE);
        showMedicineAtIndex(expectedModel, INDEX_FIRST_MEDICINE);

        Index outOfBoundsIndex = INDEX_SECOND_MEDICINE;
        // ensures that outOfBoundIndex is still in bounds of inventory list
        assertTrue(outOfBoundsIndex.getZeroBased() < model.getInventory().getMedicineList().size());

        assertExecutionFailure(outOfBoundsIndex, Messages.MESSAGE_INVALID_MEDICINE_DISPLAYED_INDEX);
    }

    @Test
    public void equals() {
        SelectCommand selectFirstCommand = new SelectCommand(INDEX_FIRST_MEDICINE);
        SelectCommand selectSecondCommand = new SelectCommand(INDEX_SECOND_MEDICINE);

        // same object -> returns true
        assertTrue(selectFirstCommand.equals(selectFirstCommand));

        // same values -> returns true
        SelectCommand selectFirstCommandCopy = new SelectCommand(INDEX_FIRST_MEDICINE);
        assertTrue(selectFirstCommand.equals(selectFirstCommandCopy));

        // different types -> returns false
        assertFalse(selectFirstCommand.equals(1));

        // null -> returns false
        assertFalse(selectFirstCommand.equals(null));

        // different medicine -> returns false
        assertFalse(selectFirstCommand.equals(selectSecondCommand));
    }

    /**
     * Executes a {@code SelectCommand} with the given {@code index},
     * and checks that the model's selected medicine is set to the medicine
     * at {@code index} in the filtered medicine list.
     */
    private void assertExecutionSuccess(Index index) {
        SelectCommand selectCommand = new SelectCommand(index);
        String expectedMessage = String.format(SelectCommand.MESSAGE_SELECT_MEDICINE_SUCCESS, index.getOneBased());
        expectedModel.setSelectedMedicine(model.getFilteredMedicineList().get(index.getZeroBased()));

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
