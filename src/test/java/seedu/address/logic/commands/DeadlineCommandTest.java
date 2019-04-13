package seedu.address.logic.commands;

import static junit.framework.TestCase.assertTrue;
import static junit.framework.TestCase.fail;
import static org.junit.Assert.assertFalse;
import static seedu.address.testutil.TypicalPdfs.getTypicalPdfBook;

import java.time.LocalDate;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.CommandHistory;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.pdf.Deadline;

public class DeadlineCommandTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private Model model = new ModelManager(getTypicalPdfBook(), new UserPrefs());
    private CommandHistory commandHistory = new CommandHistory();

    @Test
    public void constructor_invalidIndex_throwsIndexOutOfBoundsException() {
        thrown.expect(IndexOutOfBoundsException.class);
        new DeadlineCommand(Index.fromZeroBased(model.getFilteredPdfList().size() + 1),
                new Deadline(), DeadlineCommand.DeadlineAction.NEW);
    }

    @Test
    public void constructor_invalidNegativeIndex_throwsIndexOutOfBoundsException() {
        thrown.expect(IndexOutOfBoundsException.class);
        new DeadlineCommand(Index.fromZeroBased(-1), new Deadline(), DeadlineCommand.DeadlineAction.NEW);
    }

    @Test
    public void constructor_nullIndex_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        new DeadlineCommand(null, new Deadline(), DeadlineCommand.DeadlineAction.NEW);
    }

    @Test
    public void constructor_nullDeadline_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        new DeadlineCommand(Index.fromZeroBased(1), null, DeadlineCommand.DeadlineAction.NEW);
    }

    @Test
    public void constructor_nullDeadlineAction_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        new DeadlineCommand(Index.fromZeroBased(1), new Deadline(), null);
    }

    @Test
    public void execute_validNew_success() {
        try {
            DeadlineCommand standardCommand = new DeadlineCommand(Index.fromOneBased(1), new Deadline(),
                    DeadlineCommand.DeadlineAction.NEW);
            standardCommand.execute(this.model, commandHistory);
        } catch (Exception e) {
            fail();
        }
    }

    @Test
    public void execute_validDone_success() {
        try {
            DeadlineCommand standardCommand = new DeadlineCommand(Index.fromOneBased(1), new Deadline(),
                    DeadlineCommand.DeadlineAction.DONE);
            standardCommand.execute(this.model, commandHistory);
        } catch (Exception e) {
            fail();
        }
    }

    @Test
    public void execute_validRemove_success() {
        try {
            DeadlineCommand standardCommand = new DeadlineCommand(Index.fromOneBased(1), new Deadline(),
                    DeadlineCommand.DeadlineAction.REMOVE);
            standardCommand.execute(this.model, commandHistory);
        } catch (Exception e) {
            fail();
        }
    }

    @Test
    public void equals() {
        final DeadlineCommand standardCommand = new DeadlineCommand(Index.fromOneBased(1), new Deadline(),
                DeadlineCommand.DeadlineAction.NEW);

        // same value -> returns true
        DeadlineCommand standardCommandCopy = new DeadlineCommand(Index.fromOneBased(1), new Deadline(),
                DeadlineCommand.DeadlineAction.NEW);
        assertTrue(standardCommand.equals(standardCommandCopy));

        // same object -> returns true
        assertTrue(standardCommand.equals(standardCommand));

        // null -> returns false
        assertFalse(standardCommand.equals(null));

        // different types -> returns false
        assertFalse(standardCommand.equals(new ClearCommand()));

        // different index -> returns false
        assertFalse(standardCommand.equals(new DeadlineCommand(Index.fromOneBased(2), new Deadline(),
                DeadlineCommand.DeadlineAction.NEW)));

        // different deadline -> returns false
        assertFalse(standardCommand.equals(new DeadlineCommand(Index.fromOneBased(1),
                new Deadline(LocalDate.of(2019, 4, 12)), DeadlineCommand.DeadlineAction.NEW)));

        // different action -> returns false
        assertFalse(standardCommand.equals(new DeadlineCommand(Index.fromOneBased(1), new Deadline(),
                DeadlineCommand.DeadlineAction.DONE)));
    }
}
