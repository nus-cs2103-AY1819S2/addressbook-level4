package seedu.address.logic.commands.request;

import static java.util.Objects.requireNonNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collections;
import java.util.function.Predicate;

import org.junit.Rule;
import org.junit.jupiter.api.Test;
import org.junit.rules.ExpectedException;

import javafx.beans.property.ReadOnlyProperty;
import javafx.collections.ObservableList;
import seedu.address.commons.core.GuiSettings;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.CommandType;
import seedu.address.model.Model;
import seedu.address.model.ReadOnlyHealthWorkerBook;
import seedu.address.model.ReadOnlyRequestBook;
import seedu.address.model.ReadOnlyUserPrefs;
import seedu.address.model.RequestBook;
import seedu.address.model.person.healthworker.HealthWorker;
import seedu.address.model.request.Request;
import seedu.address.testutil.Assert;
import seedu.address.testutil.RequestBuilder;
import seedu.address.testutil.TypicalRequests;

class AddRequestCommandTest {

    protected static final CommandHistory EMPTY_COMMAND_HISTORY = new CommandHistory();

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    protected CommandHistory commandHistory = new CommandHistory();

    @Test
    public void constructor_nullRequest_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> new AddRequestCommand(null));
    }

    @Test
    public void execute_requestAcceptedByModel_addSuccessful() throws Exception {
        ModelStubAcceptingRequestAdded modelStub = new ModelStubAcceptingRequestAdded();
        Request validRequest = new RequestBuilder().build();

        CommandResult commandResult = new AddRequestCommand(validRequest).execute(modelStub,
            commandHistory);

        assertEquals(String.format(AddRequestCommand.MESSAGE_SUCCESS, validRequest),
            commandResult.getFeedbackToUser());
        assertEquals(Collections.singletonList(validRequest), modelStub.requestsAdded);
        assertEquals(EMPTY_COMMAND_HISTORY, commandHistory);
    }

    @Test
    public void execute_duplicateRequest_throwsCommandException() {
        Request validRequest = new RequestBuilder().build();
        AddRequestCommand addRequestCommand = new AddRequestCommand(validRequest);
        ModelStub modelStub = new ModelStubWithRequest(validRequest);

        Assert.assertThrows(CommandException.class,
            AddRequestCommand.MESSAGE_DUPLICATE_REQUEST, () -> addRequestCommand.execute
                (modelStub, commandHistory));
    }

    @Test
    public void equals() {

        AddRequestCommand addAliceRequest = new AddRequestCommand(TypicalRequests.ALICE_REQUEST);
        AddRequestCommand addBensonRequest = new AddRequestCommand(TypicalRequests.BENSON_REQUEST);

        // same object -> returns true
        assertTrue(addAliceRequest.equals(addAliceRequest));

        // same values -> returns true
        AddRequestCommand addAliceRequestCopy =
            new AddRequestCommand(TypicalRequests.ALICE_REQUEST);
        assertTrue(addAliceRequest.equals(addAliceRequestCopy));

        // different types -> returns false
        assertFalse(addAliceRequest.equals(1));

        // null -> returns false
        assertFalse(addAliceRequest.equals(null));

        // differnt request -> returns false
        assertFalse(addAliceRequest.equals(addBensonRequest));
    }

    @Test
    void execute() {
    }

    protected class ModelStub implements Model {

        @Override
        public void setUserPrefs(ReadOnlyUserPrefs userPrefs) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ReadOnlyUserPrefs getUserPrefs() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public GuiSettings getGuiSettings() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setGuiSettings(GuiSettings guiSettings) {
            throw new AssertionError("This method should not be called.");
        }


        @Override
        public ReadOnlyHealthWorkerBook getHealthWorkerBook() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public Path getHealthWorkerBookFilePath() {
            throw new AssertionError("This method should not be called.");
        }


        @Override
        public boolean hasHealthWorker(HealthWorker healthWorker) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void deleteHealthWorker(HealthWorker target) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void addHealthWorker(HealthWorker healthWorker) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setHealthWorker(HealthWorker target, HealthWorker editedWorker) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setSelectedHealthWorker(HealthWorker healthWorker) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ReadOnlyProperty<HealthWorker> selectedHealthWorkerProperty() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ObservableList<HealthWorker> getFilteredHealthWorkerList() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void updateFilteredHealthWorkerList(Predicate<HealthWorker> predicate) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void commitHealthWorkerBook() {
            throw new AssertionError("This method should not be called.");
        }


        @Override
        public Path getRequestBookFilePath() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ReadOnlyRequestBook getRequestBook() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ObservableList<Request> getFilteredRequestList() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean hasRequest(Request request) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void updateRequest(Request target, Request editedRequest) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void deleteRequest(Request target) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void updateFilteredRequestList(Predicate<Request> predicate) {

        }

        @Override
        public void addRequest(Request request) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void resetData(ReadOnlyRequestBook newData) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setRequest(Request target, Request editedRequest) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setSelectedRequest(Request request) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ReadOnlyProperty<Request> selectedRequestProperty() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void updateRequestOnNameEdit(String oldNric, String newNric) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean isAssigned(String name) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void commitRequestBook() {
            throw new AssertionError("This method should not be called.");
        } @Override
        public boolean canUndo() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean canRedo() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void undo() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void redo() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void commit(CommandType commandType) {
            throw new AssertionError("This method should not be called.");

        }

    }

    private class ModelStubWithRequest extends ModelStub {
        private final Request request;

        ModelStubWithRequest(Request request) {
            requireNonNull(request);
            this.request = request;
        }

        @Override
        public boolean hasRequest(Request request) {
            requireNonNull(request);
            return this.request.isSameRequest(request);
        }
    }

    private class ModelStubAcceptingRequestAdded extends ModelStub {
        final ArrayList<Request> requestsAdded = new ArrayList<>();

        @Override
        public boolean hasRequest(Request request) {
            requireNonNull(request);
            return requestsAdded.stream().anyMatch(request::isSameRequest);
        }

        @Override
        public void addRequest(Request request) {
            requireNonNull(request);
            requestsAdded.add(request);
        }

        @Override
        public void commitRequestBook() {
            // called by {@code AddRequestCommand#execute()}
        }

        @Override
        public ReadOnlyRequestBook getRequestBook() {
            return new RequestBook();
        }
        @Override
        public void commit(CommandType commandType) {
            // called by {@code AddRequestCommand#execute()}
        }
    }
}
