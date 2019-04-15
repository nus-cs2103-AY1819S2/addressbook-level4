package seedu.address.logic.commands;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_AGE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_APPT_STATUS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_CHRONOLOGY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DATE_OF_APPT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DOCTOR_ID;
import static seedu.address.logic.parser.CliSyntax.PREFIX_GENDER;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PATIENT_ID;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_SPECIALISATION;
import static seedu.address.logic.parser.CliSyntax.PREFIX_START_TIME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.address.logic.parser.CliSyntax.PREFIX_YEAR;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.doctor.EditDoctorCommand;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.commands.patient.EditPatientCommand;
import seedu.address.model.DocX;
import seedu.address.model.Model;
import seedu.address.model.medicalhistory.MedHistContainsKeywordsPredicate;
import seedu.address.model.medicalhistory.MedicalHistory;
import seedu.address.model.person.doctor.Doctor;
import seedu.address.model.person.doctor.DoctorContainsKeywordsPredicate;
import seedu.address.model.person.patient.Patient;
import seedu.address.model.person.patient.PatientNameContainsKeywordsPredicate;
import seedu.address.model.prescription.Prescription;
import seedu.address.model.prescription.PrescriptionContainsKeywordsPredicate;
import seedu.address.testutil.EditDoctorDescriptorBuilder;
import seedu.address.testutil.EditPatientDescriptorBuilder;


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
    public static final String VALID_PHONE_ALVINA = "62423423";
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

    public static final EditDoctorCommand.EditDoctorDescriptor DESC_ALVINA;
    public static final EditDoctorCommand.EditDoctorDescriptor DESC_STEVEN;

    static {
        DESC_ALVINA = new EditDoctorDescriptorBuilder().withName(VALID_NAME_ALVINA)
                .withGender(VALID_GENDER_ALVINA).withYear(VALID_YEAR_ALVINA)
                .withPhone(VALID_PHONE_ALVINA).withSpecs(VALID_SPECIALISATION_ACUPUNCTURE).build();
        DESC_STEVEN = new EditDoctorDescriptorBuilder().withName(VALID_NAME_STEVEN)
                .withGender(VALID_GENDER_STEVEN).withYear(VALID_YEAR_STEVEN).withPhone(VALID_PHONE_STEVEN)
                .withSpecs(VALID_SPECIALISATION_ACUPUNCTURE, VALID_SPECIALISATION_GENERAL).build();
    }

    // end of add-doctor testing

    // for testing appointment
    public static final String VALID_PATIENT_ID = "1";
    public static final String INVALID_APPT_PATIENT_ID = "-1";
    public static final String VALID_DOCTOR_ID = "2";
    public static final String INVALID_APPT_DOCTOR_ID = "-2";
    public static final LocalDateTime FUTURE_DATE_TIME = LocalDateTime.now().withHour(9).withMinute(0).plusDays(1);
    public static final String VALID_DATE_OF_APPT = FUTURE_DATE_TIME.toLocalDate().toString();
    public static final String INVALID_DATE_OF_APPT = "20190833";
    public static final String VALID_START_TIME = FUTURE_DATE_TIME.toLocalTime()
            .format(DateTimeFormatter.ofPattern("HH:mm"));
    public static final String INVALID_START_TIME = "9";
    public static final String VALID_STATUS = "ACTIVE";
    public static final String INVALID_STATUS = "ASDF";
    public static final String VALID_CHRONOLOGY = "PAST";
    public static final String INVALID_CHRONOLOGY = "ASDF";
    public static final String VALID_MARK_APPT_INDEX = "1";
    public static final String INVALID_MARK_APPT_INDEX = "-1";
    public static final String DESC_VALID_PATIENT_ID = " " + PREFIX_PATIENT_ID + VALID_PATIENT_ID;
    public static final String DESC_INVALID_PATIENT_ID = " " + PREFIX_PATIENT_ID + INVALID_APPT_PATIENT_ID;
    public static final String DESC_VALID_DOCTOR_ID = " " + PREFIX_DOCTOR_ID + VALID_DOCTOR_ID;
    public static final String DESC_INVALID_DOCTOR_ID = " " + PREFIX_DOCTOR_ID + INVALID_APPT_DOCTOR_ID;
    public static final String DESC_VALID_DATE_OF_APPT = " " + PREFIX_DATE_OF_APPT + VALID_DATE_OF_APPT;
    public static final String DESC_INVALID_DATE_OF_APPT = " " + PREFIX_DATE_OF_APPT + INVALID_DATE_OF_APPT;
    public static final String DESC_VALID_START_TIME = " " + PREFIX_START_TIME + VALID_START_TIME;
    public static final String DESC_INVALID_START_TIME = " " + PREFIX_START_TIME + INVALID_START_TIME;
    public static final String DESC_VALID_STATUS = " " + PREFIX_APPT_STATUS + VALID_STATUS;
    public static final String DESC_INVALID_STATUS = " " + PREFIX_APPT_STATUS + INVALID_STATUS;
    public static final String DESC_VALID_CHRONOLOGY = " " + PREFIX_CHRONOLOGY + VALID_CHRONOLOGY;
    public static final String DESC_INVALID_CHRONOLOGY = " " + PREFIX_CHRONOLOGY + INVALID_CHRONOLOGY;
    public static final String DESC_VALID_MARK_APPT = " " + VALID_MARK_APPT_INDEX + " " + PREFIX_APPT_STATUS
            + VALID_STATUS;
    public static final String DESC_INVALID_MARK_APPT_INDEX = " " + INVALID_MARK_APPT_INDEX + " " + DESC_VALID_STATUS;
    public static final String DESC_INVALID_MARK_APPT_STATUS = " " + VALID_MARK_APPT_INDEX + " " + DESC_INVALID_STATUS;
    // end of testing appointment

    // medical history test
    public static final String INVALID_PATIENT_ID = "aaa";
    public static final String INVALID_DOCTOR_ID = "bbb";
    public static final String VALID_MEDHIST_ID = "1/2/2019-03-02";
    public static final String VALID_DATE_OF_MED_HIST = "2019-03-02";
    public static final String INVALID_FUTURE_DATE_OF_MED_HIST = "2020-09-09";
    public static final String INVALID_DATE_OF_MED_HIST = "2019-02-29";
    public static final String VALID_WRITE_UP = "Have a fever.";

    // prescription test
    public static final String VALID_DESCRIPTION = "For curing fever";

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
    public static final String INVALID_GENDER_DESC = " " + PREFIX_GENDER + "boy"; // either M or F or m or f
    public static final String INVALID_AGE_DESC = " " + PREFIX_AGE + "12yo"; // 'yo' not allowed in age
    public static final String INVALID_YEAR_DESC = " " + PREFIX_YEAR + "2years"; // only numbers, no 'years'
    public static final String INVALID_PHONE_DESC = " " + PREFIX_PHONE + "911a"; // 'a' not allowed in phones
    public static final String INVALID_ADDRESS_DESC = " " + PREFIX_ADDRESS; // empty string not allowed for addresses
    public static final String INVALID_TAG_DESC = " " + PREFIX_TAG + "hubby*"; // '*' not allowed in tags
    public static final String INVALID_SPECIALISATION_DESC = " " + PREFIX_SPECIALISATION + "general*";
    // '*' not allowed in tags

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
     * Updates {@code model}'s filtered list to show only the prescription at the given {@code targetIndex} in the
     * {@code model}'s address book.
     */
    public static void showPrescriptionAtIndex(Model model, Index targetIndex) {
        assertTrue(targetIndex.getZeroBased() < model.getFilteredPrescriptionList().size());

        Prescription prescription = model.getFilteredPrescriptionList().get(targetIndex.getZeroBased());
        final String[] description = prescription.getDescription().getDescription().split("\\s+");
        model.updateFilteredPrescriptionList(new PrescriptionContainsKeywordsPredicate(Arrays.asList(description[0])));

        assertEquals(1, model.getFilteredPrescriptionList().size());
    }

    /**
     * Updates {@code model}'s filtered list to show only the medical history at the given {@code targetIndex} in the
     * {@code model}'s address book.
     */
    public static void showMedHistAtIndex(Model model, Index targetIndex) {
        assertTrue(targetIndex.getZeroBased() < model.getFilteredMedHistList().size());

        MedicalHistory medHist = model.getFilteredMedHistList().get(targetIndex.getZeroBased());
        final String[] splitWriteUp = medHist.getWriteUp().value.split("\\s+");
        model.updateFilteredMedHistList(new MedHistContainsKeywordsPredicate(Arrays.asList(splitWriteUp[0])));

        assertEquals(1, model.getFilteredMedHistList().size());
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
        model.updateFilteredDoctorList(new DoctorContainsKeywordsPredicate(Arrays.asList(splitName[0])));

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
