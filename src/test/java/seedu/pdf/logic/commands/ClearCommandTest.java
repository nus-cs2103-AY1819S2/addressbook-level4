package seedu.pdf.logic.commands;

import static seedu.pdf.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.pdf.testutil.TypicalPdfs.getTypicalPdfBook;

import org.junit.Test;

import seedu.pdf.logic.CommandHistory;
import seedu.pdf.model.Model;
import seedu.pdf.model.ModelManager;
import seedu.pdf.model.PdfBook;
import seedu.pdf.model.UserPrefs;

public class ClearCommandTest {

    private static final CommandHistory EMPTY_COMMAND_HISTORY = new CommandHistory();

    @Test
    public void execute_emptyPdfBook_success() {
        Model model = new ModelManager();
        Model expectedModel = new ModelManager();
        expectedModel.commitPdfBook();

        assertCommandSuccess(new ClearCommand(), model,
                EMPTY_COMMAND_HISTORY, ClearCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void execute_nonEmptyPdfBook_success() {
        Model model = new ModelManager(getTypicalPdfBook(), new UserPrefs());
        Model expectedModel = new ModelManager(getTypicalPdfBook(), new UserPrefs());
        expectedModel.setPdfBook(new PdfBook());
        expectedModel.commitPdfBook();

        assertCommandSuccess(new ClearCommand(), model,
                EMPTY_COMMAND_HISTORY, ClearCommand.MESSAGE_SUCCESS, expectedModel);
    }
}
