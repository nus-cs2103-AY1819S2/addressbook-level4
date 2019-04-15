package seedu.pdf.logic.commands;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import seedu.pdf.logic.CommandHistory;
import seedu.pdf.model.Model;
import seedu.pdf.model.ModelManager;
import seedu.pdf.model.UserPrefs;

import static seedu.pdf.testutil.TypicalPdfs.getTypicalPdfBook;

public class SortCommandTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private Model model = new ModelManager(getTypicalPdfBook(), new UserPrefs());
    private CommandHistory commandHistory = new CommandHistory();

    @Test
    public void constructor_invalidNullComparator_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        new SortCommand(null);
    }
}
