package seedu.pdf.logic.commands;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static seedu.pdf.logic.parser.CliSyntax.PREFIX_DEADLINE_NEW;
import static seedu.pdf.logic.parser.CliSyntax.PREFIX_DIRECTORY;
import static seedu.pdf.logic.parser.CliSyntax.PREFIX_FILE;
import static seedu.pdf.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.pdf.logic.parser.CliSyntax.PREFIX_PASSWORD;
import static seedu.pdf.logic.parser.CliSyntax.PREFIX_TAG_ADD;

import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.pdf.commons.core.index.Index;
import seedu.pdf.logic.CommandHistory;
import seedu.pdf.logic.commands.exceptions.CommandException;
import seedu.pdf.model.Model;
import seedu.pdf.model.PdfBook;
import seedu.pdf.model.pdf.NameContainsKeywordsPredicate;
import seedu.pdf.model.pdf.Pdf;
import seedu.pdf.testutil.EditPdfDescriptorBuilder;

/**
 * Contains helper methods for testing commands.
 */
public class CommandTestUtil {

    //Error Handling Constants
    public static final String MESSAGE_UNEXPECTEDEXCEPTION_VALIDINPUT = "Unexpected Exception thrown for valid input.";

    //Name Constants
    public static final String NAME_1_VALID = "CS2103T_PDF++_UG_Intro.pdf";
    public static final String NAME_2_VALID = "CS2103T_Week9_Integration Approaches.pdf";
    public static final String NAME_3_VALID = "CS2103T_Schedule_AY1819S2.pdf";
    public static final String NAME_1_DUPLICATE_VALID = "CS2103T_PDF++_UG_Intro.pdf";
    public static final String NAME_2_DUPLICATE_VALID = "CS2103T_Week9_Integration Approaches.pdf";
    public static final String NAME_INVALID_EXTENSION = "InvalidName.invalid";
    public static final String NAME_INVALID_CHARACTERS = "abcd*.pdf";

    public static final String NAME_DESC_1_VALID = " " + PREFIX_NAME + NAME_1_VALID;
    public static final String NAME_DESC_2_VALID = " " + PREFIX_NAME + NAME_2_VALID;
    public static final String NAME_DESC_1_INVALID = " " + PREFIX_NAME + NAME_INVALID_EXTENSION;

    //Directory Constants
    public static final String DIR_1_VALID =
            Paths.get("src", "test", "data", "SampleFiles", "NormalFiles")
            .toAbsolutePath().toString();
    public static final String DIR_2_VALID =
            Paths.get("src", "test", "data", "SampleFiles", "NormalFiles")
            .toAbsolutePath().toString();
    public static final String DIR_3_VALID =
            Paths.get("src", "test", "data", "SampleFiles", "NormalFiles")
            .toAbsolutePath().toString();
    public static final String DIR_1_DUPLICATE_VALID =
            Paths.get("src", "test", "data", "SampleFiles", "DuplicateFiles")
                    .toAbsolutePath().toString();
    public static final String DIR_2_DUPLICATE_VALID =
            Paths.get("src", "test", "data", "SampleFiles", "DuplicateFiles")
                    .toAbsolutePath().toString();
    public static final String DIR_INVALID_NONEXISTENT =
            Paths.get("definitelyNotARealDirectory").toAbsolutePath().toString();

    public static final String DIRECTORY_DESC_1 = " " + PREFIX_DIRECTORY + DIR_3_VALID;

    //FilePath Constants
    public static final String FILEPATH_1_VALID =
            Paths.get(DIR_1_VALID, NAME_1_VALID).toAbsolutePath().toString();
    public static final String FILEPATH_2_VALID =
            Paths.get(DIR_2_VALID, NAME_2_VALID).toAbsolutePath().toString();
    public static final String FILEPATH_3_VALID =
            Paths.get(DIR_3_VALID, NAME_3_VALID).toAbsolutePath().toString();
    public static final String FILEPATH_1_DUPLICATE_VALID =
            Paths.get(DIR_1_DUPLICATE_VALID, NAME_1_DUPLICATE_VALID).toAbsolutePath().toString();
    public static final String FILEPATH_2_DUPLICATE_VALID =
            Paths.get(DIR_2_DUPLICATE_VALID, NAME_2_DUPLICATE_VALID).toAbsolutePath().toString();

    public static final String FILE_DESC_1_PDF = " " + PREFIX_FILE + FILEPATH_1_VALID;
    public static final String FILE_DESC_2_PDF = " " + PREFIX_FILE + FILEPATH_2_VALID;
    public static final String FILE_DESC_PATH_INVALID = " " + PREFIX_FILE + "DefinitelyNotAFilePath"; // not valid path

    //Size Constants
    public static final String SIZE_1_VALID = Long.toString(Paths.get(FILEPATH_1_VALID)
            .toAbsolutePath().toFile().length());
    public static final String SIZE_2_VALID = Long.toString(Paths.get(FILEPATH_2_VALID)
            .toAbsolutePath().toFile().length());
    public static final String SIZE_3_VALID = Long.toString(Paths.get(FILEPATH_3_VALID)
            .toAbsolutePath().toFile().length());
    public static final String SIZE_INVALID_ALPHABET = "abcdef";
    public static final String SIZE_INVALID_NEGATIVE = "-20";

    //Deadline Constants
    public static final String DATE_1_VALID = "2019-10-03";
    public static final String DATE_2_VALID = "2019-05-03";
    public static final String DATE_INVALID_DATE = "2019-10-32";
    public static final String DATE_USER_INPUT_INVALID = "13/04/2019";
    public static final String DATE_USER_INPUT_VALID = "13-04-2019";

    //Password Constant
    public static final String PASSWORD_1_VALID = "validPassword1";

    public static final String PROPERTY_SEPARATOR_PREFIX = "/";

    public static final String DEADLINE_STATUS_NOTDONE = "false";
    public static final String DEADLINE_STATUS_DONE = "true";
    public static final String DEADLINE_STATUS_INVALID = "INVALID";

    public static final String DEADLINE_JSON_NOT_DONE = DATE_1_VALID + PROPERTY_SEPARATOR_PREFIX
            + DEADLINE_STATUS_NOTDONE;
    public static final String DEADLINE_JSON_DONE = DATE_2_VALID + PROPERTY_SEPARATOR_PREFIX
            + DEADLINE_STATUS_DONE;
    public static final String DEADLINE_JSON_INVALID_MISSINGSEPERATORPREFIX = DATE_1_VALID + DEADLINE_STATUS_DONE;
    public static final String DEADLINE_JSON_INVALID_MISSINGSTATUS = DATE_2_VALID;
    public static final String DEADLINE_JSON_INVALID_INVALIDSTATUS = DATE_2_VALID + PROPERTY_SEPARATOR_PREFIX
            + DEADLINE_STATUS_INVALID;
    public static final String DEADLINE_JSON_INVALID_INVALID_DATE = DATE_INVALID_DATE + PROPERTY_SEPARATOR_PREFIX
            + DEADLINE_STATUS_NOTDONE;

    public static final String DEADLINE_DESC_VALID = " " + PREFIX_DEADLINE_NEW + DATE_1_VALID;
    //Delete Constants
    public static final String DELETE_TYPE_SOFT = "soft";
    public static final String DELETE_TYPE_HARD = "hard";
    public static final String DELETE_TYPE_INVALID = "invalidType";

    //Tag Constants
    public static final String TAG_VALID_CS2103T = "CS2103T";
    public static final String TAG_VALID_LECTURE = "lecture";
    public static final String TAG_INVALID_FRIEND = "#friend";

    public static final String TAG_DESC_LECTURE = " " + PREFIX_TAG_ADD + TAG_VALID_LECTURE;
    public static final String TAG_DESC_CS2103T = " " + PREFIX_TAG_ADD + TAG_VALID_CS2103T;
    public static final String TAG_DESC_INVALID = " " + PREFIX_TAG_ADD + "moduleA*"; // '*' not allowed in tags

    public static final String PASSWORD_DESC_VALID = " " + PREFIX_PASSWORD + PASSWORD_1_VALID;

    //Preamble Constants
    public static final String PREAMBLE_WHITESPACE = "\t  \r  \n";
    public static final String PREAMBLE_NON_EMPTY = "NonEmptyPreamble";

    //Edit Command Descriptors
    public static final RenameCommand.EditPdfDescriptor DESC_1;
    public static final RenameCommand.EditPdfDescriptor DESC_2;

    static {
        DESC_1 = new EditPdfDescriptorBuilder().withName(NAME_1_VALID).build();
        DESC_2 = new EditPdfDescriptorBuilder().withName(NAME_2_VALID).build();
    }

    /**
     * Executes the given {@code command}, confirms that <br>
     * - the returned {@link CommandResult} matches {@code expectedCommandResult} <br>
     * - the {@code actualModel} matches {@code expectedModel} <br>
     * - the {@code actualCommandHistory} remains unchanged.
     */
    public static void assertCommandSuccess(Command command, Model actualModel, CommandHistory actualCommandHistory,
            CommandResult expectedCommandResult, Model expectedModel) {
        CommandHistory expectedCommandHistory = new CommandHistory(actualCommandHistory);
        try {
            CommandResult result = command.execute(actualModel, actualCommandHistory);
            assertEquals(expectedCommandResult, result);
            assertEquals(expectedModel, actualModel);
            assertEquals(expectedCommandHistory, actualCommandHistory);
        } catch (CommandException ce) {
            throw new AssertionError("Execution of command should not fail.", ce);
        }
    }

    /**
     * Convenience wrapper to {@link #assertCommandSuccess(Command, Model, CommandHistory, CommandResult, Model)}
     * that takes a string {@code expectedMessage}.
     */
    public static void assertCommandSuccess(Command command, Model actualModel, CommandHistory actualCommandHistory,
            String expectedMessage, Model expectedModel) {
        CommandResult expectedCommandResult = new CommandResult(expectedMessage);
        assertCommandSuccess(command, actualModel, actualCommandHistory, expectedCommandResult, expectedModel);
    }

    /**
     * Executes the given {@code command}, confirms that <br>
     * - a {@code CommandException} is thrown <br>
     * - the CommandException message matches {@code expectedMessage} <br>
     * - the pdf book, filtered pdf list and selected pdf in {@code actualModel} remain unchanged <br>
     * - {@code actualCommandHistory} remains unchanged.
     */
    public static void assertCommandFailure(Command command, Model actualModel, CommandHistory actualCommandHistory,
            String expectedMessage) {
        // we are unable to defensively copy the model for comparison later, so we can
        // only do so by copying its components.
        PdfBook expectedPdfBook = new PdfBook(actualModel.getPdfBook());
        List<Pdf> expectedFilteredList = new ArrayList<>(actualModel.getFilteredPdfList());
        Pdf expectedSelectedPdf = actualModel.getSelectedPdf();

        CommandHistory expectedCommandHistory = new CommandHistory(actualCommandHistory);

        try {
            command.execute(actualModel, actualCommandHistory);
            throw new AssertionError("The expected CommandException was not thrown.");
        } catch (CommandException e) {
            assertEquals(expectedMessage, e.getMessage());
            assertEquals(expectedPdfBook, actualModel.getPdfBook());
            assertEquals(expectedFilteredList, actualModel.getFilteredPdfList());
            assertEquals(expectedSelectedPdf, actualModel.getSelectedPdf());
            assertEquals(expectedCommandHistory, actualCommandHistory);
        }
    }

    /**
     * Convenience wrapper to {@link #assertMergeCommandSuccess(Pdf, Pdf, Model, CommandHistory,
     * CommandResult, Model, CommandResult)}
     * that takes a string {@code expectedMessage} for MergeCommand.
     */
    public static void assertMergeCommandSuccess(Pdf pdfToTest, Pdf mergedPdf, Model actualModel,
            CommandHistory actualCommandHistory, String expectedMessage,
            Model expectedModel, CommandResult actualResult) {
        CommandResult expectedCommandResult = new CommandResult(expectedMessage);
        assertMergeCommandSuccess(pdfToTest, mergedPdf, actualModel,
                actualCommandHistory, expectedCommandResult, expectedModel, actualResult);
    }

    /**
     * Executes the given {@code command}, confirms that <br>
     * - the returned {@link CommandResult} matches {@code expectedCommandResult} <br>
     * - the {@code actualModel} matches {@code expectedModel} <br>
     * - the {@code actualCommandHistory} remains unchanged.
     */
    public static void assertMergeCommandSuccess(Pdf pdfToTest, Pdf mergedPdf, Model actualModel,
            CommandHistory actualCommandHistory, CommandResult expectedCommandResult,
            Model expectedModel, CommandResult actualResult) {
        CommandHistory expectedCommandHistory = new CommandHistory(actualCommandHistory);

        assertEquals(pdfToTest, mergedPdf);
        assertEquals(expectedCommandResult, actualResult);
        assertEquals(expectedModel, actualModel);
        assertEquals(expectedCommandHistory, actualCommandHistory);

    }

    /**
     * Updates {@code model}'s filtered list to show only the pdf at the given {@code targetIndex} in the
     * {@code model}'s pdf book.
     */
    public static void showPdfAtIndex(Model model, Index targetIndex) {
        assertTrue(targetIndex.getZeroBased() < model.getFilteredPdfList().size());

        Pdf pdf = model.getFilteredPdfList().get(targetIndex.getZeroBased());
        final String[] splitName = pdf.getName().getFullName().split("\\s+");
        model.updateFilteredPdfList(new NameContainsKeywordsPredicate(Arrays.asList(splitName[0])));

        assertEquals(1, model.getFilteredPdfList().size());
    }

    /**
     * Deletes the first pdf in {@code model}'s filtered list from {@code model}'s pdf book.
     */
    public static void deleteFirstPdf(Model model) {
        Pdf firstPdf = model.getFilteredPdfList().get(0);
        model.deletePdf(firstPdf);
        model.commitPdfBook();
    }

}
