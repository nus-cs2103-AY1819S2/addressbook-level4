package systemtests;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.ADDRESS_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.ADDRESS_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.AGE_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.AGE_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.GENDER_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.GENDER_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_ADDRESS_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_AGE_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_GENDER_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_NAME_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_PHONE_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_TAG_DESC;
import static seedu.address.logic.commands.CommandTestUtil.NAME_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.NAME_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.PHONE_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.PHONE_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.TAG_DESC_FRIEND;
import static seedu.address.logic.commands.CommandTestUtil.TAG_DESC_HUSBAND;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PATIENTS;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_PERSON;
import static seedu.address.testutil.TypicalPatients.AMY;
import static seedu.address.testutil.TypicalPatients.BOB;
import static seedu.address.testutil.TypicalPatients.KEYWORD_MATCHING_MEIER;

import org.junit.Test;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.EditPatientCommand;
import seedu.address.logic.commands.RedoCommand;
import seedu.address.logic.commands.UndoCommand;
import seedu.address.model.Model;
import seedu.address.model.person.Address;
import seedu.address.model.person.Age;
import seedu.address.model.person.Gender;
import seedu.address.model.person.Name;
import seedu.address.model.person.Patient;
import seedu.address.model.person.Phone;
import seedu.address.model.tag.Tag;
import seedu.address.testutil.PatientBuilder;
import seedu.address.testutil.PatientUtil;

public class EditPatientCommandSystemTest extends AddressBookSystemTest {

    @Test
    public void edit() {
        Model model = getModel();

        /* ----------------- Performing edit operation while an unfiltered list is being shown ---------------------- */

        /* Case: edit all fields, command with leading spaces, trailing spaces and multiple spaces between each field
         * -> edited
         */
        Index index = INDEX_FIRST_PERSON;
        String command = " " + EditPatientCommand.COMMAND_WORD + "  " + index.getOneBased() + "  "
                + NAME_DESC_BOB + "  "
                + GENDER_DESC_BOB + " " + AGE_DESC_BOB + " " + PHONE_DESC_BOB + " "
                + "  " + ADDRESS_DESC_BOB + " " + TAG_DESC_HUSBAND + " ";
        Patient editedPatient = new PatientBuilder(BOB).withTags(VALID_TAG_HUSBAND).build();
        assertCommandSuccess(command, index, editedPatient);

        /* Case: undo editing the last patient in the list -> last patient restored */
        command = UndoCommand.COMMAND_WORD;
        String expectedResultMessage = UndoCommand.MESSAGE_SUCCESS;
        assertCommandSuccess(command, model, expectedResultMessage);

        /* Case: redo editing the last patient in the list -> last patient edited again */
        command = RedoCommand.COMMAND_WORD;
        expectedResultMessage = RedoCommand.MESSAGE_SUCCESS;
        model.setPatient(getModel().getFilteredPatientList().get(INDEX_FIRST_PERSON.getZeroBased()), editedPatient);
        assertCommandSuccess(command, model, expectedResultMessage);

        /* Case: edit a patient with new values same as existing values -> edited */
        command = EditPatientCommand.COMMAND_WORD + " " + index.getOneBased() + NAME_DESC_BOB
                + GENDER_DESC_BOB + AGE_DESC_BOB + PHONE_DESC_BOB
                + ADDRESS_DESC_BOB + TAG_DESC_FRIEND + TAG_DESC_HUSBAND;
        assertCommandSuccess(command, index, BOB);

        /* Case: edit a patient with new values same as another patient's values but with different name -> edited */
        assertTrue(getModel().getAddressBook().getPatientList().contains(BOB));
        index = INDEX_SECOND_PERSON;
        assertNotEquals(getModel().getFilteredPatientList().get(index.getZeroBased()), BOB);
        command = EditPatientCommand.COMMAND_WORD + " " + index.getOneBased() + NAME_DESC_AMY
                + GENDER_DESC_BOB + AGE_DESC_BOB + PHONE_DESC_BOB
                + ADDRESS_DESC_BOB + TAG_DESC_FRIEND + TAG_DESC_HUSBAND;
        editedPatient = new PatientBuilder(BOB).withName(VALID_NAME_AMY).build();
        assertCommandSuccess(command, index, editedPatient);

        /* Case: edit a patient with new values same as another patient's values but with different phone
         * -> edited
         */
        index = INDEX_SECOND_PERSON;
        command = EditPatientCommand.COMMAND_WORD + " " + index.getOneBased() + NAME_DESC_BOB
                + GENDER_DESC_BOB + AGE_DESC_BOB + PHONE_DESC_AMY
                + ADDRESS_DESC_BOB + TAG_DESC_FRIEND + TAG_DESC_HUSBAND;
        editedPatient = new PatientBuilder(BOB).withPhone(VALID_PHONE_AMY).build();
        assertCommandSuccess(command, index, editedPatient);

        /* Case: clear tags -> cleared */
        index = INDEX_FIRST_PERSON;
        command = EditPatientCommand.COMMAND_WORD + " " + index.getOneBased() + " " + PREFIX_TAG.getPrefix();
        Patient patientToEdit = getModel().getFilteredPatientList().get(index.getZeroBased());
        editedPatient = new PatientBuilder(patientToEdit).withTags().build();
        assertCommandSuccess(command, index, editedPatient);

        /* ------------------ Performing edit operation while a filtered list is being shown ------------------------ */

        /* Case: filtered patient list, edit index within bounds of address book and patient list -> edited */
        showPatientsWithName(KEYWORD_MATCHING_MEIER);
        index = INDEX_FIRST_PERSON;
        assertTrue(index.getZeroBased() < getModel().getFilteredPatientList().size());
        command = EditPatientCommand.COMMAND_WORD + " " + index.getOneBased() + " " + NAME_DESC_BOB;
        patientToEdit = getModel().getFilteredPatientList().get(index.getZeroBased());
        editedPatient = new PatientBuilder(patientToEdit).withName(VALID_NAME_BOB).build();
        assertCommandSuccess(command, index, editedPatient);

        /* Case: filtered patient list, edit index within bounds of address book but out of bounds of patient list
         * -> rejected
         */
        showPatientsWithName(KEYWORD_MATCHING_MEIER);
        int invalidIndex = getModel().getAddressBook().getPatientList().size();
        assertCommandFailure(EditPatientCommand.COMMAND_WORD + " " + invalidIndex + NAME_DESC_BOB,
                Messages.MESSAGE_INVALID_PATIENT_DISPLAYED_INDEX);

        /* --------------------- Performing edit operation while a patient card is selected ------------------------- */

        /* Case: selects first card in the patient list, edit a patient -> edited, card selection remains unchanged but
         * browser url changes
         */
        showAllPatients();
        index = INDEX_FIRST_PERSON;
        selectPatient(index);
        command = EditPatientCommand.COMMAND_WORD + " " + index.getOneBased() + NAME_DESC_AMY
                + GENDER_DESC_AMY + AGE_DESC_AMY + PHONE_DESC_AMY
                + ADDRESS_DESC_AMY + TAG_DESC_FRIEND;
        // this can be misleading: card selection actually remains unchanged but the
        // browser's url is updated to reflect the new patient's name
        assertCommandSuccess(command, index, AMY, index);

        /* --------------------------------- Performing invalid edit operation -------------------------------------- */

        /* Case: invalid index (0) -> rejected */
        assertCommandFailure(EditPatientCommand.COMMAND_WORD + " 0" + NAME_DESC_BOB,
                String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT, EditPatientCommand.MESSAGE_USAGE));

        /* Case: invalid index (-1) -> rejected */
        assertCommandFailure(EditPatientCommand.COMMAND_WORD + " -1" + NAME_DESC_BOB,
                String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT, EditPatientCommand.MESSAGE_USAGE));

        /* Case: invalid index (size + 1) -> rejected */
        invalidIndex = getModel().getFilteredPatientList().size() + 1;
        assertCommandFailure(EditPatientCommand.COMMAND_WORD + " " + invalidIndex + NAME_DESC_BOB,
                Messages.MESSAGE_INVALID_PATIENT_DISPLAYED_INDEX);

        /* Case: missing index -> rejected */
        assertCommandFailure(EditPatientCommand.COMMAND_WORD + NAME_DESC_BOB,
                String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT, EditPatientCommand.MESSAGE_USAGE));

        /* Case: missing all fields -> rejected */
        assertCommandFailure(EditPatientCommand.COMMAND_WORD + " " + INDEX_FIRST_PERSON.getOneBased(),
                EditPatientCommand.MESSAGE_NOT_EDITED);

        /* Case: invalid name -> rejected */
        assertCommandFailure(EditPatientCommand.COMMAND_WORD + " "
                        + INDEX_FIRST_PERSON.getOneBased() + INVALID_NAME_DESC,
                Name.MESSAGE_CONSTRAINTS);

        /* Case: invalid gender -> rejected */
        assertCommandFailure(EditPatientCommand.COMMAND_WORD + " "
                        + INDEX_FIRST_PERSON.getOneBased() + INVALID_GENDER_DESC,
                Gender.MESSAGE_CONSTRAINTS);

        /* Case: invalid age -> rejected */
        assertCommandFailure(EditPatientCommand.COMMAND_WORD + " "
                        + INDEX_FIRST_PERSON.getOneBased() + INVALID_AGE_DESC,
                Age.MESSAGE_CONSTRAINTS);

        /* Case: invalid phone -> rejected */
        assertCommandFailure(EditPatientCommand.COMMAND_WORD + " "
                        + INDEX_FIRST_PERSON.getOneBased() + INVALID_PHONE_DESC,
                Phone.MESSAGE_CONSTRAINTS);

        /* Case: invalid address -> rejected */
        assertCommandFailure(EditPatientCommand.COMMAND_WORD + " "
                        + INDEX_FIRST_PERSON.getOneBased() + INVALID_ADDRESS_DESC,
                Address.MESSAGE_CONSTRAINTS);

        /* Case: invalid tag -> rejected */
        assertCommandFailure(EditPatientCommand.COMMAND_WORD + " "
                        + INDEX_FIRST_PERSON.getOneBased() + INVALID_TAG_DESC,
                Tag.MESSAGE_CONSTRAINTS);

        /* Case: edit a patient with new values same as another patient's values -> rejected */
        executeCommand(PatientUtil.getAddPatientCommand(BOB));
        assertTrue(getModel().getAddressBook().getPatientList().contains(BOB));
        index = INDEX_FIRST_PERSON;
        assertFalse(getModel().getFilteredPatientList().get(index.getZeroBased()).equals(BOB));
        command = EditPatientCommand.COMMAND_WORD + " " + index.getOneBased() + NAME_DESC_BOB
                + GENDER_DESC_BOB + AGE_DESC_BOB + PHONE_DESC_BOB
                + ADDRESS_DESC_BOB + TAG_DESC_FRIEND + TAG_DESC_HUSBAND;
        assertCommandFailure(command, EditPatientCommand.MESSAGE_DUPLICATE_PATIENT);

        /* Case: edit a patient with new values same as another patient's values
        but with different gender -> rejected */
        command = EditPatientCommand.COMMAND_WORD + " " + index.getOneBased() + NAME_DESC_BOB
                + GENDER_DESC_AMY + AGE_DESC_BOB + PHONE_DESC_BOB
                + ADDRESS_DESC_BOB + TAG_DESC_FRIEND + TAG_DESC_HUSBAND;
        assertCommandFailure(command, EditPatientCommand.MESSAGE_DUPLICATE_PATIENT);

        /* Case: edit a patient with new values same as another patient's values but with different age -> rejected */
        command = EditPatientCommand.COMMAND_WORD + " " + index.getOneBased() + NAME_DESC_BOB
                + GENDER_DESC_BOB + AGE_DESC_AMY + PHONE_DESC_BOB
                + ADDRESS_DESC_BOB + TAG_DESC_FRIEND + TAG_DESC_HUSBAND;
        assertCommandFailure(command, EditPatientCommand.MESSAGE_DUPLICATE_PATIENT);

        /* Case: edit a patient with new values same as another patient's values but with different phone -> rejected */
        command = EditPatientCommand.COMMAND_WORD + " " + index.getOneBased() + NAME_DESC_BOB
                + GENDER_DESC_BOB + AGE_DESC_BOB + PHONE_DESC_AMY
                + ADDRESS_DESC_BOB + TAG_DESC_FRIEND + TAG_DESC_HUSBAND;
        assertCommandFailure(command, EditPatientCommand.MESSAGE_DUPLICATE_PATIENT);

        /* Case: edit a patient with new values same as another patient's values but with different tags -> rejected */
        command = EditPatientCommand.COMMAND_WORD + " " + index.getOneBased() + NAME_DESC_BOB
                + GENDER_DESC_BOB + AGE_DESC_BOB + PHONE_DESC_BOB
                + ADDRESS_DESC_BOB + TAG_DESC_HUSBAND;
        assertCommandFailure(command, EditPatientCommand.MESSAGE_DUPLICATE_PATIENT);

        /* Case: edit a patient with new values same as another patient's values
         but with different address -> rejected */
        command = EditPatientCommand.COMMAND_WORD + " " + index.getOneBased() + NAME_DESC_BOB
                + GENDER_DESC_BOB + AGE_DESC_BOB + PHONE_DESC_BOB
                + ADDRESS_DESC_AMY + TAG_DESC_FRIEND + TAG_DESC_HUSBAND;
        assertCommandFailure(command, EditPatientCommand.MESSAGE_DUPLICATE_PATIENT);
    }

    /**
     * Performs the same verification as {@code assertCommandSuccess(String, Index, Patient, Index)} except that
     * the browser url and selected card remain unchanged.
     *
     * @param toEdit the index of the current model's filtered list
     * @see EditPatientCommandSystemTest#assertCommandSuccess(String, Index, Patient, Index)
     */
    private void assertCommandSuccess(String command, Index toEdit, Patient editedPatient) {
        assertCommandSuccess(command, toEdit, editedPatient, null);
    }

    /**
     * Performs the same verification as {@code assertCommandSuccess(String, Model, String, Index)} and in addition,<br>
     * 1. Asserts that result display box displays the success message of executing {@code EditPatientCommand}.<br>
     * 2. Asserts that the model related components are updated to reflect the patient at index {@code toEdit} being
     * updated to values specified {@code editedPatient}.<br>
     *
     * @param toEdit the index of the current model's filtered list.
     * @see EditPatientCommandSystemTest#assertCommandSuccess(String, Model, String, Index)
     */
    private void assertCommandSuccess(String command, Index toEdit, Patient editedPatient,
                                      Index expectedSelectedCardIndex) {
        Model expectedModel = getModel();
        expectedModel.setPatient(expectedModel.getFilteredPatientList().get(toEdit.getZeroBased()), editedPatient);
        expectedModel.updateFilteredPatientList(PREDICATE_SHOW_ALL_PATIENTS);

        assertCommandSuccess(command, expectedModel,
                String.format(EditPatientCommand.MESSAGE_EDIT_PATIENT_SUCCESS,
                        editedPatient), expectedSelectedCardIndex);
    }

    /**
     * Performs the same verification as {@code assertCommandSuccess(String, Model, String, Index)} except that the
     * browser url and selected card remain unchanged.
     *
     * @see EditPatientCommandSystemTest#assertCommandSuccess(String, Model, String, Index)
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
     * {@code AddressBookSystemTest#assertApplicationDisplaysExpected(String, String, Model)}.<br>
     *
     * @see AddressBookSystemTest#assertApplicationDisplaysExpected(String, String, Model)
     * @see AddressBookSystemTest#assertSelectedCardChanged(Index)
     */
    private void assertCommandSuccess(String command, Model expectedModel, String expectedResultMessage,
                                      Index expectedSelectedCardIndex) {
        executeCommand(command);
        expectedModel.updateFilteredPatientList(PREDICATE_SHOW_ALL_PATIENTS);
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
     * {@code AddressBookSystemTest#assertApplicationDisplaysExpected(String, String, Model)}.<br>
     *
     * @see AddressBookSystemTest#assertApplicationDisplaysExpected(String, String, Model)
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
