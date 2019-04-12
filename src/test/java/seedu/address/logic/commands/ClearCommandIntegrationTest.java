package seedu.address.logic.commands;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalPdfs.getTypicalPdfBook;

import org.junit.Before;
import org.junit.Test;

import seedu.address.logic.CommandHistory;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.PdfBook;
import seedu.address.model.UserPrefs;

public class ClearCommandIntegrationTest {
    private Model model;
    private CommandHistory commandHistory = new CommandHistory();

    @Before
    public void setUp() {
        model = new ModelManager(getTypicalPdfBook(), new UserPrefs());
    }

    @Test
    public void execute_clearModel_success() {

        Model expectedModel = new ModelManager(getTypicalPdfBook(), new UserPrefs());
        expectedModel.setPdfBook(new PdfBook());
        expectedModel.commitPdfBook();

        assertCommandSuccess(new ClearCommand(), model, commandHistory,
                ClearCommand.MESSAGE_SUCCESS, expectedModel);
    }
}
