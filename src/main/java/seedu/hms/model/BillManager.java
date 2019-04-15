package seedu.hms.model;

import static java.util.Objects.requireNonNull;
import static seedu.hms.commons.util.CollectionUtil.requireAllNonNull;

import java.nio.file.Path;
import java.util.HashMap;
import java.util.Objects;
import java.util.function.Predicate;
import java.util.logging.Logger;

import javafx.beans.property.ReadOnlyObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.util.Pair;
import seedu.hms.commons.core.GuiSettings;
import seedu.hms.commons.core.LogsCenter;
import seedu.hms.model.bill.Bill;
import seedu.hms.model.booking.Booking;
import seedu.hms.model.booking.serviceType.ServiceType;
import seedu.hms.model.reservation.Reservation;
import seedu.hms.model.reservation.roomType.RoomType;
import seedu.hms.model.util.DateRange;
import seedu.hms.model.util.TimeRange;

/**
 * Represents the in-memory model of the hms book data.
 */
public class BillManager implements BillModel {
    private static final Logger logger = LogsCenter.getLogger(BillManager.class);

    private final VersionedHotelManagementSystem versionedHotelManagementSystem;
    private final UserPrefs userPrefs;
    private final FilteredList<Booking> filteredBookings;
    private final FilteredList<ServiceType> serviceTypeList;
    private final SimpleObjectProperty<Booking> selectedBooking = new SimpleObjectProperty<>();
    private final FilteredList<Reservation> filteredReservations;
    private final FilteredList<RoomType> roomTypeList;
    private final SimpleObjectProperty<Reservation> selectedReservation = new SimpleObjectProperty<>();
    private final SimpleObjectProperty<Bill> updatedBill = new SimpleObjectProperty<>();

    /**
     * Initializes a ModelManager with the given hotelManagementSystem and userPrefs.
     */
    public BillManager(VersionedHotelManagementSystem hotelManagementSystem, ReadOnlyUserPrefs userPrefs) {
        super();
        requireAllNonNull(hotelManagementSystem, userPrefs);

        logger.fine("Initializing with hms book: " + hotelManagementSystem + " and user prefs " + userPrefs);

        versionedHotelManagementSystem = hotelManagementSystem;
        this.userPrefs = new UserPrefs(userPrefs);
        filteredBookings = new FilteredList<>(versionedHotelManagementSystem.getBookingList());
        serviceTypeList = new FilteredList<ServiceType>(versionedHotelManagementSystem.getServiceTypeList());
        filteredBookings.addListener(this::ensureSelectedBookingIsValid);
        filteredReservations = new FilteredList<>(versionedHotelManagementSystem.getReservationList());
        roomTypeList = new FilteredList<RoomType>(versionedHotelManagementSystem.getRoomTypeList());
        filteredReservations.addListener(this::ensureSelectedReservationIsValid);
    }

    public BillManager() {
        this(new VersionedHotelManagementSystem(new HotelManagementSystem()), new UserPrefs());
    }

    public void updateBill(Bill bill) {
        updatedBill.setValue(bill);
    }

    public ReadOnlyObjectProperty<Bill> getBill() {
        return updatedBill;
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

    /**
     * Returns an unmodifiable view of the list of {@code Booking} backed by the internal list of
     * {@code versionedHotelManagementSystem}
     */
    public ObservableList<Booking> getFilteredBookingList() {
        return filteredBookings;
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

    //=========== Filtered Reservation List Accessors =============================================================

    /**
     * Returns an unmodifiable view of the list of {@code Reservation} backed by the internal list of
     * {@code versionedHotelManagementSystem}
     */
    public ObservableList<Reservation> getFilteredReservationList() {
        return filteredReservations;
    }

    /**
     * Returns an unmodifiable view of the list of {@code Reservation} backed by the internal list of
     * {@code versionedHotelManagementSystem}
     */
    public ObservableList<RoomType> getFilteredRoomTypeList() {
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

    //=========== Selected Booking ===========================================================================

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

    //=========== Selected Reservation ===========================================================================


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


    @Override
    public boolean equals(Object obj) {
        // short circuit if same object
        if (obj == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(obj instanceof BillManager)) {
            return false;
        }

        // state check
        BillManager other = (BillManager) obj;
        return versionedHotelManagementSystem.equals(other.versionedHotelManagementSystem)
            && userPrefs.equals(other.userPrefs)
            && filteredBookings.equals(other.filteredBookings)
            && Objects.equals(selectedBooking.get(), other.selectedBooking.get())
            && filteredReservations.equals(other.filteredReservations)
            && Objects.equals(selectedReservation.get(), other.selectedReservation.get())
            && Objects.equals(updatedBill.get(), other.updatedBill.get());
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

    /**
     * Updates the filter of the filtered person list to filter by the given {@code predicate}.
     *
     * @throws NullPointerException if {@code predicate} is null.
     */
    public void updateFilteredReservationList(Predicate<Reservation> predicate) {
        requireNonNull(predicate);
        filteredReservations.setPredicate(predicate);
    }

    /**
     * Generates bill amount for all bookings for the specific customer
     *
     * @param bookingObservableList unmodifiable booking list
     * @return total amount the customer has to pay for all bookings
     */
    public double generateBillForBooking(ObservableList<Booking> bookingObservableList) {
        double totalAmount = 0.0;
        for (Booking booking : bookingObservableList) {
            ServiceType serviceType = booking.getService();
            TimeRange timeRange = booking.getTiming();
            int hoursBooked = timeRange.numOfHours();
            double ratePerHour = serviceType.getRatePerHour();
            double amount = hoursBooked * ratePerHour;
            totalAmount = totalAmount + amount;
        }
        return totalAmount;
    }

    /**
     * Generates bill for all bookings for the specific customer
     *
     * @param bookingObservableList unmodifiable booking list
     * @return hash map that returns the entire booking bill for the customer
     */
    public HashMap<ServiceType, Pair<Double, Integer>> generateHashMapForBooking(ObservableList<Booking>
                                                                                bookingObservableList) {

        HashMap<ServiceType, Pair<Double, Integer>> bookingBill = new HashMap<>();
        for (ServiceType st: serviceTypeList) {
            bookingBill.put(st, generateTotalBillBasedOnServiceType(bookingObservableList, st));
        }
        return bookingBill;
    }

    /**
     * @param bookingObservableList unmodifiable booking list
     * @param serviceType           the service type based on which the bill has to be calculated
     * @return a pair that contains the total amount for the service type and number of hours the service type was used
     */
    private Pair<Double, Integer> generateTotalBillBasedOnServiceType(ObservableList<Booking> bookingObservableList,
                                                                      ServiceType serviceType) {
        int totalTime = 0;
        double totalAmount = 0.0;
        for (Booking booking : bookingObservableList) {
            if ((booking.getService().equals(serviceType))) {
                TimeRange timeRange = booking.getTiming();
                int hoursBooked = timeRange.numOfHours();
                totalTime = totalTime + hoursBooked;
                double ratePerHour = booking.getService().getRatePerHour();
                double amount = hoursBooked * ratePerHour;
                totalAmount = totalAmount + amount;
            }
        }
        return new Pair<>(totalAmount, totalTime);
    }


    /**
     * Generates bill amount for all reservations for the specific customer
     *
     * @param reservationObservableList unmodifiable reservation list
     * @return total amount the customer has to pay for all reservations
     */
    public double generateBillForReservation(ObservableList<Reservation> reservationObservableList) {
        double totalAmount = 0.0;
        for (Reservation reservation : reservationObservableList) {
            RoomType roomType = reservation.getRoom();
            DateRange dateRange = reservation.getDates();
            long daysBooked = dateRange.numOfDays();
            double ratePerDay = roomType.getRatePerDay();
            double amount = daysBooked * ratePerDay;
            totalAmount = totalAmount + amount;
        }
        return totalAmount;

    }

    /**
     * Generates bill for all reservation for the specific customer
     *
     * @param reservationObservableList unmodifiable reservation list
     * @return hash map that returns the entire reservation bill for the customer
     */
    public HashMap<RoomType, Pair<Double, Long>> generateHashMapForReservation(ObservableList<Reservation>
                                                                                 reservationObservableList) {

        HashMap<RoomType, Pair<Double, Long>> reservationBill = new HashMap<>();
        for (RoomType rt: roomTypeList) {
            reservationBill.put(rt, generateTotalBillBasedOnRoomType(reservationObservableList, rt));
        }
        return reservationBill;
    }

    /**
     * @param reservationObservableList unmodifiable reservation list
     * @param roomType                  the room type based on which the bill has to be calculated
     * @return a pair that contains the total amount for the room type and number of days the room type was reserved
     */
    private Pair<Double, Long> generateTotalBillBasedOnRoomType(ObservableList<Reservation>
                                                                    reservationObservableList, RoomType roomType) {
        long totalDays = 0;
        double totalAmount = 0.0;
        for (Reservation reservation : reservationObservableList) {
            if (reservation.getRoom().equals(roomType)) {
                DateRange dateRange = reservation.getDates();
                long daysBooked = dateRange.numOfDays();
                totalDays = totalDays + daysBooked;
                double ratePerDay = reservation.getRoom().getRatePerDay();
                double amount = daysBooked * ratePerDay;
                totalAmount = totalAmount + amount;
            }
        }
        return new Pair<>(totalAmount, totalDays);
    }
}


