package seedu.address.model;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.nio.file.Path;
import java.util.Calendar;
import java.util.List;
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
import seedu.address.model.analytics.Analytics;
import seedu.address.model.interviews.Interviews;
import seedu.address.model.job.Job;
import seedu.address.model.job.JobName;
import seedu.address.model.person.Nric;
import seedu.address.model.person.Person;
import seedu.address.model.person.UniquePersonList;
import seedu.address.model.person.exceptions.PersonNotFoundException;

/**
 * Represents the in-memory model of the address book data.
 */
public class ModelManager implements Model {
    private static final Logger logger = LogsCenter.getLogger(ModelManager.class);

    private final VersionedAddressBook versionedAddressBook;
    private final UserPrefs userPrefs;
    private final SimpleObjectProperty<Person> selectedPerson = new SimpleObjectProperty<>();
    private final SimpleObjectProperty<Job> selectedJob = new SimpleObjectProperty<>();
    private FilteredList<Person> originalFilteredPersons;
    private FilteredList<Person> displayedFilteredPersons;
    private FilteredList<Job> filteredJobs;
    private Job activeJob;
    private FilteredList<Person> activeJobAllApplcants;
    private FilteredList<Person> activeJobKiv;
    private FilteredList<Person> activeJobInterview;
    private FilteredList<Person> activeJobShortlist;
    private FilteredList<Job> allJobsList;


    /**
     * Initializes a ModelManager with the given addressBook and userPrefs.
     */
    public ModelManager(ReadOnlyAddressBook addressBook, ReadOnlyUserPrefs userPrefs) {
        super();
        requireAllNonNull(addressBook, userPrefs);

        logger.fine("Initializing with address book: " + addressBook + " and user prefs " + userPrefs);

        versionedAddressBook = new VersionedAddressBook(addressBook);
        this.userPrefs = new UserPrefs(userPrefs);
        originalFilteredPersons = new FilteredList<>(versionedAddressBook.getPersonList());
        filteredJobs = new FilteredList<>(versionedAddressBook.getJobList());
        originalFilteredPersons.addListener(this::ensureSelectedPersonIsValid);
        filteredJobs.addListener(this::ensureSelectedJobIsValid);
        displayedFilteredPersons = originalFilteredPersons;
        UniquePersonList fakeList = new UniquePersonList();
        activeJobAllApplcants = new FilteredList<>(fakeList.asUnmodifiableObservableList());
        activeJobKiv = new FilteredList<>(fakeList.asUnmodifiableObservableList());
        activeJobShortlist = new FilteredList<>(fakeList.asUnmodifiableObservableList());
        activeJobInterview = new FilteredList<>(fakeList.asUnmodifiableObservableList());
        allJobsList = new FilteredList<>(versionedAddressBook.getAllJobList());
    }

    public ModelManager() {
        this(new AddressBook(), new UserPrefs());
    }

    //=========== UserPrefs ==================================================================================

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

    @Override
    public Path getAddressBookFilePath() {
        return userPrefs.getAddressBookFilePath();
    }

    @Override
    public void setAddressBookFilePath(Path addressBookFilePath) {
        requireNonNull(addressBookFilePath);
        userPrefs.setAddressBookFilePath(addressBookFilePath);
    }

    //=========== AddressBook ================================================================================

    @Override
    public void setAddressBook(ReadOnlyAddressBook addressBook) {
        versionedAddressBook.resetData(addressBook);
    }

    @Override
    public ReadOnlyAddressBook getAddressBook() {
        return versionedAddressBook;
    }

    @Override
    public boolean hasPerson(Person person) {
        requireNonNull(person);
        return versionedAddressBook.hasPerson(person);
    }

    @Override
    public boolean hasJob(Job job) {
        requireNonNull(job);
        return versionedAddressBook.hasJob(job);
    }

    @Override
    public void addFilteredPersonsToJob(JobName jobName) {
        requireNonNull(jobName);
        versionedAddressBook.addFilteredListToJob(displayedFilteredPersons, jobName);
    }

    @Override
    public boolean addPersonToJob(JobName job, Nric nric) {
        requireAllNonNull(job, nric);

        return versionedAddressBook.addPersonToJobByNric(nric, job);
    }

    @Override
    public void deletePerson(Person target) {
        versionedAddressBook.removePerson(target);
    }

    @Override
    public void addPerson(Person person) {
        versionedAddressBook.addPerson(person);
        updateBaseFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
    }

    @Override
    public void addJob(Job job) {
        versionedAddressBook.addJob(job);
    }

    @Override
    public void deleteJob(Job job) {
        versionedAddressBook.deleteJob(job);
        revertList();
        updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
    }

    @Override
    public Integer movePerson(JobName jobName, Nric nric, Integer source, Integer dest) {
        return versionedAddressBook.movePerson(jobName, nric, source, dest);
    }

    @Override
    public void setPerson(Person target, Person editedPerson) {
        requireAllNonNull(target, editedPerson);

        versionedAddressBook.setPerson(target, editedPerson);
    }

    @Override
    public UniquePersonList getJobList(JobName name, Integer listNumber) {
        Job job = getJob(name);
        return job.getList(listNumber);
    }

    public ObservableList<Person> getJobsList(int listNumber) {
        if (listNumber == 0) {
            return activeJobAllApplcants;
        }
        else if (listNumber == 1) {
            return activeJobKiv;
        }

        else if (listNumber == 2) {
            return activeJobInterview;
        }

        else {
            return activeJobShortlist;
        }
    }

    @Override
    public Job getJob(JobName name) {
        this.activeJob = versionedAddressBook.getJob(name);
        this.activeJobAllApplcants =
                new FilteredList<>(activeJob.getList(0).asUnmodifiableObservableList());
        this.activeJobKiv =
                new FilteredList<>(activeJob.getList(1).asUnmodifiableObservableList());
        this.activeJobInterview =
                new FilteredList<>(activeJob.getList(2).asUnmodifiableObservableList());
        this.activeJobShortlist =
                new FilteredList<>(activeJob.getList(3).asUnmodifiableObservableList());
        return activeJob;
    }

    //=========== Filtered Person List Accessors =============================================================

    /**
     * Returns an unmodifiable view of the list of {@code Person} backed by the internal list of
     * {@code versionedAddressBook}
     */
    @Override
    public ObservableList<Person> getFilteredPersonList() {
        return displayedFilteredPersons;
    }

    @Override
    public void updateBaseFilteredPersonList(Predicate<Person> predicate) {
        requireNonNull(predicate);
        originalFilteredPersons.setPredicate(predicate);
    }

    @Override
    public void updateFilteredPersonList(Predicate<Person> predicate) {
        requireNonNull(predicate);
        displayedFilteredPersons.setPredicate(predicate);
    }

    @Override
    public void changeFilteredPersonList(UniquePersonList list) {
        requireNonNull(list);

        this.displayedFilteredPersons = new FilteredList<>(list.asUnmodifiableObservableList());
    }

    @Override
    public void revertList() {
        this.displayedFilteredPersons = originalFilteredPersons;
    }

    //=========== Filtered Job List Accessors =============================================================

    /**
     * Returns an unmodifiable view of the list of {@code Job} backed by the internal list of
     * {@code versionedAddressBook}
     */
    @Override
    public ObservableList<Job> getFilteredJobList() {
        return filteredJobs;
    }

    //=========== Undo/Redo =================================================================================

    @Override
    public boolean canUndoAddressBook() {
        return versionedAddressBook.canUndo();
    }

    @Override
    public boolean canRedoAddressBook() {
        return versionedAddressBook.canRedo();
    }

    @Override
    public void undoAddressBook() {
        versionedAddressBook.undo();
    }

    @Override
    public void redoAddressBook() {
        versionedAddressBook.redo();
    }

    @Override
    public void commitAddressBook() {
        versionedAddressBook.commit();
    }

    //=========== Selected person ===========================================================================

    @Override
    public ReadOnlyProperty<Person> selectedPersonProperty() {
        return selectedPerson;
    }

    @Override
    public Person getSelectedPerson() {
        return selectedPerson.getValue();
    }

    @Override
    public void setSelectedPerson(Person person) {
        if (person != null && !displayedFilteredPersons.contains(person)) {
            throw new PersonNotFoundException();
        }
        selectedPerson.setValue(person);
    }

    public void setSelectedAll(Person person) {
        if (person != null && !activeJobAllApplcants.contains(person)) {
            throw new PersonNotFoundException();
        }
        selectedPerson.setValue(person);
    }

    public void setSelectedKiv(Person person) {
        if (person != null && !activeJobKiv.contains(person)) {
            throw new PersonNotFoundException();
        }
        selectedPerson.setValue(person);
    }

    public void setSelectedInterviewed(Person person) {
        if (person != null && !activeJobInterview.contains(person)) {
            throw new PersonNotFoundException();
        }
        selectedPerson.setValue(person);
    }

    public void setSelectedSelected(Person person) {
        if (person != null && !activeJobShortlist.contains(person)) {
            throw new PersonNotFoundException();
        }
        selectedPerson.setValue(person);
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
     * Ensures {@code selectedJob} is a valid job in {@code filteredJob}.
     */
    private void ensureSelectedJobIsValid(ListChangeListener.Change<? extends Job> change) {
        while (change.next()) {
            if (selectedJob.getValue() == null) {
                // null is always a valid selected job, so we do not need to check that it is valid anymore.
                return;
            }

            boolean wasSelectedJobChanged = change.wasReplaced() && change.getAddedSize() == change.getRemovedSize()
                    && change.getRemoved().contains(selectedJob.getValue());
            if (wasSelectedJobChanged) {
                // Update selectedJob to its new value.
                int index = change.getRemoved().indexOf(selectedJob.getValue());
                selectedJob.setValue(change.getAddedSubList().get(index));
                continue;
            }

            boolean wasSelectedJobRemoved = change.getRemoved().stream()
                    .anyMatch(removedJob -> selectedJob.getValue().isSameJob(removedJob));
            if (wasSelectedJobChanged) {
                // Select the job that came before it in the list,
                // or clear the selection if there is no such job.
                selectedJob.setValue(change.getFrom() > 0 ? change.getList().get(change.getFrom() - 1) : null);
            }
        }
    }

    @Override
    public void generateInterviews() {
        versionedAddressBook.generateInterviews();
    }

    @Override
    public Interviews getInterviews() {
        return versionedAddressBook.getInterviews();
    }

    @Override
    public void setMaxInterviewsADay(int maxInterviewsADay) {
        versionedAddressBook.setMaxInterviewsADay(maxInterviewsADay);
    }

    @Override
    public void clearInterviews() {
        versionedAddressBook.clearInterviews();
    }

    /**
     * Obtains current viewed list and generate analytics based on it
     * */
    public Analytics generateAnalytics() {
        Analytics analytics = new Analytics(getFilteredPersonList());
        return analytics;
    }

    @Override
    public void setBlockOutDates(List<Calendar> blockOutDates) {
        versionedAddressBook.setBlockOutDates(blockOutDates);
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
        return versionedAddressBook.equals(other.versionedAddressBook)
                && userPrefs.equals(other.userPrefs)
                && originalFilteredPersons.equals(other.originalFilteredPersons)
                && Objects.equals(selectedPerson.get(), other.selectedPerson.get());
    }

}
