package seedu.address.logic.commands;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

//import static seedu.address.logic.parser.CliSyntax.PREFIX_DIRECTORY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_FILE;
//import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG_NEW;

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
    public static final String VALID_NAME_1 = "validName_A.pdf";
    public static final String VALID_NAME_2 = "Valid Name_B.pdf";
    public static final String VALID_NAME_3 = "validnamec!.pdf";
    public static final String VALID_DIR_1 =
            Paths.get("src", "test", "data", "JsonAdaptedPdfTest", "CS2103T_Lecture3.pdf")
            .getParent().toAbsolutePath().toString();
    public static final String VALID_DIR_2 =
            Paths.get("src", "data", "JsonAdaptedPdfTest", "CS2103T_Schedule_AY1819S2.pdf")
            .toAbsolutePath().toString();
    public static final String VALID_DIR_3 =
            Paths.get("src", "data", "JsonAdaptedPdfTest", "CS2103T_sample PPP.pdf")
            .toAbsolutePath().toString();
    public static final String VALID_SIZE_A = Long.toString(Paths.get("src", "data", "JsonAdaptedPdfTest",
            "CS2103T_Lecture3.pdf").toAbsolutePath().toFile().length());
    public static final String VALID_SIZE_B = Long.toString(Paths.get("src", "data", "JsonAdaptedPdfTest",
            "CS2103T_Schedule_AY1819S2.pdf").toAbsolutePath().toFile().length());
    public static final String VALID_SIZE_C = Long.toString(Paths.get("src", "data", "JsonAdaptedPdfTest",
            "CS2103T_sample PPP.pdf").toAbsolutePath().toFile().length());

    public static final String VALID_TAG_CS2103T = "CS2103T";
    public static final String VALID_TAG_LECTURE = "lecture";

    public static final String FILE_DESC_A_PDF = " " + PREFIX_FILE + Paths.get(VALID_DIR_1, VALID_NAME_1)
            .toAbsolutePath().toString();
    public static final String FILE_DESC_B_PDF = " " + PREFIX_FILE + Paths.get(VALID_DIR_2, VALID_NAME_2)
            .toAbsolutePath().toString();

    public static final String TAG_DESC_LECTURE = " " + PREFIX_TAG_NEW + VALID_TAG_LECTURE;
    public static final String TAG_DESC_CS2103T = " " + PREFIX_TAG_NEW + VALID_TAG_CS2103T;
    public static final String INVALID_FILE_PATH_DESC = " " + PREFIX_FILE + "DefinitelyNotAFilePath"; // not valid path
    public static final String INVALID_TAG_DESC = " " + PREFIX_TAG_NEW + "moduleA*"; // '*' not allowed in tags


    public static final String PREAMBLE_WHITESPACE = "\t  \r  \n";
    public static final String PREAMBLE_NON_EMPTY = "NonEmptyPreamble";

    public static final EditCommand.EditPersonDescriptor DESC_A;
    public static final EditCommand.EditPersonDescriptor DESC_B;

    static {
        DESC_A = new EditPdfDescriptorBuilder().withName(VALID_NAME_1).build();
        DESC_B = new EditPdfDescriptorBuilder().withName(VALID_NAME_2).build();
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
        PdfBook expectedAddressBook = new PdfBook(actualModel.getPdfBook());
        List<Pdf> expectedFilteredList = new ArrayList<>(actualModel.getFilteredPdfList());
        Pdf expectedSelectedPdf = actualModel.getSelectedPdf();

        CommandHistory expectedCommandHistory = new CommandHistory(actualCommandHistory);

        try {
            command.execute(actualModel, actualCommandHistory);
            throw new AssertionError("The expected CommandException was not thrown.");
        } catch (CommandException e) {
            assertEquals(expectedMessage, e.getMessage());
            assertEquals(expectedAddressBook, actualModel.getPdfBook());
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
