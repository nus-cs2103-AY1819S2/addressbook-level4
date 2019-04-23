package systemtests;

import static org.junit.Assert.assertTrue;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_REQUEST_DISPLAYED_INDEX;
import static seedu.address.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;
import static seedu.address.testutil.TestUtil.getLastIndex;
import static seedu.address.testutil.TestUtil.getMidIndex;
import static seedu.address.testutil.TestUtil.getRequest;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST;
import static seedu.address.testutil.TypicalRequests.KEYWORD_MATCHING_MEIER;

import org.junit.Test;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.UndoCommand;
import seedu.address.logic.commands.request.DeleteRequestCommand;
import seedu.address.logic.parser.ParserUtil;
import seedu.address.model.Model;
import seedu.address.model.request.Request;
import seedu.address.model.request.RequestNameContainsKeywordPredicate;

//import seedu.address.logic.commands.RedoCommand;

/**
 * Deprecated system test for DeleteCommand in AB4.
 */
public class DeleteRequestCommandSystemTest extends HealthHubSystemTest {

    private static final String MESSAGE_INVALID_DELETE_COMMAND_FORMAT =
            String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT, DeleteRequestCommand.MESSAGE_USAGE);

    @Test
    public void delete() {
        /* --------- Performing delete operation while an unfiltered list is being shown ------------ */

        /* Case: delete the first request in the list, command with leading spaces and trailing spaces
         * -- > deleted
         */
        Model expectedModel = getModel();
        String command = "     " + DeleteRequestCommand.COMMAND_WORD + " " + DeleteRequestCommand.COMMAND_OPTION
                + "       " + INDEX_FIRST.getOneBased() + "       ";
        Request deletedRequest = removeRequest(expectedModel, INDEX_FIRST);
        String expectedResultMessage = String.format(DeleteRequestCommand.MESSAGE_DELETE_REQUEST_SUCCESS,
                deletedRequest);
        assertCommandSuccess(command, expectedModel, expectedResultMessage);

        /* Case: delete the last request in the list --> deleted */
        Model modelBeforeDeletingLast = expectedModel;
        Index lastPersonIndex = getLastIndex(expectedModel);
        command = "     " + DeleteRequestCommand.COMMAND_WORD + " " + DeleteRequestCommand.COMMAND_OPTION
                + "       " + lastPersonIndex.getOneBased() + "       ";
        deletedRequest = removeRequest(expectedModel, lastPersonIndex);
        expectedResultMessage = String.format(DeleteRequestCommand.MESSAGE_DELETE_REQUEST_SUCCESS,
                deletedRequest);
        assertCommandSuccess(command, expectedModel, expectedResultMessage);

        /* Case: undo deleting the last request in the list --> last request restored */
        command = UndoCommand.COMMAND_WORD;
        expectedResultMessage = UndoCommand.MESSAGE_SUCCESS;
        assertCommandSuccess(command, modelBeforeDeletingLast, expectedResultMessage);

        /* Case: redo deleting the last request in the list -> last request deleted again */
        //command = RedoCommand.COMMAND_WORD;
        //removeRequest(modelBeforeDeletingLast, lastPersonIndex);
        //expectedResultMessage = RedoCommand.MESSAGE_SUCCESS;
        //assertCommandSuccess(command, modelBeforeDeletingLast, expectedResultMessage);

        /* Case: delete the middle person in the list -> deleted */
        Index middlePersonIndex = getMidIndex(getModel());
        command = "     " + DeleteRequestCommand.COMMAND_WORD + " " + DeleteRequestCommand.COMMAND_OPTION
                + "       " + middlePersonIndex.getOneBased() + "       ";
        deletedRequest = removeRequest(expectedModel, middlePersonIndex);
        expectedResultMessage = String.format(DeleteRequestCommand.MESSAGE_DELETE_REQUEST_SUCCESS,
                deletedRequest);
        assertCommandSuccess(command, expectedModel, expectedResultMessage);

        /* --------- Performing delete operation while a filtered list is being shown ----------- */

        /* Case: filtered request list, delete index within bounds of request book and request list --> deleted */
        showPatientsWithName(KEYWORD_MATCHING_MEIER);
        Index index = INDEX_FIRST;
        assertTrue(index.getZeroBased() < getModel().getFilteredRequestList().size());
        command = "     " + DeleteRequestCommand.COMMAND_WORD + " " + DeleteRequestCommand.COMMAND_OPTION
                + "       " + index.getOneBased() + "       ";
        expectedModel.updateFilteredRequestList(new RequestNameContainsKeywordPredicate(KEYWORD_MATCHING_MEIER));
        deletedRequest = removeRequest(expectedModel, index);
        expectedResultMessage = String.format(DeleteRequestCommand.MESSAGE_DELETE_REQUEST_SUCCESS,
                deletedRequest);
        assertCommandSuccess(command, expectedModel, expectedResultMessage);

        /* Case: filtered request list, delete index within bounds of request book but out of bounds of request list
         * --> rejected
         */
        showPatientsWithName(KEYWORD_MATCHING_MEIER);
        int invalidIndex = getModel().getRequestBook().getRequestList().size();
        command = DeleteRequestCommand.COMMAND_WORD + " " + DeleteRequestCommand.COMMAND_OPTION + " " + invalidIndex;
        assertCommandFailure(command, MESSAGE_INVALID_REQUEST_DISPLAYED_INDEX, expectedModel);

        /* ------------ Performing delete operation while a person card is selected ------------- */


        /* Case: delete the selected request -> request list panel selects the request before the deleted request */
        showAllRequests();
        expectedModel.updateFilteredRequestList(Model.PREDICATE_SHOW_ALL_REQUESTS);
        Index selectedIndex = getLastIndex(expectedModel);
        Index expectedIndex = Index.fromZeroBased(selectedIndex.getZeroBased() - 1);
        selectRequest(selectedIndex);
        command = DeleteRequestCommand.COMMAND_WORD + " " + DeleteRequestCommand.COMMAND_OPTION + " "
                + selectedIndex.getOneBased();
        deletedRequest = removeRequest(expectedModel, selectedIndex);
        expectedResultMessage = String.format(DeleteRequestCommand.MESSAGE_DELETE_REQUEST_SUCCESS, deletedRequest);
        assertCommandSuccess(command, expectedModel, expectedResultMessage, expectedIndex);

        /* ----------------------- Performing invalid delete operation ---------------------- */


        /* Case: invalid index (0) --> rejected */
        command = DeleteRequestCommand.COMMAND_WORD + " " + DeleteRequestCommand.COMMAND_OPTION + " 0";
        assertCommandFailure(command, ParserUtil.MESSAGE_INVALID_INDEX, expectedModel);

        /* Case: invalid index (-1) --> rejected */
        command = DeleteRequestCommand.COMMAND_WORD + " " + DeleteRequestCommand.COMMAND_OPTION + " -1";
        assertCommandFailure(command, ParserUtil.MESSAGE_INVALID_INDEX, expectedModel);

        /* Case: invalid index (size + 1) --> rejected */
        Index outOfBoundsIndex = Index.fromOneBased(
                getModel().getRequestBook().getRequestList().size() + 1);
        command = DeleteRequestCommand.COMMAND_WORD + " " + DeleteRequestCommand.COMMAND_OPTION + " "
                + outOfBoundsIndex.getOneBased();
        assertCommandFailure(command, MESSAGE_INVALID_REQUEST_DISPLAYED_INDEX, expectedModel);

        /* Case: invalid arguments (alphabets) --> rejected */
        assertCommandFailure(DeleteRequestCommand.COMMAND_WORD + " " + DeleteRequestCommand.COMMAND_OPTION
                + " abc", ParserUtil.MESSAGE_INVALID_INDEX, expectedModel);

        /* Case: invalid arguments (extra argument) -> rejected */
        assertCommandFailure(DeleteRequestCommand.COMMAND_WORD + " " + DeleteRequestCommand.COMMAND_OPTION
                + " 1 abc", ParserUtil.MESSAGE_INVALID_INDEX, expectedModel);

        /* Case: mixed case command word --> rejected */
        assertCommandFailure("DelETE 1", MESSAGE_UNKNOWN_COMMAND, expectedModel);
    }

    /**
     * Removes the {@code Request} at the specified {@code index} in {@code model}'s request book.
     * @return the removed request
     */
    private Request removeRequest(Model model, Index index) {
        Request targetRequest = getRequest(model, index);
        model.deleteRequest(targetRequest);
        return targetRequest;
    }

    /**
     * Deletes the person at {@code toDelete} by creating a default {@code DeletePersonCommand} using {@code toDelete}
     * and performs the same verification as {@code assertCommandSuccess(String, Model, String)}.
     * @see DeleteRequestCommandSystemTest#assertCommandSuccess(String, Model, String)
     */
    private void assertCommandSuccess(Index toDelete) {
        Model expectedModel = getModel();
        Request deletedRequest = removeRequest(expectedModel, toDelete);
        String expectedResultMessage = String.format(
                DeleteRequestCommand.MESSAGE_DELETE_REQUEST_SUCCESS, deletedRequest);

        assertCommandSuccess(DeleteRequestCommand.COMMAND_WORD + " " + DeleteRequestCommand.COMMAND_OPTION
                + " " + toDelete.getOneBased(), expectedModel, expectedResultMessage);
    }

    /**
     * Executes {@code command} and in addition,<br>
     * 1. Asserts that the command box displays an empty string.<br>
     * 2. Asserts that the result display box displays {@code expectedResultMessage}.<br>
     * 3. Asserts that the browser url and selected card remains unchanged.<br>
     * 4. Asserts that the status bar's sync status changes.<br>
     * 5. Asserts that the command box has the default style class.<br>
     * Verifications 1 and 2 are performed by
     * {@code HealthHubSystemTest#assertApplicationDisplaysExpected(String, String, Model)}.
     * @see HealthHubSystemTest#assertApplicationDisplaysExpected(String, String, Model)
     */
    private void assertCommandSuccess(String command, Model expectedModel, String expectedResultMessage) {
        assertCommandSuccess(command, expectedModel, expectedResultMessage, null);
    }

    /**
     * Performs the same verification as {@code assertCommandSuccess(String, Model, String)} except that the
     browser url
     * and selected card are expected to update accordingly depending on the card at
     {@code expectedSelectedCardIndex}.
     * @see DeleteRequestCommandSystemTest#assertCommandSuccess(String, Model, String)
     * @see HealthHubSystemTest#assertSelectedCardChanged(Index)
     */
    private void assertCommandSuccess(String command, Model expectedModel, String expectedResultMessage,
            Index expectedSelectedCardIndex) {
        executeCommand(command);
        assertApplicationDisplaysExpected("", expectedResultMessage, expectedModel);

        if (expectedSelectedCardIndex != null) {
            assertSelectedCardChanged(expectedSelectedCardIndex);
        } else {
            assertSelectedCardUnchanged();
        }

        assertCommandBoxShowsDefaultStyle();
        //assertStatusBarUnchangedExceptSyncStatus();
    }

    /**
     * Executes {@code command} and in addition,<br>
     * 1. Asserts that the command box displays {@code command}.<br>
     * 2. Asserts that result display box displays {@code expectedResultMessage}.<br>
     * 3. Asserts that the browser url, selected card and status bar remain unchanged.<br>
     * 4. Asserts that the command box has the error style.<br>
     * Verifications 1 and 2 are performed by
     * {@code HealthHubSystemTest#assertApplicationDisplaysExpected(String, String, Model)}.<br>
     * @see HealthHubSystemTest#assertApplicationDisplaysExpected(String, String, Model)
     */
    private void assertCommandFailure(String command, String expectedResultMessage, Model expectedModel) {
        executeCommand(command);
        assertApplicationDisplaysExpected(command, expectedResultMessage, expectedModel);
        assertSelectedCardUnchanged();
        assertCommandBoxShowsErrorStyle();
        assertStatusBarUnchanged();
    }
}
