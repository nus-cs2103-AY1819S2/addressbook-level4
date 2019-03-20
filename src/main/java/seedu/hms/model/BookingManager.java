package seedu.hms.model;

import static java.util.Objects.requireNonNull;
import static seedu.hms.commons.util.CollectionUtil.requireAllNonNull;

import java.nio.file.Path;
import java.util.Objects;
import java.util.function.Predicate;
import java.util.logging.Logger;

import javafx.beans.property.ReadOnlyProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import seedu.hms.commons.core.GuiSettings;
import seedu.hms.commons.core.LogsCenter;
import seedu.hms.model.booking.Booking;
import seedu.hms.model.booking.ServiceType;
import seedu.hms.model.booking.exceptions.BookingNotFoundException;

/**
 * Represents the in-memory model of the hms book data.
 */
public class BookingManager implements BookingModel {
    private static final Logger logger = LogsCenter.getLogger(BookingManager.class);

    private final VersionedHotelManagementSystem versionedHotelManagementSystem;
    private final UserPrefs userPrefs;
    private final FilteredList<Booking> filteredBookings;
    private final SimpleObjectProperty<Booking> selectedBooking = new SimpleObjectProperty<>();
    private final SimpleObjectProperty<ServiceType> selectedServiceType = new SimpleObjectProperty<>();

    /**
     * Initializes a ModelManager with the given hotelManagementSystem and userPrefs.
     */
    public BookingManager(VersionedHotelManagementSystem hotelManagementSystem, ReadOnlyUserPrefs userPrefs) {
        super();
        requireAllNonNull(hotelManagementSystem, userPrefs);

        logger.fine("Initializing with hms book: " + hotelManagementSystem + " and user prefs " + userPrefs);

        versionedHotelManagementSystem = hotelManagementSystem;
        this.userPrefs = new UserPrefs(userPrefs);
        filteredBookings = new FilteredList<>(versionedHotelManagementSystem.getBookingList());
        filteredBookings.addListener(this::ensureSelectedBookingIsValid);
    }

    public BookingManager() {
        this(new VersionedHotelManagementSystem(new HotelManagementSystem()), new UserPrefs());
    }

    //=========== UserPrefs ==================================================================================

    @Override
    public ReadOnlyUserPrefs getUserPrefs() {
        return userPrefs;
    }

    @Override
    public void setUserPrefs(ReadOnlyUserPrefs userPrefs) {
        requireNonNull(userPrefs);
        this.userPrefs.resetData(userPrefs);
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
    public Path getHotelManagementSystemFilePath() {
        return userPrefs.getHotelManagementSystemFilePath();
    }

    @Override
    public void setHotelManagementSystemFilePath(Path hotelManagementSystemFilePath) {
        requireNonNull(hotelManagementSystemFilePath);
        userPrefs.setHotelManagementSystemFilePath(hotelManagementSystemFilePath);
    }

    //=========== HotelManagementSystem ================================================================================

    @Override
    public ReadOnlyHotelManagementSystem getHotelManagementSystem() {
        return versionedHotelManagementSystem;
    }

    @Override
    public void setHotelManagementSystem(ReadOnlyHotelManagementSystem hotelManagementSystem) {
        versionedHotelManagementSystem.resetData(hotelManagementSystem);
    }

    public void deleteBooking(int bookingIndex) {
        versionedHotelManagementSystem.removeBooking(bookingIndex);
    }

    /*
     * Adds a booking
     */
    public void addBooking(Booking booking) {
        versionedHotelManagementSystem.addBooking(booking);
    }

    public void setBooking(int bookingIndex, Booking editedBooking) {
        requireNonNull(editedBooking);

        versionedHotelManagementSystem.setBooking(bookingIndex, editedBooking);
    }

    //=========== Filtered Booking List Accessors =============================================================

    /**
     * Returns an unmodifiable view of the list of {@code Booking} backed by the internal list of
     * {@code versionedHotelManagementSystem}
     */
    public ObservableList<Booking> getFilteredBookingList() {
        return filteredBookings;
    }

    //=========== Undo/Redo =================================================================================

    @Override
    public boolean canUndoHotelManagementSystem() {
        return versionedHotelManagementSystem.canUndo();
    }

    @Override
    public boolean canRedoHotelManagementSystem() {
        return versionedHotelManagementSystem.canRedo();
    }

    @Override
    public void undoHotelManagementSystem() {
        versionedHotelManagementSystem.undo();
    }

    @Override
    public void redoHotelManagementSystem() {
        versionedHotelManagementSystem.redo();
    }

    @Override
    public void commitHotelManagementSystem() {
        versionedHotelManagementSystem.commit();
    }

    //=========== Selected Booking ===========================================================================

    public ReadOnlyProperty<Booking> selectedBookingProperty() {
        return selectedBooking;
    }

    public Booking getSelectedBooking() {
        return selectedBooking.getValue();
    }

    public void setSelectedBooking(Booking booking) {
        if (booking != null && !filteredBookings.contains(booking)) {
            throw new BookingNotFoundException();
        }
        selectedBooking.setValue(booking);
    }

    /**
     * Ensures {@code selectedBooking} is a valid Booking in {@code filteredBookings}.
     */
    private void ensureSelectedBookingIsValid(ListChangeListener.Change<? extends Booking> change) {
        while (change.next()) {
            if (selectedBooking.getValue() == null) {
                // null is always a valid selected Booking, so we do not need to check that it is valid anymore.
                return;
            }

            boolean wasSelectedBookingReplaced = change.wasReplaced()
                && change.getAddedSize() == change.getRemovedSize()
                && change.getRemoved().contains(selectedBooking.getValue());
            if (wasSelectedBookingReplaced) {
                // Update selectedBooking to its new value.
                int index = change.getRemoved().indexOf(selectedBooking.getValue());
                selectedBooking.setValue(change.getAddedSubList().get(index));
                continue;
            }

            boolean wasSelectedBookingRemoved = change.getRemoved().stream()
                .anyMatch(removedBooking -> selectedBooking.getValue().equals(removedBooking));
            if (wasSelectedBookingRemoved) {
                // Select the Booking that came before it in the list,
                // or clear the selection if there is no such Booking.
                selectedBooking.setValue(change.getFrom() > 0 ? change.getList().get(change.getFrom() - 1) : null);
            }
        }
    }

    //=========== Selected ServiceType ===========================================================================

    public ReadOnlyProperty<ServiceType> selectedServiceTypeProperty() {
        return selectedServiceTypeProperty();
    }

    @Override
    public ServiceType getSelectedServiceType() {
        return selectedBooking.getValue();
    }

    @Override
    public void setSelectedServiceType(ServiceType serviceType) {
        if (serviceType != null && !filteredBookings.contains(serviceType)) {
            throw new BookingNotFoundException();
        }
        selectedServiceType.setValue(serviceType);
    }

    @Override
    public boolean equals(Object obj) {
        // short circuit if same object
        if (obj == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(obj instanceof BookingManager)) {
            return false;
        }

        // state check
        BookingManager other = (BookingManager) obj;
        return versionedHotelManagementSystem.equals(other.versionedHotelManagementSystem)
            && userPrefs.equals(other.userPrefs)
            && filteredBookings.equals(other.filteredBookings)
            && Objects.equals(selectedBooking.get(), other.selectedBooking.get());
    }

    /**
     * Updates the filter of the filtered person list to filter by the given {@code predicate}.
     *
     * @throws NullPointerException if {@code predicate} is null.
     */
    public void updateFilteredBookingList(Predicate<Booking> predicate) {
        requireNonNull(predicate);
        filteredBookings.setPredicate(predicate);
    }
}
