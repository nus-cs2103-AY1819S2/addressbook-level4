package seedu.address.logic.commands;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EXPECTED_MAX_GRADE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EXPECTED_MIN_GRADE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_FINISHED;
import static seedu.address.logic.parser.CliSyntax.PREFIX_GRADE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_LECTURE_HOUR;
import static seedu.address.logic.parser.CliSyntax.PREFIX_MODULE_INFO_CODE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_SEMESTER;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.address.logic.parser.ParserUtil.FINISHED_STATUS_TRUE;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.GradTrak;
import seedu.address.model.Model;
import seedu.address.model.moduletaken.ModuleTaken;
import seedu.address.model.moduletaken.NameContainsKeywordsPredicate;
import seedu.address.testutil.EditPersonDescriptorBuilder;

/**
 * Contains helper methods for testing commands.
 */
public class CommandTestUtil {

    public static final String VALID_MODULE_INFO_CODE_CS2103T = "CS2103T";
    public static final String VALID_MODULE_INFO_CODE_CS1010 = "CS1010";
    public static final String VALID_SEMESTER_CS2103T = "Y2S2";
    public static final String VALID_SEMESTER_CS1010 = "Y2S1";
    public static final String VALID_GRADE_CS2103T = "A";
    public static final String VALID_GRADE_CS1010 = "B";
    public static final String FINISHED_STATUS_FALSE = "n";
    public static final String VALID_EXPECTED_MIN_GRADE_CS2103T = "C";
    public static final String VALID_EXPECTED_MIN_GRADE_CS1010 = "D";
    public static final String VALID_EXPECTED_MAX_GRADE_CS2103T = "A";
    public static final String VALID_EXPECTED_MAX_GRADE_CS1010 = "B";
    public static final String VALID_LECTURE_HOUR_CS2103T = "0";
    public static final String VALID_LECTURE_HOUR_CS1010 = "0";
    public static final String VALID_TAG_HUSBAND = "husband";
    public static final String VALID_TAG_FRIEND = "friend";

    public static final String NAME_DESC_CS2103T = " " + PREFIX_MODULE_INFO_CODE + VALID_MODULE_INFO_CODE_CS2103T;
    public static final String NAME_DESC_CS1010 = " " + PREFIX_MODULE_INFO_CODE + VALID_MODULE_INFO_CODE_CS1010;
    public static final String SEMESTER_DESC_CS2103T = " " + PREFIX_SEMESTER + VALID_SEMESTER_CS2103T;
    public static final String SEMESTER_DESC_CS1010 = " " + PREFIX_SEMESTER + VALID_SEMESTER_CS1010;
    public static final String GRADE_DESC_CS2103T = " " + PREFIX_GRADE + VALID_GRADE_CS2103T;
    public static final String GRADE_DESC_CS1010 = " " + PREFIX_GRADE + VALID_GRADE_CS1010;
    public static final String FINISHED_STATUS_DESC_TRUE = " " + PREFIX_FINISHED + FINISHED_STATUS_TRUE;
    public static final String FINISHED_STATUS_DESC_FALSE = " " + PREFIX_FINISHED + FINISHED_STATUS_FALSE;
    public static final String EXPECTED_MIN_GRADE_DESC_CS2103T = " " + PREFIX_EXPECTED_MIN_GRADE
            + VALID_EXPECTED_MIN_GRADE_CS2103T;
    public static final String EXPECTED_MIN_GRADE_DESC_CS1010 = " " + PREFIX_EXPECTED_MIN_GRADE
            + VALID_EXPECTED_MIN_GRADE_CS1010;
    public static final String EXPECTED_MAX_GRADE_DESC_CS2103T = " " + PREFIX_EXPECTED_MAX_GRADE
            + VALID_EXPECTED_MAX_GRADE_CS2103T;
    public static final String EXPECTED_MAX_GRADE_DESC_CS1010 = " " + PREFIX_EXPECTED_MAX_GRADE
            + VALID_EXPECTED_MAX_GRADE_CS1010;
    public static final String TAG_DESC_FRIEND = " " + PREFIX_TAG + VALID_TAG_FRIEND;
    public static final String TAG_DESC_HUSBAND = " " + PREFIX_TAG + VALID_TAG_HUSBAND;

    public static final String INVALID_CS2103T_DESC = " " + PREFIX_MODULE_INFO_CODE + "CS123I";
    public static final String INVALID_SEMESTER_DESC = " " + PREFIX_SEMESTER + "911a";
    public static final String INVALID_GRADE_DESC = " " + PREFIX_GRADE + "K";
    public static final String INVALID_EXPECTED_MIN_GRADE_DESC = " "
            + PREFIX_EXPECTED_MIN_GRADE + "bob!yahoo";
    public static final String INVALID_EXPECTED_MAX_GRADE_DESC = " "
            + PREFIX_EXPECTED_MAX_GRADE;
    public static final String INVALID_LECTURE_HOUR_DESC = " "
            + PREFIX_LECTURE_HOUR;
    public static final String INVALID_TAG_DESC = " " + PREFIX_TAG + "hubby*"; // '*' not allowed in tags

    public static final String PREAMBLE_WHITESPACE = "\t  \r  \n";
    public static final String PREAMBLE_NON_EMPTY = "NonEmptyPreamble";

    public static final EditCommand.EditPersonDescriptor DESC_CS2103T;
    public static final EditCommand.EditPersonDescriptor DESC_CS1010;

    static {
        DESC_CS2103T = new EditPersonDescriptorBuilder().withName(VALID_MODULE_INFO_CODE_CS2103T)
                .withSemester(VALID_SEMESTER_CS2103T)
                .withExpectedMinGrade(VALID_EXPECTED_MIN_GRADE_CS2103T)
                .withExpectedMaxGrade(VALID_EXPECTED_MAX_GRADE_CS2103T)
                .withLectureHour(VALID_LECTURE_HOUR_CS2103T)
                .withTags(VALID_TAG_FRIEND).build();
        DESC_CS1010 = new EditPersonDescriptorBuilder().withName(VALID_MODULE_INFO_CODE_CS1010)
                .withSemester(VALID_SEMESTER_CS1010)
                .withExpectedMinGrade(VALID_EXPECTED_MIN_GRADE_CS1010)
                .withExpectedMaxGrade(VALID_EXPECTED_MAX_GRADE_CS1010)
                .withLectureHour(VALID_LECTURE_HOUR_CS1010)
                .withTags(VALID_TAG_HUSBAND, VALID_TAG_FRIEND).build();
    }

    /**
     * Executes the given {@code command}, confirms that <br>
     * - the returned {@link CommandResult} matches {@code expectedCommandResult} <br>
     * - the {@code actualModel} matches {@code expectedModel} <br>
     * - the {@code actualCommandHistory} remains unchanged.
     */
    public static void assertCommandSuccess(Command command, Model actualModel,
                                            CommandHistory actualCommandHistory,
                                            CommandResult expectedCommandResult,
                                            Model expectedModel) {
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
    public static void assertCommandSuccess(Command command, Model actualModel,
                                            CommandHistory actualCommandHistory,
                                            String expectedMessage, Model expectedModel) {
        CommandResult expectedCommandResult = new CommandResult(expectedMessage);
        assertCommandSuccess(command, actualModel, actualCommandHistory, expectedCommandResult, expectedModel);
    }

    /**
     * Executes the given {@code command}, confirms that <br>
     * - a {@code CommandException} is thrown <br>
     * - the CommandException message matches {@code expectedMessage} <br>
     * - the address book, filtered moduleTaken list and selected moduleTaken in {@code actualModel}
     * remain unchanged <br>
     * - {@code actualCommandHistory} remains unchanged.
     */
    public static void assertCommandFailure(Command command, Model actualModel, CommandHistory actualCommandHistory,
            String expectedMessage) {
        // we are unable to defensively copy the model for comparison later, so we can
        // only do so by copying its components.
        GradTrak expectedAddressBook = new GradTrak(actualModel.getAddressBook());
        List<ModuleTaken> expectedFilteredList = new ArrayList<>(actualModel.getFilteredPersonList());
        ModuleTaken expectedSelectedModuleTaken = actualModel.getSelectedPerson();

        CommandHistory expectedCommandHistory = new CommandHistory(actualCommandHistory);

        try {
            command.execute(actualModel, actualCommandHistory);
            throw new AssertionError("The expected CommandException was not thrown.");
        } catch (CommandException e) {
            assertEquals(expectedMessage, e.getMessage());
            assertEquals(expectedAddressBook, actualModel.getAddressBook());
            assertEquals(expectedFilteredList, actualModel.getFilteredPersonList());
            assertEquals(expectedSelectedModuleTaken, actualModel.getSelectedPerson());
            assertEquals(expectedCommandHistory, actualCommandHistory);
        }
    }

    /**
     * Updates {@code model}'s filtered list to show only the moduleTaken at the given {@code targetIndex} in the
     * {@code model}'s address book.
     */
    public static void showPersonAtIndex(Model model, Index targetIndex) {
        assertTrue(targetIndex.getZeroBased() < model.getFilteredPersonList().size());

        ModuleTaken moduleTaken = model.getFilteredPersonList().get(targetIndex.getZeroBased());
        final String[] splitName = moduleTaken.getModuleInfo().toString().split("\\s+");
        model.updateFilteredPersonList(new NameContainsKeywordsPredicate(Arrays.asList(splitName[0])));

        assertEquals(1, model.getFilteredPersonList().size());
    }

    /**
     * Deletes the first moduleTaken in {@code model}'s filtered list from {@code model}'s address book.
     */
    public static void deleteFirstPerson(Model model) {
        ModuleTaken firstModuleTaken = model.getFilteredPersonList().get(0);
        model.deletePerson(firstModuleTaken);
        model.commitAddressBook();
    }

}
