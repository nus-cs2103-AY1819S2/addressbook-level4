package systemtests;

import static seedu.equipment.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.equipment.logic.commands.CommandTestUtil.ADDRESS_DESC_AMY;
import static seedu.equipment.logic.commands.CommandTestUtil.ADDRESS_DESC_BOB;
import static seedu.equipment.logic.commands.CommandTestUtil.EMAIL_DESC_AMY;
import static seedu.equipment.logic.commands.CommandTestUtil.EMAIL_DESC_BOB;
//import static seedu.equipment.logic.commands.CommandTestUtil.INVALID_ADDRESS_DESC;
//import static seedu.equipment.logic.commands.CommandTestUtil.INVALID_EMAIL_DESC;
import static seedu.equipment.logic.commands.CommandTestUtil.INVALID_NAME_DESC;
//import static seedu.equipment.logic.commands.CommandTestUtil.INVALID_PHONE_DESC;
//import static seedu.equipment.logic.commands.CommandTestUtil.INVALID_SERIAL_NUMBER_DESC;
//import static seedu.equipment.logic.commands.CommandTestUtil.INVALID_TAG_DESC;
import static seedu.equipment.logic.commands.CommandTestUtil.NAME_DESC_AMY;
import static seedu.equipment.logic.commands.CommandTestUtil.NAME_DESC_BOB;
import static seedu.equipment.logic.commands.CommandTestUtil.PHONE_DESC_AMY;
import static seedu.equipment.logic.commands.CommandTestUtil.PHONE_DESC_BOB;
import static seedu.equipment.logic.commands.CommandTestUtil.SERIAL_NUMBER_DESC_AMY;
import static seedu.equipment.logic.commands.CommandTestUtil.SERIAL_NUMBER_DESC_BOB;
import static seedu.equipment.logic.commands.CommandTestUtil.TAG_DESC_FRIEND;
import static seedu.equipment.logic.commands.CommandTestUtil.TAG_DESC_HUSBAND;
import static seedu.equipment.logic.commands.CommandTestUtil.VALID_ADDRESS_BOB;
import static seedu.equipment.logic.commands.CommandTestUtil.VALID_EMAIL_BOB;
//import static seedu.equipment.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static seedu.equipment.logic.commands.CommandTestUtil.VALID_PHONE_BOB;
import static seedu.equipment.logic.commands.CommandTestUtil.VALID_SERIAL_NUMBER_BOB;
import static seedu.equipment.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.equipment.testutil.TypicalEquipments.ANCHORVALECC;
import static seedu.equipment.testutil.TypicalEquipments.AMY;
import static seedu.equipment.testutil.TypicalEquipments.BOB;
import static seedu.equipment.testutil.TypicalEquipments.HOON;
import static seedu.equipment.testutil.TypicalEquipments.IDA;
import static seedu.equipment.testutil.TypicalEquipments.KEYWORD_MATCHING_CC;
import static seedu.equipment.testutil.TypicalEquipments.TECKGHEECC;

import org.junit.Test;

import seedu.equipment.commons.core.Messages;
import seedu.equipment.commons.core.index.Index;
import seedu.equipment.logic.commands.AddCommand;
import seedu.equipment.logic.commands.RedoCommand;
import seedu.equipment.logic.commands.UndoCommand;
import seedu.equipment.model.Model;
//import Address;
//import Email;
import seedu.equipment.model.equipment.Equipment;
import seedu.equipment.model.equipment.Name;
//import Phone;
//import Tag;
import seedu.equipment.testutil.EquipmentBuilder;
import seedu.equipment.testutil.EquipmentUtil;

public class AddCommandSystemTest extends EquipmentManagerSystemTest {

    @Test
    public void add() {
        Model model = getModel();

        /* ------------------------ Perform add operations on the shown unfiltered list ----------------------------- */

        /* Case: add a equipment without tags to a non-empty equipment book, command with leading spaces
         * and trailing spaces -> added
         */
        Equipment toAdd = AMY;
        String command = " " + AddCommand.COMMAND_WORD + "  " + NAME_DESC_AMY + "  " + PHONE_DESC_AMY + " "
                + EMAIL_DESC_AMY + "   " + ADDRESS_DESC_AMY + "   " + SERIAL_NUMBER_DESC_AMY + "   " + TAG_DESC_FRIEND
                + " ";
        assertCommandSuccess(command, toAdd);

        /* Case: undo adding Amy to the list -> Amy deleted */
        command = UndoCommand.COMMAND_WORD;
        String expectedResultMessage = UndoCommand.MESSAGE_SUCCESS;
        assertCommandSuccess(command, model, expectedResultMessage);

        /* Case: redo adding Amy to the list -> Amy added again */
        command = RedoCommand.COMMAND_WORD;
        model.addPerson(toAdd);
        expectedResultMessage = RedoCommand.MESSAGE_SUCCESS;
        assertCommandSuccess(command, model, expectedResultMessage);

        /* Case: add an equipment with all fields same as another equipment in the equipment manager
         * except serial number -> added */
        toAdd = new EquipmentBuilder(AMY).withSerialNumber(VALID_SERIAL_NUMBER_BOB).build();
        command = AddCommand.COMMAND_WORD + NAME_DESC_AMY + PHONE_DESC_AMY + EMAIL_DESC_AMY + ADDRESS_DESC_AMY
                + SERIAL_NUMBER_DESC_BOB + TAG_DESC_FRIEND;
        assertCommandSuccess(command, toAdd);

        /* Case: add to empty equipment book -> added */
        deleteAllPersons();
        assertCommandSuccess(ANCHORVALECC);

        /* Case: add a equipment with tags, command with parameters in random order -> added */
        toAdd = BOB;
        command = AddCommand.COMMAND_WORD + TAG_DESC_FRIEND + PHONE_DESC_BOB + ADDRESS_DESC_BOB + NAME_DESC_BOB
                + SERIAL_NUMBER_DESC_BOB + TAG_DESC_HUSBAND + EMAIL_DESC_BOB;
        assertCommandSuccess(command, toAdd);

        /* Case: add a equipment, missing tags -> added */
        assertCommandSuccess(HOON);

        /* -------------------------- Perform add operation on the shown filtered list ------------------------------ */

        /* Case: filters the equipment list before adding -> added */
        showPersonsWithName(KEYWORD_MATCHING_CC);
        assertCommandSuccess(IDA);

        /* ------------------------ Perform add operation while a equipment card is selected ------------------------ */

        /* Case: selects first card in the equipment list, add a equipment -> added, card selection remains unchanged */
        selectPerson(Index.fromOneBased(1));
        assertCommandSuccess(TECKGHEECC);

        /* ----------------------------------- Perform invalid add operations --------------------------------------- */

        /* Case: add a duplicate equipment -> rejected */
        command = EquipmentUtil.getAddCommand(HOON);
        assertCommandFailure(command, AddCommand.MESSAGE_DUPLICATE_EQUIPMENT);

        /* Case: add a duplicate equipment except with different phone -> rejected */
        toAdd = new EquipmentBuilder(HOON).withPhone(VALID_PHONE_BOB).build();
        command = EquipmentUtil.getAddCommand(toAdd);
        assertCommandFailure(command, AddCommand.MESSAGE_DUPLICATE_EQUIPMENT);

        /* Case: add a duplicate equipment except with different email -> rejected */
        toAdd = new EquipmentBuilder(HOON).withEmail(VALID_EMAIL_BOB).build();
        command = EquipmentUtil.getAddCommand(toAdd);
        assertCommandFailure(command, AddCommand.MESSAGE_DUPLICATE_EQUIPMENT);

        /* Case: add a duplicate equipment except with different equipment -> rejected */
        toAdd = new EquipmentBuilder(HOON).withAddress(VALID_ADDRESS_BOB).build();
        command = EquipmentUtil.getAddCommand(toAdd);
        assertCommandFailure(command, AddCommand.MESSAGE_DUPLICATE_EQUIPMENT);

        /* Case: add a duplicate equipment except with different tags -> rejected */
        command = EquipmentUtil.getAddCommand(HOON) + " " + PREFIX_TAG.getPrefix() + "west";
        assertCommandFailure(command, AddCommand.MESSAGE_DUPLICATE_EQUIPMENT);

        /* Case: missing name -> rejected */
        command = AddCommand.COMMAND_WORD + PHONE_DESC_AMY + EMAIL_DESC_AMY + ADDRESS_DESC_AMY + SERIAL_NUMBER_DESC_AMY;
        assertCommandFailure(command, String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));

        /* Case: missing phone -> rejected */
        command = AddCommand.COMMAND_WORD + NAME_DESC_AMY + EMAIL_DESC_AMY + ADDRESS_DESC_AMY + SERIAL_NUMBER_DESC_AMY;
        assertCommandFailure(command, String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));

        /* Case: missing email -> rejected */
        command = AddCommand.COMMAND_WORD + NAME_DESC_AMY + PHONE_DESC_AMY + ADDRESS_DESC_AMY + SERIAL_NUMBER_DESC_AMY;
        assertCommandFailure(command, String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));

        /* Case: missing equipment -> rejected */
        command = AddCommand.COMMAND_WORD + NAME_DESC_AMY + PHONE_DESC_AMY + EMAIL_DESC_AMY + SERIAL_NUMBER_DESC_AMY;
        assertCommandFailure(command, String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));

        /* Case: missing serial number -> rejected */
        command = AddCommand.COMMAND_WORD + NAME_DESC_AMY + PHONE_DESC_AMY + EMAIL_DESC_AMY;
        assertCommandFailure(command, String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));

        /* Case: invalid keyword -> rejected */
        command = "adds " + EquipmentUtil.getPersonDetails(toAdd);
        assertCommandFailure(command, Messages.MESSAGE_UNKNOWN_COMMAND);

        /* Case: invalid name -> rejected */
        command = AddCommand.COMMAND_WORD + INVALID_NAME_DESC + PHONE_DESC_AMY + EMAIL_DESC_AMY
                + ADDRESS_DESC_AMY + SERIAL_NUMBER_DESC_AMY;
        assertCommandFailure(command, Name.MESSAGE_CONSTRAINTS);
    }

    /**
     * Executes the {@code AddCommand} that adds {@code toAdd} to the model and asserts that the,<br>
     * 1. Command box displays an empty string.<br>
     * 2. Command box has the default style class.<br>
     * 3. Result display box displays the success message of executing {@code AddCommand} with the details of
     * {@code toAdd}.<br>
     * 4. {@code Storage} and {@code EquipmentListPanel} equal to the corresponding components in
     * the current model added with {@code toAdd}.<br>
     * 5. Browser url and selected card remain unchanged.<br>
     * 6. Status bar's sync status changes.<br>
     * Verifications 1, 3 and 4 are performed by
     * {@code EquipmentManagerSystemTest#assertApplicationDisplaysExpected(String, String, Model)}.<br>
     * @see EquipmentManagerSystemTest#assertApplicationDisplaysExpected(String, String, Model)
     */
    private void assertCommandSuccess(Equipment toAdd) {
        assertCommandSuccess(EquipmentUtil.getAddCommand(toAdd), toAdd);
    }

    /**
     * Performs the same verification as {@code assertCommandSuccess(Equipment)}. Executes {@code command}
     * instead.
     * @see AddCommandSystemTest#assertCommandSuccess(Equipment)
     */
    private void assertCommandSuccess(String command, Equipment toAdd) {
        Model expectedModel = getModel();
        expectedModel.addPerson(toAdd);
        String expectedResultMessage = String.format(AddCommand.MESSAGE_SUCCESS, toAdd);

        assertCommandSuccess(command, expectedModel, expectedResultMessage);
    }

    /**
     * Performs the same verification as {@code assertCommandSuccess(String, Equipment)} except asserts that
     * the,<br>
     * 1. Result display box displays {@code expectedResultMessage}.<br>
     * 2. {@code Storage} and {@code EquipmentListPanel} equal to the corresponding components in
     * {@code expectedModel}.<br>
     * @see AddCommandSystemTest#assertCommandSuccess(String, Equipment)
     */
    private void assertCommandSuccess(String command, Model expectedModel, String expectedResultMessage) {
        executeCommand(command);
        assertApplicationDisplaysExpected("", expectedResultMessage, expectedModel);
        assertSelectedCardUnchanged();
        assertCommandBoxShowsDefaultStyle();
        assertStatusBarUnchangedExceptSyncStatus();
    }

    /**
     * Executes {@code command} and asserts that the,<br>
     * 1. Command box displays {@code command}.<br>
     * 2. Command box has the error style class.<br>
     * 3. Result display box displays {@code expectedResultMessage}.<br>
     * 4. {@code Storage} and {@code EquipmentListPanel} remain unchanged.<br>
     * 5. Browser url, selected card and status bar remain unchanged.<br>
     * Verifications 1, 3 and 4 are performed by
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
