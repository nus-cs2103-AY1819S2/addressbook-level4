package seedu.address.logic.commands;

import static seedu.address.logic.commands.CommandTestUtil.PASSWORD_1_VALID;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PDF;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_PDF;
import static seedu.address.testutil.TypicalPdfs.SAMPLE_PDF_2;
import static seedu.address.testutil.TypicalPdfs.getTypicalPdfBook;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.pdf.Pdf;

public class DecryptCommandTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private CommandHistory commandHistory = new CommandHistory();
    private Model model = new ModelManager(getTypicalPdfBook(), new UserPrefs());

    @Before
    public void before() throws CommandException {
        initialiseTest(SAMPLE_PDF_2);
    }

    @After
    public void after() throws CommandException {
        resetTestFile(SAMPLE_PDF_2);
    }

    @Test
    public void constructor_invalidIndex_throwsNullPointerException() {
        thrown.expect(IndexOutOfBoundsException.class);
        new DecryptCommand((Index.fromZeroBased(-1)), PASSWORD_1_VALID);

        thrown.expect(IndexOutOfBoundsException.class);
        new DecryptCommand((Index.fromZeroBased(model.getFilteredPdfList().size() + 1)), PASSWORD_1_VALID);
    }

    /**
     * Encrypts {@code pdfToInitialise} if it is not encrypted.
     */
    private void initialiseTest(Pdf pdfToInitialise) throws CommandException {
        System.out.println(pdfToInitialise.getIsEncryted());
        if (!pdfToInitialise.getIsEncryted()) {
            Model initialisationModel = new ModelManager(getTypicalPdfBook(), new UserPrefs());
            CommandHistory initialisationCommandHistory = new CommandHistory();
            EncryptCommand encryptCommand = new EncryptCommand(INDEX_SECOND_PDF, PASSWORD_1_VALID);
            encryptCommand.execute(initialisationModel, initialisationCommandHistory);
            initialisationModel.setPdf(model.getFilteredPdfList().get(0), pdfToInitialise);
        }
    }

    /**
     * Decrypts {@code pdfToReset} if it is encrypted.
     */
    private void resetTestFile(Pdf pdfToReset) throws CommandException {
        if (pdfToReset.getIsEncryted()) {
            Model initialisationModel = new ModelManager(getTypicalPdfBook(), new UserPrefs());
            CommandHistory initialisationCommandHistory = new CommandHistory();
            DecryptCommand decryptCommand = new DecryptCommand(INDEX_FIRST_PDF, PASSWORD_1_VALID);
            decryptCommand.execute(initialisationModel, initialisationCommandHistory);
        }
    }
}
