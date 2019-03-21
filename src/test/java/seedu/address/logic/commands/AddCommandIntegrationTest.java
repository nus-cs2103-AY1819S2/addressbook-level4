package seedu.address.logic.commands;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalPdfs.D_PDF;
import static seedu.address.testutil.TypicalPdfs.getTypicalAddressBook;

import org.junit.Before;
import org.junit.Test;

import seedu.address.logic.CommandHistory;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.pdf.Pdf;

/**
 * Contains integration tests (interaction with the Model) for {@code AddCommand}.
 */
public class AddCommandIntegrationTest {

    private Model model;
    private CommandHistory commandHistory = new CommandHistory();

    @Before
    public void setUp() {
        model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    }

    @Test
    public void execute_newPerson_success() {
        Pdf validPdf = D_PDF;

        Model expectedModel = new ModelManager(model.getPdfBook(), new UserPrefs());
        expectedModel.addPdf(validPdf);
        expectedModel.commitPdfBook();

        assertCommandSuccess(new AddCommand(validPdf), model, commandHistory,
                String.format(AddCommand.MESSAGE_SUCCESS, validPdf), expectedModel);
    }

    @Test
    public void execute_duplicatePerson_throwsCommandException() {
        Pdf pdfInList = model.getPdfBook().getPdfList().get(0);
        assertCommandFailure(new AddCommand(pdfInList), model, commandHistory,
                AddCommand.MESSAGE_DUPLICATE_PDF);
    }

}
