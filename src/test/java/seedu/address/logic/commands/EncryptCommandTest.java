package seedu.address.logic.commands;

import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.PASSWORD_1_VALID;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.CommandTestUtil.showPdfAtIndex;
import static seedu.address.logic.commands.EncryptCommand.MESSAGE_ENCRYPT_PDF_SUCCESS;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PDF;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_PDF;
import static seedu.address.testutil.TypicalPdfs.SAMPLE_PDF_1;
import static seedu.address.testutil.TypicalPdfs.SAMPLE_PDF_ENCRYPTED;
import static seedu.address.testutil.TypicalPdfs.getTypicalPdfBook;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.PdfBook;
import seedu.address.model.UserPrefs;
import seedu.address.model.pdf.Pdf;



public class EncryptCommandTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private CommandHistory commandHistory = new CommandHistory();
    private Model model = new ModelManager(getTypicalPdfBook(), new UserPrefs());

    @Before
    public void before() throws CommandException {
        initialiseTest(SAMPLE_PDF_1);
    }

    @After
    public void after() throws CommandException {
        initialiseTest(SAMPLE_PDF_1);
    }

    @Test
    public void constructor_invalidIndex_throwsNullPointerException() {
        thrown.expect(IndexOutOfBoundsException.class);
        new EncryptCommand((Index.fromZeroBased(-1)), "123");

        thrown.expect(IndexOutOfBoundsException.class);
        new EncryptCommand((Index.fromZeroBased(model.getFilteredPdfList().size() + 1)), "123");
    }

    @Test
    public void constructor_nullPassword_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        new EncryptCommand((Index.fromZeroBased(1)), null);
    }

    @Test
    public void execute_onlyCompulsoryFieldSpecifiedUnfilteredList_success() {
        Pdf pdfToEncrypt = SAMPLE_PDF_ENCRYPTED;
        EncryptCommand encryptCommand = new EncryptCommand(INDEX_FIRST_PDF, PASSWORD_1_VALID);

        String expectedMessage = String.format(MESSAGE_ENCRYPT_PDF_SUCCESS, pdfToEncrypt);

        Model expectedModel = new ModelManager(new PdfBook(model.getPdfBook()), new UserPrefs());
        expectedModel.setPdf(model.getFilteredPdfList().get(0), pdfToEncrypt);
        expectedModel.commitPdfBook();

        assertCommandSuccess(encryptCommand, model, commandHistory, expectedMessage, expectedModel);
    }

    @Test
    public void execute_fileAlreadyEncrypted_throwsCommandException() throws CommandException {
        thrown.expect(CommandException.class);
        EncryptCommand encryptCommand = new EncryptCommand(INDEX_FIRST_PDF, PASSWORD_1_VALID);
        encryptCommand.execute(model, commandHistory);
        encryptCommand.execute(model, commandHistory);
    }

    @Test
    public void execute_filteredList_success() {
        showPdfAtIndex(model, INDEX_FIRST_PDF);

        Pdf encryptedPdf = SAMPLE_PDF_ENCRYPTED;
        EncryptCommand encryptCommand = new EncryptCommand(INDEX_FIRST_PDF, PASSWORD_1_VALID);

        String expectedMessage = String.format(MESSAGE_ENCRYPT_PDF_SUCCESS, encryptedPdf);

        Model expectedModel = new ModelManager(new PdfBook(model.getPdfBook()), new UserPrefs());
        expectedModel.setPdf(model.getFilteredPdfList().get(0), encryptedPdf);
        expectedModel.commitPdfBook();

        assertCommandSuccess(encryptCommand, model, commandHistory, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidPdfIndexUnfilteredList_failure() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredPdfList().size() + 1);
        EncryptCommand encryptCommand = new EncryptCommand(outOfBoundIndex, PASSWORD_1_VALID);

        assertCommandFailure(encryptCommand, model, commandHistory, Messages.MESSAGE_INVALID_PDF_DISPLAYED_INDEX);
    }

    @Test
    public void execute_invalidPdfIndexFilteredList_failure() {
        showPdfAtIndex(model, INDEX_FIRST_PDF);
        Index outOfBoundIndex = INDEX_SECOND_PDF;
        // ensures that outOfBoundIndex is still in bounds of pdf book list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getPdfBook().getPdfList().size());

        EncryptCommand encryptCommand = new EncryptCommand(outOfBoundIndex, PASSWORD_1_VALID);

        assertCommandFailure(encryptCommand, model, commandHistory, Messages.MESSAGE_INVALID_PDF_DISPLAYED_INDEX);
    }

    @Test
    public void executeUndoRedo_invalidIndexUnfilteredList_failure() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredPdfList().size() + 1);

        EncryptCommand encryptCommand = new EncryptCommand(outOfBoundIndex, PASSWORD_1_VALID);

        // execution failed -> pdf book state not added into model
        assertCommandFailure(encryptCommand, model, commandHistory, Messages.MESSAGE_INVALID_PDF_DISPLAYED_INDEX);

        // single pdf book state in model -> undoCommand and redoCommand fail
        assertCommandFailure(new UndoCommand(), model, commandHistory, UndoCommand.MESSAGE_FAILURE);
        assertCommandFailure(new RedoCommand(), model, commandHistory, RedoCommand.MESSAGE_FAILURE);
    }

    @Test
    public void executeUndoRedo_encryptionDoesNotAllowUndoAndRedo_failure() throws Exception {
        EncryptCommand encryptCommand = new EncryptCommand(INDEX_FIRST_PDF, PASSWORD_1_VALID);

        encryptCommand.execute(model, commandHistory);

        assertCommandFailure(new UndoCommand(), model, commandHistory, UndoCommand.MESSAGE_FAILURE);
        assertCommandFailure(new RedoCommand(), model, commandHistory, RedoCommand.MESSAGE_FAILURE);
    }

    /**
     * Decrypts {@code pdfToInitialise} if it is encrypted.
     */
    private void initialiseTest(Pdf pdfToInitialise) throws CommandException {
        if (pdfToInitialise.getIsEncrypted()) {
            Model initialisationModel = new ModelManager(getTypicalPdfBook(), new UserPrefs());
            CommandHistory initialisationCommandHistory = new CommandHistory();
            DecryptCommand decryptCommand = new DecryptCommand(INDEX_FIRST_PDF, PASSWORD_1_VALID);
            decryptCommand.execute(initialisationModel, initialisationCommandHistory);
        }
    }
}
