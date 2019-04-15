package seedu.pdf.logic.commands;

import static org.junit.Assert.assertTrue;
import static seedu.pdf.logic.commands.CommandTestUtil.DIR_1_VALID;
import static seedu.pdf.logic.commands.CommandTestUtil.PASSWORD_1_VALID;
import static seedu.pdf.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.pdf.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.pdf.logic.commands.CommandTestUtil.showPdfAtIndex;
import static seedu.pdf.logic.commands.DecryptCommand.MESSAGE_DECRYPT_PDF_FAILURE;
import static seedu.pdf.logic.commands.EncryptCommand.MESSAGE_ENCRYPT_PDF_SUCCESS;
import static seedu.pdf.testutil.TypicalIndexes.INDEX_FIRST_PDF;
import static seedu.pdf.testutil.TypicalIndexes.INDEX_SECOND_PDF;
import static seedu.pdf.testutil.TypicalPdfs.SAMPLE_PDF_1;
import static seedu.pdf.testutil.TypicalPdfs.SAMPLE_PDF_1_ENCRYPTED;
import static seedu.pdf.testutil.TypicalPdfs.getTypicalPdfBook;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.pdf.commons.core.Messages;
import seedu.pdf.commons.core.index.Index;
import seedu.pdf.logic.CommandHistory;
import seedu.pdf.logic.commands.exceptions.CommandException;
import seedu.pdf.model.Model;
import seedu.pdf.model.ModelManager;
import seedu.pdf.model.PdfBook;
import seedu.pdf.model.UserPrefs;
import seedu.pdf.model.pdf.Pdf;
import seedu.pdf.testutil.PdfBuilder;

public class EncryptCommandTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private CommandHistory commandHistory = new CommandHistory();
    private Model model = new ModelManager(getTypicalPdfBook(), new UserPrefs());

    @BeforeClass
    public static void beforeClass() throws CommandException {
        preAndPostProcessingTest(SAMPLE_PDF_1);
    }

    @AfterClass
    public static void afterClass() throws CommandException {
        preAndPostProcessingTest(SAMPLE_PDF_1);
    }

    @Before
    public void before() throws CommandException {
        initialiseTest(SAMPLE_PDF_1);
    }

    @After
    public void after() throws CommandException {
        initialiseTest(SAMPLE_PDF_1);
    }

    @Test
    public void constructor_invalidIndex_throwsIndexOutOfBoundsException() {
        thrown.expect(IndexOutOfBoundsException.class);
        new EncryptCommand((Index.fromZeroBased(-1)), PASSWORD_1_VALID);

        thrown.expect(IndexOutOfBoundsException.class);
        new EncryptCommand((Index.fromZeroBased(model.getFilteredPdfList().size() + 1)), PASSWORD_1_VALID);
    }

    @Test
    public void constructor_nullPassword_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        new EncryptCommand((Index.fromZeroBased(1)), null);
    }

    @Test
    public void execute_onlyCompulsoryFieldSpecifiedUnfilteredList_success() {
        Pdf pdfToEncrypt = new PdfBuilder(SAMPLE_PDF_1_ENCRYPTED).withDirectory(DIR_1_VALID).build();
        EncryptCommand encryptCommand = new EncryptCommand(INDEX_FIRST_PDF, PASSWORD_1_VALID);

        String expectedMessage = String.format(MESSAGE_ENCRYPT_PDF_SUCCESS, pdfToEncrypt);

        Model expectedModel = new ModelManager(new PdfBook(model.getPdfBook()), new UserPrefs());
        expectedModel.setPdf(SAMPLE_PDF_1, pdfToEncrypt);

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

        Pdf encryptedPdf = SAMPLE_PDF_1_ENCRYPTED;
        EncryptCommand encryptCommand = new EncryptCommand(INDEX_FIRST_PDF, PASSWORD_1_VALID);

        String expectedMessage = String.format(MESSAGE_ENCRYPT_PDF_SUCCESS, encryptedPdf);

        Model expectedModel = new ModelManager(new PdfBook(model.getPdfBook()), new UserPrefs());
        expectedModel.setPdf(model.getFilteredPdfList().get(0), encryptedPdf);

        assertCommandSuccess(encryptCommand, model, commandHistory, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexIndexUnfilteredList_failure() {
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
        try {
            PDDocument file = PDDocument.load(new File(pdfToInitialise.getDirectory().getDirectory(),
                    pdfToInitialise.getName().getFullName()), PASSWORD_1_VALID);
            file.setAllSecurityToBeRemoved(true);
            file.save(Paths.get(pdfToInitialise.getDirectory().getDirectory(),
                    pdfToInitialise.getName().getFullName()).toFile());
            file.close();
        } catch (IOException ioe) {
            throw new CommandException(String.format(MESSAGE_DECRYPT_PDF_FAILURE, pdfToInitialise.getName()));
        }
    }

    /**
     * Set {code pdfToInitialise} to be decrypted
     */
    private static void preAndPostProcessingTest(Pdf pdfToInitialise) throws CommandException {
        try {
            PDDocument file = PDDocument.load(new File(pdfToInitialise.getDirectory().getDirectory(),
                    pdfToInitialise.getName().getFullName()), PASSWORD_1_VALID);
            file.setAllSecurityToBeRemoved(true);
            file.save(Paths.get(pdfToInitialise.getDirectory().getDirectory(),
                    pdfToInitialise.getName().getFullName()).toFile());
            file.close();
        } catch (IOException ioe) {
            throw new CommandException(String.format(MESSAGE_DECRYPT_PDF_FAILURE, pdfToInitialise.getName()));
        }
    }
}
