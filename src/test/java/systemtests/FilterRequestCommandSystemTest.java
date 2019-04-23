package systemtests;

import static org.junit.Assert.assertFalse;
import static seedu.address.commons.core.Messages.MESSAGE_REQUESTS_LISTED_OVERVIEW;
import static seedu.address.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;
import static seedu.address.model.request.RequestStatus.MESSAGE_STATUS_CONSTRAINTS;
import static seedu.address.testutil.TypicalRequests.ALICE_REQUEST;
import static seedu.address.testutil.TypicalRequests.BENSON_REQUEST;
import static seedu.address.testutil.TypicalRequests.CARL_REQUEST;
import static seedu.address.testutil.TypicalRequests.DANIEL_REQUEST;
import static seedu.address.testutil.TypicalRequests.EMMANUEL_REQUEST;
import static seedu.address.testutil.TypicalRequests.FRANCIS_REQUEST;
import static seedu.address.testutil.TypicalRequests.GLADYS_REQUEST;
import static seedu.address.testutil.TypicalRequests.HEPZHI_REQUEST;
import static seedu.address.testutil.TypicalRequests.INDIANA_REQUEST;
import static seedu.address.testutil.TypicalRequests.KEYWORD_MATCHING_MEIER;
import static seedu.address.testutil.TypicalRequests.NEA_REQUEST;

import org.junit.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.RedoCommand;
import seedu.address.logic.commands.UndoCommand;
import seedu.address.logic.commands.request.DeleteRequestCommand;
import seedu.address.logic.commands.request.FilterRequestCommand;
import seedu.address.model.Model;
import seedu.address.model.request.RequestDate;

/**
 *
 */
public class FilterRequestCommandSystemTest extends HealthHubSystemTest {

    @Test
    public void filter() {

        /* Case: find multiple patients in request book, command with leading spaces and trailing spaces
         * --> 7 out of 11 patients found
         */
        String command = "   " + FilterRequestCommand.COMMAND_WORD + " " + FilterRequestCommand.COMMAND_OPTION + " n/"
                + KEYWORD_MATCHING_MEIER + "   ";
        Model expectedModel = getModel();
        // first names of the below mentioned patients are "Meier"
        ModelHelper.setFilteredRequestList(expectedModel, BENSON_REQUEST, DANIEL_REQUEST, EMMANUEL_REQUEST,
                FRANCIS_REQUEST, GLADYS_REQUEST, HEPZHI_REQUEST, INDIANA_REQUEST);
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: repeat previous filter command where request list is displaying the patients we are finding
         * --> 7 out of 11 patients found
         */
        command = FilterRequestCommand.COMMAND_WORD + " " + FilterRequestCommand.COMMAND_OPTION + " n/"
                + KEYWORD_MATCHING_MEIER;
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: find a patient when request list is not displaying the patient we are searching for
         * --> 1 patient found
         */
        command = FilterRequestCommand.COMMAND_WORD + " " + FilterRequestCommand.COMMAND_OPTION + " n/Carl";
        ModelHelper.setFilteredRequestList(expectedModel, CARL_REQUEST);
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: find multiple patients in request book, with consecutive 2 keywords --> 0 patients found
        * Multiple searching of patients under different names is not implemented in Health Hub.
        */
        //command = FilterRequestCommand.COMMAND_WORD + " " + FilterRequestCommand.COMMAND_OPTION + " n/Benson Daniel";
        //ModelHelper.setFilteredRequestList(expectedModel, BENSON_REQUEST, DANIEL_REQUEST);
        //String expectedResultMessage = String.format(MESSAGE_REQUESTS_LISTED_OVERVIEW, 0);
        //assertCommandFailure(command, expectedResultMessage);

        /* Case: undo previous filter command --> rejected */
        command = UndoCommand.COMMAND_WORD;
        String expectedResultMessage = UndoCommand.MESSAGE_FAILURE;
        assertCommandFailure(command, expectedResultMessage);

        /* Case: redo previous filter command --> rejected */
        command = RedoCommand.COMMAND_WORD;
        expectedResultMessage = RedoCommand.MESSAGE_FAILURE;
        assertCommandFailure(command, expectedResultMessage);

        /* Case: find same requests in request book after deleting 1 of the request
         * --> 7 patient found
         */
        executeCommand(DeleteRequestCommand.COMMAND_WORD + " " + DeleteRequestCommand.COMMAND_OPTION + " 1");
        //assertFalse(getModel().getRequestBook().getRequestList().);
        command = FilterRequestCommand.COMMAND_WORD + " " + FilterRequestCommand.COMMAND_OPTION + " n/"
                + KEYWORD_MATCHING_MEIER;
        expectedModel = getModel();
        ModelHelper.setFilteredRequestList(expectedModel, BENSON_REQUEST, DANIEL_REQUEST, EMMANUEL_REQUEST,
                FRANCIS_REQUEST, GLADYS_REQUEST, HEPZHI_REQUEST, INDIANA_REQUEST);
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: find patient in request book, keyword is same as name but of different case --> 7 patients found */
        command = FilterRequestCommand.COMMAND_WORD + " " + FilterRequestCommand.COMMAND_OPTION + " n/MeIeR";
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: find patient in request book, keyword is substring of name --> 7 patients found */
        command = FilterRequestCommand.COMMAND_WORD + " " + FilterRequestCommand.COMMAND_OPTION + " n/Mei";
        ModelHelper.setFilteredRequestList(expectedModel, BENSON_REQUEST, DANIEL_REQUEST, EMMANUEL_REQUEST,
                FRANCIS_REQUEST, GLADYS_REQUEST, HEPZHI_REQUEST, INDIANA_REQUEST);
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: find patient not in request book --> 0 patients found */
        command = FilterRequestCommand.COMMAND_WORD + " " + FilterRequestCommand.COMMAND_OPTION + " n/Mark";
        ModelHelper.setFilteredRequestList(expectedModel);
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: find request by a phone number --> 1 patient found */
        command = FilterRequestCommand.COMMAND_WORD + " " + FilterRequestCommand.COMMAND_OPTION + " p/"
                + DANIEL_REQUEST.getPhone().value;
        ModelHelper.setFilteredRequestList(expectedModel, DANIEL_REQUEST);
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: find request by date --> 3  requests found */
        command = FilterRequestCommand.COMMAND_WORD + " " + FilterRequestCommand.COMMAND_OPTION
                + " dt/02-01-2019 18:00:00";
        ModelHelper.setFilteredRequestList(expectedModel, DANIEL_REQUEST, EMMANUEL_REQUEST, FRANCIS_REQUEST);
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: find request by date not in specified format --> rejected */
        command = FilterRequestCommand.COMMAND_WORD + " " + FilterRequestCommand.COMMAND_OPTION
                + " dt/01-25-2019 18:00:00";
        expectedResultMessage = RequestDate.MESSAGE_CONSTRAINTS;
        assertCommandFailure(command, expectedResultMessage);

        /* Case: find request by pending status --> 5 requests found */
        command = FilterRequestCommand.COMMAND_WORD + " " + FilterRequestCommand.COMMAND_OPTION + " st/PENDING";
        ModelHelper.setFilteredRequestList(expectedModel, EMMANUEL_REQUEST, FRANCIS_REQUEST, GLADYS_REQUEST,
                HEPZHI_REQUEST, INDIANA_REQUEST);
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: find request by pending status with trailing spaces --> 5 requests found */
        command = FilterRequestCommand.COMMAND_WORD + " " + FilterRequestCommand.COMMAND_OPTION + " st/PENDING        ";
        ModelHelper.setFilteredRequestList(expectedModel, EMMANUEL_REQUEST, FRANCIS_REQUEST, GLADYS_REQUEST,
                HEPZHI_REQUEST, INDIANA_REQUEST);
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: find request by pending status with additional keywords --> rejected */
        command = FilterRequestCommand.COMMAND_WORD + " " + FilterRequestCommand.COMMAND_OPTION + " st/PENDING123";
        expectedResultMessage = MESSAGE_STATUS_CONSTRAINTS;
        assertCommandFailure(command, expectedResultMessage);

        /* Case: find request by a patient's condition (physiotherapy) --> 2 requests found */
        command = FilterRequestCommand.COMMAND_WORD + " " + FilterRequestCommand.COMMAND_OPTION + " c/physiotherapy";
        ModelHelper.setFilteredRequestList(expectedModel, ALICE_REQUEST, NEA_REQUEST);
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: find while a request is selected --> selected card deselected */
        showAllRequests();
        selectRequest(Index.fromOneBased(2));
        assertFalse(getRequestListPanel().getHandleToSelectedCard().getName().equals(DANIEL_REQUEST.getName()));
        command = FilterRequestCommand.COMMAND_WORD + " " + FilterRequestCommand.COMMAND_OPTION + " n/Daniel";
        ModelHelper.setFilteredRequestList(expectedModel, DANIEL_REQUEST);
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardDeselected();

        /* Case: find person in empty address book -> 0 persons found */
        showAllRequests();
        deleteAllRequests();
        command = FilterRequestCommand.COMMAND_WORD + " " + FilterRequestCommand.COMMAND_OPTION + " n/"
                + KEYWORD_MATCHING_MEIER;
        expectedModel = getModel();
        ModelHelper.setFilteredRequestList(expectedModel);
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: mixed case command word --> rejected */
        command = "FiNd Meier";
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
     * @see HealthHubSystemTest#assertApplicationDisplaysExpected(String, String, Model)
     */
    private void assertCommandSuccess(String command, Model expectedModel) {
        String expectedResultMessage = String.format(
                MESSAGE_REQUESTS_LISTED_OVERVIEW, expectedModel.getFilteredRequestList().size());

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
     * @see HealthHubSystemTest#assertApplicationDisplaysExpected(String, String, Model)
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
