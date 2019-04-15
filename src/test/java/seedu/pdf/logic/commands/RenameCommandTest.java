package seedu.pdf.logic.commands;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.pdf.logic.commands.CommandTestUtil.DESC_1;
import static seedu.pdf.logic.commands.CommandTestUtil.DESC_2;
import static seedu.pdf.logic.commands.CommandTestUtil.NAME_1_VALID;
import static seedu.pdf.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.pdf.logic.commands.CommandTestUtil.assertCommandSuccess;

import static seedu.pdf.logic.commands.CommandTestUtil.showPdfAtIndex;
import static seedu.pdf.logic.commands.RenameCommand.MESSAGE_DUPLICATE_PDF;
import static seedu.pdf.logic.commands.RenameCommand.MESSAGE_DUPLICATE_PDF_DIRECTORY;
import static seedu.pdf.testutil.TypicalIndexes.INDEX_FIRST_PDF;
import static seedu.pdf.testutil.TypicalIndexes.INDEX_SECOND_PDF;
import static seedu.pdf.testutil.TypicalPdfs.SAMPLE_EDITEDPDF;
import static seedu.pdf.testutil.TypicalPdfs.SAMPLE_PDF_1;
import static seedu.pdf.testutil.TypicalPdfs.SAMPLE_PDF_2;
import static seedu.pdf.testutil.TypicalPdfs.getTypicalPdfBook;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import org.junit.rules.ExpectedException;

import seedu.pdf.commons.core.Messages;
import seedu.pdf.commons.core.index.Index;
import seedu.pdf.logic.CommandHistory;
import seedu.pdf.logic.commands.RenameCommand.EditPdfDescriptor;
import seedu.pdf.model.Model;
import seedu.pdf.model.ModelManager;
import seedu.pdf.model.PdfBook;
import seedu.pdf.model.UserPrefs;
import seedu.pdf.model.pdf.Pdf;
import seedu.pdf.testutil.EditPdfDescriptorBuilder;
import seedu.pdf.testutil.PdfBuilder;

/**
 * Contains integration tests (interaction with the Model, UndoCommand and RedoCommand)
 * and unit tests for RenameCommand.
 */
public class RenameCommandTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private Model model = new ModelManager(getTypicalPdfBook(), new UserPrefs());
    private CommandHistory commandHistory = new CommandHistory();

    @Before
    public void before() {
        initialiseTest(SAMPLE_EDITEDPDF);
    }

    @Test
    public void constructor_invalidIndex_throwsIndexOutOfBoundsException() {
        thrown.expect(IndexOutOfBoundsException.class);
        new RenameCommand(Index.fromZeroBased(-1), new EditPdfDescriptorBuilder(SAMPLE_PDF_1).build());
    }

    @Test
    public void constructor_nullPdfDescriptorBuilder_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        new RenameCommand(Index.fromZeroBased(1), null);
    }

    @Test
    public void execute_onlyCompulsoryFieldSpecifiedUnfilteredList_success() {
        Pdf editedPdf = new PdfBuilder(SAMPLE_PDF_1).withName("Test.pdf").build();
        RenameCommand.EditPdfDescriptor descriptor = new EditPdfDescriptorBuilder()
                .withName("Test.pdf").build();
        RenameCommand renameCommand = new RenameCommand(INDEX_FIRST_PDF, descriptor);

        String expectedMessage = String.format(RenameCommand.MESSAGE_EDIT_PDF_SUCCESS, editedPdf);

        Model expectedModel = new ModelManager(new PdfBook(model.getPdfBook()), new UserPrefs());
        expectedModel.setPdf(model.getFilteredPdfList().get(0), editedPdf);
        expectedModel.commitPdfBook();

        assertCommandSuccess(renameCommand, model, commandHistory, expectedMessage, expectedModel);
        revertBackup(SAMPLE_PDF_1, editedPdf);
    }

    @Test
    public void execute_someFieldsSpecifiedUnfilteredList_success() {
        Index indexLastPdf = Index.fromOneBased(model.getFilteredPdfList().size());
        Pdf lastPdf = model.getFilteredPdfList().get(indexLastPdf.getZeroBased());

        PdfBuilder pdfInList = new PdfBuilder(lastPdf);
        Pdf editedPdf = pdfInList.withName(SAMPLE_EDITEDPDF.getName().getFullName()).build();

        EditPdfDescriptor descriptor = new EditPdfDescriptorBuilder().withName(SAMPLE_EDITEDPDF.getName().getFullName())
                .build();
        RenameCommand renameCommand = new RenameCommand(indexLastPdf, descriptor);

        String expectedMessage = String.format(RenameCommand.MESSAGE_EDIT_PDF_SUCCESS, editedPdf);

        Model expectedModel = new ModelManager(new PdfBook(model.getPdfBook()), new UserPrefs());
        expectedModel.setPdf(lastPdf, editedPdf);
        expectedModel.commitPdfBook();

        assertCommandSuccess(renameCommand, model, commandHistory, expectedMessage, expectedModel);
        revertBackup(lastPdf, editedPdf);
    }

    @Test
    public void execute_fileWithSameName_throwsCommandException() {
        Pdf firstPdf = model.getFilteredPdfList().get(Index.fromOneBased(1).getOneBased());
        Pdf secondPdf = model.getFilteredPdfList().get(Index.fromOneBased(2).getOneBased());

        EditPdfDescriptor descriptor = new EditPdfDescriptorBuilder().withName(secondPdf.getName().getFullName())
                .build();
        RenameCommand renameCommand = new RenameCommand(Index.fromZeroBased(1), descriptor);

        String expectedMessage = String.format(String.format(MESSAGE_DUPLICATE_PDF_DIRECTORY,
                secondPdf.getName().getFullName(), firstPdf.getDirectory().getDirectory()));

        assertCommandFailure(renameCommand, model, commandHistory, expectedMessage);
    }

    @Test
    public void execute_noFieldSpecifiedUnfilteredList_success() {
        RenameCommand renameCommand = new RenameCommand(INDEX_FIRST_PDF, new EditPdfDescriptor());
        String expectedMessage = MESSAGE_DUPLICATE_PDF;
        assertCommandFailure(renameCommand, model, commandHistory, expectedMessage);
    }

    @Test
    public void execute_filteredList_success() {
        showPdfAtIndex(model, INDEX_FIRST_PDF);

        Pdf pdfInFilteredList = model.getFilteredPdfList().get(INDEX_FIRST_PDF.getZeroBased());
        Pdf editedPdf = new PdfBuilder(pdfInFilteredList).withName(SAMPLE_EDITEDPDF.getName().getFullName()).build();
        RenameCommand renameCommand = new RenameCommand(INDEX_FIRST_PDF, new EditPdfDescriptorBuilder()
                .withName(SAMPLE_EDITEDPDF.getName().getFullName()).build());

        String expectedMessage = String.format(RenameCommand.MESSAGE_EDIT_PDF_SUCCESS, editedPdf);

        Model expectedModel = new ModelManager(new PdfBook(model.getPdfBook()), new UserPrefs());
        expectedModel.setPdf(model.getFilteredPdfList().get(0), editedPdf);
        expectedModel.commitPdfBook();

        assertCommandSuccess(renameCommand, model, commandHistory, expectedMessage, expectedModel);

        revertBackup(pdfInFilteredList, editedPdf);
    }

    @Test
    public void execute_duplicatePdfUnfilteredList_failure() {
        Pdf firstPdf = model.getFilteredPdfList().get(INDEX_FIRST_PDF.getZeroBased());
        EditPdfDescriptor descriptor = new EditPdfDescriptorBuilder(firstPdf).build();
        RenameCommand renameCommand = new RenameCommand(INDEX_SECOND_PDF, descriptor);

        assertCommandFailure(renameCommand, model, commandHistory, String.format(MESSAGE_DUPLICATE_PDF_DIRECTORY,
                firstPdf.getName().getFullName(), SAMPLE_PDF_2.getDirectory().getDirectory()));
    }

    @Test
    public void execute_duplicatePdfFilteredList_failure() {
        showPdfAtIndex(model, INDEX_FIRST_PDF);

        // edit pdf in filtered list into a duplicate in pdf book
        Pdf pdfInList = model.getPdfBook().getPdfList().get(INDEX_SECOND_PDF.getZeroBased());
        RenameCommand renameCommand = new RenameCommand(INDEX_FIRST_PDF,
                new EditPdfDescriptorBuilder(pdfInList).build());

        assertCommandFailure(renameCommand, model, commandHistory, String.format(MESSAGE_DUPLICATE_PDF_DIRECTORY,
                pdfInList.getName().getFullName(), SAMPLE_PDF_2.getDirectory().getDirectory()));
    }

    @Test
    public void execute_invalidPdfIndexUnfilteredList_failure() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredPdfList().size() + 1);
        EditPdfDescriptor descriptor = new EditPdfDescriptorBuilder().withName(NAME_1_VALID).build();
        RenameCommand renameCommand = new RenameCommand(outOfBoundIndex, descriptor);

        assertCommandFailure(renameCommand, model, commandHistory, Messages.MESSAGE_INVALID_PDF_DISPLAYED_INDEX);
    }

    /**
     * Edit filtered list where index is larger than size of filtered list,
     * but smaller than size of pdf book
     */
    @Test
    public void execute_invalidPdfIndexFilteredList_failure() {
        showPdfAtIndex(model, INDEX_FIRST_PDF);
        Index outOfBoundIndex = INDEX_SECOND_PDF;
        // ensures that outOfBoundIndex is still in bounds of pdf book list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getPdfBook().getPdfList().size());

        RenameCommand renameCommand = new RenameCommand(outOfBoundIndex,
                new EditPdfDescriptorBuilder().withName(NAME_1_VALID).build());

        assertCommandFailure(renameCommand, model, commandHistory, Messages.MESSAGE_INVALID_PDF_DISPLAYED_INDEX);
    }

    @Test
    public void executeUndoRedo_validIndexUnfilteredList_success() throws Exception {
        Pdf editedPdf = new PdfBuilder(SAMPLE_PDF_1).withName(SAMPLE_EDITEDPDF.getName().getFullName()).build();
        Pdf pdfToEdit = model.getFilteredPdfList().get(INDEX_FIRST_PDF.getZeroBased());
        EditPdfDescriptor editPdfDescriptor = new EditPdfDescriptorBuilder()
                .withName(editedPdf.getName().getFullName()).build();

        RenameCommand renameCommand = new RenameCommand(INDEX_FIRST_PDF, editPdfDescriptor);

        Model expectedModel = new ModelManager(model.getPdfBook(), new UserPrefs());
        expectedModel.setPdf(pdfToEdit, editedPdf);
        expectedModel.commitPdfBook();

        // edit -> first pdf deleted
        renameCommand.execute(model, commandHistory);

        // undo -> reverts pdfbook back to previous state and filtered pdf list to show all pdfs
        expectedModel.undoPdfBook();
        assertCommandSuccess(new UndoCommand(), model, commandHistory, UndoCommand.MESSAGE_SUCCESS, expectedModel);

        // redo -> same first pdf deleted again
        expectedModel.redoPdfBook();
        assertCommandSuccess(new RedoCommand(), model, commandHistory, RedoCommand.MESSAGE_SUCCESS, expectedModel);

        revertBackup(pdfToEdit, editedPdf);
    }

    @Test
    public void executeUndoRedo_invalidIndexUnfilteredList_failure() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredPdfList().size() + 1);
        EditPdfDescriptor descriptor = new EditPdfDescriptorBuilder().withName(NAME_1_VALID).build();
        RenameCommand renameCommand = new RenameCommand(outOfBoundIndex, descriptor);

        // execution failed -> pdf book state not added into model
        assertCommandFailure(renameCommand, model, commandHistory, Messages.MESSAGE_INVALID_PDF_DISPLAYED_INDEX);

        // single pdf book state in model -> undoCommand and redoCommand fail
        assertCommandFailure(new UndoCommand(), model, commandHistory, UndoCommand.MESSAGE_FAILURE);
        assertCommandFailure(new RedoCommand(), model, commandHistory, RedoCommand.MESSAGE_FAILURE);
    }

    @Test
    public void equals() {
        final RenameCommand standardCommand = new RenameCommand(INDEX_FIRST_PDF, DESC_2);

        // same values -> returns true
        EditPdfDescriptor copyDescriptor = new EditPdfDescriptor(DESC_2);
        RenameCommand commandWithSameValues = new RenameCommand(INDEX_FIRST_PDF, copyDescriptor);
        assertTrue(standardCommand.equals(commandWithSameValues));

        // same object -> returns true
        assertTrue(standardCommand.equals(standardCommand));

        // null -> returns false
        assertFalse(standardCommand.equals(null));

        // different types -> returns false
        assertFalse(standardCommand.equals(new ClearCommand()));

        // different index -> returns false
        assertFalse(standardCommand.equals(new RenameCommand(INDEX_SECOND_PDF, DESC_2)));

        // different descriptor -> returns false
        assertFalse(standardCommand.equals(new RenameCommand(INDEX_FIRST_PDF, DESC_1)));
    }

    /**
     * Initialises the files for th test
     */
    private void initialiseTest(Pdf target) {
        if (Paths.get(target.getDirectory().getDirectory() , target.getName().getFullName()).toFile().exists()) {
            try {
                Files.delete(Paths.get(target.getDirectory().getDirectory() + "\\" + target.getName()));
            } catch (IOException ioe) {
                System.out.println("The test is already at initialised state");
            }
        }
    }

    /**
     * Moves {@code editedFile} back to its original location
     */
    private void revertBackup(Pdf target, Pdf editedFile) {
        File fileToRevert = Paths.get(editedFile.getDirectory().getDirectory(),
                editedFile.getName().getFullName()).toFile();
        File revertedFile = Paths.get(target.getDirectory().getDirectory(),
                target.getName().getFullName()).toFile();
        fileToRevert.renameTo(revertedFile);
    }
}
