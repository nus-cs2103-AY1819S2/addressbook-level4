package systemtests;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.GENDER_DESC_ALVINA;
import static seedu.address.logic.commands.CommandTestUtil.GENDER_DESC_STEVEN;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_GENDER_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_NAME_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_PHONE_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_SPECIALISATION_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_YEAR_DESC;
import static seedu.address.logic.commands.CommandTestUtil.NAME_DESC_ALVINA;
import static seedu.address.logic.commands.CommandTestUtil.NAME_DESC_STEVEN;
import static seedu.address.logic.commands.CommandTestUtil.PHONE_DESC_ALVINA;
import static seedu.address.logic.commands.CommandTestUtil.PHONE_DESC_STEVEN;
import static seedu.address.logic.commands.CommandTestUtil.SPECIALISATION_DESC_ACUPUNCTURE;
import static seedu.address.logic.commands.CommandTestUtil.SPECIALISATION_DESC_GENERAL;
import static seedu.address.logic.commands.CommandTestUtil.SPECIALISATION_DESC_MASSAGE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_GENDER_ALVINA;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_ALVINA;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_STEVEN;
import static seedu.address.logic.commands.CommandTestUtil.VALID_YEAR_STEVEN;
import static seedu.address.logic.commands.CommandTestUtil.YEAR_DESC_ALVINA;
import static seedu.address.logic.commands.CommandTestUtil.YEAR_DESC_STEVEN;
import static seedu.address.logic.parser.CliSyntax.PREFIX_SPECIALISATION;
import static seedu.address.testutil.TypicalDoctors.ALVINA;
import static seedu.address.testutil.TypicalDoctors.CHARLIE;
import static seedu.address.testutil.TypicalDoctors.ILLIOT;
import static seedu.address.testutil.TypicalDoctors.STEVEN;
import static seedu.address.testutil.TypicalPatients.KEYWORD_MATCHING_MEIER;

import org.junit.Test;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.doctor.AddDoctorCommand;
import seedu.address.model.Model;
import seedu.address.model.person.Gender;
import seedu.address.model.person.Name;
import seedu.address.model.person.Phone;
import seedu.address.model.person.doctor.Doctor;
import seedu.address.model.person.doctor.Year;
import seedu.address.model.person.specialisation.Specialisation;
import seedu.address.testutil.DoctorBuilder;
import seedu.address.testutil.DoctorUtil;

public class AddDoctorCommandSystemTest extends DocXSystemTest {

    @Test
    public void add() {
        Model model = getModel();

        /* ----------------------- Perform add operations on the shown unfiltered list ---------------------------- */

        /* Case: add a doctor to a non-empty docX, command
         * with leading spaces and trailing spaces
         * -> added
         */
        Doctor toAdd = ALVINA;
        String command = "   " + AddDoctorCommand.COMMAND_WORD + "  " + NAME_DESC_ALVINA
                + " " + GENDER_DESC_ALVINA + " " + YEAR_DESC_ALVINA + "  " + PHONE_DESC_ALVINA + " "
                + SPECIALISATION_DESC_ACUPUNCTURE + " ";
        assertCommandSuccess(command, toAdd);

        /* Case: add a doctor with all fields same as another doctor in docX except phone -> added */
        toAdd = new DoctorBuilder(ALVINA).withPhone(VALID_PHONE_STEVEN).build();
        command = AddDoctorCommand.COMMAND_WORD + NAME_DESC_ALVINA
                + GENDER_DESC_ALVINA + YEAR_DESC_ALVINA + PHONE_DESC_STEVEN
                + SPECIALISATION_DESC_ACUPUNCTURE;
        assertCommandSuccess(command, toAdd);

        /* Case: add to empty docX -> added */
        deleteAllDoctors();
        assertCommandSuccess(ALVINA);

        /* Case: add a doctor with specialisations, command with parameters in random order -> added */
        toAdd = STEVEN;
        command = AddDoctorCommand.COMMAND_WORD + SPECIALISATION_DESC_ACUPUNCTURE + PHONE_DESC_STEVEN + NAME_DESC_STEVEN
                + GENDER_DESC_STEVEN + YEAR_DESC_STEVEN + SPECIALISATION_DESC_MASSAGE;
        assertCommandSuccess(command, toAdd);

        /* ------------------------- Perform add operation on the shown filtered list ----------------------------- */

        /* Case: filters the doctor list before adding -> added */
        showDoctorsWithName(KEYWORD_MATCHING_MEIER);
        assertCommandSuccess(ILLIOT);

        /* ----------------------- Perform add operation while a doctor card is selected ------------------------- */

        /* Case: selects first card in the doctor list, add a doctor -> added, card selection remains unchanged */
        selectDoctor(Index.fromOneBased(1));
        assertCommandSuccess(CHARLIE);

        /* ---------------------------------- Perform invalid add operations -------------------------------------- */

        /* Case: add a duplicate doctor -> rejected */
        command = DoctorUtil.getAddDoctorCommand(ILLIOT);
        assertCommandFailure(command, AddDoctorCommand.MESSAGE_DUPLICATE_DOCTOR);

        /* Case: add a duplicate doctor except with different name -> rejected */
        toAdd = new DoctorBuilder(ILLIOT).withName(VALID_NAME_ALVINA).build();
        command = DoctorUtil.getAddDoctorCommand(toAdd);
        assertCommandFailure(command, AddDoctorCommand.MESSAGE_DUPLICATE_DOCTOR);

        /* Case: add a duplicate doctor except with different gender -> rejected */
        toAdd = new DoctorBuilder(ILLIOT).withGender(VALID_GENDER_ALVINA).build();
        command = DoctorUtil.getAddDoctorCommand(toAdd);
        assertCommandFailure(command, AddDoctorCommand.MESSAGE_DUPLICATE_DOCTOR);

        /* Case: add a duplicate doctor except with different year of experience -> rejected */
        toAdd = new DoctorBuilder(ILLIOT).withYear(VALID_YEAR_STEVEN).build();
        command = DoctorUtil.getAddDoctorCommand(toAdd);
        assertCommandFailure(command, AddDoctorCommand.MESSAGE_DUPLICATE_DOCTOR);

        /* Case: add a duplicate doctor except with different specialisations -> rejected */
        command = DoctorUtil.getAddDoctorCommand(ILLIOT
        ) + " " + PREFIX_SPECIALISATION.getPrefix() + "general";
        assertCommandFailure(command, AddDoctorCommand.MESSAGE_DUPLICATE_DOCTOR);

        /* Case: missing name -> rejected */
        command = AddDoctorCommand.COMMAND_WORD + GENDER_DESC_ALVINA + YEAR_DESC_ALVINA + PHONE_DESC_ALVINA;
        assertCommandFailure(command, String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddDoctorCommand.MESSAGE_USAGE));

        /* Case: missing gender -> rejected */
        command = AddDoctorCommand.COMMAND_WORD + NAME_DESC_ALVINA + YEAR_DESC_ALVINA + PHONE_DESC_ALVINA;
        assertCommandFailure(command, String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddDoctorCommand.MESSAGE_USAGE));

        /* Case: missing year of experience -> rejected */
        command = AddDoctorCommand.COMMAND_WORD + NAME_DESC_ALVINA + GENDER_DESC_ALVINA + PHONE_DESC_ALVINA;
        assertCommandFailure(command, String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddDoctorCommand.MESSAGE_USAGE));

        /* Case: missing phone -> rejected */
        command = AddDoctorCommand.COMMAND_WORD + NAME_DESC_ALVINA + GENDER_DESC_ALVINA + YEAR_DESC_ALVINA;
        assertCommandFailure(command, String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddDoctorCommand.MESSAGE_USAGE));

        /* Case: invalid keyword -> rejected */
        command = "adds " + DoctorUtil.getDoctorDetails(toAdd);
        assertCommandFailure(command, Messages.MESSAGE_UNKNOWN_COMMAND);

        /* Case: invalid name -> rejected */
        command = AddDoctorCommand.COMMAND_WORD + INVALID_NAME_DESC
                + GENDER_DESC_ALVINA + YEAR_DESC_ALVINA + PHONE_DESC_ALVINA + SPECIALISATION_DESC_ACUPUNCTURE;
        assertCommandFailure(command, Name.MESSAGE_CONSTRAINTS);

        /* Case: invalid gender -> rejected */
        command = AddDoctorCommand.COMMAND_WORD + NAME_DESC_ALVINA
                + INVALID_GENDER_DESC + YEAR_DESC_ALVINA + PHONE_DESC_ALVINA + SPECIALISATION_DESC_MASSAGE;
        assertCommandFailure(command, Gender.MESSAGE_CONSTRAINTS);

        /* Case: invalid year of experience -> rejected */
        command = AddDoctorCommand.COMMAND_WORD + NAME_DESC_ALVINA
                + GENDER_DESC_ALVINA + INVALID_YEAR_DESC + PHONE_DESC_ALVINA + SPECIALISATION_DESC_GENERAL;
        assertCommandFailure(command, Year.MESSAGE_CONSTRAINTS);

        /* Case: invalid phone -> rejected */
        command = AddDoctorCommand.COMMAND_WORD + NAME_DESC_ALVINA
                + GENDER_DESC_ALVINA + YEAR_DESC_ALVINA + INVALID_PHONE_DESC + SPECIALISATION_DESC_MASSAGE;
        assertCommandFailure(command, Phone.MESSAGE_CONSTRAINTS);

        /* Case: invalid specialisation -> rejected */
        command = AddDoctorCommand.COMMAND_WORD + NAME_DESC_ALVINA
                + GENDER_DESC_ALVINA + YEAR_DESC_ALVINA + PHONE_DESC_ALVINA
                + INVALID_SPECIALISATION_DESC;
        assertCommandFailure(command, Specialisation.MESSAGE_CONSTRAINTS);

    }

    /**
     * Executes the {@code AddDoctorCommand} that adds {@code toAdd} to the model and asserts that the,<br>
     * 1. Command box displays an empty string.<br>
     * 2. Command box has the default style class.<br>
     * 3. Result display box displays the success message of executing {@code AddDoctorCommand} with the details of
     * {@code toAdd}.<br>
     * 4. {@code Storage} and {@code DoctorListPanel} equal to the corresponding components in
     * the current model added with {@code toAdd}.<br>
     * 5. Browser url and selected card remain unchanged.<br>
     * 6. Status bar's sync status changes.<br>
     * Verifications 1, 3 and 4 are performed by
     * {@code DocXSystemTest#assertApplicationDisplaysExpected(String, String, Model)}.<br>
     *
     * @see DocXSystemTest#assertApplicationDisplaysExpected(String, String, Model)
     */
    private void assertCommandSuccess(Doctor toAdd) {
        assertCommandSuccess(DoctorUtil.getAddDoctorCommand(toAdd), toAdd);
    }

    /**
     * Performs the same verification as {@code assertCommandSuccess(Doctor)}. Executes {@code command}
     * instead.
     *
     * @see AddDoctorCommandSystemTest#assertCommandSuccess(Doctor)
     */

    private void assertCommandSuccess(String command, Doctor toAdd) {
        Model expectedModel = getModel();
        expectedModel.addDoctor(toAdd);

        String expectedResultMessage = String.format(AddDoctorCommand.MESSAGE_SUCCESS, toAdd);

        assertCommandSuccess(command, expectedModel, expectedResultMessage);
    }

    /**
     * Performs the same verification as {@code assertCommandSuccess(String, Doctor)} except asserts that
     * the,<br>
     * 1. Result display box displays {@code expectedResultMessage}.<br>
     * 2. {@code Storage} and {@code DoctorListPanel} equal to the corresponding components in
     * {@code expectedModel}.<br>
     *
     * @see AddDoctorCommandSystemTest#assertCommandSuccess(String, Doctor)
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
     * 4. {@code Storage} and {@code DoctorListPanel} remain unchanged.<br>
     * 5. Browser url, selected card and status bar remain unchanged.<br>
     * Verifications 1, 3 and 4 are performed by
     * {@code DocXSystemTest#assertApplicationDisplaysExpected(String, String, Model)}.<br>
     *
     * @see DocXSystemTest#assertApplicationDisplaysExpected(String, String, Model)
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
