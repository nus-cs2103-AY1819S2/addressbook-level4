package seedu.address.logic.commands;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DEADLINE_NEW;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DIRECTORY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_FILE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG_ADD;

import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.PdfBook;
import seedu.address.model.pdf.NameContainsKeywordsPredicate;
import seedu.address.model.pdf.Pdf;
import seedu.address.testutil.EditPdfDescriptorBuilder;

/**
 * Contains helper methods for testing commands.
 */
public class CommandTestUtil {
    public static final String NAME_1_VALID = "CS2103T_PDF++_UG_Intro.pdf";
    public static final String NAME_2_VALID = "CS2103T_Week9_Integration Approaches.pdf";
    public static final String NAME_3_VALID = "CS2103T_Schedule_AY1819S2.pdf";
    public static final String NAME_INVALID_EXTENSION = "InvalidName.invalid";
    public static final String NAME_INVALID_CHARACTERS = "abcd*.pdf";

    public static final String DIR_1_VALID =
            Paths.get("src", "test", "data", "JsonAdaptedPdfTest")
            .toAbsolutePath().toString();
    public static final String DIR_2_VALID =
            Paths.get("src", "test", "data", "JsonAdaptedPdfTest", "Duplicates")
            .toAbsolutePath().toString();
    public static final String DIR_3_VALID =
            Paths.get("src", "test", "data", "JsonAdaptedPdfTest", "NewTestDirectory")
            .toAbsolutePath().toString();
    public static final String DIR_INVALID_NONEXISTENT =
            Paths.get("definitelyNotARealDirectory").toAbsolutePath().toString();

    public static final String FILEPATH_1_VALID =
            Paths.get(DIR_1_VALID, NAME_1_VALID).toAbsolutePath().toString();
    public static final String FILEPATH_2_VALID =
            Paths.get(DIR_2_VALID, NAME_2_VALID).toAbsolutePath().toString();
    public static final String FILEPATH_3_VALID =
            Paths.get(DIR_3_VALID, NAME_3_VALID).toAbsolutePath().toString();

    public static final String SIZE_1_VALID = Long.toString(Paths.get(FILEPATH_1_VALID)
            .toAbsolutePath().toFile().length());
    public static final String SIZE_2_VALID = Long.toString(Paths.get(FILEPATH_2_VALID)
            .toAbsolutePath().toFile().length());
    public static final String SIZE_3_VALID = Long.toString(Paths.get(FILEPATH_3_VALID)
            .toAbsolutePath().toFile().length());
    public static final String SIZE_INVALID_ALPHABET = "abcdef";
    public static final String SIZE_INVALID_NEGATIVE = "-20";

    public static final String DATE_1_VALID = "2019-10-03";
    public static final String DATE_2_VALID = "2019-05-03";
    public static final String DATE_INVALID_DATE = "2019-10-32";
    public static final String DATE_INVALID_FORMAT = "2019/10-03";

    public static final String PROPERTY_SEPARATOR_PREFIX = "/";

    public static final String DEADLINE_STATUS_READY = "READY";
    public static final String DEADLINE_STATUS_REMOVE = "REMOVE";
    public static final String DEADLINE_STATUS_COMPLETE = "COMPLETE";
    public static final String DEADLINE_STATUS_INVALID = "INVALID";

    public static final String TAG_VALID_CS2103T = "CS2103T";
    public static final String TAG_VALID_LECTURE = "lecture";

    public static final String FILE_DESC_1_PDF = " " + PREFIX_FILE + Paths.get(DIR_1_VALID).toAbsolutePath().toString();
    public static final String FILE_DESC_2_PDF = " " + PREFIX_FILE + Paths.get(DIR_2_VALID).toAbsolutePath().toString();
    public static final String FILE_DESC_PATH_INVALID = " " + PREFIX_FILE + "DefinitelyNotAFilePath"; // not valid path

    public static final String TAG_DESC_LECTURE = " " + PREFIX_TAG_ADD + TAG_VALID_LECTURE;
    public static final String TAG_DESC_CS2103T = " " + PREFIX_TAG_ADD + TAG_VALID_CS2103T;
    public static final String TAG_DESC_INVALID = " " + PREFIX_TAG_ADD + "moduleA*"; // '*' not allowed in tags

    public static final String DEADLINE_DESC_READY = DATE_1_VALID + PROPERTY_SEPARATOR_PREFIX
            + DEADLINE_STATUS_READY;
    public static final String DEADLINE_DESC_COMPLETE = DATE_2_VALID + PROPERTY_SEPARATOR_PREFIX
            + DEADLINE_STATUS_COMPLETE;
    public static final String DEADLINE_DESC_INVALID_MISSING_STATUS = " " + PREFIX_DEADLINE_NEW + DATE_1_VALID;
    public static final String DEADLINE_DESC_INVALID_WRONG_STATUS = " " + PREFIX_DEADLINE_NEW + DATE_2_VALID
            + PROPERTY_SEPARATOR_PREFIX + DEADLINE_STATUS_INVALID;
    public static final String DEADLINE_DESC_INVALID_DATE = " " + PREFIX_DEADLINE_NEW + DATE_INVALID_DATE
            + PROPERTY_SEPARATOR_PREFIX + DEADLINE_STATUS_READY;
    public static final String DEADLINE_DESC_INVALID_FORMAT = " " + PREFIX_DEADLINE_NEW + DATE_INVALID_FORMAT
            + PROPERTY_SEPARATOR_PREFIX + DEADLINE_STATUS_READY;

    public static final String NAME_DESC_1_VALID = " " + PREFIX_NAME + NAME_1_VALID;
    public static final String NAME_DESC_2_VALID = " " + PREFIX_NAME + NAME_2_VALID;
    public static final String NAME_DESC_1_INVALID = " " + PREFIX_NAME + NAME_INVALID_EXTENSION;

    public static final String DIRECTORY_DESC_1 = " " + PREFIX_DIRECTORY + DIR_3_VALID;

    public static final String PREAMBLE_WHITESPACE = "\t  \r  \n";
    public static final String PREAMBLE_NON_EMPTY = "NonEmptyPreamble";

    public static final EditCommand.EditPdfDescriptor DESC_1;
    public static final EditCommand.EditPdfDescriptor DESC_2;

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
     * - the address book, filtered pdf list and selected pdf in {@code actualModel} remain unchanged <br>
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
     * Updates {@code model}'s filtered list to show only the pdf at the given {@code targetIndex} in the
     * {@code model}'s address book.
     */
    public static void showPdfAtIndex(Model model, Index targetIndex) {
        assertTrue(targetIndex.getZeroBased() < model.getFilteredPdfList().size());

        Pdf pdf = model.getFilteredPdfList().get(targetIndex.getZeroBased());
        final String[] splitName = pdf.getName().getFullName().split("\\s+");
        model.updateFilteredPdfList(new NameContainsKeywordsPredicate(Arrays.asList(splitName[0])));

        assertEquals(1, model.getFilteredPdfList().size());
    }

    /**
     * Deletes the first pdf in {@code model}'s filtered list from {@code model}'s address book.
     */
    public static void deleteFirstPdf(Model model) {
        Pdf firstPdf = model.getFilteredPdfList().get(0);
        model.deletePdf(firstPdf);
        model.commitPdfBook();
    }

}
