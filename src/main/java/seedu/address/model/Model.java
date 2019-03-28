package seedu.address.model;

import java.nio.file.Path;
import java.util.function.Predicate;

import javafx.beans.property.ReadOnlyProperty;
import javafx.collections.ObservableList;
import seedu.address.commons.core.GuiSettings;
import seedu.address.model.person.Person;
import seedu.address.model.person.healthworker.HealthWorker;
import seedu.address.model.request.Request;

/**
 * The API of the Model component.
 * TODO: Overhaul to only have components needed for HealthHub
 */
public interface Model {
    /** {@code Predicate} that always evaluate to true */
    Predicate<Person> PREDICATE_SHOW_ALL_PERSONS = unused -> true;

    /** {@code Predicate} that always evaluate to true */
    Predicate<HealthWorker> PREDICATE_SHOW_ALL_HEALTHWORKERS = unused -> true;

    /** {@code Predicate} that always evaluate to true */
    Predicate<Request> PREDICATE_SHOW_ALL_REQUESTS = unused -> true;

    /**
     * Replaces user prefs data with the data in {@code userPrefs}.
     */
    void setUserPrefs(ReadOnlyUserPrefs userPrefs);

    /**
     * Returns the user prefs.
     */
    ReadOnlyUserPrefs getUserPrefs();

    /**
     * Returns the user prefs' GUI settings.
     */
    GuiSettings getGuiSettings();

    /**
     * Sets the user prefs' GUI settings.
     */
    void setGuiSettings(GuiSettings guiSettings);


    /** Returns the HealthWorkerBook */
    ReadOnlyHealthWorkerBook getHealthWorkerBook();



    // ============== Added methods for AddHealthWorkerCommand ===============
    // @author: Lookaz

    /**
     * Returns true if a person with the same identity as {@code healthWorker}
     * exists in the address book.
     */
    boolean hasHealthWorker(HealthWorker healthWorker);

    /**
     * Deletes the given HealthWorker.
     * The HealthWorker object must exist in the address book.
     */
    void deleteHealthWorker(HealthWorker target);

    /**
     * Adds the given HealthWorker.
     * {@code healthWorker} must not already exist in the address book.
     */
    void addHealthWorker(HealthWorker healthWorker);

    /**
     * Replaces the given person {@code target} with {@code editedWorker}.
     * {@code target} must exist in the address book.
     * The identity of {@code editedWorker} must not be the same as
     * another existing HealthWorker in the address book.
     */
    void setHealthWorker(HealthWorker target, HealthWorker editedWorker);

    /** Returns an unmodifiable view of the filtered health worker list */
    ObservableList<HealthWorker> getFilteredHealthWorkerList();

    /**
     * Updates the filter of the filtered HealthWorker list to filter by the given {@code predicate}.
     * @throws NullPointerException if {@code predicate} is null.
     */
    void updateFilteredHealthWorkerList(Predicate<HealthWorker> predicate);

    /**
     * Selected health worker in the filtered health worker list.
     * null if no health worker is selected.
     */
    ReadOnlyProperty<HealthWorker> selectedHealthWorkerProperty();

    /**
     * Sets the selected health worker in the filtered health worker list.
     */
    void setSelectedHealthWorker(HealthWorker worker);

    void commitHealthWorkerBook();


    // ================== Request related code =========================================
    // @author David, Hui Chun

    /**
     * Returns the user prefs' request book file path.
     */
    Path getRequestBookFilePath();

    /** Returns the RequestBook */
    ReadOnlyRequestBook getRequestBook();

    /**
     * Returns an unmodifiable view of the request list.
     */
    ObservableList<Request> getFilteredRequestList();

    /**
     * Selected request in the filtered request list.
     * null if no request is selected.
     */
    ReadOnlyProperty<Request> selectedRequestProperty();

    /**
     * Sets the selected request in the filtered request list.
     */
    void setSelectedRequest(Request request);

    /**
     * Returns true if a request with the same identity as {@code request} exists in the address
     * book.
     */
    boolean hasRequest(Request request);


    /**
     * Replaces the given order {@code target} with {@code editedRequest}.
     * {@code target} must exist in the request book.
     * The request identity of {@code editedRequest} must not be the same as another existing
     * request in the request book.
     */
    void updateRequest(Request target, Request editedRequest);

    /**
     * Deletes the given request.
     * The request must exist in the request book.
     */
    void deleteRequest(Request target);

    /**
     * Updates the filter of the filtered order list to filter by the given {@code predicate}.
     *
     * @throws NullPointerException if {@code predicate} is null.
     */
    void updateFilteredRequestList(Predicate<Request> predicate);

    /**
     * Adds the given request.
     * {@code request} must not already exist in the request book.
     */
    void addRequest(Request request);

    /**
     * Replaces the given request {@code target} with {@code editedRequest}.
     * {@code target} must exist in the request book.
     * The request identity of {@code editedRequest} must not be the same as another existing
     * request in the request book.
     */
    void setRequest(Request target, Request editedRequest);

    void commitRequestBook();
}
