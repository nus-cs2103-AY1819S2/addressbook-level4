package seedu.pdf.logic.commands;

import static org.junit.Assert.assertTrue;
import static seedu.pdf.logic.commands.CommandTestUtil.PASSWORD_1_VALID;
import static seedu.pdf.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.pdf.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.pdf.logic.commands.CommandTestUtil.showPdfAtIndex;
import static seedu.pdf.logic.commands.DecryptCommand.MESSAGE_DECRYPT_PDF_SUCCESS;
import static seedu.pdf.logic.commands.EncryptCommand.ENCRYPTION_KEY_LENGTH;
import static seedu.pdf.testutil.TypicalIndexes.INDEX_FIRST_PDF;
import static seedu.pdf.testutil.TypicalIndexes.INDEX_SECOND_PDF;
import static seedu.pdf.testutil.TypicalPdfs.SAMPLE_PDF_1;
import static seedu.pdf.testutil.TypicalPdfs.SAMPLE_PDF_2;
import static seedu.pdf.testutil.TypicalPdfs.getTypicalPdfBook;

import java.io.IOException;
import java.nio.file.Paths;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.encryption.AccessPermission;
import org.apache.pdfbox.pdmodel.encryption.StandardProtectionPolicy;
import org.junit.After;
import org.junit.Before;
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

public class DecryptCommandTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private CommandHistory commandHistory = new CommandHistory();
    private Model model = new ModelManager(getTypicalPdfBook(), new UserPrefs());

    @Before
    public void before() {
        initialiseTest(SAMPLE_PDF_2);
    }

    @After
    public void after() throws CommandException {
        resetTestFile(SAMPLE_PDF_2);
    }

    @Test
    public void constructor_invalidIndex_throwsIndexOutOfBoundsException() {
        thrown.expect(IndexOutOfBoundsException.class);
        new DecryptCommand((Index.fromZeroBased(-1)), PASSWORD_1_VALID);

        thrown.expect(IndexOutOfBoundsException.class);
        new DecryptCommand((Index.fromZeroBased(model.getFilteredPdfList().size() + 1)), PASSWORD_1_VALID);
    }

    @Test
    public void constructor_nullPassword_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        new EncryptCommand((Index.fromZeroBased(2)), null);
    }

    @Test
    public void execute_onlyCompulsoryFieldSpecifiedUnfilteredList_success() {
        Pdf pdfToDecrypt = SAMPLE_PDF_2;
        DecryptCommand decryptCommand = new DecryptCommand(INDEX_SECOND_PDF, PASSWORD_1_VALID);

        String expectedMessage = String.format(MESSAGE_DECRYPT_PDF_SUCCESS, pdfToDecrypt);

        Model expectedModel = new ModelManager(new PdfBook(model.getPdfBook()), new UserPrefs());

        assertCommandSuccess(decryptCommand, model, commandHistory, expectedMessage, expectedModel);
    }

    @Test
    public void execute_fileAlreadyEncrypted_throwsCommandException() throws CommandException {
        thrown.expect(CommandException.class);
        DecryptCommand decryptCommand = new DecryptCommand(INDEX_SECOND_PDF, PASSWORD_1_VALID);
        decryptCommand.execute(model, commandHistory);
        decryptCommand.execute(model, commandHistory);
    }

    @Test
    public void execute_filteredList_success() {
        showPdfAtIndex(model, INDEX_SECOND_PDF);

        Pdf decryptedPdf = SAMPLE_PDF_2;
        DecryptCommand decryptCommand = new DecryptCommand(INDEX_FIRST_PDF, PASSWORD_1_VALID);

        String expectedMessage = String.format(MESSAGE_DECRYPT_PDF_SUCCESS, decryptedPdf);

        Model expectedModel = new ModelManager(new PdfBook(model.getPdfBook()), new UserPrefs());

        assertCommandSuccess(decryptCommand, model, commandHistory, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexIndexUnfilteredList_failure() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredPdfList().size() + 1);
        DecryptCommand decryptCommand = new DecryptCommand(outOfBoundIndex, PASSWORD_1_VALID);

        assertCommandFailure(decryptCommand, model, commandHistory, Messages.MESSAGE_INVALID_PDF_DISPLAYED_INDEX);
    }

    @Test
    public void execute_invalidPdfIndexFilteredList_failure() {
        showPdfAtIndex(model, INDEX_SECOND_PDF);
        Index outOfBoundIndex = INDEX_SECOND_PDF;
        // ensures that outOfBoundIndex is still in bounds of pdf book list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getPdfBook().getPdfList().size());

        DecryptCommand decryptCommand = new DecryptCommand(outOfBoundIndex, PASSWORD_1_VALID);

        assertCommandFailure(decryptCommand, model, commandHistory, Messages.MESSAGE_INVALID_PDF_DISPLAYED_INDEX);
    }

    @Test
    public void executeUndoRedo_invalidIndexUnfilteredList_failure() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredPdfList().size() + 1);

        DecryptCommand decryptCommand = new DecryptCommand(outOfBoundIndex, PASSWORD_1_VALID);

        // execution failed -> pdf book state not added into model
        assertCommandFailure(decryptCommand, model, commandHistory, Messages.MESSAGE_INVALID_PDF_DISPLAYED_INDEX);

        // single pdf book state in model -> undoCommand and redoCommand fail
        assertCommandFailure(new UndoCommand(), model, commandHistory, UndoCommand.MESSAGE_FAILURE);
        assertCommandFailure(new RedoCommand(), model, commandHistory, RedoCommand.MESSAGE_FAILURE);
    }

    @Test
    public void executeUndoRedo_encryptionDoesNotAllowUndoAndRedo_failure() throws Exception {
        DecryptCommand decryptCommand = new DecryptCommand(INDEX_FIRST_PDF, PASSWORD_1_VALID);
        initialiseTest(SAMPLE_PDF_1);
        decryptCommand.execute(model, commandHistory);

        assertCommandFailure(new UndoCommand(), model, commandHistory, UndoCommand.MESSAGE_FAILURE);
        assertCommandFailure(new RedoCommand(), model, commandHistory, RedoCommand.MESSAGE_FAILURE);
    }


    /**
     * Encrypts {@code pdfToInitialise} if it is not encrypted.
     */
    @SuppressWarnings("Duplicates")
    private void initialiseTest(Pdf pdfToInitialise) {
        try {
            PDDocument file = PDDocument.load(Paths.get(pdfToInitialise.getDirectory().getDirectory(),
                    pdfToInitialise.getName().getFullName()).toFile());
            AccessPermission ap = new AccessPermission();
            StandardProtectionPolicy spp = new StandardProtectionPolicy(PASSWORD_1_VALID, PASSWORD_1_VALID, ap);

            spp.setEncryptionKeyLength(ENCRYPTION_KEY_LENGTH);
            spp.setPermissions(ap);
            file.protect(spp);
            file.save(Paths.get(pdfToInitialise.getDirectory().getDirectory(),
                    pdfToInitialise.getName().getFullName()).toFile());
            file.close();
        } catch (IOException ioe) {
            System.out.println();
        }
    }

    /**
     * Decrypts {@code pdfToReset} if it is encrypted.
     */
    private void resetTestFile(Pdf pdfToReset) throws CommandException {
        if (pdfToReset.getIsEncrypted()) {
            Model initialisationModel = new ModelManager(getTypicalPdfBook(), new UserPrefs());
            CommandHistory initialisationCommandHistory = new CommandHistory();
            DecryptCommand decryptCommand = new DecryptCommand(INDEX_SECOND_PDF, PASSWORD_1_VALID);
            decryptCommand.execute(initialisationModel, initialisationCommandHistory);
        }
    }
}
