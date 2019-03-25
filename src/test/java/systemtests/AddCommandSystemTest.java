package systemtests;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.EXPECTED_MAX_GRADE_DESC_CS1010;
import static seedu.address.logic.commands.CommandTestUtil.EXPECTED_MAX_GRADE_DESC_CS2103T;
import static seedu.address.logic.commands.CommandTestUtil.EXPECTED_MIN_GRADE_DESC_CS1010;
import static seedu.address.logic.commands.CommandTestUtil.EXPECTED_MIN_GRADE_DESC_CS2103T;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_CS2103T_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_EXPECTED_MAX_GRADE_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_EXPECTED_MIN_GRADE_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_SEMESTER_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_TAG_DESC;
import static seedu.address.logic.commands.CommandTestUtil.NAME_DESC_CS1010;
import static seedu.address.logic.commands.CommandTestUtil.NAME_DESC_CS2103T;
import static seedu.address.logic.commands.CommandTestUtil.SEMESTER_DESC_CS1010;
import static seedu.address.logic.commands.CommandTestUtil.SEMESTER_DESC_CS2103T;
import static seedu.address.logic.commands.CommandTestUtil.TAG_DESC_FRIEND;
import static seedu.address.logic.commands.CommandTestUtil.TAG_DESC_HUSBAND;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EXPECTED_MAX_GRADE_CS1010;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EXPECTED_MIN_GRADE_CS1010;
import static seedu.address.logic.commands.CommandTestUtil.VALID_MODULE_INFO_CODE_CS1010;
import static seedu.address.logic.commands.CommandTestUtil.VALID_SEMESTER_CS1010;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.address.testutil.TypicalModuleTaken.CS1010S;
import static seedu.address.testutil.TypicalModuleTaken.CS2030;
import static seedu.address.testutil.TypicalModuleTaken.CS2040;
import static seedu.address.testutil.TypicalModuleTaken.CS2103T;
import static seedu.address.testutil.TypicalModuleTaken.DEFAULT_MODULE_CS1010;
import static seedu.address.testutil.TypicalModuleTaken.DEFAULT_MODULE_CS2103T;
import static seedu.address.testutil.TypicalModuleTaken.KEYWORD_MATCHING_CS2103T;

import org.junit.Test;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.AddCommand;
import seedu.address.logic.commands.RedoCommand;
import seedu.address.logic.commands.UndoCommand;
import seedu.address.model.Model;
import seedu.address.model.moduleinfo.ModuleInfoCode;
import seedu.address.model.person.Grade;
import seedu.address.model.person.ModuleTaken;
import seedu.address.model.person.Semester;
import seedu.address.model.tag.Tag;
import seedu.address.testutil.ModuleTakenBuilder;
import seedu.address.testutil.PersonUtil;

public class AddCommandSystemTest extends AddressBookSystemTest {

    @Test
    public void add() {
        Model model = getModel();

        /* ------------------------ Perform add operations on the shown unfiltered list ----------------------------- */

        /* Case: add a moduleTaken without tags to a non-empty address book, command with leading spaces and
         * trailing spaces -> added
         */
        ModuleTaken toAdd = DEFAULT_MODULE_CS2103T;
        String command = "   " + AddCommand.COMMAND_WORD + "  " + NAME_DESC_CS2103T + "  " + SEMESTER_DESC_CS2103T + " "
                + EXPECTED_MIN_GRADE_DESC_CS2103T + "   " + EXPECTED_MAX_GRADE_DESC_CS2103T + "   "
                + TAG_DESC_FRIEND + " ";
        assertCommandSuccess(command, toAdd);

        /* Case: undo adding Amy to the list -> Amy deleted */
        command = UndoCommand.COMMAND_WORD;
        String expectedResultMessage = UndoCommand.MESSAGE_SUCCESS;
        assertCommandSuccess(command, model, expectedResultMessage);

        /* Case: redo adding Amy to the list -> Amy added again */
        command = RedoCommand.COMMAND_WORD;
        model.addPerson(toAdd);
        expectedResultMessage = RedoCommand.MESSAGE_SUCCESS;
        assertCommandSuccess(command, model, expectedResultMessage);

        /* Case: add a moduleTaken with all fields same as another moduleTaken in the address book
        except name -> added */
        toAdd = new ModuleTakenBuilder(DEFAULT_MODULE_CS2103T)
                .withModuleInfoCode(VALID_MODULE_INFO_CODE_CS1010).build();
        command = AddCommand.COMMAND_WORD + NAME_DESC_CS1010
                + SEMESTER_DESC_CS2103T + EXPECTED_MIN_GRADE_DESC_CS2103T
                + EXPECTED_MAX_GRADE_DESC_CS2103T + TAG_DESC_FRIEND;
        assertCommandSuccess(command, toAdd);

        /* Case: add a moduleTaken with all fields same as another moduleTaken in the address book except
         * phone and email -> added
         */
        toAdd = new ModuleTakenBuilder(DEFAULT_MODULE_CS2103T)
                .withSemester(VALID_SEMESTER_CS1010)
                .withExpectedMinGrade(VALID_EXPECTED_MIN_GRADE_CS1010)
                .build();
        command = PersonUtil.getAddCommand(toAdd);
        assertCommandSuccess(command, toAdd);

        /* Case: add to empty address book -> added */
        deleteAllPersons();
        assertCommandSuccess(CS2103T);

        /* Case: add a moduleTaken with tags, command with parameters in random order -> added */
        toAdd = DEFAULT_MODULE_CS1010;
        command = AddCommand.COMMAND_WORD + TAG_DESC_FRIEND
                + SEMESTER_DESC_CS1010 + EXPECTED_MAX_GRADE_DESC_CS1010
                + NAME_DESC_CS1010 + TAG_DESC_HUSBAND
                + EXPECTED_MIN_GRADE_DESC_CS1010;
        assertCommandSuccess(command, toAdd);

        /* Case: add a moduleTaken, missing tags -> added */
        assertCommandSuccess(CS2030);

        /* -------------------------- Perform add operation on the shown filtered list ---------------------------- */

        /* Case: filters the moduleTaken list before adding -> added */
        showPersonsWithName(KEYWORD_MATCHING_CS2103T);
        assertCommandSuccess(CS2040);

        /* ------------------------ Perform add operation while a moduleTaken card is selected --------------------- */

        /* Case: selects first card in the moduleTaken list, add a moduleTaken -> added,
        card selection remains unchanged */
        selectPerson(Index.fromOneBased(1));
        assertCommandSuccess(CS1010S);

        /* ----------------------------------- Perform invalid add operations -------------------------------------- */

        /* Case: add a duplicate moduleTaken -> rejected */
        command = PersonUtil.getAddCommand(CS2030);
        assertCommandFailure(command, AddCommand.MESSAGE_DUPLICATE_PERSON);

        /* Case: add a duplicate moduleTaken except with different email -> rejected */
        toAdd = new ModuleTakenBuilder(CS2030).withExpectedMinGrade(VALID_EXPECTED_MIN_GRADE_CS1010).build();
        command = PersonUtil.getAddCommand(toAdd);
        assertCommandFailure(command, AddCommand.MESSAGE_DUPLICATE_PERSON);

        /* Case: add a duplicate moduleTaken except with different address -> rejected */
        toAdd = new ModuleTakenBuilder(CS2030).withExpectedMaxGrade(VALID_EXPECTED_MAX_GRADE_CS1010).build();
        command = PersonUtil.getAddCommand(toAdd);
        assertCommandFailure(command, AddCommand.MESSAGE_DUPLICATE_PERSON);

        /* Case: add a duplicate moduleTaken except with different tags -> rejected */
        command = PersonUtil.getAddCommand(CS2030) + " " + PREFIX_TAG.getPrefix() + "friends";
        assertCommandFailure(command, AddCommand.MESSAGE_DUPLICATE_PERSON);

        /* Case: missing name -> rejected */
        command = AddCommand.COMMAND_WORD + SEMESTER_DESC_CS2103T
                + EXPECTED_MIN_GRADE_DESC_CS2103T + EXPECTED_MAX_GRADE_DESC_CS2103T;
        assertCommandFailure(command, String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));

        /* Case: missing phone -> rejected */
        command = AddCommand.COMMAND_WORD + NAME_DESC_CS2103T
                + EXPECTED_MIN_GRADE_DESC_CS2103T + EXPECTED_MAX_GRADE_DESC_CS2103T;
        assertCommandFailure(command, String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));

        /* Case: invalid keyword -> rejected */
        command = "adds " + PersonUtil.getPersonDetails(toAdd);
        assertCommandFailure(command, Messages.MESSAGE_UNKNOWN_COMMAND);

        /* Case: invalid name -> rejected */
        command = AddCommand.COMMAND_WORD + INVALID_CS2103T_DESC
                + SEMESTER_DESC_CS2103T + EXPECTED_MIN_GRADE_DESC_CS2103T
                + EXPECTED_MAX_GRADE_DESC_CS2103T;
        assertCommandFailure(command, ModuleInfoCode.MESSAGE_CONSTRAINTS);

        /* Case: invalid phone -> rejected */
        command = AddCommand.COMMAND_WORD + NAME_DESC_CS2103T
                + INVALID_SEMESTER_DESC + EXPECTED_MIN_GRADE_DESC_CS2103T
                + EXPECTED_MAX_GRADE_DESC_CS2103T;
        assertCommandFailure(command, Semester.MESSAGE_CONSTRAINTS);

        /* Case: invalid email -> rejected */
        command = AddCommand.COMMAND_WORD + NAME_DESC_CS2103T + SEMESTER_DESC_CS2103T
                + INVALID_EXPECTED_MIN_GRADE_DESC + EXPECTED_MAX_GRADE_DESC_CS2103T;
        assertCommandFailure(command, Grade.MESSAGE_CONSTRAINTS);

        /* Case: invalid address -> rejected */
        command = AddCommand.COMMAND_WORD + NAME_DESC_CS2103T
                + SEMESTER_DESC_CS2103T + EXPECTED_MIN_GRADE_DESC_CS2103T
                + INVALID_EXPECTED_MAX_GRADE_DESC;
        assertCommandFailure(command, Grade.MESSAGE_CONSTRAINTS);

        /* Case: invalid tag -> rejected */
        command = AddCommand.COMMAND_WORD + NAME_DESC_CS2103T
                + SEMESTER_DESC_CS2103T + EXPECTED_MIN_GRADE_DESC_CS2103T
                + EXPECTED_MAX_GRADE_DESC_CS2103T + INVALID_TAG_DESC;
        assertCommandFailure(command, Tag.MESSAGE_CONSTRAINTS);
    }

    /**
     * Executes the {@code AddCommand} that adds {@code toAdd} to the model and asserts that the,<br>
     * 1. Command box displays an empty string.<br>
     * 2. Command box has the default style class.<br>
     * 3. Result display box displays the success message of executing {@code AddCommand} with the details of
     * {@code toAdd}.<br>
     * 4. {@code Storage} and {@code PersonListPanel} equal to the corresponding components in
     * the current model added with {@code toAdd}.<br>
     * 5. Browser url and selected card remain unchanged.<br>
     * 6. Status bar's sync status changes.<br>
     * Verifications 1, 3 and 4 are performed by
     * {@code AddressBookSystemTest#assertApplicationDisplaysExpected(String, String, Model)}.<br>
     * @see AddressBookSystemTest#assertApplicationDisplaysExpected(String, String, Model)
     */
    private void assertCommandSuccess(ModuleTaken toAdd) {
        assertCommandSuccess(PersonUtil.getAddCommand(toAdd), toAdd);
    }

    /**
     * Performs the same verification as {@code assertCommandSuccess(ModuleTaken)}. Executes {@code command}
     * instead.
     * @see AddCommandSystemTest#assertCommandSuccess(ModuleTaken)
     */
    private void assertCommandSuccess(String command, ModuleTaken toAdd) {
        Model expectedModel = getModel();
        expectedModel.addPerson(toAdd);
        String expectedResultMessage = String.format(AddCommand.MESSAGE_SUCCESS, toAdd);

        assertCommandSuccess(command, expectedModel, expectedResultMessage);
    }

    /**
     * Performs the same verification as {@code assertCommandSuccess(String, ModuleTaken)} except asserts that
     * the,<br>
     * 1. Result display box displays {@code expectedResultMessage}.<br>
     * 2. {@code Storage} and {@code PersonListPanel} equal to the corresponding components in
     * {@code expectedModel}.<br>
     * @see AddCommandSystemTest#assertCommandSuccess(String, ModuleTaken)
     */
    private void assertCommandSuccess(String command, Model expectedModel, String expectedResultMessage) {
        executeCommand(command);
        assertApplicationDisplaysExpected("", expectedResultMessage, expectedModel);
        assertSelectedCardUnchanged();
        assertCommandBoxShowsDefaultStyle();
        assertStatusBarUnchangedExceptSyncStatus();
    }

    /**
     * Executes {@code command} and asserts that the,<br>
     * 1. Command box displays {@code command}.<br>
     * 2. Command box has the error style class.<br>
     * 3. Result display box displays {@code expectedResultMessage}.<br>
     * 4. {@code Storage} and {@code PersonListPanel} remain unchanged.<br>
     * 5. Browser url, selected card and status bar remain unchanged.<br>
     * Verifications 1, 3 and 4 are performed by
     * {@code AddressBookSystemTest#assertApplicationDisplaysExpected(String, String, Model)}.<br>
     * @see AddressBookSystemTest#assertApplicationDisplaysExpected(String, String, Model)
     */
    private void assertCommandFailure(String command, String expectedResultMessage) {
        Model expectedModel = getModel();

        executeCommand(command);
        assertApplicationDisplaysExpected(command, expectedResultMessage, expectedModel);
        assertSelectedCardUnchanged();
        assertCommandBoxShowsErrorStyle();
        assertStatusBarUnchanged();
    }
}
