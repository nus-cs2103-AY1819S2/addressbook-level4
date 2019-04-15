package seedu.pdf.logic.commands;

import static seedu.pdf.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.pdf.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.pdf.testutil.TypicalPdfs.SAMPLE_PDF_4;
import static seedu.pdf.testutil.TypicalPdfs.getTypicalPdfBook;

import org.junit.Before;
import org.junit.Test;

import seedu.pdf.logic.CommandHistory;
import seedu.pdf.model.Model;
import seedu.pdf.model.ModelManager;
import seedu.pdf.model.UserPrefs;
import seedu.pdf.model.pdf.Pdf;

/**
 * Contains integration tests (interaction with the Model) for {@code AddCommand}.
 */
public class AddCommandIntegrationTest {

    private Model model;
    private CommandHistory commandHistory = new CommandHistory();

    @Before
    public void setUp() {
        model = new ModelManager(getTypicalPdfBook(), new UserPrefs());
    }

    @Test
    public void execute_newPdf_success() {
        Pdf validPdf = SAMPLE_PDF_4;

        Model expectedModel = new ModelManager(model.getPdfBook(), new UserPrefs());
        expectedModel.addPdf(validPdf);
        expectedModel.commitPdfBook();

        assertCommandSuccess(new AddCommand(validPdf), model, commandHistory,
                String.format(AddCommand.MESSAGE_SUCCESS, validPdf), expectedModel);
    }

    @Test
    public void execute_duplicatePdf_throwsCommandException() {
        Pdf pdfInList = model.getPdfBook().getPdfList().get(0);
        assertCommandFailure(new AddCommand(pdfInList), model, commandHistory,
                AddCommand.MESSAGE_DUPLICATE_PDF);
    }

}
