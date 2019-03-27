package seedu.address.logic.commands;

import static seedu.address.logic.commands.CommandTestUtil.DEADLINE_1_VALID;
import static seedu.address.logic.commands.CommandTestUtil.DEADLINE_DATE_INVALID;
import static seedu.address.logic.commands.CommandTestUtil.DEADLINE_FORMAT_INVALID;
import static seedu.address.testutil.TypicalPdfs.getTypicalPdfBook;

import java.time.format.DateTimeParseException;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.CommandHistory;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.pdf.Deadline;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;


public class DeadlineCommandTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private Model model = new ModelManager(getTypicalPdfBook(), new UserPrefs());
    private CommandHistory commandHistory = new CommandHistory();

    @Test
    public void constructor_invalidIndex_throwsIndexOutOfBoundsException() {
        thrown.expect(IndexOutOfBoundsException.class);
        new DeadlineCommand(Index.fromZeroBased(-1), new Deadline(DEADLINE_1_VALID));
    }

    @Test
    public void constructor_invalidDateDeadline_throwsDateTimeParseException() {
        thrown.expect(DateTimeParseException.class);
        new DeadlineCommand(Index.fromOneBased(1), new Deadline(DEADLINE_DATE_INVALID));
    }

    @Test
    public void constructor_invalidFormatDeadline_throwsDateTimeParseException() {
        thrown.expect(DateTimeParseException.class);
        new DeadlineCommand(Index.fromOneBased(1), new Deadline(DEADLINE_FORMAT_INVALID));
    }

    @Test
    public void constructor_validDeadline_success() {
        //new DeadlineCommand(Index.fromOneBased(1), new Deadline(DEADLINE_1_VALID));
    }
}
