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
    public void execute_sortParameters_orderDefault() throws ParseException {
        execute_sortParameter("name", false, Arrays.asList(ALICE, BENSON, CARL, DANIEL, ELLE, FIONA, GEORGE));
        execute_sortParameter("phone", false, Arrays.asList(DANIEL, ALICE, ELLE, FIONA, GEORGE, CARL, BENSON));
        execute_sortParameter("address", false, Arrays.asList(DANIEL, ALICE, BENSON, GEORGE, FIONA, ELLE, CARL));
        execute_sortParameter("email", false, Arrays.asList(ALICE, GEORGE, DANIEL, CARL, BENSON, FIONA, ELLE));
        execute_sortParameter("nric", false, Arrays.asList(ALICE, BENSON, CARL, DANIEL, ELLE, FIONA, GEORGE));
        execute_sortParameter("dob", false, Arrays.asList(FIONA, ALICE, CARL, GEORGE, ELLE, BENSON, DANIEL));
    }

    @Test
    public void execute_sortParameters_orderReverse() throws ParseException {
        execute_sortParameter("name", true, Arrays.asList(GEORGE, FIONA, ELLE, DANIEL, CARL, BENSON, ALICE));
        execute_sortParameter("phone", true, Arrays.asList(BENSON, CARL, GEORGE, FIONA, ELLE, ALICE, DANIEL));
        execute_sortParameter("address", true, Arrays.asList(CARL, ELLE, FIONA, GEORGE, BENSON, ALICE, DANIEL));
        execute_sortParameter("email", true, Arrays.asList(ELLE, FIONA, BENSON, CARL, DANIEL, GEORGE, ALICE));
        execute_sortParameter("nric", true, Arrays.asList(GEORGE, FIONA, ELLE, DANIEL, CARL, BENSON, ALICE));
        execute_sortParameter("dob", true, Arrays.asList(DANIEL, BENSON, ELLE, GEORGE, CARL, ALICE, FIONA));
    }

    /**
     * Wrapper function to test sorting
     *
     * @param parameter Patient attribute to sort by
     * @param isReverse Order of sorting
     * @param expectedList Expected Order
     * @throws ParseException
     */
    public void execute_sortParameter (String parameter, boolean isReverse,
                                       List<Person> expectedList) throws ParseException {
        String expectedMessage = String.format(SortCommand.MESSAGE_SUCCESS, parameter);
        Comparator<Patient> patientComparator = PatientComparator.getPatientComparator(parameter);
        SortCommand command = new SortCommand(patientComparator, parameter, isReverse);
        expectedModel.sortAddressBook(patientComparator, isReverse);
        expectedModel.commitAddressBook();
        assertCommandSuccess(command, model, commandHistory, expectedMessage, expectedModel);
        assertEquals(expectedList, model.getFilteredPersonList());
    }

}
