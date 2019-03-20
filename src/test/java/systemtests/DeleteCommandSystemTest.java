package systemtests;

import static org.junit.Assert.assertTrue;
import static seedu.equipment.commons.core.Messages.MESSAGE_INVALID_EQUIPMENT_DISPLAYED_INDEX;
import static seedu.equipment.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;
import static seedu.equipment.logic.commands.DeleteCommand.MESSAGE_DELETE_PERSON_SUCCESS;
import static seedu.equipment.testutil.TestUtil.getLastIndex;
import static seedu.equipment.testutil.TestUtil.getMidIndex;
import static seedu.equipment.testutil.TestUtil.getPerson;
import static seedu.equipment.testutil.TypicalEquipments.KEYWORD_MATCHING_HWI;
//import static seedu.equipment.testutil.TypicalEquipments.KEYWORD_MATCHING_CC;
import static seedu.equipment.testutil.TypicalIndexes.INDEX_FIRST_PERSON;

import org.junit.Test;

import seedu.equipment.commons.core.Messages;
import seedu.equipment.commons.core.index.Index;
import seedu.equipment.logic.commands.DeleteCommand;
import seedu.equipment.logic.commands.RedoCommand;
import seedu.equipment.logic.commands.UndoCommand;
import seedu.equipment.model.Model;
import seedu.equipment.model.equipment.Equipment;

public class DeleteCommandSystemTest extends EquipmentManagerSystemTest {

    private static final String MESSAGE_INVALID_DELETE_COMMAND_FORMAT =
            String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT, DeleteCommand.MESSAGE_USAGE);

    @Test
    public void delete() {
        /* ----------------- Performing delete operation while an unfiltered list is being shown -------------------- */

        /* Case: delete the first equipment in the list, command with leading spaces and trailing spaces -> deleted */
        Model expectedModel = getModel();
        String command = "     " + DeleteCommand.COMMAND_WORD + "      " + INDEX_FIRST_PERSON.getOneBased() + "       ";
        Equipment deletedEquipment = removePerson(expectedModel, INDEX_FIRST_PERSON);
        String expectedResultMessage = String.format(MESSAGE_DELETE_PERSON_SUCCESS, deletedEquipment);
        assertCommandSuccess(command, expectedModel, expectedResultMessage);

        /* Case: delete the last equipment in the list -> deleted */
        Model modelBeforeDeletingLast = getModel();
        Index lastPersonIndex = getLastIndex(modelBeforeDeletingLast);
        assertCommandSuccess(lastPersonIndex);

        /* Case: undo deleting the last equipment in the list -> last equipment restored */
        command = UndoCommand.COMMAND_WORD;
        expectedResultMessage = UndoCommand.MESSAGE_SUCCESS;
        assertCommandSuccess(command, modelBeforeDeletingLast, expectedResultMessage);

        /* Case: redo deleting the last equipment in the list -> last equipment deleted again */
        command = RedoCommand.COMMAND_WORD;
        removePerson(modelBeforeDeletingLast, lastPersonIndex);
        expectedResultMessage = RedoCommand.MESSAGE_SUCCESS;
        assertCommandSuccess(command, modelBeforeDeletingLast, expectedResultMessage);

        /* Case: delete the middle equipment in the list -> deleted */
        Index middlePersonIndex = getMidIndex(getModel());
        assertCommandSuccess(middlePersonIndex);

        /* ------------------ Performing delete operation while a filtered list is being shown ---------------------- */

        /* Case: filtered equipment list, delete index within bounds of equipment book and equipment list -> deleted */
        showPersonsWithName(KEYWORD_MATCHING_HWI);
        Index index = INDEX_FIRST_PERSON;
        assertTrue(index.getZeroBased() < getModel().getFilteredPersonList().size());
        assertCommandSuccess(index);

        /* Case: filtered equipment list, delete index within bounds of equipment book but out of bounds of equipment list
         * -> rejected
         */
        showPersonsWithName(KEYWORD_MATCHING_HWI);
        int invalidIndex = getModel().getAddressBook().getPersonList().size();
        command = DeleteCommand.COMMAND_WORD + " " + invalidIndex;
        assertCommandFailure(command, MESSAGE_INVALID_EQUIPMENT_DISPLAYED_INDEX);

        /* ------------------- Performing delete operation while a equipment card is selected --------------------- */

        /* Case: delete the selected equipment -> equipment list panel selects the equipment
         * before the deleted equipment
         */
        showAllPersons();
        expectedModel = getModel();
        Index selectedIndex = getLastIndex(expectedModel);
        Index expectedIndex = Index.fromZeroBased(selectedIndex.getZeroBased() - 1);
        selectPerson(selectedIndex);
        command = DeleteCommand.COMMAND_WORD + " " + selectedIndex.getOneBased();
        deletedEquipment = removePerson(expectedModel, selectedIndex);
        expectedResultMessage = String.format(MESSAGE_DELETE_PERSON_SUCCESS, deletedEquipment);
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
                getModel().getAddressBook().getPersonList().size() + 1);
        command = DeleteCommand.COMMAND_WORD + " " + outOfBoundsIndex.getOneBased();
        assertCommandFailure(command, MESSAGE_INVALID_EQUIPMENT_DISPLAYED_INDEX);

        /* Case: invalid arguments (alphabets) -> rejected */
        assertCommandFailure(DeleteCommand.COMMAND_WORD + " abc", MESSAGE_INVALID_DELETE_COMMAND_FORMAT);

        /* Case: invalid arguments (extra argument) -> rejected */
        assertCommandFailure(DeleteCommand.COMMAND_WORD + " 1 abc", MESSAGE_INVALID_DELETE_COMMAND_FORMAT);

        /* Case: mixed case command word -> rejected */
        assertCommandFailure("DelETE 1", MESSAGE_UNKNOWN_COMMAND);
    }

    /**
     * Removes the {@code Equipment} at the specified {@code index} in {@code model}'s equipment book.
     * @return the removed equipment
     */
    private Equipment removePerson(Model model, Index index) {
        Equipment targetEquipment = getPerson(model, index);
        model.deletePerson(targetEquipment);
        return targetEquipment;
    }

    /**
     * Deletes the equipment at {@code toDelete} by creating a default {@code DeleteCommand} using {@code toDelete} and
     * performs the same verification as {@code assertCommandSuccess(String, Model, String)}.
     * @see DeleteCommandSystemTest#assertCommandSuccess(String, Model, String)
     */
    private void assertCommandSuccess(Index toDelete) {
        Model expectedModel = getModel();
        Equipment deletedEquipment = removePerson(expectedModel, toDelete);
        String expectedResultMessage = String.format(MESSAGE_DELETE_PERSON_SUCCESS, deletedEquipment);

        assertCommandSuccess(DeleteCommand.COMMAND_WORD + " " + toDelete.getOneBased(),
                expectedModel, expectedResultMessage);
    }

    /**
     * Executes {@code command} and in addition,<br>
     * 1. Asserts that the command box displays an empty string.<br>
     * 2. Asserts that the result display box displays {@code expectedResultMessage}.<br>
     * 3. Asserts that the browser url and selected card remains unchanged.<br>
     * 4. Asserts that the status bar's sync status changes.<br>
     * 5. Asserts that the command box has the default style class.<br>
     * Verifications 1 and 2 are performed by
     * {@code EquipmentManagerSystemTest#assertApplicationDisplaysExpected(String, String, Model)}.
     * @see EquipmentManagerSystemTest#assertApplicationDisplaysExpected(String, String, Model)
     */
    private void assertCommandSuccess(String command, Model expectedModel, String expectedResultMessage) {
        assertCommandSuccess(command, expectedModel, expectedResultMessage, null);
    }

    /**
     * Performs the same verification as {@code assertCommandSuccess(String, Model, String)} except that the browser url
     * and selected card are expected to update accordingly depending on the card at {@code expectedSelectedCardIndex}.
     * @see DeleteCommandSystemTest#assertCommandSuccess(String, Model, String)
     * @see EquipmentManagerSystemTest#assertSelectedCardChanged(Index)
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
     * 3. Asserts that the browser url, selected card and status bar remain unchanged.<br>
     * 4. Asserts that the command box has the error style.<br>
     * Verifications 1 and 2 are performed by
     * {@code EquipmentManagerSystemTest#assertApplicationDisplaysExpected(String, String, Model)}.<br>
     * @see EquipmentManagerSystemTest#assertApplicationDisplaysExpected(String, String, Model)
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
