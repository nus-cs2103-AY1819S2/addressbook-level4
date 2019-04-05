package seedu.address.logic.commands;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_AGE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_GENDER;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_SPECIALISATION;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.address.logic.parser.CliSyntax.PREFIX_YEAR;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.DocX;
import seedu.address.model.Model;
import seedu.address.model.person.Doctor;
import seedu.address.model.person.DoctorNameContainsKeywordsPredicate;
import seedu.address.model.person.Patient;
import seedu.address.model.person.PatientNameContainsKeywordsPredicate;
import seedu.address.testutil.EditPatientDescriptorBuilder;

//import javax.print.Doc;

/**
 * Contains helper methods for testing commands.
 */
public class CommandTestUtil {

    // for doctor testing
    public static final String VALID_NAME_STEVEN = "Steven Lim";
    public static final String VALID_NAME_ALVINA = "Alvina Ong";
    public static final String VALID_GENDER_STEVEN = "M";
    public static final String VALID_GENDER_ALVINA = "F";
    public static final String VALID_YEAR_STEVEN = "23";
    public static final String VALID_YEAR_ALVINA = "21";
    public static final String VALID_PHONE_STEVEN = "91612342";
    public static final String VALID_PHONE_ALVINA = "82376447";
    public static final String VALID_SPECIALISATION_ACUPUNCTURE = "acupuncture";
    public static final String VALID_SPECIALISATION_MASSAGE = "massage";
    public static final String VALID_SPECIALISATION_GENERAL = "general";

    public static final String NAME_DESC_STEVEN = " " + PREFIX_NAME + VALID_NAME_STEVEN;
    public static final String NAME_DESC_ALVINA = " " + PREFIX_NAME + VALID_NAME_ALVINA;
    public static final String GENDER_DESC_STEVEN = " " + PREFIX_GENDER + VALID_GENDER_STEVEN;
    public static final String GENDER_DESC_ALVINA = " " + PREFIX_GENDER + VALID_GENDER_ALVINA;
    public static final String YEAR_DESC_STEVEN = " " + PREFIX_YEAR + VALID_YEAR_STEVEN;
    public static final String YEAR_DESC_ALVINA = " " + PREFIX_YEAR + VALID_YEAR_ALVINA;
    public static final String PHONE_DESC_STEVEN = " " + PREFIX_PHONE + VALID_PHONE_STEVEN;
    public static final String PHONE_DESC_ALVINA = " " + PREFIX_PHONE + VALID_PHONE_ALVINA;
    public static final String SPECIALISATION_DESC_ACUPUNCTURE = " " + PREFIX_SPECIALISATION
            + VALID_SPECIALISATION_ACUPUNCTURE;
    public static final String SPECIALISATION_DESC_MASSAGE = " " + PREFIX_SPECIALISATION
            + VALID_SPECIALISATION_MASSAGE;
    public static final String SPECIALISATION_DESC_GENERAL = " " + PREFIX_SPECIALISATION
            + VALID_SPECIALISATION_GENERAL;
    public static final String INVALID_SPECIALISATION_DESC = " " + PREFIX_SPECIALISATION + "general*";
    // '*' not allowed in tags
    // end of add-doctor testing

    public static final String VALID_NAME_AMY = "Amy Bee";
    public static final String VALID_NAME_BOB = "Bob Choo";
    public static final String VALID_GENDER_AMY = "F";
    public static final String VALID_GENDER_BOB = "M";
    public static final String VALID_AGE_AMY = "7";
    public static final String VALID_AGE_BOB = "25";
    public static final String VALID_PHONE_AMY = "61111111";
    public static final String VALID_PHONE_BOB = "94351253";
    public static final String VALID_ADDRESS_AMY = "Block 312, Amy Street 1";
    public static final String VALID_ADDRESS_BOB = "Block 123, Bobby Street 3";
    public static final String VALID_TAG_STROKE = "stroke";
    public static final String VALID_TAG_HBP = "highbloodpressure";

    public static final String NAME_DESC_AMY = " " + PREFIX_NAME + VALID_NAME_AMY;
    public static final String NAME_DESC_BOB = " " + PREFIX_NAME + VALID_NAME_BOB;
    public static final String GENDER_DESC_AMY = " " + PREFIX_GENDER + VALID_GENDER_AMY;
    public static final String GENDER_DESC_BOB = " " + PREFIX_GENDER + VALID_GENDER_BOB;
    public static final String AGE_DESC_AMY = " " + PREFIX_AGE + VALID_AGE_AMY;
    public static final String AGE_DESC_BOB = " " + PREFIX_AGE + VALID_AGE_BOB;
    public static final String PHONE_DESC_AMY = " " + PREFIX_PHONE + VALID_PHONE_AMY;
    public static final String PHONE_DESC_BOB = " " + PREFIX_PHONE + VALID_PHONE_BOB;
    public static final String ADDRESS_DESC_AMY = " " + PREFIX_ADDRESS + VALID_ADDRESS_AMY;
    public static final String ADDRESS_DESC_BOB = " " + PREFIX_ADDRESS + VALID_ADDRESS_BOB;
    public static final String TAG_DESC_FRIEND = " " + PREFIX_TAG + VALID_TAG_HBP;
    public static final String TAG_DESC_HUSBAND = " " + PREFIX_TAG + VALID_TAG_STROKE;

    public static final String INVALID_NAME_DESC = " " + PREFIX_NAME + "James&"; // '&' not allowed in names
    public static final String INVALID_GENDER_DESC = " " + PREFIX_GENDER + "boy"; // either M or F
    public static final String INVALID_AGE_DESC = " " + PREFIX_AGE + "12yo"; // 'yo' not allowed in age
    public static final String INVALID_PHONE_DESC = " " + PREFIX_PHONE + "911a"; // 'a' not allowed in phones
    public static final String INVALID_ADDRESS_DESC = " " + PREFIX_ADDRESS; // empty string not allowed for addresses
    public static final String INVALID_TAG_DESC = " " + PREFIX_TAG + "hubby*"; // '*' not allowed in tags

    public static final String PREAMBLE_WHITESPACE = "\t  \r  \n";
    public static final String PREAMBLE_NON_EMPTY = "NonEmptyPreamble";

    public static final EditPatientCommand.EditPatientDescriptor DESC_AMY;
    public static final EditPatientCommand.EditPatientDescriptor DESC_BOB;

    static {
        DESC_AMY = new EditPatientDescriptorBuilder().withName(VALID_NAME_AMY)
                .withGender(VALID_GENDER_AMY).withAge(VALID_AGE_AMY)
                .withPhone(VALID_PHONE_AMY).withAddress(VALID_ADDRESS_AMY)
                .withTags(VALID_TAG_HBP).build();
        DESC_BOB = new EditPatientDescriptorBuilder().withName(VALID_NAME_BOB)
                .withGender(VALID_GENDER_BOB).withAge(VALID_AGE_BOB)
                .withPhone(VALID_PHONE_BOB).withAddress(VALID_ADDRESS_BOB)
                .withTags(VALID_TAG_STROKE, VALID_TAG_HBP).build();
    }

    /**
     * Executes the given {@code command}, confirms that <br>
     * - the returned {@link CommandResult} matches {@code expectedCommandResult} <br>
     * - the {@code actualModel} matches {@code expectedModel} <br>
     * - the {@code actualCommandHistory} remains unchanged.
     */
    public static void assertCommandSuccess(Command command, Model actualModel, CommandHistory actualCommandHistory,
            CommandResult expectedCommandResult, Model expectedModel) {
        CommandHistory expectedCommandHistory = new CommandHistory(actualCommandHistory);
        try {
            CommandResult result = command.execute(actualModel, actualCommandHistory);
            assertEquals(expectedCommandResult, result);
            assertEquals(expectedModel, actualModel);
            assertEquals(expectedCommandHistory, actualCommandHistory);
        } catch (CommandException ce) {
            throw new AssertionError("Execution of command should not fail.", ce);
        }
    }

    /**
     * Convenience wrapper to {@link #assertCommandSuccess(Command, Model, CommandHistory, CommandResult, Model)}
     * that takes a string {@code expectedMessage}.
     */
    public static void assertCommandSuccess(Command command, Model actualModel, CommandHistory actualCommandHistory,
            String expectedMessage, Model expectedModel) {
        CommandResult expectedCommandResult = new CommandResult(expectedMessage);
        assertCommandSuccess(command, actualModel, actualCommandHistory, expectedCommandResult, expectedModel);
    }

    /**
     * Executes the given {@code command}, confirms that <br>
     * - a {@code CommandException} is thrown <br>
     * - the CommandException message matches {@code expectedMessage} <br>
     * - the address book, filtered patient list and selected patient in {@code actualModel} remain unchanged <br>
     * - {@code actualCommandHistory} remains unchanged.
     */
    public static void assertCommandFailure(Command command, Model actualModel, CommandHistory actualCommandHistory,
            String expectedMessage) {
        // we are unable to defensively copy the model for comparison later, so we can
        // only do so by copying its components.
        DocX expectedDocX = new DocX(actualModel.getDocX());
        List<Patient> expectedFilteredList = new ArrayList<>(actualModel.getFilteredPatientList());
        List<Doctor> expectedFilteredDoctorList = new ArrayList<>(actualModel.getFilteredDoctorList());
        Patient expectedSelectedPatient = actualModel.getSelectedPatient();
        Doctor expectedSelectedDoctor = actualModel.getSelectedDoctor();

        CommandHistory expectedCommandHistory = new CommandHistory(actualCommandHistory);

        try {
            command.execute(actualModel, actualCommandHistory);
            throw new AssertionError("The expected CommandException was not thrown.");
        } catch (CommandException e) {
            assertEquals(expectedMessage, e.getMessage());
            assertEquals(expectedDocX, actualModel.getDocX());
            assertEquals(expectedFilteredList, actualModel.getFilteredPatientList());
            assertEquals(expectedSelectedPatient, actualModel.getSelectedPatient());
            assertEquals(expectedFilteredDoctorList, actualModel.getFilteredDoctorList());
            assertEquals(expectedSelectedDoctor, actualModel.getSelectedDoctor());
            assertEquals(expectedCommandHistory, actualCommandHistory);
        }
    }

    /**
     * Updates {@code model}'s filtered list to show only the patient at the given {@code targetIndex} in the
     * {@code model}'s address book.
     */
    public static void showPatientAtIndex(Model model, Index targetIndex) {
        assertTrue(targetIndex.getZeroBased() < model.getFilteredPatientList().size());

        Patient patient = model.getFilteredPatientList().get(targetIndex.getZeroBased());
        final String[] splitName = patient.getName().fullName.split("\\s+");
        model.updateFilteredPatientList(new PatientNameContainsKeywordsPredicate(Arrays.asList(splitName[0])));

        assertEquals(1, model.getFilteredPatientList().size());
    }

    /**
     * Deletes the first patient in {@code model}'s filtered list from {@code model}'s address book.
     */
    public static void deleteFirstPatient(Model model) {
        Patient firstPatient = model.getFilteredPatientList().get(0);
        model.deletePatient(firstPatient);
        model.commitDocX();
    }

    /**
     * Updates {@code model}'s filtered list to show only the doctor at the given {@code targetIndex} in the
     * {@code model}'s address book.
     */
    public static void showDoctorAtIndex(Model model, Index targetIndex) {
        assertTrue(targetIndex.getZeroBased() < model.getFilteredDoctorList().size());

        Doctor doctor = model.getFilteredDoctorList().get(targetIndex.getZeroBased());
        final String[] splitName = doctor.getName().fullName.split("\\s+");
        model.updateFilteredDoctorList(new DoctorNameContainsKeywordsPredicate(Arrays.asList(splitName[0])));

        assertEquals(1, model.getFilteredDoctorList().size());
    }

    /**
     * Deletes the first doctor in {@code model}'s filtered list from {@code model}'s address book.
     */
    public static void deleteFirstDoctor(Model model) {
        Doctor firstDoctor = model.getFilteredDoctorList().get(0);
        model.deleteDoctor(firstDoctor);
        model.commitDocX();
    }

}
