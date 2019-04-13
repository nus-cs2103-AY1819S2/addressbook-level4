package quickdocs.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import static quickdocs.logic.commands.CommandTestUtil.assertCommandSuccess;
import static quickdocs.testutil.TypicalReminders.REM_B;
import static quickdocs.testutil.TypicalReminders.REM_C;
import static quickdocs.testutil.TypicalReminders.REM_D;
import static quickdocs.testutil.TypicalReminders.getTypicalRemindersQuickDocs;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Collections;

import org.junit.Rule;
import org.junit.Test;

import org.junit.rules.ExpectedException;

import quickdocs.commons.core.index.Index;
import quickdocs.logic.CommandHistory;
import quickdocs.logic.commands.exceptions.CommandException;
import quickdocs.model.Model;
import quickdocs.model.ModelManager;
import quickdocs.model.QuickDocs;
import quickdocs.model.UserPrefs;
import quickdocs.model.reminder.ReminderWithinDatesPredicate;

/**
 * Contains integration tests (interaction with the Model) and unit tests for {@code ListRemCommand}.
 */
public class ListRemCommandTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private QuickDocs quickDocs = getTypicalRemindersQuickDocs();
    private Model model = new ModelManager(quickDocs, new UserPrefs());
    private CommandHistory commandHistory = new CommandHistory();
    private LocalDate testStart = LocalDate.parse("2019-10-24");
    private LocalDate testEnd = LocalDate.parse("2019-10-26");
    private Index targetIndex = Index.fromOneBased(1);

    @Test
    public void executeListRem_listSingleReminder_reminderFound() throws Exception {
        // reminder with index 1 is REM_B
        ReminderWithinDatesPredicate predicate = new ReminderWithinDatesPredicate(testStart, testEnd);
        model.updateFilteredReminderList(predicate);

        CommandResult result = new ListRemCommand(targetIndex).execute(model, commandHistory);
        String expected = String.format(ListRemCommand.MESSAGE_SINGLE_REMINDER_SUCCESS,
                targetIndex.getOneBased(), REM_B);
        assertEquals(result.getFeedbackToUser(), expected);
    }

    @Test
    public void executeListRem_listSingleReminder_reminderNotFound() throws Exception {
        // no reminder of index 10
        Index targetIndex = Index.fromOneBased(10);
        ReminderWithinDatesPredicate predicate = new ReminderWithinDatesPredicate(testStart, testEnd);
        model.updateFilteredReminderList(predicate);

        thrown.expect(CommandException.class);
        thrown.expectMessage(ListRemCommand.MESSAGE_INVALID_REMINDER_INDEX);
        new ListRemCommand(targetIndex).execute(model, commandHistory);
    }

    @Test
    public void executeListRem_listByFormatAndDate_noReminderFound() {
        LocalDate testStart = LocalDate.parse("2019-01-01");
        LocalDate testEnd = LocalDate.parse("2019-02-01");
        String expectedMessage = String.format(ListRemCommand.MESSAGE_SUCCESS, testStart, testEnd);
        ListRemCommand command = new ListRemCommand(testStart, testEnd);

        ReminderWithinDatesPredicate predicate = new ReminderWithinDatesPredicate(testStart, testEnd);
        Model expectedModel = new ModelManager(getTypicalRemindersQuickDocs(), new UserPrefs());
        expectedModel.updateFilteredReminderList(predicate);
        assertCommandSuccess(command, model, commandHistory, expectedMessage, expectedModel);
        assertEquals(Collections.emptyList(), model.getFilteredReminderList());
    }

    @Test
    public void executeListRem_listByFormatAndDate_remindersFound() {
        String expectedMessage = String.format(ListRemCommand.MESSAGE_SUCCESS, testStart, testEnd);
        ListRemCommand command = new ListRemCommand(testStart, testEnd);

        ReminderWithinDatesPredicate predicate = new ReminderWithinDatesPredicate(testStart, testEnd);
        Model expectedModel = new ModelManager(getTypicalRemindersQuickDocs(), new UserPrefs());
        expectedModel.updateFilteredReminderList(predicate);
        assertCommandSuccess(command, model, commandHistory, expectedMessage, expectedModel);
        assertEquals(Arrays.asList(REM_B, REM_C, REM_D), model.getFilteredReminderList());
    }

    @Test
    public void equals() {
        ListRemCommand listRemA = new ListRemCommand(testStart, testEnd);

        // same object -> returns true
        assertEquals(listRemA, listRemA);

        // same values -> returns true
        ListRemCommand listRemACopy = new ListRemCommand(testStart, testEnd);
        assertEquals(listRemA, listRemACopy);

        // different types -> returns false
        assertNotEquals(listRemA, 1);

        // null -> returns false
        assertNotEquals(listRemA, null);

        // different start date -> returns false
        ListRemCommand listRemB = new ListRemCommand(testStart.minusDays(1), testEnd);
        assertNotEquals(listRemA, listRemB);

        // start date null -> returns false
        listRemB = new ListRemCommand(null, testEnd);
        assertNotEquals(listRemA, listRemB);

        // different end date -> returns false
        listRemB = new ListRemCommand(testStart, testEnd.minusDays(1));
        assertNotEquals(listRemA, listRemB);

        // end date null -> returns false
        listRemB = new ListRemCommand(testStart, null);
        assertNotEquals(listRemA, listRemB);

        // different targetIndex -> returns false
        listRemA = new ListRemCommand(targetIndex);
        listRemB = new ListRemCommand(Index.fromOneBased(2));
        assertNotEquals(listRemA, listRemB);

        // targetIndex null -> returns false
        listRemB = new ListRemCommand(null);
        assertNotEquals(listRemA, listRemB);
    }
}
