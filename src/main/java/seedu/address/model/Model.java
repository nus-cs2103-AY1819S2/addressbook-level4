package seedu.address.model;

import java.nio.file.Path;
import java.util.Comparator;
import java.util.function.Predicate;

import javafx.beans.property.ReadOnlyProperty;
import javafx.collections.ObservableList;
import seedu.address.commons.core.GuiSettings;
import seedu.address.model.person.Pdf;

/**
 * The API of the Model component.
 */
public interface Model {
    /** {@code Predicate} that always evaluate to true */
    Predicate<Pdf> PREDICATE_SHOW_ALL_PDFS = unused -> true;

    /** {@code Comparator} that compares two PDFs alphabetically based on name */
    Comparator<Pdf> COMPARATOR_ASCENDING_NAME_PDFS = Comparator.comparing(Pdf::getName);
    Comparator<Pdf> COMPARATOR_DESCENDING_NAME_PDFS = COMPARATOR_ASCENDING_NAME_PDFS.reversed();

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
     * Returns true if a pdf with the same identity as {@code pdf} exists in the address book.
     */
    boolean hasPerson(Pdf pdf);

    /**
     * Deletes the given pdf.
     * The pdf must exist in the address book.
     */
    void deletePerson(Pdf target);

    /**
     * Adds the given pdf.
     * {@code pdf} must not already exist in the address book.
     */
    void addPerson(Pdf pdf);

    /**
     * Replaces the given pdf {@code target} with {@code editedPdf}.
     * {@code target} must exist in the address book.
     * The pdf identity of {@code editedPdf} must not be the same as another existing pdf in the address book.
     */
    void setPerson(Pdf target, Pdf editedPdf);

    /** Returns an unmodifiable view of the filtered pdf list */
    ObservableList<Pdf> getFilteredPersonList();

    /**
     * Updates the filter of the filtered pdf list to filter by the given {@code predicate}.
     * @throws NullPointerException if {@code predicate} is null.
     */
    void updateFilteredPersonList(Predicate<Pdf> predicate);

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
     * Selected pdf in the filtered pdf list.
     * null if no pdf is selected.
     */
    ReadOnlyProperty<Pdf> selectedPersonProperty();

    /**
     * Returns the selected pdf in the filtered pdf list.
     * null if no pdf is selected.
     */
    Pdf getSelectedPerson();

    /**
     * Sets the selected pdf in the filtered pdf list.
     */
    void setSelectedPerson(Pdf pdf);
}
