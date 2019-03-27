package seedu.address.model;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.nio.file.Path;
import java.util.Comparator;
import java.util.Objects;
import java.util.function.Predicate;
import java.util.logging.Logger;

import javafx.beans.property.ReadOnlyProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import seedu.address.commons.core.GuiSettings;
import seedu.address.commons.core.LogsCenter;
import seedu.address.model.course.Course;
import seedu.address.model.moduleinfo.ModuleInfo;
import seedu.address.model.moduleinfo.ModuleInfoList;
import seedu.address.model.person.ModuleTaken;
import seedu.address.model.person.Semester;
import seedu.address.model.person.exceptions.PersonNotFoundException;

/**
 * Represents the in-memory model of the address book data.
 */
public class ModelManager implements Model {
    private static final Logger logger = LogsCenter.getLogger(ModelManager.class);

    private Course course;

    private final VersionedAddressBook versionedAddressBook;
    private final UserPrefs userPrefs;
    private final FilteredList<ModuleTaken> filteredModuleTakens;
    private final FilteredList<SemLimit> semesterLimitList;
    private final SimpleObjectProperty<ModuleTaken> selectedPerson = new SimpleObjectProperty<>();

    //Model Information List for Model Manager to have Module Info List and list to be printed for displaymod
    private final ObservableList<ModuleInfo> allModules;
    private final FilteredList<ModuleInfo> displayList;
    private final ModuleInfoList moduleInfoList;
    private final SortedList<ModuleInfo> sortedDisplayList;

    /**
     * Initializes a ModelManager with the given addressBook and userPrefs.
     */
    public ModelManager(ReadOnlyAddressBook addressBook, ReadOnlyUserPrefs userPrefs, ModuleInfoList allModules) {
        super();
        requireAllNonNull(addressBook, userPrefs);

        logger.fine("Initializing with address book: " + addressBook + " and user prefs " + userPrefs);

        versionedAddressBook = new VersionedAddressBook(addressBook);
        this.userPrefs = new UserPrefs(userPrefs);
        filteredModuleTakens = new FilteredList<>(versionedAddressBook.getPersonList());
        filteredModuleTakens.addListener(this::ensureSelectedPersonIsValid);
        semesterLimitList = new FilteredList<>(versionedAddressBook.getSemesterLimitList());

        //Get an non Modifiable List of all modules and use a filtered list based on that to search for modules
        this.allModules = allModules.getObservableList();
        this.displayList = new FilteredList<>(this.allModules);
        this.moduleInfoList = allModules;
        this.sortedDisplayList = new SortedList<>(displayList);
    }

    public ModelManager() {
        this(new AddressBook(), new UserPrefs(), new ModuleInfoList());
    }

    //=========== UserPrefs ==================================================================================

    @Override
    public void setCourse(Course course) {
        requireNonNull(course);
        this.course = course;
    }

    @Override
    public Course getCourse() {
        return course;
    }

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
    public boolean hasPerson(ModuleTaken moduleTaken) {
        requireNonNull(moduleTaken);
        return versionedAddressBook.hasPerson(moduleTaken);
    }

    @Override
    public void deletePerson(ModuleTaken target) {
        versionedAddressBook.removePerson(target);
    }

    @Override
    public void addPerson(ModuleTaken moduleTaken) {
        versionedAddressBook.addPerson(moduleTaken);
        updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
    }

    @Override
    public void setPerson(ModuleTaken target, ModuleTaken editedModuleTaken) {
        requireAllNonNull(target, editedModuleTaken);
        versionedAddressBook.setPerson(target, editedModuleTaken);
    }

    @Override
    public void setSemesterLimit(int index, SemLimit editedSemLimit) {
        requireAllNonNull(index, editedSemLimit);
        versionedAddressBook.setSemesterLimit(index, editedSemLimit);
    }

    @Override
    public void setCurrentSemester(Semester semester) {
        requireAllNonNull(semester);
        versionedAddressBook.setCurrentSemester(semester);
    }

    //=========== Filtered ModuleTaken List Accessors =============================================================

    /**
     * Returns an unmodifiable view of the list of {@code ModuleTaken} backed by the internal list of
     * {@code versionedAddressBook}
     */
    @Override
    public ObservableList<ModuleTaken> getFilteredPersonList() {
        return filteredModuleTakens;
    }

    /**
     * Returns an unmodifiable view of the list of {@code SemLimit} backed by the internal list of
     * {@code versionedAddressBook}
     */
    @Override
    public ObservableList<SemLimit> getSemLimitList() {
        return semesterLimitList;
    }

    @Override
    public void updateFilteredPersonList(Predicate<ModuleTaken> predicate) {
        requireNonNull(predicate);
        filteredModuleTakens.setPredicate(predicate);
    }

    /**
     * Returns a generated html string that shows where their CAP and workload limits are violated.
     */
    @Override
    public String checkLimit() {
        //TODO
        //get current sem
        //get all modules taken
        //get all semester limits
        //cumulatively add the min max lec tut lab proj prep doubles for all 10 semesters and their counts.
        //divide each double by count for all semesters.
        //calculate current cap before current sem
        //calculate min and max final cap based on all semesters
        //generate table in html
        return "";
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

    //=========== Selected moduleTaken ===========================================================================

    @Override
    public ReadOnlyProperty<ModuleTaken> selectedPersonProperty() {
        return selectedPerson;
    }

    @Override
    public ModuleTaken getSelectedPerson() {
        return selectedPerson.getValue();
    }

    @Override
    public void setSelectedPerson(ModuleTaken moduleTaken) {
        if (moduleTaken != null && !filteredModuleTakens.contains(moduleTaken)) {
            throw new PersonNotFoundException();
        }
        selectedPerson.setValue(moduleTaken);
    }

    //=========== Module Info List ===========================================================================
    @Override
    public ObservableList<ModuleInfo> getDisplayList() {
        return this.displayList;
    }

    @Override
    public void updateDisplayList(Predicate<ModuleInfo> predicate) {
        requireNonNull(predicate);
        displayList.setPredicate(predicate);
    }

    //=========== Module recommendation ===========================================================================
    @Override
    public ObservableList<ModuleInfo> getSortedDisplayList() {
        return sortedDisplayList;
    }

    @Override
    public void sortDisplayList(Comparator<ModuleInfo> comparator) {
        requireNonNull(comparator);
        sortedDisplayList.setComparator(comparator);
    }

    @Override
    public RecModulePredicate getRecModulePredicate() {
        return new RecModulePredicate(course, versionedAddressBook);
    }

    @Override
    public RecModuleComparator getRecModuleComparator() {
        return new RecModuleComparator(course);
    }

    /**
     * Ensures {@code selectedPerson} is a valid moduleTaken in {@code filteredModuleTakens}.
     */
    private void ensureSelectedPersonIsValid(ListChangeListener.Change<? extends ModuleTaken> change) {
        while (change.next()) {
            if (selectedPerson.getValue() == null) {
                // null is always a valid selected moduleTaken, so we do not need to check that it is valid anymore.
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
                // Select the moduleTaken that came before it in the list,
                // or clear the selection if there is no such moduleTaken.
                selectedPerson.setValue(change.getFrom() > 0 ? change.getList().get(change.getFrom() - 1) : null);
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
        return versionedAddressBook.equals(other.versionedAddressBook)
                && userPrefs.equals(other.userPrefs)
                && filteredModuleTakens.equals(other.filteredModuleTakens)
                && Objects.equals(selectedPerson.get(), other.selectedPerson.get());
    }

}
