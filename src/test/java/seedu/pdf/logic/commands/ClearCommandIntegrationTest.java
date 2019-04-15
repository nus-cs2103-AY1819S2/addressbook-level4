package seedu.pdf.logic.commands;

import static seedu.pdf.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.pdf.testutil.TypicalPdfs.getTypicalPdfBook;

import org.junit.Before;
import org.junit.Test;

import seedu.pdf.logic.CommandHistory;
import seedu.pdf.model.Model;
import seedu.pdf.model.ModelManager;
import seedu.pdf.model.PdfBook;
import seedu.pdf.model.UserPrefs;

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
