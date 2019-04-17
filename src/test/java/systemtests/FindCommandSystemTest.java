package systemtests;

import static org.junit.Assert.assertFalse;
import static seedu.address.commons.core.Messages.MESSAGE_APPARELS_LISTED_OVERVIEW;
import static seedu.address.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;
import static seedu.address.testutil.TypicalApparels.BELT1;
import static seedu.address.testutil.TypicalApparels.BELT2;
import static seedu.address.testutil.TypicalApparels.BELT3;
import static seedu.address.testutil.TypicalApparels.KEYWORD_MATCHING_BELT;
import static seedu.address.testutil.TypicalApparels.PANTS1;

import org.junit.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.DeleteCommand;
import seedu.address.logic.commands.FindCommand;
import seedu.address.logic.commands.RedoCommand;
import seedu.address.logic.commands.UndoCommand;
import seedu.address.model.Model;

public class FindCommandSystemTest extends AddressBookSystemTest {

    @Test
    public void find() {
        /* Case: find multiple apparels in address book, command with leading spaces and trailing spaces
         * -> 3 apparels found
         */
        String command = "   " + FindCommand.COMMAND_WORD + " " + KEYWORD_MATCHING_BELT + "   ";
        Model expectedModel = getModel();
        ModelHelper.setFilteredList(expectedModel, BELT1, BELT2, BELT3); //
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: repeat previous find command where apparel list is displaying the apparels we are finding
         * -> 3 apparels found
         */
        command = FindCommand.COMMAND_WORD + " " + KEYWORD_MATCHING_BELT;
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: find apparel where apparel list is not displaying the apparel we are finding -> 0 apparel found */
        command = FindCommand.COMMAND_WORD + " Necklace";
        ModelHelper.setFilteredList(expectedModel);
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: find multiple apparels in address book, 2 keywords -> 4 apparels found */
        command = FindCommand.COMMAND_WORD + " Pants Belt";
        ModelHelper.setFilteredList(expectedModel, PANTS1, BELT1, BELT2, BELT3);
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: find multiple persons in address book, 2 keywords in reversed order -> 4 apparels found */
        command = FindCommand.COMMAND_WORD + " Belt Pants";
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: find multiple persons in address book, 2 keywords with 1 repeat -> 4 apparels found */
        command = FindCommand.COMMAND_WORD + " Pants Belt Pants";
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: find multiple persons in address book, 2 matching keywords and 1 non-matching keyword
         * -> 4 apparels found
         */
        command = FindCommand.COMMAND_WORD + " Pants Belt NonMatchingKeyWord";
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: undo previous find command -> rejected */
        command = UndoCommand.COMMAND_WORD;
        String expectedResultMessage = UndoCommand.MESSAGE_FAILURE;
        assertCommandFailure(command, expectedResultMessage);

        /* Case: redo previous find command -> rejected */
        command = RedoCommand.COMMAND_WORD;
        expectedResultMessage = RedoCommand.MESSAGE_FAILURE;
        assertCommandFailure(command, expectedResultMessage);

        /* Case: find same apparels in address book after deleting 1 of them -> 3 apparel found */
        executeCommand(DeleteCommand.COMMAND_WORD + " 1");
        assertFalse(getModel().getAddressBook().getApparelList().contains(PANTS1));
        command = FindCommand.COMMAND_WORD + " " + KEYWORD_MATCHING_BELT;
        expectedModel = getModel();
        ModelHelper.setFilteredList(expectedModel, BELT1, BELT2, BELT3);
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: find apparel in address book, keyword is same as name but of different case -> 3 apparel found */
        command = FindCommand.COMMAND_WORD + " BeLt";
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: find apparel in address book, keyword is substring of name -> 0 apparels found */
        command = FindCommand.COMMAND_WORD + " Bel";
        ModelHelper.setFilteredList(expectedModel);
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: find apparel in address book, name is substring of keyword -> 0 apparels found */
        command = FindCommand.COMMAND_WORD + " Belts";
        ModelHelper.setFilteredList(expectedModel);
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: find apparel not in address book -> 0 apparels found */
        command = FindCommand.COMMAND_WORD + " Necklace";
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: find color number of apparel in address book -> 0 apparels found */
        command = FindCommand.COMMAND_WORD + " " + BELT1.getColor();
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /**//* Case: find clothingType of apparel in address book -> 0 apparels found *//*
        Belt's clothingType is 'Belt', so not valid here
        command = FindCommand.COMMAND_WORD + " " + SHIRT2.getClothingType();
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();*/

        /* Case: find while a apparel is selected -> selected card deselected */
        showAllPersons();
        selectPerson(Index.fromOneBased(1));
        assertFalse(getPersonListPanel().getHandleToSelectedCard().getName().equals(BELT1.getName().fullName));
        command = FindCommand.COMMAND_WORD + " Belt";
        ModelHelper.setFilteredList(expectedModel, BELT1, BELT2, BELT3);
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardDeselected();

        /* Case: find apparel in empty address book -> 0 apparels found */
        deleteAllPersons();
        command = FindCommand.COMMAND_WORD + " " + KEYWORD_MATCHING_BELT;
        expectedModel = getModel();
        ModelHelper.setFilteredList(expectedModel, BELT1, BELT2, BELT3);
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: mixed case command word -> rejected */
        command = "FiNd Belt";
        assertCommandFailure(command, MESSAGE_UNKNOWN_COMMAND);
    }

    /**
     * Executes {@code command} and verifies that the command box displays an empty string, the result display
     * box displays {@code Messages#MESSAGE_APPARELS_LISTED_OVERVIEW} with the number of people in the filtered list,
     * and the model related components equal to {@code expectedModel}.
     * These verifications are done by
     * {@code AddressBookSystemTest#assertApplicationDisplaysExpected(String, String, Model)}.<br>
     * Also verifies that the status bar remains unchanged, and the command box has the default style class, and the
     * selected card updated accordingly, depending on {@code cardStatus}.
     * @see AddressBookSystemTest#assertApplicationDisplaysExpected(String, String, Model)
     */
    private void assertCommandSuccess(String command, Model expectedModel) {
        String expectedResultMessage = String.format(
                MESSAGE_APPARELS_LISTED_OVERVIEW, expectedModel.getFilteredApparelList().size());

        executeCommand(command);
        assertApplicationDisplaysExpected("", expectedResultMessage, expectedModel);
        assertCommandBoxShowsDefaultStyle();
        assertStatusBarUnchanged();
    }

    /**
     * Executes {@code command} and verifies that the command box displays {@code command}, the result display
     * box displays {@code expectedResultMessage} and the model related components equal to the current model.
     * These verifications are done by
     * {@code AddressBookSystemTest#assertApplicationDisplaysExpected(String, String, Model)}.<br>
     * Also verifies that the browser url, selected card and status bar remain unchanged, and the command box has the
     * error style.
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
