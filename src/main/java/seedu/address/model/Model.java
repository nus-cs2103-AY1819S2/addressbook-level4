package seedu.address.model;

import java.nio.file.Path;
import java.util.function.Predicate;

import javafx.beans.property.ReadOnlyProperty;
import javafx.collections.ObservableList;
import seedu.address.commons.core.GuiSettings;
import seedu.address.model.person.Person;

/**
 * The API of the Model component.
 */
public interface Model {
    /** {@code Predicate} that always evaluate to true */
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
     * Returns the user prefs' card folder file path.
     */
    Path getCardFolderFilePath();

    /**
     * Sets the user prefs' card folder file path.
     */
    void setCardFolderFilePath(Path cardFolderFilePath);

    /**
     * Replaces card folder data with the data in {@code cardFolder}.
     */
    void setCardFolder(ReadOnlyCardFolder cardFolder);

    /** Returns the CardFolder */
    ReadOnlyCardFolder getCardFolder();

    /**
     * Returns true if a person with the same identity as {@code person} exists in the card folder.
     */
    boolean hasPerson(Person person);

    /**
     * Deletes the given person.
     * The person must exist in the card folder.
     */
    void deletePerson(Person target);

    /**
     * Adds the given person.
     * {@code person} must not already exist in the card folder.
     */
    void addPerson(Person person);

    /**
     * Replaces the given person {@code target} with {@code editedPerson}.
     * {@code target} must exist in the card folder.
     * The person identity of {@code editedPerson} must not be the same as another existing person in the card folder.
     */
    void setPerson(Person target, Person editedPerson);

    /** Returns an unmodifiable view of the filtered person list */
    ObservableList<Person> getFilteredPersonList();

    /**
     * Updates the filter of the filtered person list to filter by the given {@code predicate}.
     * @throws NullPointerException if {@code predicate} is null.
     */
    void updateFilteredPersonList(Predicate<Person> predicate);

    /**
     * Returns true if the model has previous card folder states to restore.
     */
    boolean canUndoCardFolder();

    /**
     * Returns true if the model has undone card folder states to restore.
     */
    boolean canRedoCardFolder();

    /**
     * Restores the model's card folder to its previous state.
     */
    void undoCardFolder();

    /**
     * Restores the model's card folder to its previously undone state.
     */
    void redoCardFolder();

    /**
     * Saves the current card folder state for undo/redo.
     */
    void commitCardFolder();

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
}
