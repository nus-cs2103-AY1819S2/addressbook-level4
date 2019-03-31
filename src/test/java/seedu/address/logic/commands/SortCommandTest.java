package seedu.address.logic.commands;

import static org.junit.Assert.assertEquals;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalPersons.ALICE;
import static seedu.address.testutil.TypicalPersons.BENSON;
import static seedu.address.testutil.TypicalPersons.CARL;
import static seedu.address.testutil.TypicalPersons.DANIEL;
import static seedu.address.testutil.TypicalPersons.ELLE;
import static seedu.address.testutil.TypicalPersons.FIONA;
import static seedu.address.testutil.TypicalPersons.GEORGE;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

import org.junit.Test;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.comparators.PatientComparator;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.patient.Patient;
import seedu.address.model.person.Person;

/**
 * Contains integration tests (interaction with the Model) for {@code SortCommand}.
 */
public class SortCommandTest {
    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    private Model expectedModel = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    private CommandHistory commandHistory = new CommandHistory();

    @Test
    public void execute_sortPatientParameters_orderDefault() throws ParseException {
        execute_sortPatientParameter("name", false, Arrays.asList(ALICE, BENSON, CARL, DANIEL, ELLE, FIONA, GEORGE));
        execute_sortPatientParameter("phone", false, Arrays.asList(DANIEL, ALICE, ELLE, FIONA, GEORGE, CARL, BENSON));
        execute_sortPatientParameter("address", false, Arrays.asList(DANIEL, ALICE, BENSON, GEORGE, FIONA, ELLE, CARL));
        execute_sortPatientParameter("email", false, Arrays.asList(ALICE, GEORGE, DANIEL, CARL, BENSON, FIONA, ELLE));
        execute_sortPatientParameter("nric", false, Arrays.asList(ALICE, BENSON, CARL, DANIEL, ELLE, FIONA, GEORGE));
        execute_sortPatientParameter("dob", false, Arrays.asList(ALICE, BENSON, GEORGE, ELLE, FIONA, CARL, DANIEL));
        execute_sortPatientParameter("sex", false, Arrays.asList(ALICE, ELLE, FIONA, CARL, BENSON, GEORGE, DANIEL));
        execute_sortPatientParameter("desc", false, Arrays.asList(FIONA, ALICE, ELLE, BENSON, CARL, DANIEL, GEORGE));
        execute_sortPatientParameter("drug", false, Arrays.asList(FIONA, ALICE, ELLE, BENSON, DANIEL, CARL, GEORGE));
        execute_sortPatientParameter("kinN", false, Arrays.asList(CARL, DANIEL, BENSON, ELLE, ALICE, FIONA, GEORGE));
        execute_sortPatientParameter("kinR", false, Arrays.asList(CARL, ALICE, ELLE, FIONA, DANIEL, BENSON, GEORGE));
        execute_sortPatientParameter("kinP", false, Arrays.asList(FIONA, DANIEL, CARL, ELLE, GEORGE, ALICE, BENSON));
        execute_sortPatientParameter("kinA", false, Arrays.asList(DANIEL, ALICE, GEORGE, BENSON, ELLE, FIONA, CARL));
    }

    @Test
    public void execute_sortPatientParameters_orderReverse() throws ParseException {
        execute_sortPatientParameter("name", true, Arrays.asList(GEORGE, FIONA, ELLE, DANIEL, CARL, BENSON, ALICE));
        execute_sortPatientParameter("phone", true, Arrays.asList(BENSON, CARL, GEORGE, FIONA, ELLE, ALICE, DANIEL));
        execute_sortPatientParameter("address", true, Arrays.asList(CARL, ELLE, FIONA, GEORGE, BENSON, ALICE, DANIEL));
        execute_sortPatientParameter("email", true, Arrays.asList(ELLE, FIONA, BENSON, CARL, DANIEL, GEORGE, ALICE));
        execute_sortPatientParameter("nric", true, Arrays.asList(GEORGE, FIONA, ELLE, DANIEL, CARL, BENSON, ALICE));
        execute_sortPatientParameter("dob", true, Arrays.asList(DANIEL, CARL, FIONA, ELLE, GEORGE, BENSON, ALICE));
        execute_sortPatientParameter("sex", true, Arrays.asList(DANIEL, GEORGE, BENSON, CARL, FIONA, ELLE, ALICE));
        execute_sortPatientParameter("desc", true, Arrays.asList(GEORGE, DANIEL, CARL, BENSON, ELLE, ALICE, FIONA));
        execute_sortPatientParameter("drug", true, Arrays.asList(GEORGE, CARL, DANIEL, BENSON, ELLE, ALICE, FIONA));
        execute_sortPatientParameter("kinN", true, Arrays.asList(GEORGE, FIONA, ALICE, ELLE, BENSON, DANIEL, CARL));
        execute_sortPatientParameter("kinR", true, Arrays.asList(GEORGE, BENSON, DANIEL, FIONA, ELLE, ALICE, CARL));
        execute_sortPatientParameter("kinP", true, Arrays.asList(BENSON, ALICE, GEORGE, ELLE, CARL, DANIEL, FIONA));
        execute_sortPatientParameter("kinA", true, Arrays.asList(CARL, FIONA, ELLE, BENSON, GEORGE, ALICE, DANIEL));
    }

    /**
     * Wrapper function to test sorting
     *
     * @param parameter Patient attribute to sort by
     * @param isReverse Order of sorting
     * @param expectedList Expected Order
     * @throws ParseException
     */
    private void execute_sortPatientParameter(String parameter, boolean isReverse,
                                              List<Person> expectedList) throws ParseException {
        String expectedMessage = String.format(SortCommand.MESSAGE_SUCCESS, parameter);
        Comparator<Patient> patientComparator = PatientComparator.getPatientComparator(parameter);
        SortCommand command = new SortPatientCommand(patientComparator, parameter, isReverse);
        expectedModel.sortAddressBook(patientComparator, isReverse);
        expectedModel.commitAddressBook();
        assertCommandSuccess(command, model, commandHistory, expectedMessage, expectedModel);
        assertEquals(expectedList, model.getFilteredPersonList());
    }

}
