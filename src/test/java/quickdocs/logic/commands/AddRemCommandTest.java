package quickdocs.logic.commands;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static quickdocs.testutil.TypicalReminders.REM_A;
import static quickdocs.testutil.TypicalReminders.REM_B;
import static quickdocs.testutil.TypicalReminders.REM_E;
import static quickdocs.testutil.TypicalReminders.getTypicalRemindersQuickDocs;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import quickdocs.logic.CommandHistory;
import quickdocs.logic.commands.exceptions.CommandException;
import quickdocs.model.Model;
import quickdocs.model.ModelManager;
import quickdocs.model.QuickDocs;
import quickdocs.model.UserPrefs;

/**
 * Contains integration tests (interaction with the Model) and unit tests for {@code AddRemCommand}.
 */
public class AddRemCommandTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private QuickDocs quickDocs = getTypicalRemindersQuickDocs();
    private Model model = new ModelManager(quickDocs, new UserPrefs());
    private CommandHistory commandHistory = new CommandHistory();

    @Test
    public void executeValidAddReminder() throws Exception {
        CommandResult commandResult = new AddRemCommand(REM_E).execute(model, commandHistory);

        StringBuilder sb = new StringBuilder();
        sb.append("Reminder added:\n")
                .append(REM_E.toString() + "\n");

        assertEquals(sb.toString(), commandResult.getFeedbackToUser());
    }

    @Test
    public void executeDuplicateAddReminder() throws Exception {
        thrown.expect(CommandException.class);
        thrown.expectMessage(AddRemCommand.MESSAGE_DUPLICATE_REM);
        new AddRemCommand(REM_A).execute(model, commandHistory);
    }

    @Test
    public void equals() {
        AddRemCommand addRemA = new AddRemCommand(REM_A);

        // same object -> returns true
        assertEquals(addRemA, addRemA);

        // same values -> returns true
        AddRemCommand addRemB = new AddRemCommand(REM_A);
        assertEquals(addRemA, addRemB);

        // different types -> returns false
        assertNotEquals(addRemA, 1);

        // null -> returns false
        assertNotEquals(addRemA, null);

        // different reminder -> returns false
        addRemB = new AddRemCommand(REM_B);
        assertNotEquals(addRemA, addRemB);
    }
}
