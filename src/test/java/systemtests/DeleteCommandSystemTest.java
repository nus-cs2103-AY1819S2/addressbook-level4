package systemtests;

import static org.junit.Assert.assertTrue;
import static seedu.pdf.commons.core.Messages.MESSAGE_INVALID_PDF_DISPLAYED_INDEX;
import static seedu.pdf.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;
import static seedu.pdf.logic.commands.DeleteCommand.MESSAGE_DELETE_PDF_SUCCESS;
import static seedu.pdf.testutil.TestUtil.getLastIndex;
import static seedu.pdf.testutil.TestUtil.getMidIndex;
import static seedu.pdf.testutil.TestUtil.getPdf;
import static seedu.pdf.testutil.TypicalIndexes.INDEX_FIRST_PDF;
import static seedu.pdf.testutil.TypicalPdfs.KEYWORD_MATCHING_MEIER;

import org.junit.Test;

import seedu.pdf.commons.core.Messages;
import seedu.pdf.commons.core.index.Index;
import seedu.pdf.logic.commands.DeleteCommand;
import seedu.pdf.logic.commands.RedoCommand;
import seedu.pdf.logic.commands.UndoCommand;
import seedu.pdf.model.Model;
import seedu.pdf.model.pdf.Pdf;

public class DeleteCommandSystemTest extends PdfBookSystemTest {

    private static final String MESSAGE_INVALID_DELETE_COMMAND_FORMAT =
            String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT, DeleteCommand.MESSAGE_USAGE);

    @Test
    public void delete() {
        /* ----------------- Performing delete operation while an unfiltered list is being shown -------------------- */

        /* Case: delete the first pdf in the list, command with leading spaces and trailing spaces -> deleted */
        Model expectedModel = getModel();
        String command = "     " + DeleteCommand.COMMAND_WORD + "      " + INDEX_FIRST_PDF.getOneBased() + "       ";
        Pdf deletedPdf = removePdf(expectedModel, INDEX_FIRST_PDF);
        String expectedResultMessage = String.format(MESSAGE_DELETE_PDF_SUCCESS, deletedPdf);
        assertCommandSuccess(command, expectedModel, expectedResultMessage);

        /* Case: delete the last pdf in the list -> deleted */
        Model modelBeforeDeletingLast = getModel();
        Index lastPdfIndex = getLastIndex(modelBeforeDeletingLast);
        assertCommandSuccess(lastPdfIndex);

        /* Case: undo deleting the last pdf in the list -> last pdf restored */
        command = UndoCommand.COMMAND_WORD;
        expectedResultMessage = UndoCommand.MESSAGE_SUCCESS;
        assertCommandSuccess(command, modelBeforeDeletingLast, expectedResultMessage);

        /* Case: redo deleting the last pdf in the list -> last pdf deleted again */
        command = RedoCommand.COMMAND_WORD;
        removePdf(modelBeforeDeletingLast, lastPdfIndex);
        expectedResultMessage = RedoCommand.MESSAGE_SUCCESS;
        assertCommandSuccess(command, modelBeforeDeletingLast, expectedResultMessage);

        /* Case: delete the middle pdf in the list -> deleted */
        Index middlePdfIndex = getMidIndex(getModel());
        assertCommandSuccess(middlePdfIndex);

        /* ------------------ Performing delete operation while a filtered list is being shown ---------------------- */

        /* Case: filtered pdf list, delete index within bounds of pdf book and pdf list -> deleted */
        showPdfWithName(KEYWORD_MATCHING_MEIER);
        Index index = INDEX_FIRST_PDF;
        assertTrue(index.getZeroBased() < getModel().getFilteredPdfList().size());
        assertCommandSuccess(index);

        /* Case: filtered pdf list, delete index within bounds of pdf book but out of bounds of pdf list
         * -> rejected
         */
        showPdfWithName(KEYWORD_MATCHING_MEIER);
        int invalidIndex = getModel().getPdfBook().getPdfList().size();
        command = DeleteCommand.COMMAND_WORD + " " + invalidIndex;
        assertCommandFailure(command, MESSAGE_INVALID_PDF_DISPLAYED_INDEX);

        /* --------------------- Performing delete operation while a pdf card is selected ------------------------ */

        /* Case: delete the selected pdf -> pdf list panel selects the pdf before the deleted pdf */
        showAllPdfs();
        expectedModel = getModel();
        Index selectedIndex = getLastIndex(expectedModel);
        Index expectedIndex = Index.fromZeroBased(selectedIndex.getZeroBased() - 1);
        selectPdf(selectedIndex);
        command = DeleteCommand.COMMAND_WORD + " " + selectedIndex.getOneBased();
        deletedPdf = removePdf(expectedModel, selectedIndex);
        expectedResultMessage = String.format(MESSAGE_DELETE_PDF_SUCCESS, deletedPdf);
        assertCommandSuccess(command, expectedModel, expectedResultMessage, expectedIndex);

        /* --------------------------------- Performing invalid delete operation ------------------------------------ */

        /* Case: invalid index (0) -> rejected */
        command = DeleteCommand.COMMAND_WORD + " 0";
        assertCommandFailure(command, MESSAGE_INVALID_DELETE_COMMAND_FORMAT);

        /* Case: invalid index (-1) -> rejected */
        command = DeleteCommand.COMMAND_WORD + " -1";
        assertCommandFailure(command, MESSAGE_INVALID_DELETE_COMMAND_FORMAT);

        /* Case: invalid index (size + 1) -> rejected */
        Index outOfBoundsIndex = Index.fromOneBased(
                getModel().getPdfBook().getPdfList().size() + 1);
        command = DeleteCommand.COMMAND_WORD + " " + outOfBoundsIndex.getOneBased();
        assertCommandFailure(command, MESSAGE_INVALID_PDF_DISPLAYED_INDEX);

        /* Case: invalid arguments (alphabets) -> rejected */
        assertCommandFailure(DeleteCommand.COMMAND_WORD + " abc", MESSAGE_INVALID_DELETE_COMMAND_FORMAT);

        /* Case: invalid arguments (extra argument) -> rejected */
        assertCommandFailure(DeleteCommand.COMMAND_WORD + " 1 abc", MESSAGE_INVALID_DELETE_COMMAND_FORMAT);

        /* Case: mixed case command word -> rejected */
        assertCommandFailure("DelETE 1", MESSAGE_UNKNOWN_COMMAND);
    }

    /**
     * Removes the {@code Pdf} at the specified {@code index} in {@code model}'s pdf book.
     * @return the removed pdf
     */
    private Pdf removePdf(Model model, Index index) {
        Pdf targetPdf = getPdf(model, index);
        model.deletePdf(targetPdf);
        return targetPdf;
    }

    /**
     * Deletes the pdf at {@code toDelete} by creating a default {@code DeleteCommand} using {@code toDelete} and
     * performs the same verification as {@code assertCommandSuccess(String, Model, String)}.
     * @see DeleteCommandSystemTest#assertCommandSuccess(String, Model, String)
     */
    private void assertCommandSuccess(Index toDelete) {
        Model expectedModel = getModel();
        Pdf deletedPdf = removePdf(expectedModel, toDelete);
        String expectedResultMessage = String.format(MESSAGE_DELETE_PDF_SUCCESS, deletedPdf);

        assertCommandSuccess(
                DeleteCommand.COMMAND_WORD + " " + toDelete.getOneBased(), expectedModel, expectedResultMessage);
    }

    /**
     * Executes {@code command} and in addition,<br>
     * 1. Asserts that the command box displays an empty string.<br>
     * 2. Asserts that the result display box displays {@code expectedResultMessage}.<br>
     * 3. Asserts that the browser url and selected card remains unchanged.<br>
     * 4. Asserts that the status bar's sync status changes.<br>
     * 5. Asserts that the command box has the default style class.<br>
     * Verifications 1 and 2 are performed by
     * {@code PdfBookSystemTest#assertApplicationDisplaysExpected(String, String, Model)}.
     * @see PdfBookSystemTest#assertApplicationDisplaysExpected(String, String, Model)
     */
    private void assertCommandSuccess(String command, Model expectedModel, String expectedResultMessage) {
        assertCommandSuccess(command, expectedModel, expectedResultMessage, null);
    }

    /**
     * Performs the same verification as {@code assertCommandSuccess(String, Model, String)} except that the browser url
     * and selected card are expected to update accordingly depending on the card at {@code expectedSelectedCardIndex}.
     * @see DeleteCommandSystemTest#assertCommandSuccess(String, Model, String)
     * @see PdfBookSystemTest#assertSelectedCardChanged(Index)
     */
    private void assertCommandSuccess(String command, Model expectedModel, String expectedResultMessage,
            Index expectedSelectedCardIndex) {
        executeCommand(command);
        assertApplicationDisplaysExpected("", expectedResultMessage, expectedModel);

        if (expectedSelectedCardIndex != null) {
            assertSelectedCardChanged(expectedSelectedCardIndex);
        } else {
            assertSelectedCardUnchanged();
        }

        assertCommandBoxShowsDefaultStyle();
        assertStatusBarUnchangedExceptSyncStatus();
    }

    /**
     * Executes {@code command} and in addition,<br>
     * 1. Asserts that the command box displays {@code command}.<br>
     * 2. Asserts that result display box displays {@code expectedResultMessage}.<br>
     * 3. Asserts that the browser url, selected card and status bar remain unchanged.<br>
     * 4. Asserts that the command box has the error style.<br>
     * Verifications 1 and 2 are performed by
     * {@code PdfBookSystemTest#assertApplicationDisplaysExpected(String, String, Model)}.<br>
     * @see PdfBookSystemTest#assertApplicationDisplaysExpected(String, String, Model)
     */
    private void assertCommandFailure(String command, String expectedResultMessage) {
        Model expectedModel = getModel();

        executeCommand(command);
        assertApplicationDisplaysExpected(command, expectedResultMessage, expectedModel);
        assertSelectedCardUnchanged();
        assertCommandBoxShowsErrorStyle();
        assertStatusBarUnchanged();
    }
}
