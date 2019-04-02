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
import seedu.hms.model.reservation.Reservation;
import seedu.hms.model.reservation.RoomType;
import seedu.hms.model.reservation.exceptions.ReservationNotFoundException;
import seedu.hms.model.reservation.exceptions.RoomTypeNotFoundException;

/**
 * Represents the in-memory model of the hms book data.
 */
public class ReservationManager implements ReservationModel {
    private static final Logger logger = LogsCenter.getLogger(ReservationManager.class);

    private final VersionedHotelManagementSystem versionedHotelManagementSystem;
    private final UserPrefs userPrefs;
    private final FilteredList<Reservation> filteredReservations;
    private final FilteredList<RoomType> roomTypeList;
    private final SimpleObjectProperty<Reservation> selectedReservation = new SimpleObjectProperty<>();
    private final SimpleObjectProperty<RoomType> selectedRoomType = new SimpleObjectProperty<>();

    /**
     * Initializes a ModelManager with the given hotelManagementSystem and userPrefs.
     */
    public ReservationManager(VersionedHotelManagementSystem hotelManagementSystem, ReadOnlyUserPrefs userPrefs) {
        super();
        requireAllNonNull(hotelManagementSystem, userPrefs);

        logger.fine("Initializing with hms book: " + hotelManagementSystem + " and user prefs " + userPrefs);

        versionedHotelManagementSystem = hotelManagementSystem;
        this.userPrefs = new UserPrefs(userPrefs);
        filteredReservations = new FilteredList<>(versionedHotelManagementSystem.getReservationList());
        roomTypeList = new FilteredList<>(versionedHotelManagementSystem.getRoomTypeList());
        filteredReservations.addListener(this::ensureSelectedReservationIsValid);
    }

    public ReservationManager() {
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

    public void deleteReservation(int reservationIndex) {
        versionedHotelManagementSystem.removeReservation(reservationIndex);
    }

    /*
     * Adds a reservation
     */
    public void addReservation(Reservation reservation) {
        versionedHotelManagementSystem.addReservation(reservation);
    }

    public void setReservation(int reservationIndex, Reservation editedReservation) {
        requireNonNull(editedReservation);

        versionedHotelManagementSystem.setReservation(reservationIndex, editedReservation);
    }

    @Override
    public void setClearReservation(ReadOnlyHotelManagementSystem hotelManagementSystem) {
        versionedHotelManagementSystem.resetDataReservation(hotelManagementSystem);
    }

    //=========== Filtered Reservation List Accessors =============================================================

    /**
     * Returns an unmodifiable view of the list of {@code Reservation} backed by the internal list of
     * {@code versionedHotelManagementSystem}
     */
    public ObservableList<Reservation> getFilteredReservationList() {
        return filteredReservations;
    }

    /**
     * Returns an unmodifiable view of the list of {@code RoomType} backed by the internal list of
     * {@code versionedHotelManagementSystem}
     */
    public ObservableList<RoomType> getRoomTypeList() {
        return roomTypeList;
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

    //=========== Selected Reservation ===========================================================================

    public ReadOnlyProperty<Reservation> selectedReservationProperty() {
        return selectedReservation;
    }

    public Reservation getSelectedReservation() {
        return selectedReservation.getValue();
    }

    public void setSelectedReservation(Reservation reservation) {
        if (reservation != null && !filteredReservations.contains(reservation)) {
            throw new ReservationNotFoundException();
        }
        selectedReservation.setValue(reservation);
    }

    /**
     * Ensures {@code selectedReservation} is a valid Reservation in {@code filteredReservations}.
     */
    private void ensureSelectedReservationIsValid(ListChangeListener.Change<? extends Reservation> change) {
        while (change.next()) {
            if (selectedReservation.getValue() == null) {
                // null is always a valid selected Reservation, so we do not need to check that it is valid anymore.
                return;
            }

            boolean wasSelectedReservationReplaced = change.wasReplaced()
                && change.getAddedSize() == change.getRemovedSize()
                && change.getRemoved().contains(selectedReservation.getValue());
            if (wasSelectedReservationReplaced) {
                // Update selectedReservation to its new value.
                int index = change.getRemoved().indexOf(selectedReservation.getValue());
                selectedReservation.setValue(change.getAddedSubList().get(index));
                continue;
            }

            boolean wasSelectedReservationRemoved = change.getRemoved().stream()
                .anyMatch(removedReservation -> selectedReservation.getValue().equals(removedReservation));
            if (wasSelectedReservationRemoved) {
                // Select the Reservation that came before it in the list,
                // or clear the selection if there is no such Reservation.
                selectedReservation.setValue(change.getFrom() > 0 ? change.getList().get(change.getFrom() - 1) : null);
            }
        }
    }

    //=========== Selected ServiceType ===========================================================================

    public ReadOnlyProperty<RoomType> selectedRoomTypeProperty() {
        return selectedRoomType;
    }

    @Override
    public RoomType getSelectedRoomType() {
        return selectedRoomType.getValue();
    }

    @Override
    public void setSelectedRoomType(RoomType roomType) {
        if (roomType != null && !roomTypeList.contains(roomType)) {
            throw new RoomTypeNotFoundException();
        }
        selectedRoomType.setValue(roomType);
    }

    @Override
    public boolean equals(Object obj) {
        // short circuit if same object
        if (obj == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(obj instanceof ReservationManager)) {
            return false;
        }

        // state check
        ReservationManager other = (ReservationManager) obj;
        return versionedHotelManagementSystem.equals(other.versionedHotelManagementSystem)
            && userPrefs.equals(other.userPrefs)
            && filteredReservations.equals(other.filteredReservations)
            && Objects.equals(selectedReservation.get(), other.selectedReservation.get());
    }

    /**
     * Updates the filter of the filtered person list to filter by the given {@code predicate}.
     *
     * @throws NullPointerException if {@code predicate} is null.
     */
    public void updateFilteredReservationList(Predicate<Reservation> predicate) {
        requireNonNull(predicate);
        filteredReservations.setPredicate(predicate);
    }
}
