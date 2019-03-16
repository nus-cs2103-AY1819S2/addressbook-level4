package seedu.address.model;

import java.nio.file.Path;
import java.util.function.Predicate;

import seedu.address.commons.core.GuiSettings;
import seedu.address.model.booking.Booking;
import seedu.address.model.customer.Customer;

/**
 * The API of the Model component.
 */
public interface Model {
    /**
     * {@code Predicate} that always evaluate to true
     */
    Predicate<Customer> PREDICATE_SHOW_ALL_CUSTOMERS = unused -> true;
    Predicate<Booking> PREDICATE_SHOW_ALL_BOOKINGS = unused -> true;

    /**
     * Returns the user prefs.
     */
    ReadOnlyUserPrefs getUserPrefs();

    /**
     * Replaces user prefs data with the data in {@code userPrefs}.
     */
    void setUserPrefs(ReadOnlyUserPrefs userPrefs);

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
    Path getHotelManagementSystemFilePath();

    /**
     * Sets the user prefs' address book file path.
     */
    void setHotelManagementSystemFilePath(Path hotelManagementSystemFilePath);

    /**
     * Returns the HotelManagementSystem
     */
    ReadOnlyHotelManagementSystem getHotelManagementSystem();

    /**
     * Replaces address book data with the data in {@code hotelManagementSystem}.
     */
    void setHotelManagementSystem(ReadOnlyHotelManagementSystem hotelManagementSystem);

    /**
     * Returns true if the model has previous address book states to restore.
     */
    boolean canUndoHotelManagementSystem();

    /**
     * Returns true if the model has undone address book states to restore.
     */
    boolean canRedoHotelManagementSystem();

    /**
     * Restores the model's address book to its previous state.
     */
    void undoHotelManagementSystem();

    /**
     * Restores the model's address book to its previously undone state.
     */
    void redoHotelManagementSystem();

    /**
     * Saves the current address book state for undo/redo.
     */
    void commitHotelManagementSystem();
}
