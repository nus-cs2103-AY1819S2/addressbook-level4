package systemtests;

//import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.INPUT_COLOR_BLUE;
import static seedu.address.logic.commands.CommandTestUtil.INPUT_COLOR_GREEN;
import static seedu.address.logic.commands.CommandTestUtil.INPUT_NAME_A;
import static seedu.address.logic.commands.CommandTestUtil.INPUT_NAME_B;
import static seedu.address.logic.commands.CommandTestUtil.INPUT_TYPE_BOTTOM;
import static seedu.address.logic.commands.CommandTestUtil.INPUT_TYPE_TOP;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_INPUT_COLOR;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_INPUT_NAME;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_INPUT_TYPE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_COLOR_GREEN;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_A;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_B;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TYPE_TOP;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_APPARELS;

import static seedu.address.testutil.TypicalApparels.ARMANY;
import static seedu.address.testutil.TypicalApparels.BOBYIN;
import static seedu.address.testutil.TypicalApparels.KEYWORD_MATCHING_BELT;

import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_APPAREL;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_APPAREL;

import org.junit.Test;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.EditCommand;
import seedu.address.logic.commands.RedoCommand;
import seedu.address.logic.commands.UndoCommand;
import seedu.address.model.Model;
import seedu.address.model.apparel.Apparel;
import seedu.address.model.apparel.ClothingType;
import seedu.address.model.apparel.Color;
import seedu.address.model.apparel.Name;
import seedu.address.testutil.ApparelBuilder;
//import seedu.address.testutil.ApparelUtil;

public class EditCommandSystemTest extends AddressBookSystemTest {


    @Test
    public void edit() {
        Model model = getModel();

        /* ----------------- Performing edit operation while an unfiltered list is being shown ---------------------- */

        /* Case: edit all fields, command with leading spaces, trailing spaces and multiple spaces between each field
         * -> edited
         */
        Index index = INDEX_FIRST_APPAREL;
        String command = " " + EditCommand.COMMAND_WORD + "  " + index.getOneBased() + "  " + INPUT_NAME_B + "  "
                + INPUT_COLOR_BLUE + " " + INPUT_TYPE_BOTTOM + " ";
        Apparel editedApparel = new ApparelBuilder(BOBYIN).build();
        assertCommandSuccess(command, index, editedApparel);

        /* Case: undo editing the last apparel in the list -> last apparel restored */
        command = UndoCommand.COMMAND_WORD;
        String expectedResultMessage = UndoCommand.MESSAGE_SUCCESS;
        assertCommandSuccess(command, model, expectedResultMessage);

        /* Case: redo editing the last apparel in the list -> last apparel edited again */
        command = RedoCommand.COMMAND_WORD;
        expectedResultMessage = RedoCommand.MESSAGE_SUCCESS;
        model.setPerson(getModel().getFilteredApparelList().get(INDEX_FIRST_APPAREL.getZeroBased()), editedApparel);
        assertCommandSuccess(command, model, expectedResultMessage);

        /* Case: edit a apparel with new values same as existing values -> edited */
        command = EditCommand.COMMAND_WORD + " " + index.getOneBased() + INPUT_NAME_B
                + INPUT_COLOR_BLUE + INPUT_TYPE_BOTTOM;
        assertCommandSuccess(command, index, BOBYIN);

        /* Case: edit a apparel with new values same as another apparel's values but with different name -> edited */
        assertTrue(getModel().getAddressBook().getApparelList().contains(BOBYIN));
        index = INDEX_SECOND_APPAREL;
        assertNotEquals(getModel().getFilteredApparelList().get(index.getZeroBased()), BOBYIN);
        command = EditCommand.COMMAND_WORD + " " + index.getOneBased() + INPUT_NAME_A
                + INPUT_COLOR_BLUE + INPUT_TYPE_BOTTOM;
        editedApparel = new ApparelBuilder(BOBYIN).withName(VALID_NAME_A).build();
        assertCommandSuccess(command, index, editedApparel);

        /* Case: edit a apparel with new values same as another apparel's values but with different phone and email
         * -> edited
         */
        index = INDEX_SECOND_APPAREL;
        command = EditCommand.COMMAND_WORD + " " + index.getOneBased() + INPUT_NAME_B
                + INPUT_COLOR_GREEN + INPUT_TYPE_TOP;
        editedApparel = new ApparelBuilder(BOBYIN).withColor(VALID_COLOR_GREEN)
                            .withClothingType(VALID_TYPE_TOP).build();
        assertCommandSuccess(command, index, editedApparel);

        /**//* Case: clear tags -> cleared *//* commented out as we dont need tags anymore
        index = INDEX_FIRST_APPAREL;
        command = EditCommand.COMMAND_WORD + " " + index.getOneBased();
        Apparel apparelToEdit = getModel().getFilteredApparelList().get(index.getZeroBased());
        editedApparel = new ApparelBuilder(apparelToEdit).build();
        assertCommandSuccess(command, index, editedApparel);*/

        /* ------------------ Performing edit operation while a filtered list is being shown ------------------------ */

        /* Case: filtered apparel list, edit index within bounds of address book and apparel list -> edited */
        showPersonsWithName(KEYWORD_MATCHING_BELT);
        index = INDEX_FIRST_APPAREL;
        assertTrue(index.getZeroBased() < getModel().getFilteredApparelList().size());
        command = EditCommand.COMMAND_WORD + " " + index.getOneBased() + " " + INPUT_NAME_B;
        Apparel apparelToEdit = getModel().getFilteredApparelList().get(index.getZeroBased());
        editedApparel = new ApparelBuilder(apparelToEdit).withName(VALID_NAME_B).build();
        assertCommandSuccess(command, index, editedApparel);

        /* Case: filtered apparel list, edit index within bounds of address book but out of bounds of apparel list
         * -> rejected
         */
        showPersonsWithName(KEYWORD_MATCHING_BELT);
        int invalidIndex = getModel().getAddressBook().getApparelList().size();
        assertCommandFailure(EditCommand.COMMAND_WORD + " " + invalidIndex + INPUT_NAME_B,
                Messages.MESSAGE_INVALID_APPAREL_DISPLAYED_INDEX);

        /* --------------------- Performing edit operation while a apparel card is selected ------------------------- */

        /* Case: selects first card in the apparel list, edit a apparel -> edited, card selection remains unchanged but
         * browser url changes
         */
        showAllPersons();
        index = INDEX_FIRST_APPAREL;
        selectPerson(index);
        command = EditCommand.COMMAND_WORD + " " + index.getOneBased() + INPUT_NAME_A
                + INPUT_COLOR_GREEN + INPUT_TYPE_TOP;
        // this can be misleading: card selection actually remains unchanged but the
        // browser's url is updated to reflect the new apparel's name
        assertCommandSuccess(command, index, ARMANY, index);

        /* --------------------------------- Performing invalid edit operation -------------------------------------- */

        /* Case: invalid index (0) -> rejected */
        assertCommandFailure(EditCommand.COMMAND_WORD + " 0" + INPUT_NAME_B,
                String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT, EditCommand.MESSAGE_USAGE));

        /* Case: invalid index (-1) -> rejected */
        assertCommandFailure(EditCommand.COMMAND_WORD + " -1" + INPUT_NAME_B,
                String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT, EditCommand.MESSAGE_USAGE));

        /* Case: invalid index (size + 1) -> rejected */
        invalidIndex = getModel().getFilteredApparelList().size() + 1;
        assertCommandFailure(EditCommand.COMMAND_WORD + " " + invalidIndex + INPUT_NAME_B,
                Messages.MESSAGE_INVALID_APPAREL_DISPLAYED_INDEX);

        /* Case: missing index -> rejected */
        assertCommandFailure(EditCommand.COMMAND_WORD + INPUT_NAME_B,
                String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT, EditCommand.MESSAGE_USAGE));

        /* Case: missing all fields -> rejected */
        assertCommandFailure(EditCommand.COMMAND_WORD + " " + INDEX_FIRST_APPAREL.getOneBased(),
                EditCommand.MESSAGE_NOT_EDITED);

        /* Case: invalid name -> rejected */
        assertCommandFailure(EditCommand.COMMAND_WORD + " " + INDEX_FIRST_APPAREL.getOneBased()
                        + INVALID_INPUT_NAME,
                Name.MESSAGE_CONSTRAINTS);

        /* Case: invalid color -> rejected */
        assertCommandFailure(EditCommand.COMMAND_WORD + " " + INDEX_FIRST_APPAREL.getOneBased()
                        + INVALID_INPUT_COLOR,
                Color.MESSAGE_CONSTRAINTS);

        /* Case: invalid clothingType -> rejected */
        assertCommandFailure(EditCommand.COMMAND_WORD + " " + INDEX_FIRST_APPAREL.getOneBased()
                        + INVALID_INPUT_TYPE,
                ClothingType.MESSAGE_CONSTRAINTS);

        /**//* Case: invalid address -> rejected *//* commented as not dealing with address and tag

        assertCommandFailure(EditCommand.COMMAND_WORD + " " + INDEX_FIRST_APPAREL.getOneBased(),
                Address.MESSAGE_CONSTRAINTS);

        *//* Case: invalid tag -> rejected *//*
        assertCommandFailure(EditCommand.COMMAND_WORD + " " + INDEX_FIRST_APPAREL.getOneBased(),
                Tag.MESSAGE_CONSTRAINTS);*/

        /**//* Case: edit a apparel with new values same as another apparel's values -> rejected *//*
        executeCommand(ApparelUtil.getAddCommand(BOBYIN));
        assertTrue(getModel().getAddressBook().getApparelList().contains(BOBYIN));
        index = INDEX_FIRST_APPAREL;
        assertFalse(getModel().getFilteredApparelList().get(index.getZeroBased()).equals(BOBYIN));
        command = EditCommand.COMMAND_WORD + " " + index.getOneBased() + INPUT_NAME_B
                + INPUT_COLOR_BLUE + INPUT_TYPE_BOTTOM;
        assertCommandFailure(command, EditCommand.MESSAGE_DUPLICATE_APPAREL);*/

        /* Case: edit a apparel with new values same as another
         apparel's values but with different tags -> rejected */
        /*command = EditCommand.COMMAND_WORD + " " + index.getOneBased() + INPUT_NAME_B
                + INPUT_COLOR_BLUE + INPUT_TYPE_BOTTOM;
        assertCommandFailure(command, EditCommand.MESSAGE_DUPLICATE_APPAREL);

        *//* Case: edit a apparel with new values same as another apparel's values but with different address ->
        rejected *//*
        command = EditCommand.COMMAND_WORD + " " + index.getOneBased() + INPUT_NAME_B
                + INPUT_COLOR_BLUE + INPUT_TYPE_BOTTOM;
        assertCommandFailure(command, EditCommand.MESSAGE_DUPLICATE_APPAREL);*/

        /**//* Case: edit a apparel with new values same as another apparel's
        values but with different color -> rejected *//*
        command = EditCommand.COMMAND_WORD + " " + index.getOneBased() + INPUT_NAME_B
                + INPUT_COLOR_GREEN + INPUT_TYPE_BOTTOM;
        assertCommandFailure(command, EditCommand.MESSAGE_DUPLICATE_APPAREL);

        *//* Case: edit a apparel with new values same as another apparel's
         values but with different clothingType -> rejected *//*
        command = EditCommand.COMMAND_WORD + " " + index.getOneBased() + INPUT_NAME_B
                + INPUT_COLOR_BLUE + INPUT_TYPE_TOP;
        assertCommandFailure(command, EditCommand.MESSAGE_DUPLICATE_APPAREL);*/
    }

    /**
     * Performs the same verification as {@code assertCommandSuccess(String, Index, Apparel, Index)} except that
     * the browser url and selected card remain unchanged.
     * @param toEdit the index of the current model's filtered list
     * @see EditCommandSystemTest#assertCommandSuccess(String, Index, Apparel, Index)
     */
    private void assertCommandSuccess(String command, Index toEdit, Apparel editedApparel) {
        assertCommandSuccess(command, toEdit, editedApparel, null);
    }

    /**
     * Performs the same verification as {@code assertCommandSuccess(String, Model, String, Index)} and in addition,<br>
     * 1. Asserts that result display box displays the success message of executing {@code EditCommand}.<br>
     * 2. Asserts that the model related components are updated to reflect the apparel at index {@code toEdit} being
     * updated to values specified {@code editedApparel}.<br>
     * @param toEdit the index of the current model's filtered list.
     * @see EditCommandSystemTest#assertCommandSuccess(String, Model, String, Index)
     */
    private void assertCommandSuccess(String command, Index toEdit, Apparel editedApparel,
            Index expectedSelectedCardIndex) {
        Model expectedModel = getModel();
        expectedModel.setPerson(expectedModel.getFilteredApparelList().get(toEdit.getZeroBased()), editedApparel);
        expectedModel.updateFilteredApparelList(PREDICATE_SHOW_ALL_APPARELS);

        assertCommandSuccess(command, expectedModel,
                String.format(EditCommand.MESSAGE_EDIT_APPAREL_SUCCESS, editedApparel), expectedSelectedCardIndex);
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
     * {@code AddressBookSystemTest#assertApplicationDisplaysExpected(String, String, Model)}.<br>
     * @see AddressBookSystemTest#assertApplicationDisplaysExpected(String, String, Model)
     * @see AddressBookSystemTest#assertSelectedCardChanged(Index)
     */
    private void assertCommandSuccess(String command, Model expectedModel, String expectedResultMessage,
            Index expectedSelectedCardIndex) {
        executeCommand(command);
        expectedModel.updateFilteredApparelList(PREDICATE_SHOW_ALL_APPARELS);
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
