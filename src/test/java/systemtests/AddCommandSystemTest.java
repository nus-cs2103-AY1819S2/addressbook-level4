package systemtests;

import static seedu.pdf.logic.commands.CommandTestUtil.DIR_2_VALID;
import static seedu.pdf.logic.commands.CommandTestUtil.FILEPATH_1_VALID;
import static seedu.pdf.logic.parser.CliSyntax.PREFIX_FILE;
import static seedu.pdf.testutil.TypicalPdfs.SAMPLE_PDF_1;
import static seedu.pdf.testutil.TypicalPdfs.SAMPLE_PDF_2;
import static seedu.pdf.testutil.TypicalPdfs.SAMPLE_PDF_2_DUPLICATE;

import org.junit.Test;

import seedu.pdf.logic.commands.AddCommand;
import seedu.pdf.logic.commands.RedoCommand;
import seedu.pdf.logic.commands.UndoCommand;
import seedu.pdf.model.Model;
import seedu.pdf.model.pdf.Pdf;
import seedu.pdf.testutil.PdfBuilder;
import seedu.pdf.testutil.PdfUtil;

public class AddCommandSystemTest extends PdfBookSystemTest {

    @Test
    public void add() {
        Model model = getModel();

        /* ------------------------ Perform add operations on the shown unfiltered list ----------------------------- */

        /* Case: add a pdf without tags to a non-empty pdf book, command with leading spaces and trailing spaces
         * -> added
         */
        Pdf toAdd = SAMPLE_PDF_1;

        String command = AddCommand.COMMAND_WORD + " " + PREFIX_FILE + FILEPATH_1_VALID;
        assertCommandSuccess(command, toAdd);

        /* Case: undo adding SAMPLE_PDF_1 to the list -> Amy deleted */
        command = UndoCommand.COMMAND_WORD;
        String expectedResultMessage = UndoCommand.MESSAGE_SUCCESS;
        assertCommandSuccess(command, model, expectedResultMessage);

        /* Case: redo adding SAMPLE_PDF_1 to the list -> Amy added again */
        command = RedoCommand.COMMAND_WORD;
        model.addPdf(toAdd);
        expectedResultMessage = RedoCommand.MESSAGE_SUCCESS;
        assertCommandSuccess(command, model, expectedResultMessage);

        /* Case: add another pdf with different properties */
        toAdd = new PdfBuilder(SAMPLE_PDF_2).build();
        command = AddCommand.COMMAND_WORD + DIR_2_VALID;
        assertCommandSuccess(command, toAdd);

        /* Case: add a pdf with the same name but different directory -> added*/
        toAdd = SAMPLE_PDF_2_DUPLICATE;
        command = PdfUtil.getAddCommand(toAdd);
        assertCommandSuccess(command, toAdd);

        /* Case: add to empty pdf book -> added */
        deleteAllPdf();
        assertCommandSuccess(SAMPLE_PDF_1);
    }

    /**
     * Executes the {@code AddCommand} that adds {@code toAdd} to the model and asserts that the,<br>
     * 1. Command box displays an empty string.<br>
     * 2. Command box has the default style class.<br>
     * 3. Result display box displays the success message of executing {@code AddCommand} with the details of
     * {@code toAdd}.<br>
     * 4. {@code Storage} and {@code PdfListPanel} equal to the corresponding components in
     * the current model added with {@code toAdd}.<br>
     * 5. Browser url and selected card remain unchanged.<br>
     * 6. Status bar's sync status changes.<br>
     * Verifications 1, 3 and 4 are performed by
     * {@code PdfBookSystemTest#assertApplicationDisplaysExpected(String, String, Model)}.<br>
     * @see PdfBookSystemTest#assertApplicationDisplaysExpected(String, String, Model)
     */
    private void assertCommandSuccess(Pdf toAdd) {
        assertCommandSuccess(PdfUtil.getAddCommand(toAdd), toAdd);
    }

    /**
     * Performs the same verification as {@code assertCommandSuccess(Pdf)}. Executes {@code command}
     * instead.
     * @see AddCommandSystemTest#assertCommandSuccess(Pdf)
     */
    private void assertCommandSuccess(String command, Pdf toAdd) {
        Model expectedModel = getModel();
        expectedModel.addPdf(toAdd);
        String expectedResultMessage = String.format(AddCommand.MESSAGE_SUCCESS, toAdd);

        assertCommandSuccess(command, expectedModel, expectedResultMessage);
    }

    /**
     * Performs the same verification as {@code assertCommandSuccess(String, Pdf)} except asserts that
     * the,<br>
     * 1. Result display box displays {@code expectedResultMessage}.<br>
     * 2. {@code Storage} and {@code PdfListPanel} equal to the corresponding components in
     * {@code expectedModel}.<br>
     * @see AddCommandSystemTest#assertCommandSuccess(String, Pdf)
     */
    private void assertCommandSuccess(String command, Model expectedModel, String expectedResultMessage) {
        executeCommand(command);
        assertApplicationDisplaysExpected("", expectedResultMessage, expectedModel);
        assertSelectedCardUnchanged();
        assertCommandBoxShowsDefaultStyle();
        assertStatusBarUnchangedExceptSyncStatus();
    }

    /**
     * Executes {@code command} and asserts that the,<br>
     * 1. Command box displays {@code command}.<br>
     * 2. Command box has the error style class.<br>
     * 3. Result display box displays {@code expectedResultMessage}.<br>
     * 4. {@code Storage} and {@code PdfListPanel} remain unchanged.<br>
     * 5. Browser url, selected card and status bar remain unchanged.<br>
     * Verifications 1, 3 and 4 are performed by
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
