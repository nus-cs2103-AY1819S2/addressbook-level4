package seedu.address.logic.commands;

import static seedu.address.logic.commands.CommandTestUtil.DEADLINE_DESC_COMPLETE;
import static seedu.address.logic.commands.CommandTestUtil.DEADLINE_DESC_INVALID_DATE;
import static seedu.address.logic.commands.CommandTestUtil.DEADLINE_DESC_INVALID_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.DEADLINE_DESC_INVALID_MISSING_STATUS;
import static seedu.address.logic.commands.CommandTestUtil.DEADLINE_DESC_INVALID_WRONG_STATUS;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PDF;
import static seedu.address.testutil.TypicalPdfs.SAMPLE_PDF_1;
import static seedu.address.testutil.TypicalPdfs.getTypicalPdfBook;

import java.time.format.DateTimeParseException;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.CommandHistory;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.PdfBook;
import seedu.address.model.UserPrefs;
import seedu.address.model.pdf.Deadline;
import seedu.address.model.pdf.Pdf;
import seedu.address.testutil.PdfBuilder;


public class DeadlineCommandTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private Model model = new ModelManager(getTypicalPdfBook(), new UserPrefs());
    private CommandHistory commandHistory = new CommandHistory();

    @Test
    public void constructor_invalidIndex_throwsIndexOutOfBoundsException() {
        thrown.expect(IndexOutOfBoundsException.class);
        new DeadlineCommand(Index.fromZeroBased(model.getFilteredPdfList().size() + 1), new Deadline(DEADLINE_DESC_COMPLETE));

        thrown.expect(IndexOutOfBoundsException.class);
        new DeadlineCommand(Index.fromZeroBased(-1), new Deadline(DEADLINE_DESC_COMPLETE));
    }

    @Test
    public void constructor_missingStatus_throwsDateTimeParseException() {
        thrown.expect(DateTimeParseException.class);
        new DeadlineCommand(Index.fromOneBased(1), new Deadline(DEADLINE_DESC_INVALID_MISSING_STATUS));
    }

    @Test
    public void constructor_wrongStatus_throwsDateTimeParseException() {
        thrown.expect(DateTimeParseException.class);
        new DeadlineCommand(Index.fromOneBased(1), new Deadline(DEADLINE_DESC_INVALID_WRONG_STATUS));
    }

    @Test
    public void constructor_invalidDateDeadline_throwsDateTimeParseException() {
        thrown.expect(DateTimeParseException.class);
        new DeadlineCommand(Index.fromOneBased(1), new Deadline(DEADLINE_DESC_INVALID_DATE));
    }

    @Test
    public void constructor_invalidFormatDeadline_throwsDateTimeParseException() {
        thrown.expect(DateTimeParseException.class);
        new DeadlineCommand(Index.fromOneBased(1), new Deadline(DEADLINE_DESC_INVALID_FORMAT));
    }

    @Test
    public void constructor_nullPdfDescriptorBuilder_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        new DeadlineCommand(Index.fromZeroBased(1), null);
    }

    @Test
    public void execute_onlyCompulsoryFieldSpecifiedUnfilteredList_success() {
        Deadline newDeadline = new Deadline(DEADLINE_DESC_COMPLETE);

        Pdf pdfToEdit = SAMPLE_PDF_1;
        Pdf editedPdf = new PdfBuilder(pdfToEdit).withDeadline(DEADLINE_DESC_COMPLETE).build();
        DeadlineCommand deadlineCommand = new DeadlineCommand(INDEX_FIRST_PDF, newDeadline);

        String expectedMessage = String.format(EditCommand.MESSAGE_EDIT_PDF_SUCCESS, editedPdf);

        Model expectedModel = new ModelManager(new PdfBook(model.getPdfBook()), new UserPrefs());
        expectedModel.setPdf(model.getFilteredPdfList().get(0), editedPdf);
        expectedModel.commitPdfBook();

        assertCommandSuccess(deadlineCommand, model, commandHistory, expectedMessage, expectedModel);
    }

    /*@Test
    public void execute_someFieldsSpecifiedUnfilteredList_success() {
        Index indexLastPerson = Index.fromOneBased(model.getFilteredPdfList().size());
        Pdf lastPdf = model.getFilteredPdfList().get(indexLastPerson.getZeroBased());

        PdfBuilder personInList = new PdfBuilder(lastPdf);
        Pdf editedPdf = personInList.withName(SAMPLE_EDITEDPDF.getName().getFullName()).build();

        EditPdfDescriptor descriptor = new EditPdfDescriptorBuilder().withName(SAMPLE_EDITEDPDF.getName().getFullName())
                .build();
        EditCommand editCommand = new EditCommand(indexLastPerson, descriptor);

        String expectedMessage = String.format(EditCommand.MESSAGE_EDIT_PDF_SUCCESS, editedPdf);

        Model expectedModel = new ModelManager(new PdfBook(model.getPdfBook()), new UserPrefs());
        expectedModel.setPdf(lastPdf, editedPdf);
        expectedModel.commitPdfBook();

        assertCommandSuccess(editCommand, model, commandHistory, expectedMessage, expectedModel);
        revertBackup(lastPdf, editedPdf);
    }*/
}
