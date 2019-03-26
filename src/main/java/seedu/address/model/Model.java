package seedu.address.model;

import java.nio.file.Path;
import java.util.HashMap;
import java.util.function.Predicate;

import javafx.beans.property.ReadOnlyProperty;
import javafx.collections.ObservableList;
import seedu.address.commons.core.GuiSettings;
import seedu.address.model.course.Course;
import seedu.address.model.course.CourseReqType;
import seedu.address.model.moduleinfo.ModuleInfo;
import seedu.address.model.moduleinfo.ModuleInfoCode;
import seedu.address.model.moduletaken.ModuleTaken;
import seedu.address.model.moduletaken.Semester;

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
     * Returns the user's current semester.
     */
    Semester getCurrentSemester();

    /**
     * Returns the user's cap up to the current semester.
     */
    Cap computeCumulativeCap();

    /**
     * Returns the user's expected minimum cap based on user's prediction of future results.
     */
    Cap computeExpectedMinimumCap();

    /**
     * Returns the user's expected maximum cap based on user's prediction of future results.
     */
    Cap computeExpectedMaximumCap();

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
    void setAddressBook(ReadOnlyGradTrak addressBook);

    /** Returns the GradTrak */
    ReadOnlyGradTrak getAddressBook();

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

    /** Returns an unmodifiable view of the filtered moduleTaken list */
    ObservableList<ModuleTaken> getFilteredPersonList();

    /**
     * Updates the filter of the filtered moduleTaken list to filter by the given {@code predicate}.
     * @throws NullPointerException if {@code predicate} is null.
     */
    void updateFilteredPersonList(Predicate<ModuleTaken> predicate);

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

    /**
     * Returns an unmodifiable view of the recommended module list.
     */
    ObservableList<ModuleInfoCode> getRecModuleListSorted();

    /**
     * Updates the recommended module list by creating a new RecModuleManager,
     * and returns a HashMap of ModuleInfoCode to CourseReqType.
     */
    HashMap<ModuleInfoCode, CourseReqType> updateRecModuleList();
}
