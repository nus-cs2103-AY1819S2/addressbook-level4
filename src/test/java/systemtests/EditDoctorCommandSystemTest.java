package systemtests;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.GENDER_DESC_ALVINA;
import static seedu.address.logic.commands.CommandTestUtil.GENDER_DESC_STEVEN;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_GENDER_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_NAME_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_PHONE_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_SPECIALISATION_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_YEAR_DESC;
import static seedu.address.logic.commands.CommandTestUtil.NAME_DESC_STEVEN;
import static seedu.address.logic.commands.CommandTestUtil.PHONE_DESC_ALVINA;
import static seedu.address.logic.commands.CommandTestUtil.PHONE_DESC_STEVEN;
import static seedu.address.logic.commands.CommandTestUtil.SPECIALISATION_DESC_ACUPUNCTURE;
import static seedu.address.logic.commands.CommandTestUtil.SPECIALISATION_DESC_MASSAGE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_STEVEN;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_ALVINA;
import static seedu.address.logic.commands.CommandTestUtil.VALID_SPECIALISATION_ACUPUNCTURE;
import static seedu.address.logic.commands.CommandTestUtil.YEAR_DESC_ALVINA;
import static seedu.address.logic.commands.CommandTestUtil.YEAR_DESC_STEVEN;
import static seedu.address.logic.parser.CliSyntax.PREFIX_SPECIALISATION;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_DOCTORS;
import static seedu.address.testutil.TypicalDoctors.ALVINA;
import static seedu.address.testutil.TypicalDoctors.STEVEN;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_PERSON;
import static seedu.address.testutil.TypicalPatients.KEYWORD_MATCHING_MEIER;

import org.junit.Test;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.doctor.EditDoctorCommand;
import seedu.address.model.Model;
import seedu.address.model.person.Gender;
import seedu.address.model.person.Name;
import seedu.address.model.person.Phone;
import seedu.address.model.person.doctor.Doctor;
import seedu.address.model.person.doctor.Year;
import seedu.address.model.person.specialisation.Specialisation;
import seedu.address.testutil.DoctorBuilder;
import seedu.address.testutil.DoctorUtil;

public class EditDoctorCommandSystemTest extends DocXSystemTest {

    @Test
    public void edit() {
        Model model = getModel();

        /* ---------------- Performing edit operation while an unfiltered list is being shown --------------------- */

        /* Case: edit all fields, command with leading spaces, trailing spaces and multiple spaces between each field
         * -> edited
         */
        Index index = INDEX_FIRST_PERSON;
        String command = " " + EditDoctorCommand.COMMAND_WORD + "  " + index.getOneBased() + "  "
                + NAME_DESC_STEVEN + "  "
                + GENDER_DESC_STEVEN + " " + YEAR_DESC_STEVEN + " " + PHONE_DESC_STEVEN + " "
                + "  " + SPECIALISATION_DESC_ACUPUNCTURE + " ";
        Doctor editedDoctor = new DoctorBuilder(STEVEN).withSpecs(VALID_SPECIALISATION_ACUPUNCTURE).build();
        assertCommandSuccess(command, index, editedDoctor);

        /* Case: edit a doctor with new values same as existing values -> edited */
        command = EditDoctorCommand.COMMAND_WORD + " " + index.getOneBased() + NAME_DESC_STEVEN
                + GENDER_DESC_STEVEN + YEAR_DESC_STEVEN + PHONE_DESC_STEVEN
                + SPECIALISATION_DESC_MASSAGE + SPECIALISATION_DESC_ACUPUNCTURE;
        assertCommandSuccess(command, index, STEVEN);

        /* Case: edit a doctor with new values same as another doctor's values but with different phone
         * -> edited
         */
        index = INDEX_SECOND_PERSON;
        command = EditDoctorCommand.COMMAND_WORD + " " + index.getOneBased() + NAME_DESC_STEVEN
                + GENDER_DESC_STEVEN + YEAR_DESC_STEVEN + PHONE_DESC_ALVINA
                + SPECIALISATION_DESC_ACUPUNCTURE + SPECIALISATION_DESC_MASSAGE;
        editedDoctor = new DoctorBuilder(STEVEN).withPhone(VALID_PHONE_ALVINA).build();
        assertCommandSuccess(command, index, editedDoctor);


        /* ----------------- Performing edit operation while a filtered list is being shown ----------------------- */

        /* Case: filtered doctor list, edit index within bounds of docX and doctor list -> edited */
        showDoctorsWithName(KEYWORD_MATCHING_MEIER);
        index = INDEX_FIRST_PERSON;
        assertTrue(index.getZeroBased() < getModel().getFilteredDoctorList().size());
        command = EditDoctorCommand.COMMAND_WORD + " " + index.getOneBased() + " " + NAME_DESC_STEVEN;
        Doctor doctorToEdit = getModel().getFilteredDoctorList().get(index.getZeroBased());
        editedDoctor = new DoctorBuilder(doctorToEdit).withName(VALID_NAME_STEVEN).build();
        assertCommandSuccess(command, index, editedDoctor);

        /* Case: filtered doctor list, edit index within bounds of docX but out of bounds of doctor list
         * -> rejected
         */
        showDoctorsWithName(KEYWORD_MATCHING_MEIER);
        int invalidIndex = getModel().getDocX().getDoctorList().size();
        assertCommandFailure(EditDoctorCommand.COMMAND_WORD + " " + invalidIndex + NAME_DESC_STEVEN,
                Messages.MESSAGE_INVALID_DOCTOR_DISPLAYED_INDEX);

        /* -------------------- Performing edit operation while a doctor card is selected ------------------------ */

        /* Case: selects first card in the doctor list, edit a doctor -> edited, card selection remains unchanged but
         * browser url changes
         */
        showAllDoctors();
        index = INDEX_FIRST_PERSON;
        selectDoctor(index);
        command = EditDoctorCommand.COMMAND_WORD + " " + index.getOneBased() + NAME_DESC_STEVEN
                + GENDER_DESC_STEVEN + YEAR_DESC_STEVEN + PHONE_DESC_STEVEN
                + SPECIALISATION_DESC_ACUPUNCTURE + SPECIALISATION_DESC_MASSAGE;
        // this can be misleading: card selection actually remains unchanged but the
        // browser's url is updated to reflect the new patient's name
        //assertCommandSuccess(command, index, STEVEN, index);

        /* -------------------------------- Performing invalid edit operation ------------------------------------- */

        /* Case: invalid index (0) -> rejected */
        assertCommandFailure(EditDoctorCommand.COMMAND_WORD + " 0" + NAME_DESC_STEVEN,
                String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT, EditDoctorCommand.MESSAGE_USAGE));

        /* Case: invalid index (-1) -> rejected */
        assertCommandFailure(EditDoctorCommand.COMMAND_WORD + " -1" + NAME_DESC_STEVEN,
                String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT, EditDoctorCommand.MESSAGE_USAGE));

        /* Case: invalid index (size + 1) -> rejected */
        invalidIndex = getModel().getFilteredDoctorList().size() + 1;
        assertCommandFailure(EditDoctorCommand.COMMAND_WORD + " " + invalidIndex + NAME_DESC_STEVEN,
                Messages.MESSAGE_INVALID_DOCTOR_DISPLAYED_INDEX);

        /* Case: missing index -> rejected */
        assertCommandFailure(EditDoctorCommand.COMMAND_WORD + NAME_DESC_STEVEN,
                String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT, EditDoctorCommand.MESSAGE_USAGE));

        /* Case: missing all fields -> rejected */
        assertCommandFailure(EditDoctorCommand.COMMAND_WORD + " " + INDEX_FIRST_PERSON.getOneBased(),
                EditDoctorCommand.MESSAGE_NOT_EDITED);

        /* Case: invalid name -> rejected */
        assertCommandFailure(EditDoctorCommand.COMMAND_WORD + " "
                        + INDEX_FIRST_PERSON.getOneBased() + INVALID_NAME_DESC,
                Name.MESSAGE_CONSTRAINTS);

        /* Case: invalid gender -> rejected */
        assertCommandFailure(EditDoctorCommand.COMMAND_WORD + " "
                        + INDEX_FIRST_PERSON.getOneBased() + INVALID_GENDER_DESC,
                Gender.MESSAGE_CONSTRAINTS);

        /* Case: invalid year of experience -> rejected */
        assertCommandFailure(EditDoctorCommand.COMMAND_WORD + " "
                        + INDEX_FIRST_PERSON.getOneBased() + INVALID_YEAR_DESC,
                Year.MESSAGE_CONSTRAINTS);

        /* Case: invalid phone -> rejected */
        assertCommandFailure(EditDoctorCommand.COMMAND_WORD + " "
                        + INDEX_FIRST_PERSON.getOneBased() + INVALID_PHONE_DESC,
                Phone.MESSAGE_CONSTRAINTS);

        /* Case: invalid specialisation -> rejected */
        assertCommandFailure(EditDoctorCommand.COMMAND_WORD + " "
                        + INDEX_FIRST_PERSON.getOneBased() + INVALID_SPECIALISATION_DESC,
                Specialisation.MESSAGE_CONSTRAINTS);

        /* Case: missing specialisation -> rejected */
        assertCommandFailure(EditDoctorCommand.COMMAND_WORD + " "
                        + INDEX_FIRST_PERSON.getOneBased() + " " + PREFIX_SPECIALISATION,
                Specialisation.MESSAGE_CONSTRAINTS);

        /* Case: edit a doctor with new values same as another doctor's values -> rejected */
        executeCommand(DoctorUtil.getAddDoctorCommand(STEVEN));
        assertTrue(getModel().getDocX().getDoctorList().contains(STEVEN));
        index = INDEX_FIRST_PERSON;
        assertFalse(getModel().getFilteredDoctorList().get(index.getZeroBased()).equals(ALVINA));
        command = EditDoctorCommand.COMMAND_WORD + " " + index.getOneBased() + NAME_DESC_STEVEN
                + GENDER_DESC_STEVEN + YEAR_DESC_STEVEN + PHONE_DESC_ALVINA
                + SPECIALISATION_DESC_MASSAGE + SPECIALISATION_DESC_ACUPUNCTURE;
        assertCommandFailure(command, EditDoctorCommand.MESSAGE_DUPLICATE_DOCTOR);

        /* Case: edit a doctor with new values same as another doctor's values
        but with different gender -> rejected */
        command = EditDoctorCommand.COMMAND_WORD + " " + index.getOneBased() + NAME_DESC_STEVEN
                + GENDER_DESC_ALVINA + YEAR_DESC_STEVEN + PHONE_DESC_ALVINA
                + SPECIALISATION_DESC_MASSAGE + SPECIALISATION_DESC_ACUPUNCTURE;
        assertCommandFailure(command, EditDoctorCommand.MESSAGE_DUPLICATE_DOCTOR);

        /* Case: edit a doctor with new values same as another doctor's values but with different
         * year of exp -> rejected
         */
        command = EditDoctorCommand.COMMAND_WORD + " " + index.getOneBased() + NAME_DESC_STEVEN
                + GENDER_DESC_STEVEN + YEAR_DESC_ALVINA + PHONE_DESC_ALVINA
                + SPECIALISATION_DESC_MASSAGE + SPECIALISATION_DESC_ACUPUNCTURE;
        assertCommandFailure(command, EditDoctorCommand.MESSAGE_DUPLICATE_DOCTOR);


        /* Case: edit a doctor with new values same as another doctor's values but with different specs -> rejected */
        command = EditDoctorCommand.COMMAND_WORD + " " + index.getOneBased() + NAME_DESC_STEVEN
                + GENDER_DESC_STEVEN + YEAR_DESC_STEVEN + PHONE_DESC_ALVINA
                + SPECIALISATION_DESC_ACUPUNCTURE;
        assertCommandFailure(command, EditDoctorCommand.MESSAGE_DUPLICATE_DOCTOR);

    }

    /**
     * Performs the same verification as {@code assertCommandSuccess(String, Index, Doctor, Index)} except that
     * the browser url and selected card remain unchanged.
     *
     * @param toEdit the index of the current model's filtered list
     * @see EditDoctorCommandSystemTest#assertCommandSuccess(String, Index, Doctor, Index)
     */
    private void assertCommandSuccess(String command, Index toEdit, Doctor editedDoctor) {
        assertCommandSuccess(command, toEdit, editedDoctor, null);
    }

    /**
     * Performs the same verification as {@code assertCommandSuccess(String, Model, String, Index)} and in addition,
         <br>
     * 1. Asserts that result display box displays the success message of executing {@code EditDoctorCommand}.<br>
     * 2. Asserts that the model related components are updated to reflect the doctor at index {@code toEdit} being
     * updated to values specified {@code editedDoctor}.<br>
     *
     * @param toEdit the index of the current model's filtered list.
     * @see EditDoctorCommandSystemTest#assertCommandSuccess(String, Model, String, Index)
     */
    private void assertCommandSuccess(String command, Index toEdit, Doctor editedDoctor,
                                      Index expectedSelectedCardIndex) {
        Model expectedModel = getModel();
        expectedModel.setDoctor(expectedModel.getFilteredDoctorList().get(toEdit.getZeroBased()), editedDoctor);
        expectedModel.updateFilteredDoctorList(PREDICATE_SHOW_ALL_DOCTORS);

        assertCommandSuccess(command, expectedModel,
                String.format(EditDoctorCommand.MESSAGE_EDIT_DOCTOR_SUCCESS,
                        editedDoctor), expectedSelectedCardIndex);
    }

    /**
     * Performs the same verification as {@code assertCommandSuccess(String, Model, String, Index)} except that the
     * browser url and selected card remain unchanged.
     *
     * @see EditDoctorCommandSystemTest#assertCommandSuccess(String, Model, String, Index)
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
     * {@code DocXSystemTest#assertApplicationDisplaysExpected(String, String, Model)}.<br>
     *
     * @see DocXSystemTest#assertApplicationDisplaysExpected(String, String, Model)
     * @see DocXSystemTest#assertSelectedCardChanged(Index)
     */
    private void assertCommandSuccess(String command, Model expectedModel, String expectedResultMessage,
                                      Index expectedSelectedCardIndex) {
        executeCommand(command);
        expectedModel.updateFilteredDoctorList(PREDICATE_SHOW_ALL_DOCTORS);
        assertApplicationDisplaysExpected("", expectedResultMessage, expectedModel);
        assertCommandBoxShowsDefaultStyle();
        if (expectedSelectedCardIndex != null) {
            assertSelectedCardChanged_doc(expectedSelectedCardIndex);
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
