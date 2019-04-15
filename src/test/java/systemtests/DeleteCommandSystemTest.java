package systemtests;

import static junit.framework.TestCase.assertTrue;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_MEDICINE_DISPLAYED_INDEX;
import static seedu.address.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;
import static seedu.address.logic.commands.DeleteCommand.MESSAGE_DELETE_MEDICINE_SUCCESS;
import static seedu.address.testutil.TestUtil.getLastIndex;
import static seedu.address.testutil.TestUtil.getMedicine;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_MEDICINE;
import static seedu.address.testutil.TypicalMedicines.KEYWORD_MATCHING_SODIUM;

import java.util.Comparator;

import org.junit.Test;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.DeleteCommand;
import seedu.address.logic.commands.RedoCommand;
import seedu.address.logic.commands.UndoCommand;
import seedu.address.model.Model;
import seedu.address.model.medicine.Medicine;

public class DeleteCommandSystemTest extends MediTabsSystemTest {

    private static final String MESSAGE_INVALID_DELETE_COMMAND_FORMAT =
            String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT, DeleteCommand.MESSAGE_USAGE);
    private static final Comparator<Medicine> comparator = Comparator.naturalOrder();

    @Test
    public void delete() {
        /* ----------------- Performing delete operation while an unfiltered list is being shown -------------------- */

        /* Case: delete the first medicine in the list, command with leading spaces and trailing spaces -> deleted */
        Model expectedModel = getModel();
        String command = "     " + DeleteCommand.COMMAND_WORD + "      " + INDEX_FIRST_MEDICINE.getOneBased()
                + "       ";
        Medicine deletedMedicine = removeMedicine(expectedModel, INDEX_FIRST_MEDICINE);
        String expectedResultMessage = String.format(MESSAGE_DELETE_MEDICINE_SUCCESS, deletedMedicine);
        assertCommandSuccess(command, expectedModel, expectedResultMessage);

        /* Case: delete the last medicine in the list -> deleted */
        Model modelBeforeDeletingLast = getModel();
        Index lastMedicineIndex = getLastIndex(modelBeforeDeletingLast);
        assertCommandSuccess(lastMedicineIndex);

        /* Case: undo deleting the last medicine in the list -> last medicine restored */
        command = UndoCommand.COMMAND_WORD;
        expectedResultMessage = UndoCommand.MESSAGE_SUCCESS;
        assertCommandSuccess(command, modelBeforeDeletingLast, expectedResultMessage);

        /* Case: redo deleting the last medicine in the list -> last medicine deleted again */
        command = RedoCommand.COMMAND_WORD;
        removeMedicine(modelBeforeDeletingLast, lastMedicineIndex);
        expectedResultMessage = RedoCommand.MESSAGE_SUCCESS;
        assertCommandSuccess(command, modelBeforeDeletingLast, expectedResultMessage);

        /* ------------------ Performing delete operation while a filtered list is being shown ---------------------- */

        /* Case: filtered medicine list, delete index within bounds of inventory and medicine list -> deleted */
        showMedicinesWithName(KEYWORD_MATCHING_SODIUM);
        Index index = INDEX_FIRST_MEDICINE;
        assertTrue(index.getZeroBased() < getModel().getFilteredMedicineList().size());
        assertCommandSuccess(index);

        /* Case: filtered medicine list, delete index within bounds of inventory but out of bounds of medicine list
         * -> rejected
         */
        showMedicinesWithName(KEYWORD_MATCHING_SODIUM);
        int invalidIndex = getModel().getInventory().getSortedMedicineList(comparator).size();
        command = DeleteCommand.COMMAND_WORD + " " + invalidIndex;
        assertCommandFailure(command, MESSAGE_INVALID_MEDICINE_DISPLAYED_INDEX);

        /* -------------------- Performing delete operation while a medicine card is selected ----------------------- */

        /* Case: delete the selected medicine -> medicine list panel selects the medicine before the deleted medicine */
        showAllMedicines();
        expectedModel = getModel();
        Index selectedIndex = getLastIndex(expectedModel);
        Index expectedIndex = Index.fromZeroBased(selectedIndex.getZeroBased() - 1);
        selectMedicine(selectedIndex);
        command = DeleteCommand.COMMAND_WORD + " " + selectedIndex.getOneBased();
        deletedMedicine = removeMedicine(expectedModel, selectedIndex);
        expectedResultMessage = String.format(MESSAGE_DELETE_MEDICINE_SUCCESS, deletedMedicine);
        assertCommandSuccess(command, expectedModel, expectedResultMessage, expectedIndex);

        /* --------------------------------- Performing invalid delete operation ------------------------------------ */

        /* Case: invalid index (0) -> rejected */
        command = DeleteCommand.COMMAND_WORD + " 0";
        assertCommandFailure(command, MESSAGE_INVALID_DELETE_COMMAND_FORMAT);

        /* Case: invalid index (-1) -> rejected */
        command = DeleteCommand.COMMAND_WORD + " -1";
        assertCommandFailure(command, MESSAGE_INVALID_DELETE_COMMAND_FORMAT);

        /* Case: invalid index (size + 1) -> rejected */
        Index outOfBoundsIndex = Index.fromOneBased(
                getModel().getInventory().getSortedMedicineList(comparator).size() + 1);
        command = DeleteCommand.COMMAND_WORD + " " + outOfBoundsIndex.getOneBased();
        assertCommandFailure(command, MESSAGE_INVALID_MEDICINE_DISPLAYED_INDEX);

        /* Case: invalid arguments (alphabets) -> rejected */
        assertCommandFailure(DeleteCommand.COMMAND_WORD + " abc", MESSAGE_INVALID_DELETE_COMMAND_FORMAT);

        /* Case: invalid arguments (extra argument) -> rejected */
        assertCommandFailure(DeleteCommand.COMMAND_WORD + " 1 abc", MESSAGE_INVALID_DELETE_COMMAND_FORMAT);

        /* Case: mixed case command word -> rejected */
        assertCommandFailure("DelETE 1", MESSAGE_UNKNOWN_COMMAND);
    }

    /**
     * Removes the {@code Medicine} at the specified {@code index} in {@code model}'s inventory.
     * @return the removed medicine
     */
    private Medicine removeMedicine(Model model, Index index) {
        Medicine targetMedicine = getMedicine(model, index);
        model.deleteMedicine(targetMedicine);
        return targetMedicine;
    }

    /**
     * Deletes the medicine at {@code toDelete} by creating a default {@code DeleteCommand} using {@code toDelete} and
     * performs the same verification as {@code assertCommandSuccess(String, Model, String)}.
     * @see DeleteCommandSystemTest#assertCommandSuccess(String, Model, String)
     */
    private void assertCommandSuccess(Index toDelete) {
        Model expectedModel = getModel();
        Medicine deletedMedicine = removeMedicine(expectedModel, toDelete);
        String expectedResultMessage = String.format(MESSAGE_DELETE_MEDICINE_SUCCESS, deletedMedicine);

        assertCommandSuccess(
                DeleteCommand.COMMAND_WORD + " " + toDelete.getOneBased(), expectedModel, expectedResultMessage);
    }

    /**
     * Executes {@code command} and in addition,<br>
     * 1. Asserts that the command box displays an empty string.<br>
     * 2. Asserts that the result display box displays {@code expectedResultMessage}.<br>
     * 3. Asserts that the loaded information table and selected card remains unchanged.<br>
     * 4. Asserts that the status bar's sync status changes.<br>
     * 5. Asserts that the command box has the default style class.<br>
     * Verifications 1 and 2 are performed by
     * {@code MediTabsSystemTest#assertApplicationDisplaysExpected(String, String, Model)}.
     * @see MediTabsSystemTest#assertApplicationDisplaysExpected(String, String, Model)
     */
    private void assertCommandSuccess(String command, Model expectedModel, String expectedResultMessage) {
        assertCommandSuccess(command, expectedModel, expectedResultMessage, null);
    }

    /**
     * Performs the same verification as {@code assertCommandSuccess(String, Model, String)} except that the loaded
     * information table and selected card are expected to update accordingly depending on the card at
     * {@code expectedSelectedCardIndex}.
     * @see DeleteCommandSystemTest#assertCommandSuccess(String, Model, String)
     * @see MediTabsSystemTest#assertSelectedCardChanged(Index)
     */
    private void assertCommandSuccess(String command, Model expectedModel, String expectedResultMessage,
            Index expectedSelectedCardIndex) {
        executeCommand(command);
        assertApplicationDisplaysExpected("", expectedResultMessage, expectedModel);

        if (expectedSelectedCardIndex != null) {
            assertSelectedCardChanged(expectedSelectedCardIndex);
        } else {
            assertSelectedCardUnchanged();
        }

        assertCommandBoxShowsDefaultStyle();
        assertStatusBarUnchangedExceptSyncStatus();
    }

    /**
     * Executes {@code command} and in addition,<br>
     * 1. Asserts that the command box displays {@code command}.<br>
     * 2. Asserts that result display box displays {@code expectedResultMessage}.<br>
     * 3. Asserts that the loaded information table, selected card and status bar remain unchanged.<br>
     * 4. Asserts that the command box has the error style.<br>
     * Verifications 1 and 2 are performed by
     * {@code MediTabsSystemTest#assertApplicationDisplaysExpected(String, String, Model)}.<br>
     * @see MediTabsSystemTest#assertApplicationDisplaysExpected(String, String, Model)
     */
    private void assertCommandFailure(String command, String expectedResultMessage) {
        Model expectedModel = getModel();

        executeCommand(command);
        assertApplicationDisplaysExpected(command, expectedResultMessage, expectedModel);
        assertSelectedCardUnchanged();
        assertCommandBoxShowsErrorStyle();
        assertStatusBarUnchanged();
    }
}
