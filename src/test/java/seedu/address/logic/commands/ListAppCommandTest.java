package seedu.address.logic.commands;

import java.time.LocalDate;
import java.util.Optional;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.patient.Nric;
import seedu.address.model.patient.Patient;


public class ListAppCommandTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();
    private Model model = new ModelManager(new AddressBook(), new UserPrefs());
    private CommandHistory commandHistory = new CommandHistory();

    @Before
    public void init() {
        model.initQuickDocsSampleData();
    }

    @Test
    public void executeListApp_searchByDate() throws Exception {
        LocalDate start = LocalDate.of(2019, 4, 1);
        LocalDate end = LocalDate.of(2019, 4, 30);
        CommandResult result = new ListAppCommand(start, end).execute(model, commandHistory);
        String expected = String.format(ListAppCommand.MESSAGE_SUCCESS_BY_DATE, start, end)
                + model.listApp(start, end);

        Assert.assertEquals(result.getFeedbackToUser(), expected);

        // no results to show
        end = LocalDate.of(2019, 4, 1);
        result = new ListAppCommand(start, end).execute(model, commandHistory);
        expected = String.format(ListAppCommand.MESSAGE_SUCCESS_BY_DATE, start, end)
                + model.listApp(start, end);

        Assert.assertEquals(result.getFeedbackToUser(), expected);
    }

    @Test
    public void executeListApp_searchByNric_success() throws Exception {
        Nric nric = new Nric("S9367777A");
        Optional<Patient> patientToList = model.getPatientWithNric(nric);
        if (!patientToList.isPresent()) {
            throw new CommandException(ListAppCommand.MESSAGE_PATIENT_NOT_FOUND);
        }
        CommandResult result = new ListAppCommand(nric).execute(model, commandHistory);
        String expected = String.format(ListAppCommand.MESSAGE_SUCCESS_BY_NRIC, patientToList.get().getName())
                + model.listApp(patientToList.get());

        Assert.assertEquals(result.getFeedbackToUser(), expected);
    }

    @Test
    public void executeListApp_searchByNric_failure() throws Exception {
        Nric nric = new Nric("S9367788A");
        ListAppCommand listAppCommand = new ListAppCommand(nric);

        thrown.expect(CommandException.class);
        thrown.expectMessage(ListAppCommand.MESSAGE_PATIENT_NOT_FOUND);
        listAppCommand.execute(model, commandHistory);
    }

    @Test
    public void equals() {
        Nric nric = new Nric("S9367777A");
        LocalDate start = LocalDate.parse("2019-10-23");
        LocalDate end = LocalDate.parse("2019-11-24");
        ListAppCommand listAppA = new ListAppCommand(start, end);
        ListAppCommand listAppB = new ListAppCommand(end, start);
        ListAppCommand listAppC = new ListAppCommand(nric);

        // same object -> returns true
        Assert.assertEquals(listAppA, listAppA);

        // same values -> returns true
        ListAppCommand listAppCopy = new ListAppCommand(start, end);
        Assert.assertEquals(listAppA, listAppCopy);

        // different types -> returns false
        Assert.assertNotEquals(listAppA, 1);

        // null -> returns false
        Assert.assertNotEquals(listAppA, null);

        // different attributes -> returns false
        Assert.assertNotEquals(listAppA, listAppB);
        Assert.assertNotEquals(listAppA, listAppC);
    }
}
