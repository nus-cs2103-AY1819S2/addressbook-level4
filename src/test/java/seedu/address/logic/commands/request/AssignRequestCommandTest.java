package seedu.address.logic.commands.request;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.request.AssignRequestCommand.MESSAGE_SUCCESS;
import static seedu.address.testutil.TypicalHealthWorkers.getTypicalHealthWorkerBook;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND;
import static seedu.address.testutil.TypicalRequests.getTypicalRequestBook;

import java.util.HashSet;
import java.util.Set;

import org.junit.Rule;
import org.junit.jupiter.api.Test;
import org.junit.rules.ExpectedException;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.healthworker.HealthWorker;
import seedu.address.model.request.Request;
import seedu.address.testutil.Assert;

class AssignRequestCommandTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private Model model = new ModelManager(getTypicalHealthWorkerBook(),
        getTypicalRequestBook(), new UserPrefs());
    private Request validRequest = model.getFilteredRequestList().get(INDEX_FIRST.getZeroBased());
    private Index validRequestIndex = Index.fromOneBased(model.getFilteredRequestList().size());
    private Index validHealthWorkerIndex =
        Index.fromOneBased(model.getFilteredHealthWorkerList().size());
    private Index outOfBoundRequestIndex =
        Index.fromOneBased(model.getFilteredRequestList().size() + 1);
    private Index outOfBoundHealthWorkerIndex =
        Index.fromOneBased(model.getFilteredHealthWorkerList().size() + 1);
    private CommandHistory commandHistory = new CommandHistory();

    @Test
    public void constructor_nullHealthworker_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        Set<Index> validRequestIds = new HashSet<>();
        validRequestIds.add(validRequestIndex);
        Assert.assertThrows(NullPointerException.class, () -> new AssignRequestCommand(
            null, validRequestIds));
    }

    @Test
    public void constructor_nullRequestIds_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new AssignRequestCommand(
            validHealthWorkerIndex, null));
    }

    @Test
    public void execute_requestAssignedToHealthworker_successful() {
        Set<Request> requestsToAdd = new HashSet<>();
        Request toAssign = new Request(validRequest);
        requestsToAdd.add(toAssign);
        HealthWorker healthWorkerToAssign =
            model.getFilteredHealthWorkerList().get(validHealthWorkerIndex.getZeroBased());
        HealthWorker assignedHealthWorker = new HealthWorker(healthWorkerToAssign);
        toAssign.setHealthWorker(assignedHealthWorker);

        Set<Index> requestIds = new HashSet<>();
        requestIds.add(INDEX_FIRST);
        AssignRequestCommand assignRequestCommand =
            new AssignRequestCommand(validHealthWorkerIndex, requestIds);
        String expectedMessage = String.format(MESSAGE_SUCCESS, INDEX_FIRST.getOneBased(),
            assignedHealthWorker);

        Model expectedModel = new ModelManager(model.getHealthWorkerBook(), model.getRequestBook(), new UserPrefs());
        // todo needs model.updateHealthworker()
        expectedModel.updateRequest(validRequest, toAssign);
        expectedModel.commitRequestBook();

        assertEquals(toAssign.getHealthStaff(), assignedHealthWorker.getName().toString());
        assertCommandSuccess(assignRequestCommand, model, commandHistory, expectedMessage,
            expectedModel);
    }

    @Test
    public void execute_orderIndexTooLarge_throwsCommandException() {
        Set<Index> requestIds = new HashSet<>();
        requestIds.add(outOfBoundRequestIndex);
        AssignRequestCommand assignRequestCommand =
            new AssignRequestCommand(validHealthWorkerIndex, requestIds);

        assertThrows(CommandException.class, () -> assignRequestCommand.execute(model,
            commandHistory), Messages.MESSAGE_INVALID_REQUEST_DISPLAYED_INDEX);
    }

    @Test
    public void execute_healthWorkerIndexTooLarge_throwsCommandException() {
        Set<Index> requestIds = new HashSet<>();
        requestIds.add(validRequestIndex);
        AssignRequestCommand assignRequestCommand =
            new AssignRequestCommand(outOfBoundHealthWorkerIndex, requestIds);
        assertThrows(CommandException.class, () -> assignRequestCommand.execute(model, commandHistory),
            Messages.MESSAGE_INVALID_HEALTHWORKER_DISPLAYED_INDEX);
    }

    @Test
    public void equals() {
        Set<Index> indexOne = new HashSet<>();
        indexOne.add(INDEX_FIRST);
        Set<Index> indexTwo = new HashSet<>();
        indexTwo.add(INDEX_SECOND);
        AssignRequestCommand firstCommand =
            new AssignRequestCommand(validHealthWorkerIndex, indexOne);
        AssignRequestCommand secondCommand = new AssignRequestCommand(validHealthWorkerIndex,
            indexTwo);

        // same object -> returns equal
        assertEquals(firstCommand, firstCommand);

        // same values -> returns equal
        AssignRequestCommand firstCommandCopy = new AssignRequestCommand(validHealthWorkerIndex,
            indexOne);
        assertEquals(firstCommand, firstCommandCopy);

        // different types -> returns not equal
        assertNotEquals(firstCommand, 1);

        // null -> returns not equal
        assertNotEquals(firstCommand, null);

        // different values -> not equal
        assertNotEquals(firstCommand, secondCommand);
    }


}
