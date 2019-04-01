package seedu.address.logic.commands.request;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalHealthWorkers.getTypicalHealthWorkerBook;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST;
import static seedu.address.testutil.TypicalRequests.ALICE_REQUEST;
import static seedu.address.testutil.TypicalRequests.getTypicalRequestBook;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import org.junit.Test;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.request.Request;
import seedu.address.testutil.RequestBuilder;

class CompleteRequestCommandTest {

    private Model model = new ModelManager(getTypicalHealthWorkerBook(),
        getTypicalRequestBook(), new UserPrefs());
    private CommandHistory commandHistory = new CommandHistory();

    private Set<Index> requestSet = new HashSet<>(Arrays.asList(INDEX_FIRST));

    @org.junit.jupiter.api.Test
    public void execute_validIndexUnfilteredList_success() throws CommandException {
        AssignRequestCommand assignCommand = new AssignRequestCommand(INDEX_FIRST, requestSet);
        assignCommand.execute(model, commandHistory);

        Request expectedRequest = new RequestBuilder(ALICE_REQUEST).withStatus("COMPLETED").build();

        String expectedMessage = String.format(CompleteRequestCommand.MESSAGE_COMPLETED_REQUEST_SUCCESS,
            expectedRequest);
        CompleteRequestCommand doneCommand = new CompleteRequestCommand(INDEX_FIRST);
        assertCommandSuccess(doneCommand, model, commandHistory, expectedMessage, model);
    }

    @Test
    public void execute_invalidIndexUnfliteredList_failure() throws CommandException {
        AssignRequestCommand assignCommand = new AssignRequestCommand(INDEX_FIRST, requestSet);
        assignCommand.execute(model, commandHistory);

        Index last = Index.fromZeroBased(model.getFilteredRequestList().size());
        CompleteRequestCommand doneCommand = new CompleteRequestCommand(last);

        String expectedMessage = Messages.MESSAGE_INVALID_REQUEST_DISPLAYED_INDEX;
        assertCommandFailure(doneCommand, model, commandHistory, expectedMessage);
    }

    @Test
    public void execute_validIndexUnfliteredList_pendingFailure() {
        Index last = Index.fromZeroBased(2);
        CompleteRequestCommand doneCommand = new CompleteRequestCommand(last);

        String expectedMessage = CompleteRequestCommand.MESSAGE_ONGOING_REQUEST;
        assertCommandFailure(doneCommand, model, commandHistory, expectedMessage);
    }


}
