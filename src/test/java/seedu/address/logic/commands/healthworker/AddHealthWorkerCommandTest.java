package seedu.address.logic.commands.healthworker;

import static java.util.Objects.requireNonNull;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.testutil.TypicalHealthWorkers.ANDY;
import static seedu.address.testutil.TypicalHealthWorkers.BETTY;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.function.Predicate;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import javafx.beans.property.ReadOnlyProperty;
import javafx.collections.ObservableList;
import seedu.address.commons.core.GuiSettings;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.AddHealthWorkerCommand;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.ReadOnlyHealthWorkerBook;
import seedu.address.model.ReadOnlyRequestBook;
import seedu.address.model.ReadOnlyUserPrefs;
import seedu.address.model.person.Person;
import seedu.address.model.person.healthworker.HealthWorker;
import seedu.address.model.request.Request;
import seedu.address.testutil.Assert;
import seedu.address.testutil.HealthWorkerBuilder;

public class AddHealthWorkerCommandTest {

    private static final CommandHistory EMPTY_COMMAND_HISTORY = new CommandHistory();

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private CommandHistory commandHistory = new CommandHistory();

    @Test
    public void constructor_nullPerson_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> new
                AddHealthWorkerCommand(null));
    }

    @Test
    public void execute() throws CommandException {
        ModelStubAcceptingHealthWorkerAdded modelStub = new
                ModelStubAcceptingHealthWorkerAdded();
        HealthWorker validWorker = new HealthWorkerBuilder().build();

        CommandResult commandResult = new AddHealthWorkerCommand(validWorker)
                .execute(modelStub, commandHistory);

        assertEquals(String.format(AddHealthWorkerCommand.MESSAGE_SUCCESS, validWorker),
                commandResult.getFeedbackToUser());
        assertEquals(Arrays.asList(validWorker), modelStub.healthWorkersAdded);
        assertEquals(EMPTY_COMMAND_HISTORY, commandHistory);

        // Duplicate health worker
        HealthWorker newValidWorker = new HealthWorkerBuilder().build();
        thrown.expectMessage(AddHealthWorkerCommand.DUPLICATE_HEALTH_WORKER);
        thrown.expect(CommandException.class);
        new AddHealthWorkerCommand(newValidWorker).execute(modelStub, commandHistory);
    }

    @Test
    public void equals() {
        AddHealthWorkerCommand addAndyCommand = new AddHealthWorkerCommand(ANDY);
        AddHealthWorkerCommand addBettyCommand = new AddHealthWorkerCommand(BETTY);

        // same object -> return true
        assertTrue(addAndyCommand.equals(addAndyCommand));

        // same values -> return true
        assertTrue(addBettyCommand.equals(new AddHealthWorkerCommand(BETTY)));

        // different types -> return false
        assertFalse(addAndyCommand.equals(1));

        // null command -> return false
        assertFalse(addAndyCommand.equals(null));

        // different health workers -> return false
        assertFalse(addAndyCommand.equals(addBettyCommand));
    }

    /**
     * Model Stub class for accepting HealthWorker objects.
     */
    private class ModelStubAcceptingHealthWorkerAdded extends ModelStub {
        private ArrayList<HealthWorker> healthWorkersAdded = new ArrayList<>();

        @Override
        public void addHealthWorker(HealthWorker healthWorker) {
            requireNonNull(healthWorker);
            this.healthWorkersAdded.add(healthWorker);
        }

        @Override
        public boolean hasHealthWorker(HealthWorker healthWorker) {
            requireNonNull(healthWorker);
            return this.healthWorkersAdded.stream().anyMatch(
                    healthWorker::isSameHealthWorker);
        }

        @Override
        public void commitAddressBook() {
            // called by {@code AddHealthWorkerCommand#execute()}
        }

        @Override
        public ReadOnlyAddressBook getAddressBook() {
            return new AddressBook();
        }
    }

    /**
     * A default model stub that have all of the methods failing.
     */
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
        public Path getAddressBookFilePath() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setAddressBookFilePath(Path addressBookFilePath) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void addPerson(Person person) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setAddressBook(ReadOnlyAddressBook newData) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ReadOnlyAddressBook getAddressBook() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean hasPerson(Person person) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void deletePerson(Person target) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setPerson(Person target, Person editedPerson) {
            throw new AssertionError("This method should not be called.");
        }

        // =========== Implemented methods supporting Health Worker ===========
        // @author Lookaz

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
        public ReadOnlyHealthWorkerBook getHealthWorkerBook() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ObservableList<Person> getFilteredPersonList() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void updateFilteredPersonList(Predicate<Person> predicate) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean canUndoAddressBook() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean canRedoAddressBook() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void undoAddressBook() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void redoAddressBook() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void commitAddressBook() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ReadOnlyProperty<Person> selectedPersonProperty() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public Person getSelectedPerson() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setSelectedPerson(Person person) {
            throw new AssertionError("This method should not be called.");
        }

        /**
         * Returns the user prefs' request book file path.
         */
        @Override
        public Path getRequestBookFilePath() {
            throw new AssertionError("This method should not be called.");
        }

        /**
         * Returns the RequestBook
         */
        @Override
        public ReadOnlyRequestBook getRequestBook() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ObservableList<Request> getFilteredRequestList() {
            return null;
        }

        /**
         * Returns true if a request with the same identity as {@code request} exists in the address
         * book.
         *
         * @param request
         */
        @Override
        public boolean hasRequest(Request request) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void updateRequest(Request target, Request editedRequest) {
            throw new AssertionError("This method should not be called.");
        }

        /**
         * Deletes the given request.
         * The request must exist in the request book.
         *
         * @param target
         */
        @Override
        public void deleteRequest(Request target) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void updateFilteredRequestList(Predicate<Request> predicate) {
            throw new AssertionError("This method should not be called.");
        }

        /**
         * Adds the given request.
         * {@code request} must not already exist in the request book.
         */
        @Override
        public void addRequest(Request request) {
            throw new AssertionError("This method should not be called.");
        }

        /**
         * Replaces the given request {@code target} with {@code editedRequest}.
         * {@code target} must exist in the request book.
         * The request identity of {@code editedRequest} must not be the same as another existing
         * request in the request book.
         *
         * @param target
         * @param editedRequest
         */
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
        public void commitRequestBook() {
            throw new AssertionError("This method should not be called.");
        }
    }

}
