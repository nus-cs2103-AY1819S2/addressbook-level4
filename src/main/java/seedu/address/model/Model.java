package seedu.address.model;

import java.nio.file.Path;
import java.util.Calendar;
import java.util.List;
import java.util.function.Predicate;

import javafx.beans.property.ReadOnlyProperty;
import javafx.collections.ObservableList;
import seedu.address.commons.core.GuiSettings;
import seedu.address.model.analytics.Analytics;
import seedu.address.model.interviews.Interviews;
import seedu.address.model.job.Job;
import seedu.address.model.job.JobName;
import seedu.address.model.person.Nric;
import seedu.address.model.person.Person;
import seedu.address.model.person.UniquePersonList;
import seedu.address.model.person.predicate.UniquePredicateList;

/**
 * The API of the Model component.
 */
public interface Model {
    /**
     * {@code Predicate} that always evaluate to true
     */
    Predicate<Person> PREDICATE_SHOW_ALL_PERSONS = unused -> true;

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

    /**
     * Returns the user prefs' address book file path.
     */
    Path getAddressBookFilePath();

    /**
     * Sets the user prefs' address book file path.
     */
    void setAddressBookFilePath(Path addressBookFilePath);

    /**
     * Replaces address book data with the data in {@code addressBook}.
     */
    void setAddressBook(ReadOnlyAddressBook addressBook);

    /**
     * Returns the AddressBook
     */
    ReadOnlyAddressBook getAddressBook();

    /**
     * Returns true if a person with the same identity as {@code person} exists in the address book.
     */
    boolean hasPerson(Person person);

    /**
     * Returns true if a job with the same identity as {@code job} exists in the address book.
     */
    boolean hasJob(Job job);

    /**
     * adds all persons in filtered personlist to {@code job}.
     */
    void addFilteredPersonsToJob(JobName jobName);

    /**
     * adds person with {@code nric} to {@code job}.
     */
    boolean addPersonToJob(JobName job, Nric nric);

    /**
     * Deletes the given person.
     * The person must exist in the address book.
     */
    void deletePerson(Person target);

    /**
     * Adds the given person.
     * {@code person} must not already exist in the address book.
     */
    void addPerson(Person person);

    /**
     * Adds the given job.
     * {@code job} must not already exist in the address book.
     */
    void addJob(Job job);

    /**
     * Deletes the given job.
     * {@code job} must exist in the address book.
     */
    void deleteJob(Job job);

    /**
     * Moves Person with {@code nric} in Job with {@code jobName}
     * from list {@code source} to list {@code dest}
     * {@code job} must exist in the address book.
     */
    Integer movePerson(JobName jobName, Nric nric, Integer source, Integer dest);

    /**
     * Replaces the given person {@code target} with {@code editedPerson}.
     * {@code target} must exist in the address book.
     * The person identity of {@code editedPerson} must not be the same as another existing person in the address book.
     */
    void setPerson(Person target, Person editedPerson);

    /**
     * add Predicate to JobShortlist
     */
    void addPredicateJobShortlist(Predicate<Person> predicate);

    /**
     * add Predicate to JobInterview
     */
    void addPredicateJobInterview(Predicate<Person> predicate);

    /**
     * add Predicate to JobKiv
     */
    void addPredicateJobKiv(Predicate<Person> predicate);

    /**
     * add Predicate to JobAllApplicants
     */
    void addPredicateJobAllApplicants(Predicate<Person> predicate);
    /**
     * Returns one of the UniquePredicateList in the job
     */
    /**
     * remove Predicate to JobShortlist
     */
    void removePredicateJobShortlist(Predicate<Person> predicate);

    /**
     * remove Predicate to JobInterview
     */
    void removePredicateJobInterview(Predicate<Person> predicate);

    /**
     * remove Predicate to JobKiv
     */
    void removePredicateJobKiv(Predicate<Person> predicate);

    /**
     * remove Predicate to JobAllApplicants
     */
    void removePredicateJobAllApplicants(Predicate<Person> predicate);
    /**
     * Returns one of the UniquePredicateList in the job
     */
    UniquePredicateList getPredicateList(JobName name, Integer listNumber);

    /**
     * Returns one of the UniquePersonList in the job
     */
    UniquePersonList getJobList(JobName name, Integer listNumber);

    /**
     * Returns the job and makes it the active job
     */
    Job getJob(JobName name);

    /**
     * Returns an unmodifiable view of the filtered person list
     */
    ObservableList<Person> getFilteredPersonList();

    /**
     * Returns an unmodifiable view of the predicate list
     */
    ObservableList<Person> getJobsList(int listNum);

    /**
     * Returns an unmodifiable view of the filtered job list
     */
    UniquePredicateList getPredicateLists(int listNumber);

    /**
     * Updates the filter of the filtered person list to filter by the given {@code predicate}.
     *
     * @throws NullPointerException if {@code predicate} is null.
     */
    void updateBaseFilteredPersonList(Predicate<Person> predicate);

    /**
     * Updates the filter of the JobAllApplcants filtered person list to filter by the given {@code predicate}.
     *
     * @throws NullPointerException if {@code predicate} is null.
     */
    void updateJobAllApplicantsFilteredPersonList(Predicate<Person> predicate);

    /**
     * Updates the filter of the JobKiv filtered person list to filter by the given {@code predicate}.
     *
     * @throws NullPointerException if {@code predicate} is null.
     */
    void updateJobKivFilteredPersonList(Predicate<Person> predicate);

    /**
     * Updates the filter of the JobInterview filtered person list to filter by the given {@code predicate}.
     *
     * @throws NullPointerException if {@code predicate} is null.
     */
    void updateJobInterviewFilteredPersonList(Predicate<Person> predicate);

    /**
     * Updates the filter of the JobShortlist filtered person list to filter by the given {@code predicate}.
     *
     * @throws NullPointerException if {@code predicate} is null.
     */
    void updateJobShortlistFilteredPersonList(Predicate<Person> predicate);

    /**
     * Updates the filter of the active filtered person list to filter by the given {@code predicate}.
     *
     * @throws NullPointerException if {@code predicate} is null.
     */
    void updateFilteredPersonList(Predicate<Person> predicate);

    /**
     * Changes the filtered person list to the given {@code list}.
     *
     * @throws NullPointerException if {@code predicate} is null.
     */
    void changeFilteredPersonList(UniquePersonList list);

    void revertList();

    ObservableList<Job> getFilteredJobList();

    /**
     * Returns true if the model has previous address book states to restore.
     */
    boolean canUndoAddressBook();

    /**
     * Returns true if the model has undone address book states to restore.
     */
    boolean canRedoAddressBook();

    /**
     * Restores the model's address book to its previous state.
     */
    void undoAddressBook();

    /**
     * Restores the model's address book to its previously undone state.
     */
    void redoAddressBook();

    /**
     * Saves the current address book state for undo/redo.
     */
    void commitAddressBook();

    /**
     * Selected person in the filtered person list.
     * null if no person is selected.
     */
    ReadOnlyProperty<Person> selectedPersonProperty();

    /**
     * Returns the selected person in the filtered person list.
     * null if no person is selected.
     */
    Person getSelectedPerson();

    /**
     * Sets the selected person in the filtered person list.
     */
    void setSelectedPerson(Person person);

    void setSelectedAll(Person person);

    void setSelectedKiv(Person person);

    void setSelectedInterviewed(Person person);

    void setSelectedSelected(Person person);

    /**
     * Generates an interview list.
     */
    void generateInterviews();

    /**
     * Returns Interviews.
     */
    Interviews getInterviews();

    /**
     * Sets the maximum number of interviews a day.
     */
    void setMaxInterviewsADay(int maxInterviewsADay);

    /**
     * Clears the generated interviews.
     */
    void clearInterviews();

    /**
     * Sets Block Out Dates.
     */
    void setBlockOutDates(List<Calendar> blockOutDates);

    /**
     * Generates analytics.
     */
    Analytics generateAnalytics();

}
