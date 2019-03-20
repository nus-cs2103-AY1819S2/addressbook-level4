package systemtests;

//import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;
import static seedu.equipment.logic.commands.CommandTestUtil.ADDRESS_DESC_AMY;
import static seedu.equipment.logic.commands.CommandTestUtil.ADDRESS_DESC_BOB;
import static seedu.equipment.logic.commands.CommandTestUtil.EMAIL_DESC_AMY;
import static seedu.equipment.logic.commands.CommandTestUtil.EMAIL_DESC_BOB;
import static seedu.equipment.logic.commands.CommandTestUtil.INVALID_ADDRESS_DESC;
import static seedu.equipment.logic.commands.CommandTestUtil.INVALID_EMAIL_DESC;
import static seedu.equipment.logic.commands.CommandTestUtil.INVALID_NAME_DESC;
import static seedu.equipment.logic.commands.CommandTestUtil.INVALID_PHONE_DESC;
import static seedu.equipment.logic.commands.CommandTestUtil.INVALID_SERIAL_NUMBER_DESC;
import static seedu.equipment.logic.commands.CommandTestUtil.INVALID_TAG_DESC;
import static seedu.equipment.logic.commands.CommandTestUtil.NAME_DESC_AMY;
import static seedu.equipment.logic.commands.CommandTestUtil.NAME_DESC_BOB;
import static seedu.equipment.logic.commands.CommandTestUtil.PHONE_DESC_AMY;
import static seedu.equipment.logic.commands.CommandTestUtil.PHONE_DESC_BOB;
import static seedu.equipment.logic.commands.CommandTestUtil.SERIAL_NUMBER_DESC_AMY;
import static seedu.equipment.logic.commands.CommandTestUtil.SERIAL_NUMBER_DESC_BOB;
import static seedu.equipment.logic.commands.CommandTestUtil.TAG_DESC_FRIEND;
import static seedu.equipment.logic.commands.CommandTestUtil.TAG_DESC_HUSBAND;
//import static seedu.equipment.logic.commands.CommandTestUtil.VALID_EMAIL_AMY;
import static seedu.equipment.logic.commands.CommandTestUtil.VALID_NAME_BOB;
//import static seedu.equipment.logic.commands.CommandTestUtil.VALID_PHONE_AMY;
import static seedu.equipment.logic.commands.CommandTestUtil.VALID_SERIAL_NUMBER_AMY;
//import static seedu.equipment.logic.commands.CommandTestUtil.VALID_SERIAL_NUMBER_BOB;
import static seedu.equipment.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;
import static seedu.equipment.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.equipment.model.Model.PREDICATE_SHOW_ALL_PERSONS;
import static seedu.equipment.testutil.TypicalEquipments.AMY;
import static seedu.equipment.testutil.TypicalEquipments.BOB;
import static seedu.equipment.testutil.TypicalEquipments.KEYWORD_MATCHING_CC;
import static seedu.equipment.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.equipment.testutil.TypicalIndexes.INDEX_SECOND_PERSON;

import org.junit.Test;

import seedu.equipment.commons.core.Messages;
import seedu.equipment.commons.core.index.Index;
import seedu.equipment.logic.commands.EditCommand;
import seedu.equipment.logic.commands.RedoCommand;
import seedu.equipment.logic.commands.UndoCommand;
import seedu.equipment.model.Model;
import seedu.equipment.model.equipment.Address;
import seedu.equipment.model.equipment.Email;
import seedu.equipment.model.equipment.Equipment;
import seedu.equipment.model.equipment.Name;
import seedu.equipment.model.equipment.Phone;
import seedu.equipment.model.equipment.SerialNumber;
import seedu.equipment.model.tag.Tag;
import seedu.equipment.testutil.EquipmentBuilder;
//import seedu.equipment.testutil.EquipmentUtil;

public class EditCommandSystemTest extends EquipmentManagerSystemTest {

    @Test
    public void edit() {
        Model model = getModel();

        /* ----------------- Performing edit operation while an unfiltered list is being shown ---------------------- */

        /* Case: edit all fields, command with leading spaces, trailing spaces and multiple spaces between each field
         * -> edited
         */
        Index index = INDEX_FIRST_PERSON;
        String command = EditCommand.COMMAND_WORD + "  " + index.getOneBased() + "  " + NAME_DESC_BOB + "  "
                + PHONE_DESC_BOB + " " + EMAIL_DESC_BOB + "  " + ADDRESS_DESC_BOB + " " + SERIAL_NUMBER_DESC_BOB
                + " " + TAG_DESC_HUSBAND + " ";
        Equipment editedEquipment = new EquipmentBuilder(BOB).withTags(VALID_TAG_HUSBAND).build();
        assertCommandSuccess(command, index, editedEquipment);

        /* Case: undo editing the last equipment in the list -> last equipment restored */
        command = UndoCommand.COMMAND_WORD;
        String expectedResultMessage = UndoCommand.MESSAGE_SUCCESS;
        assertCommandSuccess(command, model, expectedResultMessage);

        /* Case: redo editing the last equipment in the list -> last equipment edited again */
        command = RedoCommand.COMMAND_WORD;
        expectedResultMessage = RedoCommand.MESSAGE_SUCCESS;
        model.setPerson(getModel().getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased()), editedEquipment);
        assertCommandSuccess(command, model, expectedResultMessage);

        /* Case: edit an equipment with new values same as existing values -> edited */
        command = EditCommand.COMMAND_WORD + " " + index.getOneBased() + NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB
                + ADDRESS_DESC_BOB + SERIAL_NUMBER_DESC_BOB + TAG_DESC_FRIEND + TAG_DESC_HUSBAND;
        assertCommandSuccess(command, index, BOB);

        /* Case: edit an equipment with new values same as another equipment's values but with
         * different serial number -> edited
         */
        assertTrue(getModel().getAddressBook().getPersonList().contains(BOB));
        index = INDEX_SECOND_PERSON;
        assertNotEquals(getModel().getFilteredPersonList().get(index.getZeroBased()), BOB);
        command = EditCommand.COMMAND_WORD + " " + index.getOneBased() + NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB
                + ADDRESS_DESC_BOB + SERIAL_NUMBER_DESC_AMY + TAG_DESC_FRIEND + TAG_DESC_HUSBAND;
        editedEquipment = new EquipmentBuilder(BOB).withSerialNumber(VALID_SERIAL_NUMBER_AMY).build();
        assertCommandSuccess(command, index, editedEquipment);

        /* Case: clear tags -> cleared */
        index = INDEX_FIRST_PERSON;
        command = EditCommand.COMMAND_WORD + " " + index.getOneBased() + " " + PREFIX_TAG.getPrefix();
        Equipment equipmentToEdit = getModel().getFilteredPersonList().get(index.getZeroBased());
        editedEquipment = new EquipmentBuilder(equipmentToEdit).withTags().build();
        assertCommandSuccess(command, index, editedEquipment);

        /* Case: edit an equipment with new values same as another equipment's values but with
         * different serial number -> edited
         */
        index = INDEX_SECOND_PERSON;
        assertNotEquals(getModel().getFilteredPersonList().get(index.getZeroBased()), AMY);
        command = EditCommand.COMMAND_WORD + " " + index.getOneBased() + NAME_DESC_AMY + PHONE_DESC_AMY + EMAIL_DESC_AMY
                + ADDRESS_DESC_AMY + SERIAL_NUMBER_DESC_AMY + TAG_DESC_FRIEND;
        editedEquipment = new EquipmentBuilder(AMY).withSerialNumber(VALID_SERIAL_NUMBER_AMY).build();
        assertCommandSuccess(command, index, editedEquipment);

        /* ------------------ Performing edit operation while a filtered list is being shown ------------------------ */

        /* Case: filtered equipment list, edit index within bounds of equipment book and equipment list -> edited */
        showPersonsWithName(KEYWORD_MATCHING_CC);
        index = INDEX_FIRST_PERSON;
        assertTrue(index.getZeroBased() < getModel().getFilteredPersonList().size());
        command = EditCommand.COMMAND_WORD + " " + index.getOneBased() + " " + NAME_DESC_BOB;
        equipmentToEdit = getModel().getFilteredPersonList().get(index.getZeroBased());
        editedEquipment = new EquipmentBuilder(equipmentToEdit).withName(VALID_NAME_BOB).build();
        assertCommandSuccess(command, index, editedEquipment);

        /* Case: filtered equipment list, edit index within bounds of equipment book but out of bounds of equipment list
         * -> rejected
         */
        showPersonsWithName(KEYWORD_MATCHING_CC);
        int invalidIndex = getModel().getAddressBook().getPersonList().size();
        assertCommandFailure(EditCommand.COMMAND_WORD + " " + invalidIndex + NAME_DESC_BOB,
                Messages.MESSAGE_INVALID_EQUIPMENT_DISPLAYED_INDEX);

        /* ------------------- Performing edit operation while a equipment card is selected ----------------------- */

        /* Case: selects first card in the equipment list, edit a equipment -> edited, card selection remains unchanged
         * but browser url changes
         */
        showAllPersons();
        index = INDEX_FIRST_PERSON;
        selectPerson(index);
        command = EditCommand.COMMAND_WORD + " " + index.getOneBased() + NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB
                + ADDRESS_DESC_BOB + SERIAL_NUMBER_DESC_BOB + TAG_DESC_FRIEND + TAG_DESC_HUSBAND;
        // this can be misleading: card selection actually remains unchanged but the
        // browser's url is updated to reflect the new equipment's name
        assertCommandSuccess(command, index, BOB, index);

        /* --------------------------------- Performing invalid edit operation ------------------------------------ */

        /* Case: invalid index (0) -> rejected */
        assertCommandFailure(EditCommand.COMMAND_WORD + " 0" + NAME_DESC_BOB,
                String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT, EditCommand.MESSAGE_USAGE));

        /* Case: invalid index (-1) -> rejected */
        assertCommandFailure(EditCommand.COMMAND_WORD + " -1" + NAME_DESC_BOB,
                String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT, EditCommand.MESSAGE_USAGE));

        /* Case: invalid index (size + 1) -> rejected */
        invalidIndex = getModel().getFilteredPersonList().size() + 1;
        assertCommandFailure(EditCommand.COMMAND_WORD + " " + invalidIndex + NAME_DESC_BOB,
                Messages.MESSAGE_INVALID_EQUIPMENT_DISPLAYED_INDEX);

        /* Case: missing index -> rejected */
        assertCommandFailure(EditCommand.COMMAND_WORD + NAME_DESC_BOB,
                String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT, EditCommand.MESSAGE_USAGE));

        /* Case: missing all fields -> rejected */
        assertCommandFailure(EditCommand.COMMAND_WORD + " " + INDEX_FIRST_PERSON.getOneBased(),
                EditCommand.MESSAGE_NOT_EDITED);

        /* Case: invalid name -> rejected */
        assertCommandFailure(EditCommand.COMMAND_WORD + " " + INDEX_FIRST_PERSON.getOneBased()
                + INVALID_NAME_DESC, Name.MESSAGE_CONSTRAINTS);

        /* Case: invalid phone -> rejected */
        assertCommandFailure(EditCommand.COMMAND_WORD + " " + INDEX_FIRST_PERSON.getOneBased()
                + INVALID_PHONE_DESC, Phone.MESSAGE_CONSTRAINTS);

        /* Case: invalid serial number -> rejected */
        assertCommandFailure(EditCommand.COMMAND_WORD + " " + INDEX_FIRST_PERSON.getOneBased()
                + INVALID_SERIAL_NUMBER_DESC, SerialNumber.MESSAGE_CONSTRAINTS);

        /* Case: invalid email -> rejected */
        assertCommandFailure(EditCommand.COMMAND_WORD + " " + INDEX_FIRST_PERSON.getOneBased()
                + INVALID_EMAIL_DESC, Email.MESSAGE_CONSTRAINTS);

        /* Case: invalid equipment -> rejected */
        assertCommandFailure(EditCommand.COMMAND_WORD + " " + INDEX_FIRST_PERSON.getOneBased()
                + INVALID_ADDRESS_DESC, Address.MESSAGE_CONSTRAINTS);

        /* Case: invalid tag -> rejected */
        assertCommandFailure(EditCommand.COMMAND_WORD + " " + INDEX_FIRST_PERSON.getOneBased()
                + INVALID_TAG_DESC, Tag.MESSAGE_CONSTRAINTS);

        /* Case: edit an equipment with new values same as another equipment's values -> rejected */
        index = INDEX_SECOND_PERSON;
        command = EditCommand.COMMAND_WORD + " " + index.getOneBased() + NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB
                + ADDRESS_DESC_BOB + SERIAL_NUMBER_DESC_BOB + TAG_DESC_FRIEND + TAG_DESC_HUSBAND;
        assertCommandFailure(command, EditCommand.MESSAGE_DUPLICATE_EQUIPMENT);

        /* Case: edit an equipment with new values same as another equipment's values
         * but with different tags -> rejected
         */
        index = INDEX_SECOND_PERSON;
        command = EditCommand.COMMAND_WORD + " " + index.getOneBased() + NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB
                + ADDRESS_DESC_BOB + SERIAL_NUMBER_DESC_BOB + TAG_DESC_FRIEND + TAG_DESC_HUSBAND;
        assertCommandFailure(command, EditCommand.MESSAGE_DUPLICATE_EQUIPMENT);

        /* Case: edit an equipment with new values same as another equipment's values
         * but with different equipment -> rejected
         */
        command = EditCommand.COMMAND_WORD + " " + index.getOneBased() + NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB
                + ADDRESS_DESC_AMY + SERIAL_NUMBER_DESC_BOB + TAG_DESC_FRIEND + TAG_DESC_HUSBAND;
        assertCommandFailure(command, EditCommand.MESSAGE_DUPLICATE_EQUIPMENT);

        /* Case: edit a equipment with new values same as another equipment's values
         * but with different phone -> rejected
         */
        command = EditCommand.COMMAND_WORD + " " + index.getOneBased() + NAME_DESC_BOB + PHONE_DESC_AMY + EMAIL_DESC_BOB
                + ADDRESS_DESC_BOB + SERIAL_NUMBER_DESC_BOB + TAG_DESC_FRIEND + TAG_DESC_HUSBAND;
        assertCommandFailure(command, EditCommand.MESSAGE_DUPLICATE_EQUIPMENT);

        /* Case: edit a equipment with new values same as another equipment's values
         * but with different email -> rejected
         */
        command = EditCommand.COMMAND_WORD + " " + index.getOneBased() + NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_AMY
                + ADDRESS_DESC_BOB + SERIAL_NUMBER_DESC_BOB + TAG_DESC_FRIEND + TAG_DESC_HUSBAND;
        assertCommandFailure(command, EditCommand.MESSAGE_DUPLICATE_EQUIPMENT);

        /* Case: edit an equipment with new values same as another equipment's values but with
         * different name -> rejected
         */
        command = EditCommand.COMMAND_WORD + " " + index.getOneBased() + NAME_DESC_AMY + PHONE_DESC_BOB + EMAIL_DESC_AMY
                + ADDRESS_DESC_BOB + SERIAL_NUMBER_DESC_BOB + TAG_DESC_FRIEND + TAG_DESC_HUSBAND;
        assertCommandFailure(command, EditCommand.MESSAGE_DUPLICATE_EQUIPMENT);
    }

    /**
     * Performs the same verification as {@code assertCommandSuccess(String, Index, Equipment, Index)} except that
     * the browser url and selected card remain unchanged.
     * @param toEdit the index of the current model's filtered list
     * @see EditCommandSystemTest#assertCommandSuccess(String, Index, Equipment, Index)
     */
    private void assertCommandSuccess(String command, Index toEdit, Equipment editedEquipment) {
        assertCommandSuccess(command, toEdit, editedEquipment, null);
    }

    /**
     * Performs the same verification as {@code assertCommandSuccess(String, Model, String, Index)} and in addition,<br>
     * 1. Asserts that result display box displays the success message of executing {@code EditCommand}.<br>
     * 2. Asserts that the model related components are updated to reflect the equipment at index {@code toEdit} being
     * updated to values specified {@code editedEquipment}.<br>
     * @param toEdit the index of the current model's filtered list.
     * @see EditCommandSystemTest#assertCommandSuccess(String, Model, String, Index)
     */
    private void assertCommandSuccess(String command, Index toEdit, Equipment editedEquipment,
            Index expectedSelectedCardIndex) {
        Model expectedModel = getModel();
        expectedModel.setPerson(expectedModel.getFilteredPersonList().get(toEdit.getZeroBased()), editedEquipment);
        expectedModel.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);

        assertCommandSuccess(command, expectedModel,
                String.format(EditCommand.MESSAGE_EDIT_PERSON_SUCCESS, editedEquipment), expectedSelectedCardIndex);
    }

    /**
     * Performs the same verification as {@code assertCommandSuccess(String, Model, String, Index)} except that the
     * browser url and selected card remain unchanged.
     * @see EditCommandSystemTest#assertCommandSuccess(String, Model, String, Index)
     */
    private void assertCommandSuccess(String command, Model expectedModel, String expectedResultMessage) {
        assertCommandSuccess(command, expectedModel, expectedResultMessage, null);
    }

    /**
     * Executes {@code command} and in addition,<br>
     * 1. Asserts that the command box displays an empty string.<br>
     * 2. Asserts that the result display box displays {@code expectedResultMessage}.<br>
     * 3. Asserts that the browser url and selected card update accordingly depending on the card at
     * {@code expectedSelectedCardIndex}.<br>
     * 4. Asserts that the status bar's sync status changes.<br>
     * 5. Asserts that the command box has the default style class.<br>
     * Verifications 1 and 2 are performed by
     * {@code EquipmentManagerSystemTest#assertApplicationDisplaysExpected(String, String, Model)}.<br>
     * @see EquipmentManagerSystemTest#assertApplicationDisplaysExpected(String, String, Model)
     * @see EquipmentManagerSystemTest#assertSelectedCardChanged(Index)
     */
    private void assertCommandSuccess(String command, Model expectedModel, String expectedResultMessage,
            Index expectedSelectedCardIndex) {
        executeCommand(command);
        expectedModel.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
        assertApplicationDisplaysExpected("", expectedResultMessage, expectedModel);
        assertCommandBoxShowsDefaultStyle();
        if (expectedSelectedCardIndex != null) {
            assertSelectedCardChanged(expectedSelectedCardIndex);
        } else {
            assertSelectedCardUnchanged();
        }
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
