package seedu.address.logic.commands;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.DESC_1;
import static seedu.address.logic.commands.CommandTestUtil.DESC_2;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_1;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;

import static seedu.address.logic.commands.CommandTestUtil.showPdfAtIndex;
import static seedu.address.logic.commands.EditCommand.MESSAGE_DUPLICATE_PDF_DIRECTORY;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PDF;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_PDF;
import static seedu.address.testutil.TypicalPdfs.SAMPLE_EDITEDPDF;
import static seedu.address.testutil.TypicalPdfs.SAMPLE_PDF_1;
import static seedu.address.testutil.TypicalPdfs.SAMPLE_PDF_2;
import static seedu.address.testutil.TypicalPdfs.getTypicalPdfBook;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import org.junit.rules.ExpectedException;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.EditCommand.EditPdfDescriptor;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.PdfBook;
import seedu.address.model.UserPrefs;
import seedu.address.model.pdf.Pdf;
import seedu.address.testutil.EditPdfDescriptorBuilder;
import seedu.address.testutil.PdfBuilder;

/**
 * Contains integration tests (interaction with the Model, UndoCommand and RedoCommand) and unit tests for EditCommand.
 */
public class EditCommandTest {
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
        new EditCommand(Index.fromZeroBased(-1), new EditPdfDescriptorBuilder(SAMPLE_PDF_1).build());
    }

    @Test
    public void constructor_nullPdfDescriptorBuilder_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        new EditCommand(Index.fromZeroBased(1), null);
    }

    @Test
    public void execute_onlyCompulsoryFieldSpecifiedUnfilteredList_success() {
        Pdf editedPdf = SAMPLE_PDF_1;
        EditCommand.EditPdfDescriptor descriptor = new EditPdfDescriptorBuilder(editedPdf).build();
        EditCommand editCommand = new EditCommand(INDEX_FIRST_PDF, descriptor);

        String expectedMessage = String.format(EditCommand.MESSAGE_EDIT_PDF_SUCCESS, editedPdf);

        Model expectedModel = new ModelManager(new PdfBook(model.getPdfBook()), new UserPrefs());
        expectedModel.setPdf(model.getFilteredPdfList().get(0), editedPdf);
        expectedModel.commitPdfBook();

        assertCommandSuccess(editCommand, model, commandHistory, expectedMessage, expectedModel);
    }

    @Test
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
    }

    @Test
    public void execute_fileWithSameName_throwsCommandException() {
        Pdf firstPdf = model.getFilteredPdfList().get(Index.fromOneBased(1).getOneBased());
        Pdf secondPdf = model.getFilteredPdfList().get(Index.fromOneBased(2).getOneBased());

        EditPdfDescriptor descriptor = new EditPdfDescriptorBuilder().withName(secondPdf.getName().getFullName())
                .build();
        EditCommand editCommand = new EditCommand(Index.fromZeroBased(1), descriptor);

        String expectedMessage = String.format(String.format(MESSAGE_DUPLICATE_PDF_DIRECTORY,
                secondPdf.getName().getFullName(), firstPdf.getDirectory().getDirectory()));

        assertCommandFailure(editCommand, model, commandHistory, expectedMessage);
    }

    @Test
    public void execute_noFieldSpecifiedUnfilteredList_success() {
        EditCommand editCommand = new EditCommand(INDEX_FIRST_PDF, new EditPdfDescriptor());
        Pdf editedPdf = model.getFilteredPdfList().get(INDEX_FIRST_PDF.getZeroBased());

        String expectedMessage = String.format(EditCommand.MESSAGE_EDIT_PDF_SUCCESS, editedPdf);

        Model expectedModel = new ModelManager(new PdfBook(model.getPdfBook()), new UserPrefs());
        expectedModel.commitPdfBook();

        assertCommandSuccess(editCommand, model, commandHistory, expectedMessage, expectedModel);
    }

    @Test
    public void execute_filteredList_success() {
        showPdfAtIndex(model, INDEX_FIRST_PDF);

        Pdf pdfInFilteredList = model.getFilteredPdfList().get(INDEX_FIRST_PDF.getZeroBased());
        Pdf editedPdf = new PdfBuilder(pdfInFilteredList).withName(SAMPLE_EDITEDPDF.getName().getFullName()).build();
        EditCommand editCommand = new EditCommand(INDEX_FIRST_PDF, new EditPdfDescriptorBuilder()
                .withName(SAMPLE_EDITEDPDF.getName().getFullName()).build());

        String expectedMessage = String.format(EditCommand.MESSAGE_EDIT_PDF_SUCCESS, editedPdf);

        Model expectedModel = new ModelManager(new PdfBook(model.getPdfBook()), new UserPrefs());
        expectedModel.setPdf(model.getFilteredPdfList().get(0), editedPdf);
        expectedModel.commitPdfBook();

        assertCommandSuccess(editCommand, model, commandHistory, expectedMessage, expectedModel);

        revertBackup(pdfInFilteredList, editedPdf);
    }

    @Test
    public void execute_duplicatePdfUnfilteredList_failure() {
        Pdf firstPdf = model.getFilteredPdfList().get(INDEX_FIRST_PDF.getZeroBased());
        EditPdfDescriptor descriptor = new EditPdfDescriptorBuilder(firstPdf).build();
        EditCommand editCommand = new EditCommand(INDEX_SECOND_PDF, descriptor);

        assertCommandFailure(editCommand, model, commandHistory, String.format(MESSAGE_DUPLICATE_PDF_DIRECTORY,
                firstPdf.getName().getFullName(), SAMPLE_PDF_2.getDirectory().getDirectory()));
    }

    @Test
    public void execute_duplicatePdfFilteredList_failure() {
        showPdfAtIndex(model, INDEX_FIRST_PDF);

        // edit pdf in filtered list into a duplicate in address book
        Pdf pdfInList = model.getPdfBook().getPdfList().get(INDEX_SECOND_PDF.getZeroBased());
        EditCommand editCommand = new EditCommand(INDEX_FIRST_PDF,
                new EditPdfDescriptorBuilder(pdfInList).build());

        assertCommandFailure(editCommand, model, commandHistory, String.format(MESSAGE_DUPLICATE_PDF_DIRECTORY,
                pdfInList.getName().getFullName(), SAMPLE_PDF_2.getDirectory().getDirectory()));
    }

    @Test
    public void execute_invalidPdfIndexUnfilteredList_failure() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredPdfList().size() + 1);
        EditPdfDescriptor descriptor = new EditPdfDescriptorBuilder().withName(VALID_NAME_1).build();
        EditCommand editCommand = new EditCommand(outOfBoundIndex, descriptor);

        assertCommandFailure(editCommand, model, commandHistory, Messages.MESSAGE_INVALID_PDF_DISPLAYED_INDEX);
    }

    /**
     * Edit filtered list where index is larger than size of filtered list,
     * but smaller than size of address book
     */
    @Test
    public void execute_invalidPdfIndexFilteredList_failure() {
        showPdfAtIndex(model, INDEX_FIRST_PDF);
        Index outOfBoundIndex = INDEX_SECOND_PDF;
        // ensures that outOfBoundIndex is still in bounds of address book list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getPdfBook().getPdfList().size());

        EditCommand editCommand = new EditCommand(outOfBoundIndex,
                new EditPdfDescriptorBuilder().withName(VALID_NAME_1).build());

        assertCommandFailure(editCommand, model, commandHistory, Messages.MESSAGE_INVALID_PDF_DISPLAYED_INDEX);
    }

    @Test
    public void executeUndoRedo_validIndexUnfilteredList_success() throws Exception {
        Pdf editedPdf = new PdfBuilder(SAMPLE_PDF_1).withName(SAMPLE_EDITEDPDF.getName().getFullName()).build();
        Pdf pdfToEdit = model.getFilteredPdfList().get(INDEX_FIRST_PDF.getZeroBased());
        EditPdfDescriptor editPdfDescriptor = new EditPdfDescriptorBuilder()
                .withName(editedPdf.getName().getFullName()).build();

        EditCommand editCommand = new EditCommand(INDEX_FIRST_PDF, editPdfDescriptor);

        Model expectedModel = new ModelManager(model.getPdfBook(), new UserPrefs());
        expectedModel.setPdf(pdfToEdit, editedPdf);
        expectedModel.commitPdfBook();

        // edit -> first pdf deleted
        editCommand.execute(model, commandHistory);

        // undo -> reverts pdfbook back to previous state and filtered pdf list to show all persons
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
        EditPdfDescriptor descriptor = new EditPdfDescriptorBuilder().withName(VALID_NAME_1).build();
        EditCommand editCommand = new EditCommand(outOfBoundIndex, descriptor);

        // execution failed -> address book state not added into model
        assertCommandFailure(editCommand, model, commandHistory, Messages.MESSAGE_INVALID_PDF_DISPLAYED_INDEX);

        // single address book state in model -> undoCommand and redoCommand fail
        assertCommandFailure(new UndoCommand(), model, commandHistory, UndoCommand.MESSAGE_FAILURE);
        assertCommandFailure(new RedoCommand(), model, commandHistory, RedoCommand.MESSAGE_FAILURE);
    }

    /**
     * 1. Edits a {@code Pdf} from a filtered list.
     * 2. Undo the edit.
     * 3. The unfiltered list should be shown now. Verify that the index of the previously edited pdf in the
     * unfiltered list is different from the index at the filtered list.
     * 4. Redo the edit. This ensures {@code RedoCommand} edits the pdf object regardless of indexing.
     */
    @Test
    public void executeUndoRedo_validIndexFilteredList_samePdfEdited() throws Exception {
        Pdf editedPdf = new PdfBuilder(SAMPLE_PDF_2).build();
        EditPdfDescriptor descriptor = new EditPdfDescriptorBuilder(editedPdf).build();
        EditCommand editCommand = new EditCommand(INDEX_FIRST_PDF, descriptor);
        Model expectedModel = new ModelManager(new PdfBook(model.getPdfBook()), new UserPrefs());

        showPdfAtIndex(model, INDEX_SECOND_PDF);
        Pdf pdfToEdit = model.getFilteredPdfList().get(INDEX_FIRST_PDF.getZeroBased());
        expectedModel.setPdf(pdfToEdit, editedPdf);
        expectedModel.commitPdfBook();

        // edit -> edits second pdf in unfiltered pdf list / first pdf in filtered pdf list
        editCommand.execute(model, commandHistory);

        // undo -> reverts addressbook back to previous state and filtered pdf list to show all persons
        expectedModel.undoPdfBook();
        assertCommandSuccess(new UndoCommand(), model, commandHistory, UndoCommand.MESSAGE_SUCCESS, expectedModel);

        assertNotEquals(model.getFilteredPdfList().get(INDEX_FIRST_PDF.getZeroBased()), pdfToEdit);
        // redo -> edits same second pdf in unfiltered pdf list
        expectedModel.redoPdfBook();
        assertCommandSuccess(new RedoCommand(), model, commandHistory, RedoCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void equals() {
        final EditCommand standardCommand = new EditCommand(INDEX_FIRST_PDF, DESC_2);

        // same values -> returns true
        EditPdfDescriptor copyDescriptor = new EditPdfDescriptor(DESC_2);
        EditCommand commandWithSameValues = new EditCommand(INDEX_FIRST_PDF, copyDescriptor);
        assertTrue(standardCommand.equals(commandWithSameValues));

        // same object -> returns true
        assertTrue(standardCommand.equals(standardCommand));

        // null -> returns false
        assertFalse(standardCommand.equals(null));

        // different types -> returns false
        assertFalse(standardCommand.equals(new ClearCommand()));

        // different index -> returns false
        assertFalse(standardCommand.equals(new EditCommand(INDEX_SECOND_PDF, DESC_2)));

        // different descriptor -> returns false

        assertFalse(standardCommand.equals(new EditCommand(INDEX_FIRST_PDF, DESC_1)));
    }

    /**
     * Initialises the files for th test
     */
    private void initialiseTest(Pdf target) {
        if (Paths.get(target.getDirectory().getDirectory() + "\\" + target.getName()).toFile().exists()) {
            try {
                Files.delete(Paths.get(target.getDirectory().getDirectory() + "\\" + target.getName()));
            } catch (IOException ioe) {
                System.out.println("The test is already at initialised state");
            }
        }
    }

    /**
     * Restores the edited file
     */
    private void revertBackup(Pdf target, Pdf editedFile) {
        try {
            Files.copy(Paths.get(editedFile.getDirectory().getDirectory() + "\\" + editedFile.getName()),
                    Paths.get(target.getDirectory().getDirectory() + "\\" + target.getName()));
            Files.delete(Paths.get(editedFile.getDirectory().getDirectory() + "\\" + editedFile.getName()));
        } catch (IOException ioe) {
            System.out.println("File not reverted.");
        }
    }

}
