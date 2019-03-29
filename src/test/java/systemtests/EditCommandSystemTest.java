package systemtests;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;
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
import static seedu.address.logic.commands.CommandTestUtil.VALID_EXPECTED_MIN_GRADE_CS2103T;
import static seedu.address.logic.commands.CommandTestUtil.VALID_MODULE_INFO_CODE_CS1010;
import static seedu.address.logic.commands.CommandTestUtil.VALID_MODULE_INFO_CODE_CS2103T;
import static seedu.address.logic.commands.CommandTestUtil.VALID_SEMESTER_CS2103T;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PERSONS;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_PERSON;
import static seedu.address.testutil.TypicalModuleTaken.DEFAULT_MODULE_CS1010;
import static seedu.address.testutil.TypicalModuleTaken.DEFAULT_MODULE_CS2103T;
import static seedu.address.testutil.TypicalModuleTaken.KEYWORD_MATCHING_CS2103T;
import static seedu.address.testutil.TypicalModuleTaken.KEYWORD_MATCHING_MA1521;

import org.junit.Test;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.EditCommand;
import seedu.address.logic.commands.RedoCommand;
import seedu.address.logic.commands.UndoCommand;
import seedu.address.model.Model;
import seedu.address.model.moduleinfo.ModuleInfoCode;
import seedu.address.model.moduletaken.Grade;
import seedu.address.model.moduletaken.ModuleTaken;
import seedu.address.model.moduletaken.Semester;
import seedu.address.model.tag.Tag;
import seedu.address.testutil.ModuleTakenBuilder;
import seedu.address.testutil.PersonUtil;

public class EditCommandSystemTest extends GradTrakSystemTest {

    @Test
    public void edit() {
        Model model = getModel();

        /* ----------------- Performing edit operation while an unfiltered list is being shown ---------------------- */

        /* Case: edit all fields, command with leading spaces, trailing spaces and multiple spaces between each field
         * -> edited
         */
        Index index = INDEX_FIRST_PERSON;
        String command = " " + EditCommand.COMMAND_WORD + "  " + index.getOneBased()
                + "  " + NAME_DESC_CS1010 + "  " + SEMESTER_DESC_CS1010 + " "
                + EXPECTED_MIN_GRADE_DESC_CS1010 + "  " + EXPECTED_MAX_GRADE_DESC_CS1010 + " "
                + TAG_DESC_HUSBAND + " ";
        ModuleTaken editedModuleTaken = new ModuleTakenBuilder(DEFAULT_MODULE_CS1010)
                .withTags(VALID_TAG_HUSBAND).build();
        assertCommandSuccess(command, index, editedModuleTaken);

        /* Case: undo editing the last moduleTaken in the list -> last moduleTaken restored */
        command = UndoCommand.COMMAND_WORD;
        String expectedResultMessage = UndoCommand.MESSAGE_SUCCESS;
        assertCommandSuccess(command, model, expectedResultMessage);

        /* Case: redo editing the last moduleTaken in the list -> last moduleTaken edited again */
        command = RedoCommand.COMMAND_WORD;
        expectedResultMessage = RedoCommand.MESSAGE_SUCCESS;
        model.setModuleTaken(getModel().getFilteredModulesTakenList().get(INDEX_FIRST_PERSON.getZeroBased()),
                editedModuleTaken);
        assertCommandSuccess(command, model, expectedResultMessage);

        /* Case: edit a moduleTaken with new values same as existing values -> edited */
        command = EditCommand.COMMAND_WORD + " " + index.getOneBased() + NAME_DESC_CS1010
                + SEMESTER_DESC_CS1010 + EXPECTED_MIN_GRADE_DESC_CS1010
                + EXPECTED_MAX_GRADE_DESC_CS1010 + TAG_DESC_FRIEND + TAG_DESC_HUSBAND;
        assertCommandSuccess(command, index, DEFAULT_MODULE_CS1010);

        /* Case: edit a moduleTaken with new values same as another moduleTaken's values but with different name
        -> edited */
        assertTrue(getModel().getGradTrak().getModulesTakenList().contains(DEFAULT_MODULE_CS1010));
        index = INDEX_SECOND_PERSON;
        assertNotEquals(getModel().getFilteredModulesTakenList().get(index.getZeroBased()), DEFAULT_MODULE_CS1010);
        command = EditCommand.COMMAND_WORD + " " + index.getOneBased() + NAME_DESC_CS2103T
                + SEMESTER_DESC_CS1010 + EXPECTED_MIN_GRADE_DESC_CS1010
                + EXPECTED_MAX_GRADE_DESC_CS1010 + TAG_DESC_FRIEND + TAG_DESC_HUSBAND;
        editedModuleTaken = new ModuleTakenBuilder(DEFAULT_MODULE_CS1010)
                .withModuleInfoCode(VALID_MODULE_INFO_CODE_CS2103T).build();
        assertCommandSuccess(command, index, editedModuleTaken);

        /* Case: edit a moduleTaken with new values same as another moduleTaken's values but with different semester
        and minGrade -> edited
         */
        index = INDEX_SECOND_PERSON;
        command = EditCommand.COMMAND_WORD + " " + index.getOneBased() + NAME_DESC_CS1010
                + SEMESTER_DESC_CS2103T + EXPECTED_MIN_GRADE_DESC_CS2103T
                + EXPECTED_MAX_GRADE_DESC_CS1010 + TAG_DESC_FRIEND + TAG_DESC_HUSBAND;
        editedModuleTaken = new ModuleTakenBuilder(DEFAULT_MODULE_CS1010)
                .withSemester(VALID_SEMESTER_CS2103T)
                .withExpectedMinGrade(VALID_EXPECTED_MIN_GRADE_CS2103T).build();
        assertCommandSuccess(command, index, editedModuleTaken);

        /* Case: clear tags -> cleared */
        index = INDEX_FIRST_PERSON;
        command = EditCommand.COMMAND_WORD + " " + index.getOneBased() + " " + PREFIX_TAG.getPrefix();
        ModuleTaken moduleTakenToEdit = getModel().getFilteredModulesTakenList().get(index.getZeroBased());
        editedModuleTaken = new ModuleTakenBuilder(moduleTakenToEdit).withTags().build();
        assertCommandSuccess(command, index, editedModuleTaken);

        /* ------------------ Performing edit operation while a filtered list is being shown ------------------------ */

        /* Case: filtered moduleTaken list, edit index within bounds of address book and moduleTaken list -> edited */
        showPersonsWithName(KEYWORD_MATCHING_MA1521);
        index = INDEX_FIRST_PERSON;
        assertTrue(index.getZeroBased() < getModel().getFilteredModulesTakenList().size());
        command = EditCommand.COMMAND_WORD + " " + index.getOneBased() + " " + NAME_DESC_CS1010;
        moduleTakenToEdit = getModel().getFilteredModulesTakenList().get(index.getZeroBased());
        editedModuleTaken = new ModuleTakenBuilder(moduleTakenToEdit)
                .withModuleInfoCode(VALID_MODULE_INFO_CODE_CS1010).build();
        assertCommandSuccess(command, index, editedModuleTaken);

        /* Case: filtered moduleTaken list, edit index within bounds of address book but out of bounds of
        moduleTaken list -> rejected
         */
        showPersonsWithName(KEYWORD_MATCHING_CS2103T);
        int invalidIndex = getModel().getGradTrak().getModulesTakenList().size();
        assertCommandFailure(EditCommand.COMMAND_WORD + " " + invalidIndex + NAME_DESC_CS1010,
                Messages.MESSAGE_INVALID_MODULETAKEN_DISPLAYED_INDEX);

        /* --------------------- Performing edit operation while a moduleTaken card is selected --------------------- */

        /* Case: selects first card in the moduleTaken list, edit a moduleTaken -> edited,
        card selection remains unchanged but browser url changes
         */
        showAllPersons();
        index = INDEX_FIRST_PERSON;
        selectPerson(index);
        command = EditCommand.COMMAND_WORD + " " + index.getOneBased() + NAME_DESC_CS2103T
                + SEMESTER_DESC_CS2103T + EXPECTED_MIN_GRADE_DESC_CS2103T
                + EXPECTED_MAX_GRADE_DESC_CS2103T + TAG_DESC_FRIEND;
        // this can be misleading: card selection actually remains unchanged but the
        // browser's url is updated to reflect the new moduleTaken's name
        assertCommandSuccess(command, index, DEFAULT_MODULE_CS2103T, index);

        /* --------------------------------- Performing invalid edit operation -------------------------------------- */

        /* Case: invalid index (0) -> rejected */
        assertCommandFailure(EditCommand.COMMAND_WORD + " 0" + NAME_DESC_CS1010,
                String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT, EditCommand.MESSAGE_USAGE));

        /* Case: invalid index (-1) -> rejected */
        assertCommandFailure(EditCommand.COMMAND_WORD + " -1" + NAME_DESC_CS1010,
                String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT, EditCommand.MESSAGE_USAGE));

        /* Case: invalid index (size + 1) -> rejected */
        invalidIndex = getModel().getFilteredModulesTakenList().size() + 1;
        assertCommandFailure(EditCommand.COMMAND_WORD + " " + invalidIndex + NAME_DESC_CS1010,
                Messages.MESSAGE_INVALID_MODULETAKEN_DISPLAYED_INDEX);

        /* Case: missing index -> rejected */
        assertCommandFailure(EditCommand.COMMAND_WORD + NAME_DESC_CS1010,
                String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT, EditCommand.MESSAGE_USAGE));

        /* Case: missing all fields -> rejected */
        assertCommandFailure(EditCommand.COMMAND_WORD + " " + INDEX_FIRST_PERSON.getOneBased(),
                EditCommand.MESSAGE_NOT_EDITED);

        /* Case: invalid name -> rejected */
        assertCommandFailure(EditCommand.COMMAND_WORD + " " + INDEX_FIRST_PERSON.getOneBased()
                        + INVALID_CS2103T_DESC, ModuleInfoCode.MESSAGE_CONSTRAINTS);

        /* Case: invalid semester -> rejected */
        assertCommandFailure(EditCommand.COMMAND_WORD + " " + INDEX_FIRST_PERSON.getOneBased()
                        + INVALID_SEMESTER_DESC, Semester.MESSAGE_CONSTRAINTS);

        /* Case: invalid email -> rejected */
        assertCommandFailure(EditCommand.COMMAND_WORD + " " + INDEX_FIRST_PERSON.getOneBased()
                        + INVALID_EXPECTED_MIN_GRADE_DESC, Grade.MESSAGE_CONSTRAINTS);

        /* Case: invalid address -> rejected */
        assertCommandFailure(EditCommand.COMMAND_WORD + " " + INDEX_FIRST_PERSON.getOneBased()
                        + INVALID_EXPECTED_MAX_GRADE_DESC, Grade.MESSAGE_CONSTRAINTS);

        /* Case: invalid tag -> rejected */
        assertCommandFailure(EditCommand.COMMAND_WORD + " " + INDEX_FIRST_PERSON.getOneBased()
                        + INVALID_TAG_DESC, Tag.MESSAGE_CONSTRAINTS);

        /* Case: edit a moduleTaken with new values same as another moduleTaken's values -> rejected */
        executeCommand(PersonUtil.getAddCommand(DEFAULT_MODULE_CS1010));
        assertTrue(getModel().getGradTrak().getModulesTakenList().contains(DEFAULT_MODULE_CS1010));
        index = INDEX_FIRST_PERSON;
        assertFalse(getModel().getFilteredModulesTakenList().get(index.getZeroBased()).equals(DEFAULT_MODULE_CS1010));
        command = EditCommand.COMMAND_WORD + " " + index.getOneBased() + NAME_DESC_CS1010
                + SEMESTER_DESC_CS1010 + EXPECTED_MIN_GRADE_DESC_CS1010 + EXPECTED_MAX_GRADE_DESC_CS1010
                + TAG_DESC_FRIEND + TAG_DESC_HUSBAND;
        assertCommandFailure(command, EditCommand.MESSAGE_DUPLICATE_PERSON);

        /* Case: edit a moduleTaken with new values same as another moduleTaken's values but with
        different tags -> rejected */
        command = EditCommand.COMMAND_WORD + " " + index.getOneBased() + NAME_DESC_CS1010
                + SEMESTER_DESC_CS1010 + EXPECTED_MIN_GRADE_DESC_CS1010
                + EXPECTED_MAX_GRADE_DESC_CS1010 + TAG_DESC_HUSBAND;
        assertCommandFailure(command, EditCommand.MESSAGE_DUPLICATE_PERSON);

        /* Case: edit a moduleTaken with new values same as another moduleTaken's values but with
        different address -> rejected */
        command = EditCommand.COMMAND_WORD + " " + index.getOneBased() + NAME_DESC_CS1010
                + SEMESTER_DESC_CS1010 + EXPECTED_MIN_GRADE_DESC_CS1010
                + EXPECTED_MAX_GRADE_DESC_CS2103T + TAG_DESC_FRIEND + TAG_DESC_HUSBAND;
        assertCommandFailure(command, EditCommand.MESSAGE_DUPLICATE_PERSON);

        /* Case: edit a moduleTaken with new values same as another moduleTaken's values
        but with different semester -> rejected */
        command = EditCommand.COMMAND_WORD + " " + index.getOneBased() + NAME_DESC_CS1010
                + SEMESTER_DESC_CS2103T + EXPECTED_MIN_GRADE_DESC_CS1010
                + EXPECTED_MAX_GRADE_DESC_CS1010 + TAG_DESC_FRIEND + TAG_DESC_HUSBAND;
        assertCommandFailure(command, EditCommand.MESSAGE_DUPLICATE_PERSON);

        /* Case: edit a moduleTaken with new values same as another moduleTaken's values but with
        different email -> rejected */
        command = EditCommand.COMMAND_WORD + " " + index.getOneBased() + NAME_DESC_CS1010
                + SEMESTER_DESC_CS1010 + EXPECTED_MIN_GRADE_DESC_CS2103T
                + EXPECTED_MAX_GRADE_DESC_CS1010 + TAG_DESC_FRIEND + TAG_DESC_HUSBAND;
        assertCommandFailure(command, EditCommand.MESSAGE_DUPLICATE_PERSON);
    }

    /**
     * Performs the same verification as {@code assertCommandSuccess(String, Index, ModuleTaken, Index)} except that
     * the browser url and selected card remain unchanged.
     * @param toEdit the index of the current model's filtered list
     * @see EditCommandSystemTest#assertCommandSuccess(String, Index, ModuleTaken, Index)
     */
    private void assertCommandSuccess(String command, Index toEdit, ModuleTaken editedModuleTaken) {
        assertCommandSuccess(command, toEdit, editedModuleTaken, null);
    }

    /**
     * Performs the same verification as {@code assertCommandSuccess(String, Model, String, Index)}
     * and in addition,<br>
     * 1. Asserts that result display box displays the success message of executing {@code EditCommand}.<br>
     * 2. Asserts that the model related components are updated to reflect the moduleTaken at index {@code toEdit} being
     * updated to values specified {@code editedModuleTaken}.<br>
     * @param toEdit the index of the current model's filtered list.
     * @see EditCommandSystemTest#assertCommandSuccess(String, Model, String, Index)
     */
    private void assertCommandSuccess(String command, Index toEdit, ModuleTaken editedModuleTaken,
            Index expectedSelectedCardIndex) {
        Model expectedModel = getModel();
        expectedModel.setModuleTaken(expectedModel.getFilteredModulesTakenList()
                .get(toEdit.getZeroBased()), editedModuleTaken);
        expectedModel.updateFilteredModulesTakenList(PREDICATE_SHOW_ALL_PERSONS);

        assertCommandSuccess(command, expectedModel,
                String.format(EditCommand.MESSAGE_EDIT_PERSON_SUCCESS, editedModuleTaken), expectedSelectedCardIndex);
    }

    /**
     * Performs the same verification as {@code assertCommandSuccess(String, Model, String, Index)} except that the
     * browser url and selected card remain unchanged.
     * @see EditCommandSystemTest#assertCommandSuccess(String, Model, String, Index)
     */
    private void assertCommandSuccess(String command, Model expectedModel, String expectedResultMessage) {
        assertCommandSuccess(command, expectedModel, expectedResultMessage, null);
    }

    /**
     * Executes {@code command} and in addition,<br>
     * 1. Asserts that the command box displays an empty string.<br>
     * 2. Asserts that the result display box displays {@code expectedResultMessage}.<br>
     * 3. Asserts that the browser url and selected card update accordingly depending on the card at
     * {@code expectedSelectedCardIndex}.<br>
     * 4. Asserts that the status bar's sync status changes.<br>
     * 5. Asserts that the command box has the default style class.<br>
     * Verifications 1 and 2 are performed by
     * {@code GradTrakSystemTest#assertApplicationDisplaysExpected(String, String, Model)}.<br>
     * @see GradTrakSystemTest#assertApplicationDisplaysExpected(String, String, Model)
     * @see GradTrakSystemTest#assertSelectedCardChanged(Index)
     */
    private void assertCommandSuccess(String command, Model expectedModel, String expectedResultMessage,
            Index expectedSelectedCardIndex) {
        executeCommand(command);
        expectedModel.updateFilteredModulesTakenList(PREDICATE_SHOW_ALL_PERSONS);
        assertApplicationDisplaysExpected("", expectedResultMessage, expectedModel);
        assertCommandBoxShowsDefaultStyle();
        if (expectedSelectedCardIndex != null) {
            assertSelectedCardChanged(expectedSelectedCardIndex);
        } else {
            assertSelectedCardUnchanged();
        }
        assertStatusBarUnchangedExceptSyncStatus();
    }

    /**
     * Executes {@code command} and in addition,<br>
     * 1. Asserts that the command box displays {@code command}.<br>
     * 2. Asserts that result display box displays {@code expectedResultMessage}.<br>
     * 3. Asserts that the browser url, selected card and status bar remain unchanged.<br>
     * 4. Asserts that the command box has the error style.<br>
     * Verifications 1 and 2 are performed by
     * {@code GradTrakSystemTest#assertApplicationDisplaysExpected(String, String, Model)}.<br>
     * @see GradTrakSystemTest#assertApplicationDisplaysExpected(String, String, Model)
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
