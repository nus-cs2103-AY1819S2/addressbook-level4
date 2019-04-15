package seedu.pdf.logic.commands;

import static seedu.pdf.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.pdf.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.pdf.logic.commands.CommandTestUtil.deleteFirstPdf;
import static seedu.pdf.testutil.TypicalPdfs.getTypicalPdfBook;

import org.junit.Before;
import org.junit.Test;

import seedu.pdf.logic.CommandHistory;
import seedu.pdf.model.Model;
import seedu.pdf.model.ModelManager;
import seedu.pdf.model.UserPrefs;

public class RedoCommandTest {

    private final Model model = new ModelManager(getTypicalPdfBook(), new UserPrefs());
    private final Model expectedModel = new ModelManager(getTypicalPdfBook(), new UserPrefs());
    private final CommandHistory commandHistory = new CommandHistory();

    @Before
    public void setUp() {
        // set up of both models' undo/redo history
        deleteFirstPdf(model);
        deleteFirstPdf(model);
        model.undoPdfBook();
        model.undoPdfBook();

        deleteFirstPdf(expectedModel);
        deleteFirstPdf(expectedModel);
        expectedModel.undoPdfBook();
        expectedModel.undoPdfBook();
    }

    @Test
    public void execute() {
        // multiple redoable states in model
        expectedModel.redoPdfBook();
        assertCommandSuccess(new RedoCommand(), model, commandHistory, RedoCommand.MESSAGE_SUCCESS, expectedModel);

        // single redoable state in model
        expectedModel.redoPdfBook();
        assertCommandSuccess(new RedoCommand(), model, commandHistory, RedoCommand.MESSAGE_SUCCESS, expectedModel);

        // no redoable state in model
        assertCommandFailure(new RedoCommand(), model, commandHistory, RedoCommand.MESSAGE_FAILURE);
    }
}
