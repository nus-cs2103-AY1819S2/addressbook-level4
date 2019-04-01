package seedu.address.model;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.nio.file.Path;
import java.util.Objects;
import java.util.function.Predicate;
import java.util.logging.Logger;

import javafx.beans.property.ReadOnlyProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import seedu.address.commons.core.GuiSettings;
import seedu.address.commons.core.LogsCenter;
import seedu.address.model.person.Person;
import seedu.address.model.person.healthworker.HealthWorker;
import seedu.address.model.person.healthworker.exceptions.HealthWorkerNotFoundException;
import seedu.address.model.request.Request;
import seedu.address.model.request.exceptions.RequestNotFoundException;

/**
 * Represents the in-memory model of the address book data.
 */
public class ModelManager implements Model {
    private static final Logger logger = LogsCenter.getLogger(ModelManager.class);

    private final VersionedHealthWorkerBook versionedHealthWorkerBook;

    private final VersionedRequestBook versionedRequestBook;
    private final UserPrefs userPrefs;

    private final FilteredList<HealthWorker> filteredHealthWorkers;

    // TODO make the relevant changes to the model manager
    // TODO get versionedAddressBook tests to pass
    private final FilteredList<Request> filteredRequests;
    private final SimpleObjectProperty<Person> selectedPerson = new SimpleObjectProperty<>();
    private final SimpleObjectProperty<HealthWorker> selectedHealthWorker = new SimpleObjectProperty<>();
    private final SimpleObjectProperty<Request> selectedRequest = new SimpleObjectProperty<>();

    /**
     * Initializes a ModelManager with the given addressBook and userPrefs.
     */
    public ModelManager(
                        ReadOnlyHealthWorkerBook healthWorkerBook,
                        ReadOnlyRequestBook requestBook,
                        ReadOnlyUserPrefs userPrefs) {
        super();
        requireAllNonNull(requestBook, healthWorkerBook, userPrefs);

        logger.fine("Initializing with request book: " + requestBook + " and user prefs " + userPrefs);

        versionedHealthWorkerBook = new VersionedHealthWorkerBook(healthWorkerBook);
        versionedRequestBook = new VersionedRequestBook(requestBook);
        this.userPrefs = new UserPrefs(userPrefs);
        filteredHealthWorkers = new FilteredList<>(versionedHealthWorkerBook.getHealthWorkerList());
        filteredRequests = new FilteredList<>(versionedRequestBook.getRequestList());
        filteredHealthWorkers.addListener(this::ensureSelectedHealthWorkerIsValid);
        filteredRequests.addListener(this::ensureSelectedRequestIsValid);
    }

    public ModelManager() {
        this(new HealthWorkerBook(), new RequestBook(), new UserPrefs());
    }

    //=========================== UserPrefs ====================================================

    @Override
    public void setUserPrefs(ReadOnlyUserPrefs userPrefs) {
        requireNonNull(userPrefs);
        this.userPrefs.resetData(userPrefs);
    }

    @Override
    public ReadOnlyUserPrefs getUserPrefs() {
        return userPrefs;
    }

    @Override
    public GuiSettings getGuiSettings() {
        return userPrefs.getGuiSettings();
    }

    @Override
    public void setGuiSettings(GuiSettings guiSettings) {
        requireNonNull(guiSettings);
        userPrefs.setGuiSettings(guiSettings);
    }


    // ======================== Implemented methods for HealthWorker through Model Interface =========================
    // @author: Lookaz

    @Override
    public boolean hasHealthWorker(HealthWorker healthWorker) {
        requireNonNull(healthWorker);
        return this.versionedHealthWorkerBook.hasHealthWorker(healthWorker);
    }

    @Override
    public void deleteHealthWorker(HealthWorker target) {
        this.versionedHealthWorkerBook.removeHealthWorker(target);
    }

    @Override
    public void addHealthWorker(HealthWorker healthWorker) {
        this.versionedHealthWorkerBook.addHealthWorker(healthWorker);
        updateFilteredHealthWorkerList(PREDICATE_SHOW_ALL_HEALTHWORKERS);
    }

    @Override
    public void setHealthWorker(HealthWorker target, HealthWorker editedWorker) {
        requireAllNonNull(target, editedWorker);

        this.versionedHealthWorkerBook.setHealthWorker(target, editedWorker);
    }

    @Override
    public ObservableList<HealthWorker> getFilteredHealthWorkerList() {
        return this.filteredHealthWorkers;
    }

    @Override
    public void updateFilteredHealthWorkerList(Predicate<HealthWorker> predicate) {
        requireNonNull(predicate);
        this.filteredHealthWorkers.setPredicate(predicate);
    }

    @Override
    public ReadOnlyProperty<HealthWorker> selectedHealthWorkerProperty() {
        return selectedHealthWorker;
    }

    @Override
    public void setSelectedHealthWorker(HealthWorker worker) {
        if (worker != null && !filteredHealthWorkers.contains(worker)) {
            throw new HealthWorkerNotFoundException();
        }
        selectedHealthWorker.setValue(worker);
    }

    @Override
    public ReadOnlyHealthWorkerBook getHealthWorkerBook() {
        return this.versionedHealthWorkerBook;
    }

    @Override
    public void commitHealthWorkerBook() {
        versionedHealthWorkerBook.commit();
    }

    /**
     * Returns the user prefs' health worker book file path.
     */
    @Override
    public Path getHealthWorkerBookFilePath() {
        return userPrefs.getHealthWorkerBookFilePath();
    }

    //=========== Undo/Redo =================================================================================
    // TODO: Modify to do redo/undo for HealthWorkerBook. Suggestion: Use a state to maintain previous type of op.

    //=========== Implemented methods for Request through the Model interface  ==============================

    @Override
    public ObservableList<Request> getFilteredRequestList() {
        return filteredRequests;
    }

    /**
     * Returns the user prefs' request book file path.
     */
    @Override
    public Path getRequestBookFilePath() {
        return userPrefs.getRequestBookFilePath();
    }

    /**
     * Returns the RequestBook
     */
    @Override
    public ReadOnlyRequestBook getRequestBook() {
        return this.versionedRequestBook;
    }

    /**
     * Returns true if a request with the same identity as {@code request} exists in the address
     * book.
     *
     * @param request
     */
    @Override
    public boolean hasRequest(Request request) {
        return false;
    }

    @Override
    public void updateRequest(Request target, Request editedRequest) {
        requireAllNonNull(target, editedRequest);

        versionedRequestBook.setRequest(target, editedRequest);
    }

    /**
     * Deletes the given request.
     * The request must exist in the request book.
     *
     * @param target
     */
    @Override
    public void deleteRequest(Request target) {
        versionedRequestBook.removeRequest(target);
    }

    @Override
    public void updateFilteredRequestList(Predicate<Request> predicate) {
        requireNonNull(predicate);
        filteredRequests.setPredicate(predicate);
    }

    /**
     * Adds the given request to the request book
     */
    @Override
    public void addRequest(Request request) {
        versionedRequestBook.addRequest(request);
        updateFilteredRequestList(PREDICATE_SHOW_ALL_REQUESTS);
    }

    @Override
    public void resetData(ReadOnlyRequestBook newData) {
        versionedRequestBook.resetData(newData);
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
        requireAllNonNull(target, editedRequest);
        versionedRequestBook.setRequest(target, editedRequest);
    }

    @Override
    public void commitRequestBook() {
        versionedRequestBook.commit();
    }


    @Override
    public ReadOnlyProperty<Request> selectedRequestProperty() {
        return selectedRequest;
    }

    @Override
    public void setSelectedRequest(Request request) {
        if (request != null && !filteredRequests.contains(request)) {
            throw new RequestNotFoundException();
        }
        selectedRequest.setValue(request);
    }

    /**
     * Ensures {@code selectedPerson} is a valid person in {@code filteredPersons}.
     */
    private void ensureSelectedPersonIsValid(ListChangeListener.Change<? extends Person> change) {
        while (change.next()) {
            if (selectedPerson.getValue() == null) {
                // null is always a valid selected person, so we do not need to check that it is valid anymore.
                return;
            }

            boolean wasSelectedPersonReplaced = change.wasReplaced() && change.getAddedSize() == change.getRemovedSize()
                    && change.getRemoved().contains(selectedPerson.getValue());
            if (wasSelectedPersonReplaced) {
                // Update selectedPerson to its new value.
                int index = change.getRemoved().indexOf(selectedPerson.getValue());
                selectedPerson.setValue(change.getAddedSubList().get(index));
                continue;
            }

            boolean wasSelectedPersonRemoved = change.getRemoved().stream()
                    .anyMatch(removedPerson -> selectedPerson.getValue().isSamePerson(removedPerson));
            if (wasSelectedPersonRemoved) {
                // Select the person that came before it in the list,
                // or clear the selection if there is no such person.
                selectedPerson.setValue(change.getFrom() > 0 ? change.getList().get(change.getFrom() - 1) : null);
            }
        }
    }

    /**
     * Ensures {@code selectedHealthWorker} is a valid request in {@code filteredHealthWorkers}.
     */
    private void ensureSelectedHealthWorkerIsValid(ListChangeListener.Change<? extends HealthWorker> change) {
        while (change.next()) {
            if (selectedHealthWorker.getValue() == null) {
                return;
            }

            boolean wasSelectedHealthWorkerReplaced =
                    change.wasReplaced() && change.getAddedSize() == change.getRemovedSize()
                            && change.getRemoved().contains(selectedHealthWorker.getValue());

            if (wasSelectedHealthWorkerReplaced) {
                // Update selectedHealthWorker to its new value
                int index = change.getRemoved().indexOf(selectedHealthWorker.getValue());
                selectedHealthWorker.setValue(change.getAddedSubList().get(index));
                continue;
            }

            boolean wasSelectedHealthWorkerRemoved =
                    change.getRemoved().stream().anyMatch(removedHealthWorker -> selectedHealthWorker.getValue()
                            .isSameHealthWorker(removedHealthWorker));
            if (wasSelectedHealthWorkerRemoved) {
                selectedHealthWorker.setValue(change.getFrom() > 0
                        ? change.getList().get(change.getFrom() - 1) : null);
            }
        }
    }

    /**
     * Ensures {@code selectedRequest} is a valid request in {@code filteredRequests}.
     */
    private void ensureSelectedRequestIsValid(ListChangeListener.Change<? extends Request> change) {
        while (change.next()) {
            if (selectedRequest.getValue() == null) {
                return;
            }

            boolean wasSelectedRequestReplaced =
                change.wasReplaced() && change.getAddedSize() == change.getRemovedSize()
                    && change.getRemoved().contains(selectedRequest.getValue());

            if (wasSelectedRequestReplaced) {
                // Update selectedRequest to its new value
                int index = change.getRemoved().indexOf(selectedRequest.getValue());
                selectedRequest.setValue(change.getAddedSubList().get(index));
                continue;
            }

            boolean wasSelectedRequestRemoved =
                change.getRemoved().stream().anyMatch(removedRequest -> selectedRequest.getValue()
                    .isSameRequest(removedRequest));
            if (wasSelectedRequestRemoved) {
                selectedRequest.setValue(change.getFrom() > 0
                    ? change.getList().get(change.getFrom() - 1) : null);
            }
        }
    }

    @Override
    public boolean equals(Object obj) {
        // short circuit if same object
        if (obj == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(obj instanceof ModelManager)) {
            return false;
        }

        // state check
        ModelManager other = (ModelManager) obj;
        return versionedHealthWorkerBook.equals(other.versionedHealthWorkerBook)
            && versionedRequestBook.equals(other.versionedRequestBook)
            && userPrefs.equals(other.userPrefs)
            && filteredRequests.equals(other.filteredRequests)
            && filteredHealthWorkers.equals(filteredHealthWorkers)
            && Objects.equals(selectedRequest.get(), other.selectedRequest.get())
            && Objects.equals(selectedPerson.get(), other.selectedPerson.get());
    }

}
