package quickdocs.logic.commands;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import static quickdocs.testutil.TypicalAppointments.APP_A;
import static quickdocs.testutil.TypicalAppointments.APP_B;
import static quickdocs.testutil.TypicalAppointments.getTypicalAppointmentsQuickDocs;

import java.time.LocalDate;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import quickdocs.logic.CommandHistory;
import quickdocs.logic.commands.exceptions.CommandException;
import quickdocs.model.Model;
import quickdocs.model.ModelManager;
import quickdocs.model.QuickDocs;
import quickdocs.model.UserPrefs;
import quickdocs.model.patient.Nric;
import quickdocs.testutil.TypicalPatients;

/**
 * Contains integration tests (interaction with the Model) and unit tests for {@code ListAppCommand}.
 */
public class ListAppCommandTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private QuickDocs quickDocs = getTypicalAppointmentsQuickDocs();
    private Model model = new ModelManager(quickDocs, new UserPrefs());
    private CommandHistory commandHistory = new CommandHistory();

    @Test
    public void executeListApp_searchByDate() throws Exception {
        LocalDate start = LocalDate.of(2019, 10, 1);
        LocalDate end = LocalDate.of(2019, 10, 30);
        CommandResult result = new ListAppCommand(start, end).execute(model, commandHistory);
        String expected = String.format(ListAppCommand.MESSAGE_SUCCESS_BY_DATE, start, end)
                + model.listApp(start, end);

        assertEquals(result.getFeedbackToUser(), expected);

        // no results to show
        end = LocalDate.of(2019, 10, 1);
        result = new ListAppCommand(start, end).execute(model, commandHistory);
        expected = String.format(ListAppCommand.MESSAGE_SUCCESS_BY_DATE, start, end)
                + model.listApp(start, end);

        assertEquals(result.getFeedbackToUser(), expected);
    }

    @Test
    public void executeListApp_searchByNric_success() throws Exception {
        Nric nric = TypicalPatients.ALICE.getNric();
        CommandResult result = new ListAppCommand(nric).execute(model, commandHistory);
        String expected = String.format(ListAppCommand.MESSAGE_SUCCESS_BY_NRIC, TypicalPatients.ALICE.getName())
                + model.listApp(TypicalPatients.ALICE);

        assertEquals(result.getFeedbackToUser(), expected);
    }

    @Test
    public void executeListApp_searchByNric_failure() throws Exception {
        Nric nric = TypicalPatients.EVE.getNric();
        ListAppCommand listAppCommand = new ListAppCommand(nric);

        thrown.expect(CommandException.class);
        thrown.expectMessage(ListAppCommand.MESSAGE_PATIENT_NOT_FOUND);
        listAppCommand.execute(model, commandHistory);
    }

    @Test
    public void equals() {
        LocalDate start = APP_A.getDate();
        LocalDate end = start.plusDays(7);
        ListAppCommand listAppA = new ListAppCommand(start, end);

        // same object -> returns true
        assertEquals(listAppA, listAppA);

        // same values -> returns true
        ListAppCommand listAppCopy = new ListAppCommand(start, end);
        assertEquals(listAppA, listAppCopy);

        // different types -> returns false
        assertNotEquals(listAppA, 1);

        // null -> returns false
        assertNotEquals(listAppA, null);

        // different start date -> returns false
        ListAppCommand listAppB = new ListAppCommand(start.minusDays(1), end);
        assertNotEquals(listAppA, listAppB);

        // null start date -> returns false
        listAppB = new ListAppCommand(null, end);
        assertNotEquals(listAppA, listAppB);

        // different end date -> returns false
        listAppB = new ListAppCommand(start, end.plusDays(1));
        assertNotEquals(listAppA, listAppB);

        // null end date -> returns false
        listAppB = new ListAppCommand(start, null);
        assertNotEquals(listAppA, listAppB);

        // different NRIC -> returns false
        listAppA = new ListAppCommand(APP_A.getPatient().getNric());
        listAppB = new ListAppCommand(APP_B.getPatient().getNric());
        assertNotEquals(listAppA, listAppB);

        // null NRIC -> returns false
        listAppB = new ListAppCommand(null);
        assertNotEquals(listAppA, listAppB);
    }
}
