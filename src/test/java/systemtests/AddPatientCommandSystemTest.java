package systemtests;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
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
import static seedu.address.logic.commands.CommandTestUtil.VALID_ADDRESS_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_AGE_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_GENDER_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_BOB;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.address.testutil.TypicalPatients.ALICE;
import static seedu.address.testutil.TypicalPatients.AMY;
import static seedu.address.testutil.TypicalPatients.BOB;
import static seedu.address.testutil.TypicalPatients.CARL;
import static seedu.address.testutil.TypicalPatients.HOON;
import static seedu.address.testutil.TypicalPatients.IDA;
import static seedu.address.testutil.TypicalPatients.KEYWORD_MATCHING_MEIER;

import org.junit.Test;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.AddPatientCommand;
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

public class AddPatientCommandSystemTest extends AddressBookSystemTest {

    @Test
    public void add() {
        Model model = getModel();

        /* ------------------------ Perform add operations on the shown unfiltered list ----------------------------- */

        /* Case: add a patient without tags to a non-empty address book, command with leading spaces and trailing spaces
         * -> added
         */
        Patient toAdd = AMY;
        String command = "   " + AddPatientCommand.COMMAND_WORD + "  " + NAME_DESC_AMY
                + " " + GENDER_DESC_AMY + " " + AGE_DESC_AMY + "  " + PHONE_DESC_AMY + " "
                + "   " + ADDRESS_DESC_AMY + "   " + TAG_DESC_FRIEND + " ";
        assertCommandSuccess(command, toAdd);

        /* Case: undo adding Amy to the list -> Amy deleted */
        command = UndoCommand.COMMAND_WORD;
        String expectedResultMessage = UndoCommand.MESSAGE_SUCCESS;
        assertCommandSuccess(command, model, expectedResultMessage);

        /* Case: redo adding Amy to the list -> Amy added again */
        command = RedoCommand.COMMAND_WORD;
        model.addPatient(toAdd);
        expectedResultMessage = RedoCommand.MESSAGE_SUCCESS;
        assertCommandSuccess(command, model, expectedResultMessage);

        /* Case: add a patient with all fields same as another patient in the address book except name -> added */
        toAdd = new PatientBuilder(AMY).withName(VALID_NAME_BOB).build();
        command = AddPatientCommand.COMMAND_WORD + NAME_DESC_BOB
                + GENDER_DESC_AMY + AGE_DESC_AMY + PHONE_DESC_AMY
                + ADDRESS_DESC_AMY
                + TAG_DESC_FRIEND;
        assertCommandSuccess(command, toAdd);

        /* Case: add a patient with all fields same as another patient in the address book except phone
         * -> added
         */
        toAdd = new PatientBuilder(AMY).withPhone(VALID_PHONE_BOB).build();
        command = PatientUtil.getAddPatientCommand(toAdd);
        assertCommandSuccess(command, toAdd);

        /* Case: add to empty address book -> added */
        deleteAllPatients();
        assertCommandSuccess(ALICE);

        /* Case: add a patient with tags, command with parameters in random order -> added */
        toAdd = BOB;
        command = AddPatientCommand.COMMAND_WORD + TAG_DESC_FRIEND + PHONE_DESC_BOB + ADDRESS_DESC_BOB + NAME_DESC_BOB
                + TAG_DESC_HUSBAND + GENDER_DESC_BOB + AGE_DESC_BOB;
        assertCommandSuccess(command, toAdd);

        /* Case: add a patient, missing tags -> added */
        assertCommandSuccess(HOON);

        /* -------------------------- Perform add operation on the shown filtered list ------------------------------ */

        /* Case: filters the patient list before adding -> added */
        showPatientsWithName(KEYWORD_MATCHING_MEIER);
        assertCommandSuccess(IDA);

        /* ------------------------ Perform add operation while a patient card is selected -------------------------- */

        /* Case: selects first card in the patient list, add a patient -> added, card selection remains unchanged */
        selectPatient(Index.fromOneBased(1));
        assertCommandSuccess(CARL);

        /* ----------------------------------- Perform invalid add operations --------------------------------------- */

        /* Case: add a duplicate patient -> rejected */
        command = PatientUtil.getAddPatientCommand(HOON);
        assertCommandFailure(command, AddPatientCommand.MESSAGE_DUPLICATE_PATIENT);

        /* Case: add a duplicate patient except with different gender -> rejected */
        toAdd = new PatientBuilder(HOON).withGender(VALID_GENDER_BOB).build();
        command = PatientUtil.getAddPatientCommand(toAdd);
        assertCommandFailure(command, AddPatientCommand.MESSAGE_DUPLICATE_PATIENT);

        /* Case: add a duplicate patient except with different age -> rejected */
        toAdd = new PatientBuilder(HOON).withAge(VALID_AGE_BOB).build();
        command = PatientUtil.getAddPatientCommand(toAdd);
        assertCommandFailure(command, AddPatientCommand.MESSAGE_DUPLICATE_PATIENT);

        /* Case: add a duplicate patient except with different address -> rejected */
        toAdd = new PatientBuilder(HOON).withAddress(VALID_ADDRESS_BOB).build();
        command = PatientUtil.getAddPatientCommand(toAdd);
        assertCommandFailure(command, AddPatientCommand.MESSAGE_DUPLICATE_PATIENT);

        /* Case: add a duplicate patient except with different tags -> rejected */
        command = PatientUtil.getAddPatientCommand(HOON) + " " + PREFIX_TAG.getPrefix() + "friends";
        assertCommandFailure(command, AddPatientCommand.MESSAGE_DUPLICATE_PATIENT);

        /* Case: missing name -> rejected */
        command = AddPatientCommand.COMMAND_WORD + GENDER_DESC_AMY + AGE_DESC_AMY + PHONE_DESC_AMY + ADDRESS_DESC_AMY;
        assertCommandFailure(command, String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddPatientCommand.MESSAGE_USAGE));

        /* Case: missing gender -> rejected */
        command = AddPatientCommand.COMMAND_WORD + NAME_DESC_AMY + AGE_DESC_AMY + PHONE_DESC_AMY + ADDRESS_DESC_AMY;
        assertCommandFailure(command, String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddPatientCommand.MESSAGE_USAGE));

        /* Case: missing age -> rejected */
        command = AddPatientCommand.COMMAND_WORD + NAME_DESC_AMY + GENDER_DESC_AMY + PHONE_DESC_AMY + ADDRESS_DESC_AMY;
        assertCommandFailure(command, String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddPatientCommand.MESSAGE_USAGE));

        /* Case: missing phone -> rejected */
        command = AddPatientCommand.COMMAND_WORD + NAME_DESC_AMY + GENDER_DESC_AMY + AGE_DESC_AMY + ADDRESS_DESC_AMY;
        assertCommandFailure(command, String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddPatientCommand.MESSAGE_USAGE));

        /* Case: missing address -> rejected */
        command = AddPatientCommand.COMMAND_WORD + NAME_DESC_AMY + GENDER_DESC_AMY + AGE_DESC_AMY + PHONE_DESC_AMY;
        assertCommandFailure(command, String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddPatientCommand.MESSAGE_USAGE));

        /* Case: invalid keyword -> rejected */
        command = "adds " + PatientUtil.getPatientDetails(toAdd);
        assertCommandFailure(command, Messages.MESSAGE_UNKNOWN_COMMAND);

        /* Case: invalid name -> rejected */
        command = AddPatientCommand.COMMAND_WORD + INVALID_NAME_DESC
                + GENDER_DESC_AMY + AGE_DESC_AMY + PHONE_DESC_AMY + ADDRESS_DESC_AMY;
        assertCommandFailure(command, Name.MESSAGE_CONSTRAINTS);

        /* Case: invalid gender -> rejected */
        command = AddPatientCommand.COMMAND_WORD + NAME_DESC_AMY
                + INVALID_GENDER_DESC + AGE_DESC_AMY + PHONE_DESC_AMY + ADDRESS_DESC_AMY;
        assertCommandFailure(command, Gender.MESSAGE_CONSTRAINTS);

        /* Case: invalid age -> rejected */
        command = AddPatientCommand.COMMAND_WORD + NAME_DESC_AMY
                + GENDER_DESC_AMY + INVALID_AGE_DESC + PHONE_DESC_AMY + ADDRESS_DESC_AMY;
        assertCommandFailure(command, Age.MESSAGE_CONSTRAINTS);

        /* Case: invalid phone -> rejected */
        command = AddPatientCommand.COMMAND_WORD + NAME_DESC_AMY
                + GENDER_DESC_AMY + AGE_DESC_AMY + INVALID_PHONE_DESC + ADDRESS_DESC_AMY;
        assertCommandFailure(command, Phone.MESSAGE_CONSTRAINTS);

        /* Case: invalid address -> rejected */
        command = AddPatientCommand.COMMAND_WORD + NAME_DESC_AMY
                + GENDER_DESC_AMY + AGE_DESC_AMY + PHONE_DESC_AMY + INVALID_ADDRESS_DESC;
        assertCommandFailure(command, Address.MESSAGE_CONSTRAINTS);

        /* Case: invalid tag -> rejected */
        command = AddPatientCommand.COMMAND_WORD + NAME_DESC_AMY
                + GENDER_DESC_AMY + AGE_DESC_AMY + PHONE_DESC_AMY + ADDRESS_DESC_AMY
                + INVALID_TAG_DESC;
        assertCommandFailure(command, Tag.MESSAGE_CONSTRAINTS);
    }

    /**
     * Executes the {@code AddPatientCommand} that adds {@code toAdd} to the model and asserts that the,<br>
     * 1. Command box displays an empty string.<br>
     * 2. Command box has the default style class.<br>
     * 3. Result display box displays the success message of executing {@code AddPatientCommand} with the details of
     * {@code toAdd}.<br>
     * 4. {@code Storage} and {@code PatientListPanel} equal to the corresponding components in
     * the current model added with {@code toAdd}.<br>
     * 5. Browser url and selected card remain unchanged.<br>
     * 6. Status bar's sync status changes.<br>
     * Verifications 1, 3 and 4 are performed by
     * {@code AddressBookSystemTest#assertApplicationDisplaysExpected(String, String, Model)}.<br>
     *
     * @see AddressBookSystemTest#assertApplicationDisplaysExpected(String, String, Model)
     */
    private void assertCommandSuccess(Patient toAdd) {
        assertCommandSuccess(PatientUtil.getAddPatientCommand(toAdd), toAdd);
    }

    /**
     * Performs the same verification as {@code assertCommandSuccess(Patient)}. Executes {@code command}
     * instead.
     *
     * @see AddPatientCommandSystemTest#assertCommandSuccess(Patient)
     */
    private void assertCommandSuccess(String command, Patient toAdd) {
        Model expectedModel = getModel();
        expectedModel.addPatient(toAdd);
        String expectedResultMessage = String.format(AddPatientCommand.MESSAGE_SUCCESS, toAdd);

        assertCommandSuccess(command, expectedModel, expectedResultMessage);
    }

    /**
     * Performs the same verification as {@code assertCommandSuccess(String, Patient)} except asserts that
     * the,<br>
     * 1. Result display box displays {@code expectedResultMessage}.<br>
     * 2. {@code Storage} and {@code PatientListPanel} equal to the corresponding components in
     * {@code expectedModel}.<br>
     *
     * @see AddPatientCommandSystemTest#assertCommandSuccess(String, Patient)
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
     * 4. {@code Storage} and {@code PatientListPanel} remain unchanged.<br>
     * 5. Browser url, selected card and status bar remain unchanged.<br>
     * Verifications 1, 3 and 4 are performed by
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
