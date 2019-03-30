package seedu.address.logic.commands;

import static seedu.address.testutil.TypicalAppointments.APP_A;
import static seedu.address.testutil.TypicalAppointments.getTypicalAppointmentsQuickDocs;
import static seedu.address.testutil.TypicalPatients.ALICE;
import static seedu.address.testutil.TypicalPatients.EVE;

import java.time.LocalDate;

import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.QuickDocs;
import seedu.address.model.UserPrefs;
import seedu.address.model.patient.Nric;

public class ListAppCommandTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private QuickDocs quickDocs = getTypicalAppointmentsQuickDocs();
    private Model model = new ModelManager(new AddressBook(), quickDocs, new UserPrefs());
    private CommandHistory commandHistory = new CommandHistory();

    @Test
    public void executeListApp_searchByDate() throws Exception {
        LocalDate start = LocalDate.of(2019, 10, 1);
        LocalDate end = LocalDate.of(2019, 10, 30);
        CommandResult result = new ListAppCommand(start, end).execute(model, commandHistory);
        String expected = String.format(ListAppCommand.MESSAGE_SUCCESS_BY_DATE, start, end)
                + model.listApp(start, end);

        Assert.assertEquals(result.getFeedbackToUser(), expected);

        // no results to show
        end = LocalDate.of(2019, 10, 1);
        result = new ListAppCommand(start, end).execute(model, commandHistory);
        expected = String.format(ListAppCommand.MESSAGE_SUCCESS_BY_DATE, start, end)
                + model.listApp(start, end);

        Assert.assertEquals(result.getFeedbackToUser(), expected);
    }

    @Test
    public void executeListApp_searchByNric_success() throws Exception {
        Nric nric = ALICE.getNric();
        CommandResult result = new ListAppCommand(nric).execute(model, commandHistory);
        String expected = String.format(ListAppCommand.MESSAGE_SUCCESS_BY_NRIC, ALICE.getName())
                + model.listApp(ALICE);

        Assert.assertEquals(result.getFeedbackToUser(), expected);
    }

    @Test
    public void executeListApp_searchByNric_failure() throws Exception {
        Nric nric = EVE.getNric();
        ListAppCommand listAppCommand = new ListAppCommand(nric);

        thrown.expect(CommandException.class);
        thrown.expectMessage(ListAppCommand.MESSAGE_PATIENT_NOT_FOUND);
        listAppCommand.execute(model, commandHistory);
    }

    @Test
    public void equals() {
        Nric nric = ALICE.getNric();
        LocalDate start = APP_A.getDate();
        LocalDate end = start.plusDays(7);
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
