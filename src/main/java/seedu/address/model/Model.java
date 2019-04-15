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
import seedu.address.model.job.JobListName;
import seedu.address.model.job.JobName;
import seedu.address.model.person.Person;
import seedu.address.model.person.predicate.UniqueFilterList;

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
    void addFilteredPersonsToJob(JobName jobName, JobListName from, JobListName to);

    /**
     * adds person with {@code nric} to {@code job}.
     */
    void addPersonToJob(Job job, Person person, JobListName list);

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
     * Deletes the given job list of a job
     * {@code job} must exist in the address book.
     */
    void deletePersonFromJobList(Person toRemove, JobName job, JobListName list);

    /**
     * Moves Person with {@code nric} in Job with {@code jobName}
     * from list {@code source} to list {@code dest}
     * {@code job} must exist in the address book.
     */
    Integer movePerson(Job job, Person person, Integer source, Integer dest);

    /**
     * Replaces the given person {@code target} with {@code editedPerson}.
     * {@code target} must exist in the address book.
     * The person identity of {@code editedPerson} must not be the same as another existing person in the address book.
     */
    void setPerson(Person target, Person editedPerson);

    /**
     * Returns the job and makes it the active job
     */
    Job getJob(JobName name);

    /**
     * Returns the current active Job.
     */
    Job getActiveJob();

    /**
     * Returns an unmodifiable view of the filtered  people list in job
     *
     * @param list indicate the job person list
     */

    ObservableList<Person> getJobsList(JobListName list);

    /**
     * Returns an unmodifiable view of the filtered job list
     */
    ObservableList<Job> getAllJobs();


    /**
     * add Predicate to filterList list
     */
    void addPredicate(String predicateName, Predicate<Person> predicate, JobListName listname);

    /**
     * remove Predicate to filterList list
     */
    void removePredicate(String predicateName, JobListName listName);


    /**
     * Updates the filter of the filtered person list to filter by the given {@code predicate}.
     *
     * @throws NullPointerException if {@code predicate} is null.
     */
    void updateBaseFilteredPersonList(Predicate<Person> predicate);

    /**
     * Updates the filter of the filtered person list to filter by the given {@code predicate}.
     *
     * @throws NullPointerException if {@code predicate} is null.
     */
    void updateFilteredPersonList(Predicate<Person> predicate);

    /**
     * Updates the filter of the JobKiv filtered person list to filter by the given {@code predicate}.
     */
    void updateFilteredPersonLists(JobListName listname);

    /**
     * Clear four filter list.
     */
    void clearJobFilteredLists();

    /**
     * Clear indicated filter list.
     */
    void clearJobFilteredLists(JobListName listName);

    /**
     * Returns an unmodifiable view of the filtered person list
     */
    ObservableList<Person> getFilteredPersonList();


    /**
     * Returns the filtered job list
     */
    UniqueFilterList getPredicateLists(JobListName listName);


    /**
     * Revert the display list.
     */
    void revertList();

    /**
     * Returns an unmodifiable view of the list of {@code Person} backed by the internal list of
     * {@code versionedAddressBook}
     */
    ObservableList<Job> getFilteredJobList();

    /**
     * Returns an unmodifiable view of the list of {@code Person} backed by the internal list of
     * {@code versionedAddressBook}
     */
    ObservableList<Person> getBaseFilteredPersonList();

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
     * Selected job in the filtered job list.
     * null if no job is selected.
     */
    ReadOnlyProperty<Job> selectedJobProperty();

    /**
     * Returns the selected person in the filtered person list.
     * null if no person is selected.
     */
    Person getSelectedPerson();

    /**
     * return whether it is at the all jobs screen ordisplay job screen
     */
    boolean getIsAllJobScreen();

    /**
     * set whether it is at the all jobs screen ordisplay job screen
     */
    void setIsAllJobScreen(boolean staus);

    /**
     * Sets the selected person in the filtered person list.
     */
    void setSelectedPerson(Person person);

    void setSelectedAll(Person person);

    void setSelectedKiv(Person person);

    void setSelectedInterviewed(Person person);

    void setSelectedSelected(Person person);

    void setSelectedJob(Job job);

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

    Analytics generateAnalytics(JobListName listName);

}
