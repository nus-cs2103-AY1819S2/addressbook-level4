package seedu.pdf.logic.commands;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.pdf.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.pdf.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.pdf.logic.commands.CommandTestUtil.showPdfAtIndex;
import static seedu.pdf.testutil.TypicalIndexes.INDEX_FIRST_PDF;
import static seedu.pdf.testutil.TypicalIndexes.INDEX_SECOND_PDF;
import static seedu.pdf.testutil.TypicalIndexes.INDEX_THIRD_PDF;
import static seedu.pdf.testutil.TypicalPdfs.getTypicalPdfBook;

import org.junit.Test;

import seedu.pdf.commons.core.Messages;
import seedu.pdf.commons.core.index.Index;
import seedu.pdf.logic.CommandHistory;
import seedu.pdf.model.Model;
import seedu.pdf.model.ModelManager;
import seedu.pdf.model.UserPrefs;

/**
 * Contains integration tests (interaction with the Model) for {@code SelectCommand}.
 */
public class SelectCommandTest {
    private Model model = new ModelManager(getTypicalPdfBook(), new UserPrefs());
    private Model expectedModel = new ModelManager(getTypicalPdfBook(), new UserPrefs());
    private CommandHistory commandHistory = new CommandHistory();

    @Test
    public void execute_validIndexUnfilteredList_success() {
        Index lastPdfIndex = Index.fromOneBased(model.getFilteredPdfList().size());

        assertExecutionSuccess(INDEX_FIRST_PDF);
        assertExecutionSuccess(INDEX_THIRD_PDF);
        assertExecutionSuccess(lastPdfIndex);
    }

    @Test
    public void execute_invalidIndexUnfilteredList_failure() {
        Index outOfBoundsIndex = Index.fromOneBased(model.getFilteredPdfList().size() + 1);

        assertExecutionFailure(outOfBoundsIndex, Messages.MESSAGE_INVALID_PDF_DISPLAYED_INDEX);
    }

    @Test
    public void execute_validIndexFilteredList_success() {
        showPdfAtIndex(model, INDEX_FIRST_PDF);
        showPdfAtIndex(expectedModel, INDEX_FIRST_PDF);

        assertExecutionSuccess(INDEX_FIRST_PDF);
    }

    @Test
    public void execute_invalidIndexFilteredList_failure() {
        showPdfAtIndex(model, INDEX_FIRST_PDF);
        showPdfAtIndex(expectedModel, INDEX_FIRST_PDF);

        Index outOfBoundsIndex = INDEX_SECOND_PDF;
        // ensures that outOfBoundIndex is still in bounds of pdf book list
        assertTrue(outOfBoundsIndex.getZeroBased() < model.getPdfBook().getPdfList().size());

        assertExecutionFailure(outOfBoundsIndex, Messages.MESSAGE_INVALID_PDF_DISPLAYED_INDEX);
    }

    @Test
    public void equals() {
        SelectCommand selectFirstCommand = new SelectCommand(INDEX_FIRST_PDF);
        SelectCommand selectSecondCommand = new SelectCommand(INDEX_SECOND_PDF);

        // same object -> returns true
        assertTrue(selectFirstCommand.equals(selectFirstCommand));

        // same values -> returns true
        SelectCommand selectFirstCommandCopy = new SelectCommand(INDEX_FIRST_PDF);
        assertTrue(selectFirstCommand.equals(selectFirstCommandCopy));

        // different types -> returns false
        assertFalse(selectFirstCommand.equals(1));

        // null -> returns false
        assertFalse(selectFirstCommand.equals(null));

        // different pdf -> returns false
        assertFalse(selectFirstCommand.equals(selectSecondCommand));
    }

    /**
     * Executes a {@code SelectCommand} with the given {@code index},
     * and checks that the model's selected pdf is set to the pdf at {@code index} in the filtered pdf list.
     */
    private void assertExecutionSuccess(Index index) {
        SelectCommand selectCommand = new SelectCommand(index);
        String expectedMessage = String.format(SelectCommand.MESSAGE_SELECT_PDF_SUCCESS, index.getOneBased());
        expectedModel.setSelectedPdf(model.getFilteredPdfList().get(index.getZeroBased()));

        assertCommandSuccess(selectCommand, model, commandHistory, expectedMessage, expectedModel);
    }

    /**
     * Executes a {@code SelectCommand} with the given {@code index}, and checks that a {@code CommandException}
     * is thrown with the {@code expectedMessage}.
     */
    private void assertExecutionFailure(Index index, String expectedMessage) {
        SelectCommand selectCommand = new SelectCommand(index);
        assertCommandFailure(selectCommand, model, commandHistory, expectedMessage);
    }
}
