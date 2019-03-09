package systemtests;

import static org.junit.Assert.assertFalse;
import static seedu.address.commons.core.Messages.MESSAGE_PERSONS_LISTED_OVERVIEW;
import static seedu.address.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;
import static seedu.address.testutil.TypicalPlaces.BENSON;
import static seedu.address.testutil.TypicalPlaces.CARL;
import static seedu.address.testutil.TypicalPlaces.ELLE;
import static seedu.address.testutil.TypicalPlaces.FIONA;
import static seedu.address.testutil.TypicalPlaces.KEYWORD_MATCHING_SINGAPORE;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.DeleteCommand;
import seedu.address.logic.commands.RedoCommand;
import seedu.address.logic.commands.SearchCommand;
import seedu.address.logic.commands.UndoCommand;
import seedu.address.model.Model;
import seedu.address.model.tag.Tag;

public class SearchCommandSystemTest extends AddressBookSystemTest {

    @Test
    public void find() {
        /* Case: find multiple places in address book, command with leading spaces and trailing spaces
         * -> 4 places found
         */
        String command = "   " + SearchCommand.COMMAND_WORD + " " + KEYWORD_MATCHING_SINGAPORE + "   ";
        Model expectedModel = getModel();
        ModelHelper.setFilteredList(expectedModel, BENSON, CARL, ELLE, FIONA);
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: repeat previous find command where place list is displaying the places we are finding
         * -> 2 places found
         */
        command = SearchCommand.COMMAND_WORD + " " + KEYWORD_MATCHING_SINGAPORE;
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: find place where place list is not displaying the place we are finding -> 1 place found */
        command = SearchCommand.COMMAND_WORD + " University";
        ModelHelper.setFilteredList(expectedModel, ELLE);
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: find multiple places in address book, 2 keywords -> 2 places found */
        command = SearchCommand.COMMAND_WORD + " University Zoo";
        ModelHelper.setFilteredList(expectedModel, ELLE, FIONA);
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: find multiple places in address book, 2 keywords in reversed order -> 2 places found */
        command = SearchCommand.COMMAND_WORD + " Zoo University";
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: find multiple places in address book, 2 keywords with 1 repeat -> 2 places found */
        command = SearchCommand.COMMAND_WORD + " Zoo University Zoo";
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: find multiple places in address book, 2 matching keywords and 1 non-matching keyword
         * -> 2 places found
         */
        command = SearchCommand.COMMAND_WORD + " Zoo University NonMatchingKeyWord";
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

        /* Case: find same places in address book after deleting 1 of them -> 3 places found */
        executeCommand(DeleteCommand.COMMAND_WORD + " 1");
        assertFalse(getModel().getAddressBook().getPlaceList().contains(ELLE));
        command = SearchCommand.COMMAND_WORD + " " + KEYWORD_MATCHING_SINGAPORE;
        expectedModel = getModel();
        ModelHelper.setFilteredList(expectedModel, BENSON, CARL, FIONA);
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: find place in address book, keyword is same as name but of different case -> 3 places found */
        command = SearchCommand.COMMAND_WORD + " SiNgApOrE";
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: find place in address book, keyword is substring of name -> 0 places found */
        command = SearchCommand.COMMAND_WORD + " Sin";
        ModelHelper.setFilteredList(expectedModel);
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: find place in address book, name is substring of keyword -> 0 places found */
        command = SearchCommand.COMMAND_WORD + " Singapores";
        ModelHelper.setFilteredList(expectedModel);
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: find place not in address book -> 0 places found */
        command = SearchCommand.COMMAND_WORD + " Mosque";
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: find rating of place in address book -> 0 places found */
        command = SearchCommand.COMMAND_WORD + " " + CARL.getRating().value;
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: find address of place in address book -> 0 places found */
        command = SearchCommand.COMMAND_WORD + " " + CARL.getAddress().value;
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: find description of place in address book -> 0 places found */
        command = SearchCommand.COMMAND_WORD + " " + CARL.getDescription().value;
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: find tags of place in address book -> 0 places found */
        List<Tag> tags = new ArrayList<>(CARL.getTags());
        command = SearchCommand.COMMAND_WORD + " " + tags.get(0).tagName;
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: find while a place is selected -> selected card deselected */
        showAllPlaces();
        selectPlace(Index.fromOneBased(1));
        assertFalse(getPlaceListPanel().getHandleToSelectedCard().getName().equals(CARL.getName().fullName));
        command = SearchCommand.COMMAND_WORD + " Universal";
        ModelHelper.setFilteredList(expectedModel, CARL);
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardDeselected();

        /* Case: find place in empty address book -> 0 places found */
        deleteAllPlaces();
        command = SearchCommand.COMMAND_WORD + " " + KEYWORD_MATCHING_SINGAPORE;
        expectedModel = getModel();
        ModelHelper.setFilteredList(expectedModel, CARL);
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: mixed case command word -> rejected */
        command = "sEaRcH Temple";
        assertCommandFailure(command, MESSAGE_UNKNOWN_COMMAND);
    }

    /**
     * Executes {@code command} and verifies that the command box displays an empty string, the result display
     * box displays {@code Messages#MESSAGE_PERSONS_LISTED_OVERVIEW} with the number of people in the filtered list,
     * and the model related components equal to {@code expectedModel}.
     * These verifications are done by
     * {@code AddressBookSystemTest#assertApplicationDisplaysExpected(String, String, Model)}.<br>
     * Also verifies that the status bar remains unchanged, and the command box has the default style class, and the
     * selected card updated accordingly, depending on {@code cardStatus}.
     * @see AddressBookSystemTest#assertApplicationDisplaysExpected(String, String, Model)
     */
    private void assertCommandSuccess(String command, Model expectedModel) {
        String expectedResultMessage = String.format(
                MESSAGE_PERSONS_LISTED_OVERVIEW, expectedModel.getFilteredPlaceList().size());

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
