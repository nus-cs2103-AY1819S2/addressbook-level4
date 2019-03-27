package seedu.address.model;

import java.nio.file.Path;
import java.util.Comparator;
import java.util.function.Predicate;

import javafx.beans.property.ReadOnlyProperty;
import javafx.collections.ObservableList;
import seedu.address.commons.core.GuiSettings;
import seedu.address.model.course.Course;
import seedu.address.model.moduleinfo.ModuleInfo;
import seedu.address.model.person.ModuleTaken;
import seedu.address.model.person.Semester;

/**
 * The API of the Model component.
 */
public interface Model {
    /** {@code Predicate} that always evaluate to true */
    Predicate<ModuleTaken> PREDICATE_SHOW_ALL_PERSONS = unused -> true;

    /**
     * Replaces course data with the data in {@code course}.
     */
    void setCourse(Course course);

    /**
     * Returns the course data.
     */
    Course getCourse();

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

    /** Returns the AddressBook */
    ReadOnlyAddressBook getAddressBook();

    /**
     * Returns true if a moduleTaken with the same identity as {@code moduleTaken} exists in the address book.
     */
    boolean hasPerson(ModuleTaken moduleTaken);

    /**
     * Deletes the given moduleTaken.
     * The moduleTaken must exist in the address book.
     */
    void deletePerson(ModuleTaken target);

    /**
     * Adds the given moduleTaken.
     * {@code moduleTaken} must not already exist in the address book.
     */
    void addPerson(ModuleTaken moduleTaken);

    /**
     * Replaces the given moduleTaken {@code target} with {@code editedModuleTaken}.
     * {@code target} must exist in the address book.
     * The moduleTaken identity of {@code editedModuleTaken} must not be the same as another
     * existing moduleTaken in the address book.
     */
    void setPerson(ModuleTaken target, ModuleTaken editedModuleTaken);

    /**
     * Replaces the semester limit at the given index with {@code editedSemLimit}.
     */
    void setSemesterLimit(int index, SemLimit editedSemLimit);

    /**
     * Replaces the current semester with the given semester.
     */
    void setCurrentSemester(Semester semester);

    /** Returns an unmodifiable view of the filtered moduleTaken list */
    ObservableList<ModuleTaken> getFilteredPersonList();

    /** Returns an unmodifiable view of the SemLimit list */
    ObservableList<SemLimit> getSemLimitList();

    /**
     * Updates the filter of the filtered moduleTaken list to filter by the given {@code predicate}.
     * @throws NullPointerException if {@code predicate} is null.
     */
    void updateFilteredPersonList(Predicate<ModuleTaken> predicate);

    /**
     * Returns the generated html string that indicates if the CAP and workload limits
     * set by the user for every semester have been violated based the modules taken in their plan.
     */
    String checkLimit();

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
     * Selected moduleTaken in the filtered moduleTaken list.
     * null if no moduleTaken is selected.
     */
    ReadOnlyProperty<ModuleTaken> selectedPersonProperty();

    /**
     * Returns the selected moduleTaken in the filtered moduleTaken list.
     * null if no moduleTaken is selected.
     */
    ModuleTaken getSelectedPerson();

    /**
     * Sets the selected moduleTaken in the filtered moduleTaken list.
     */
    void setSelectedPerson(ModuleTaken moduleTaken);

    /**
     * Returns an Observable list of all module information from storage
     * @return Observable list of ModuleInfo based on predicate set
     */
    ObservableList<ModuleInfo> getDisplayList();

    /**
     * Updates the filtered list based on the predicate provided by user input
     * @param predicate
     */
    void updateDisplayList(Predicate<ModuleInfo> predicate);

    ObservableList<ModuleInfo> getSortedDisplayList();

    void sortDisplayList(Comparator<ModuleInfo> comparator);

    RecModulePredicate getRecModulePredicate();

    RecModuleComparator getRecModuleComparator();
}
