package seedu.address.logic.commands;

import static seedu.address.testutil.TypicalPdfs.getTypicalPdfBook;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.CommandHistory;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;


public class EncryptCommandTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private Model model = new ModelManager(getTypicalPdfBook(), new UserPrefs());
    private CommandHistory commandHistory = new CommandHistory();

    @Test
    public void constructor_invalidIndex_throwsNullPointerException() {
        thrown.expect(IndexOutOfBoundsException.class);
        new EncryptCommand((Index.fromZeroBased(-1)), "123");

        thrown.expect(IndexOutOfBoundsException.class);
        new EncryptCommand((Index.fromZeroBased(model.getFilteredPdfList().size() + 1)), "123");
    }

    @Test
    public void constructor_nullPassword_throwsNullPointerException() {
        thrown.expect(IndexOutOfBoundsException.class);
        new EncryptCommand((Index.fromZeroBased(1)), null);
    }
}
