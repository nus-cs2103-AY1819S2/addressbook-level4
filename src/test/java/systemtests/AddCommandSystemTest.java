package systemtests;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.INPUT_COLOR_BLUE;
import static seedu.address.logic.commands.CommandTestUtil.INPUT_COLOR_GREEN;
import static seedu.address.logic.commands.CommandTestUtil.INPUT_NAME_A;
import static seedu.address.logic.commands.CommandTestUtil.INPUT_NAME_B;
import static seedu.address.logic.commands.CommandTestUtil.INPUT_TYPE_BOTTOM;
import static seedu.address.logic.commands.CommandTestUtil.INPUT_TYPE_TOP;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_INPUT_COLOR;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_INPUT_NAME;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_INPUT_TYPE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_COLOR_BLUE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_B;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TYPE_BOTTOM;
import static seedu.address.testutil.TypicalApparels.ARMANY;
import static seedu.address.testutil.TypicalApparels.BOBYIN;
import static seedu.address.testutil.TypicalApparels.KEYWORD_MATCHING_BELT;
import static seedu.address.testutil.TypicalApparels.SHIRT1;
import static seedu.address.testutil.TypicalApparels.SHIRT2;
import static seedu.address.testutil.TypicalApparels.SHOES2;
import static seedu.address.testutil.TypicalApparels.SHOES3;

import org.junit.Test;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.AddCommand;
import seedu.address.logic.commands.RedoCommand;
import seedu.address.logic.commands.UndoCommand;
import seedu.address.model.Model;
import seedu.address.model.apparel.Apparel;
import seedu.address.model.apparel.ClothingType;
import seedu.address.model.apparel.Color;
import seedu.address.model.apparel.Name;
import seedu.address.testutil.ApparelBuilder;
import seedu.address.testutil.ApparelUtil;

public class AddCommandSystemTest extends AddressBookSystemTest {

    @Test
    public void add() {
        Model model = getModel();

        /* ------------------------ Perform add operations on the shown unfiltered list ----------------------------- */

        /* Case: add a apparel without tags to a non-empty address book, command with leading spaces and trailing spaces
         * -> added
         */
        Apparel toAdd = ARMANY;
        String command = "   " + AddCommand.COMMAND_WORD + "  " + INPUT_NAME_A + "  " + INPUT_COLOR_GREEN + " "
                + INPUT_TYPE_TOP + " ";

        assertCommandSuccess(command, toAdd);

        /* Case: undo adding Amy to the list -> Amy deleted */
        command = UndoCommand.COMMAND_WORD;
        String expectedResultMessage = UndoCommand.MESSAGE_SUCCESS;
        assertCommandSuccess(command, model, expectedResultMessage);

        /* Case: redo adding Amy to the list -> Amy added again */
        command = RedoCommand.COMMAND_WORD;
        model.addApparel(toAdd);
        expectedResultMessage = RedoCommand.MESSAGE_SUCCESS;
        assertCommandSuccess(command, model, expectedResultMessage);

        /* Case: add a apparel with all fields same as another apparel in the address book except name -> added */
        toAdd = new ApparelBuilder(ARMANY).withName(VALID_NAME_B).build();
        command = AddCommand.COMMAND_WORD + INPUT_NAME_B + INPUT_COLOR_GREEN + INPUT_TYPE_TOP;
        assertCommandSuccess(command, toAdd);

        /* Case: add a apparel with all fields same as another apparel in the address book except phone and email
         * -> added
         */
        toAdd = new ApparelBuilder(ARMANY).withColor(VALID_COLOR_BLUE).withClothingType(VALID_TYPE_BOTTOM).build();
        command = ApparelUtil.getAddCommand(toAdd);
        assertCommandSuccess(command, toAdd);

        /* Case: add to empty address book -> added */
        deleteAllPersons();
        assertCommandSuccess(SHIRT1);

        /* Case: add a apparel with tags, command with parameters in random order -> added */
        toAdd = BOBYIN;
        command = AddCommand.COMMAND_WORD + INPUT_COLOR_BLUE + INPUT_NAME_B + INPUT_TYPE_BOTTOM;
        assertCommandSuccess(command, toAdd);

        /* Case: add a apparel, missing tags -> added */
        assertCommandSuccess(SHOES2);

        /* -------------------------- Perform add operation on the shown filtered list ------------------------------ */

        /* Case: filters the apparel list before adding -> added */
        showPersonsWithName(KEYWORD_MATCHING_BELT);
        assertCommandSuccess(SHOES3);

        /* ------------------------ Perform add operation while a apparel card is selected -------------------------- */

        /* Case: selects first card in the apparel list, add a apparel -> added, card selection remains unchanged */
        selectPerson(Index.fromOneBased(1));
        assertCommandSuccess(SHIRT2);

        /* ----------------------------------- Perform invalid add operations --------------------------------------- */

        /* Case: add a duplicate apparel -> rejected */
        command = ApparelUtil.getAddCommand(SHOES2);
        assertCommandFailure(command, AddCommand.MESSAGE_DUPLICATE_APPAREL);

        /**//* Case: add a duplicate apparel except with different color -> rejected *//* this is invalid now
        toAdd = new ApparelBuilder(SHOES2).withColor(VALID_COLOR_BLUE).build();
        command = ApparelUtil.getAddCommand(toAdd);
        assertCommandFailure(command, AddCommand.MESSAGE_DUPLICATE_APPAREL);

        *//* Case: add a duplicate apparel except with different clothingType -> rejected *//* also invalid
        toAdd = new ApparelBuilder(SHOES2).withClothingType(VALID_TYPE_BOTTOM).build();
        command = ApparelUtil.getAddCommand(toAdd);
        assertCommandFailure(command, AddCommand.MESSAGE_DUPLICATE_APPAREL);

        *//* Case: add a duplicate apparel except with different address -> rejected *//* address not valid anymore
        toAdd = new ApparelBuilder(SHOES2).build();
        command = ApparelUtil.getAddCommand(toAdd);
        assertCommandFailure(command, AddCommand.MESSAGE_DUPLICATE_APPAREL);

        *//* Case: add a duplicate apparel except with different tags -> rejected *//* no more tags
        command = ApparelUtil.getAddCommand(SHOES2);
        assertCommandFailure(command, AddCommand.MESSAGE_DUPLICATE_APPAREL);*/

        /* Case: missing name -> rejected */
        command = AddCommand.COMMAND_WORD + INPUT_COLOR_GREEN + INPUT_TYPE_TOP;
        assertCommandFailure(command, String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));

        /* Case: missing color -> rejected */
        command = AddCommand.COMMAND_WORD + INPUT_NAME_A + INPUT_TYPE_TOP;
        assertCommandFailure(command, String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));

        /* Case: missing clothingType -> rejected */
        command = AddCommand.COMMAND_WORD + INPUT_NAME_A + INPUT_COLOR_GREEN;
        assertCommandFailure(command, String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));

        /**//* Case: missing address -> rejected *//* no more address
        command = AddCommand.COMMAND_WORD + INPUT_NAME_A + INPUT_COLOR_GREEN + INPUT_TYPE_TOP;
        assertCommandFailure(command, String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));*/

        /* Case: invalid keyword -> rejected */
        command = "adds " + ApparelUtil.getApparelDetails(toAdd);
        assertCommandFailure(command, Messages.MESSAGE_UNKNOWN_COMMAND);

        /* Case: invalid name -> rejected */
        command = AddCommand.COMMAND_WORD + INVALID_INPUT_NAME + INPUT_COLOR_GREEN + INPUT_TYPE_TOP;
        assertCommandFailure(command, Name.MESSAGE_CONSTRAINTS);

        /* Case: invalid color -> rejected */
        command = AddCommand.COMMAND_WORD + INPUT_NAME_A + INVALID_INPUT_COLOR + INPUT_TYPE_TOP;
        assertCommandFailure(command, Color.MESSAGE_CONSTRAINTS);

        /* Case: invalid clothingType -> rejected */
        command = AddCommand.COMMAND_WORD + INPUT_NAME_A + INPUT_COLOR_GREEN + INVALID_INPUT_TYPE;
        assertCommandFailure(command, ClothingType.MESSAGE_CONSTRAINTS);

        /**//* Case: invalid address -> rejected *//*
        command = AddCommand.COMMAND_WORD + INPUT_NAME_A + INPUT_COLOR_GREEN + INPUT_TYPE_TOP;
        assertCommandFailure(command, Address.MESSAGE_CONSTRAINTS);

        *//* Case: invalid tag -> rejected *//*
        command = AddCommand.COMMAND_WORD + INPUT_NAME_A + INPUT_COLOR_GREEN + INPUT_TYPE_TOP;
        assertCommandFailure(command, Tag.MESSAGE_CONSTRAINTS);*/
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
    private void assertCommandSuccess(Apparel toAdd) {
        assertCommandSuccess(ApparelUtil.getAddCommand(toAdd), toAdd);
    }

    /**
     * Performs the same verification as {@code assertCommandSuccess(Apparel)}. Executes {@code command}
     * instead.
     * @see AddCommandSystemTest#assertCommandSuccess(Apparel)
     */
    private void assertCommandSuccess(String command, Apparel toAdd) {
        Model expectedModel = getModel();
        expectedModel.addApparel(toAdd);
        String expectedResultMessage = String.format(AddCommand.MESSAGE_SUCCESS, toAdd);

        assertCommandSuccess(command, expectedModel, expectedResultMessage);
    }

    /**
     * Performs the same verification as {@code assertCommandSuccess(String, Apparel)} except asserts that
     * the,<br>
     * 1. Result display box displays {@code expectedResultMessage}.<br>
     * 2. {@code Storage} and {@code PersonListPanel} equal to the corresponding components in
     * {@code expectedModel}.<br>
     * @see AddCommandSystemTest#assertCommandSuccess(String, Apparel)
     */
    private void assertCommandSuccess(String command, Model expectedModel, String expectedResultMessage) {
        executeCommand(command);
        assertApplicationDisplaysExpected("", expectedResultMessage, expectedModel);
        getBrowserPanel().rememberState();
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
