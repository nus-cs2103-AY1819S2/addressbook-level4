package seedu.hms.model;

import static java.util.Objects.requireNonNull;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javafx.beans.InvalidationListener;
import javafx.collections.ObservableList;
import seedu.hms.commons.util.InvalidationListenerManager;
import seedu.hms.model.booking.Booking;
import seedu.hms.model.booking.BookingList;
import seedu.hms.model.booking.serviceType.ServiceType;
import seedu.hms.model.booking.serviceType.ServiceTypeList;
import seedu.hms.model.customer.Customer;
import seedu.hms.model.customer.UniqueCustomerList;
import seedu.hms.model.reservation.Reservation;
import seedu.hms.model.reservation.ReservationList;
import seedu.hms.model.reservation.roomType.RoomType;
import seedu.hms.model.reservation.roomType.RoomTypeList;
import seedu.hms.model.util.TimeRange;

/**
 * Wraps all data at the hms-book level
 * Duplicates are not allowed (by .isSameCustomer comparison)
 */
public class HotelManagementSystem implements ReadOnlyHotelManagementSystem {

    private final BookingList bookings;
    private final UniqueCustomerList customers;
    private final ReservationList reservations;
    private final InvalidationListenerManager invalidationListenerManager = new InvalidationListenerManager();
    private final ServiceTypeList serviceTypes;
    private final RoomTypeList roomTypes;

    /**
     * The 'unusual' code block below is an non-static initialization block, sometimes used to avoid duplication
     * between constructors. See https://docs.oracle.com/javase/tutorial/java/javaOO/initial.html
     *
     * Note that non-static init blocks are not recommended to use. There are other ways to avoid duplication
     *   among constructors.
     */
    {
        bookings = new BookingList();
        customers = new UniqueCustomerList();
        serviceTypes = new ServiceTypeList();
        roomTypes = new RoomTypeList();
        reservations = new ReservationList();
    }

    public HotelManagementSystem() {
    }

    /**
     * Creates an HotelManagementSystem using the Customers in the {@code toBeCopied}
     */
    public HotelManagementSystem(ReadOnlyHotelManagementSystem toBeCopied) {
        this();
        resetData(toBeCopied);
    }

    //// list overwrite operations

    /**
     * Replaces the contents of the customer list with {@code customers}.
     * {@code customers} must not contain duplicate customers.
     */
    public void setCustomers(List<Customer> customers) {
        this.customers.setCustomers(customers);
        indicateModified();
    }

    /**
     * Replaces the contents of the booking list with {@code bookings}.
     */
    public void setBookings(List<Booking> bookings) {
        this.bookings.setBookings(bookings);
        indicateModified();
    }

    /**
     * Replaces the contents of the reservation list with {@code reservations}.
     */
    public void setReservations(List<Reservation> reservations) {
        this.reservations.setReservations(reservations);
        indicateModified();
    }

    /**
     * Replaces the contents of the reservation list with {@code reservations}.
     */
    public void setServiceTypes(List<ServiceType> serviceTypes) {
        this.serviceTypes.setServiceTypes(serviceTypes);
        indicateModified();
    }

    /**
     * Replaces the contents of the reservation list with {@code reservations}.
     */
    public void setRoomTypes(List<RoomType> roomTypes) {
        this.roomTypes.setRoomTypes(roomTypes);
        indicateModified();
    }

    /**
     * Resets the existing data of this {@code HotelManagementSystem} with {@code newData}.
     */
    public void resetData(ReadOnlyHotelManagementSystem newData) {
        requireNonNull(newData);

        setCustomers(newData.getCustomerList());
        setBookings(newData.getBookingList());
        setReservations(newData.getReservationList());
        setServiceTypes(newData.getServiceTypeList());
        setRoomTypes(newData.getRoomTypeList());
    }

    /**
     * Resets the existing booking data of this {@code HotelManagementSystem} with {@code newData}
     */
    public void resetDataBooking(ReadOnlyHotelManagementSystem newData) {
        requireNonNull(newData);

        setBookings(newData.getBookingList());
    }

    /**
     * Resets the existing room types data of this {@code HotelManagementSystem} with {@code newData}
     */
    public void resetDataRoomTypes(ReadOnlyHotelManagementSystem newData) {
        requireNonNull(newData);

        setRoomTypes(newData.getRoomTypeList());
    }

    /**
     * Resets the existing service types data of this {@code HotelManagementSystem} with {@code newData}
     */
    public void resetDataServiceTypes(ReadOnlyHotelManagementSystem newData) {
        requireNonNull(newData);

        setServiceTypes(newData.getServiceTypeList());
    }

    /**
     * Resets the existing reservation data of this {@code HotelManagementSystem} with {@code newData}
     */
    public void resetDataReservation(ReadOnlyHotelManagementSystem newData) {
        requireNonNull(newData);

        setReservations(newData.getReservationList());
    }
    //// customer-level operations

    /**
     * Returns true if a customer with the same identity as {@code customer} exists in the hms book.
     */
    public boolean hasCustomer(Customer customer) {
        requireNonNull(customer);
        return customers.contains(customer);
    }

    /**
     * Returns true if a customer with the same identity as {@code customer} exists in the hms book.
     */
    public boolean hasServiceType(ServiceType st) {
        requireNonNull(st);
        return serviceTypes.contains(st);
    }

    /**
     * Returns true if a customer with the same identity as {@code customer} exists in the hms book.
     */
    public boolean hasRoomType(RoomType st) {
        requireNonNull(st);
        return roomTypes.contains(st);
    }

    /**
     * Adds a customer to the hms book.
     * The customer must not already exist in the hms book.
     */
    public void addCustomer(Customer p) {
        customers.add(p);
        indicateModified();
    }

    /**
     * Replaces the given customer {@code target} in the list with {@code editedCustomer}.
     * {@code target} must exist in the hms book.
     * The customer identity of {@code editedCustomer} must not be the same as
     * another existing customer in the hms book.
     */
    public void setCustomer(Customer target, Customer editedCustomer) {
        requireNonNull(editedCustomer);

        customers.setCustomer(target, editedCustomer);
        indicateModified();
    }

    /**
     * Removes {@code key} from this {@code HotelManagementSystem}.
     * {@code key} must exist in the hms book.
     */
    public void removeCustomer(Customer key) {
        bookings.removeCustomer(key);
        reservations.removeCustomer(key);
        customers.remove(key);
        indicateModified();
    }

    //// booking-level operations

    /**
     * Adds a booking to the hms book.
     */
    public void addBooking(Booking p) {
        bookings.add(p);
        indicateModified();
    }

    /**
     * Replaces the booking at the given {@code bookingIndex} in the list with {@code editedBooking}.
     * {@code bookingIndex} must be within the list of bookings.
     */
    public void setBooking(int bookingIndex, Booking editedBooking) {
        requireNonNull(editedBooking);

        bookings.setBooking(bookingIndex, editedBooking);
        indicateModified();
    }

    /**
     * Removes {@code key} from this {@code HotelManagementSystem}.
     * {@code key} must exist in the hms book.
     */
    public void removeBooking(int removeIndex) {
        bookings.remove(removeIndex);
        indicateModified();
    }

    /**
     * Removes {@code booking} from this {@code HotelManagementSystem}.
     * {@code booking} must exist in the hms book.
     */
    public void removeBooking(Booking b) {
        bookings.remove(b);
        indicateModified();
    }

    /**
     * Removes {@code bookings} from this {@code HotelManagementSystem}.
     * All the bookings in {@code bookings} must exist in the hms book.
     */
    public void removeAllBookings(List<Booking> bookings) {
        bookings.removeAll(bookings);
        indicateModified();
    }

    //// service type level operations

    /**
     * Adds a booking to the hms book.
     */
    public void addServiceType(ServiceType p) {
        serviceTypes.add(p);
        indicateModified();
    }

    /**
     * Replaces the serviceType at the given {@code serviceTypeIndex} in the list with {@code editedServiceType}.
     * {@code serviceTypeIndex} must be within the list of serviceTypes.
     */
    public void setServiceType(int serviceTypeIndex, ServiceType editedServiceType) {
        requireNonNull(editedServiceType);
        ServiceType oldServiceType = serviceTypes.asUnmodifiableObservableList().get(serviceTypeIndex);
        serviceTypes.setServiceType(serviceTypeIndex, editedServiceType);
        bookings.setServiceType(oldServiceType, editedServiceType);
        indicateModified();
    }

    /**
     * Removes {@code key} from this {@code HotelManagementSystem}.
     * {@code key} must exist in the hms book.
     */
    public void removeServiceType(int removeIndex) {
        serviceTypes.remove(removeIndex);
        indicateModified();
    }

    /**
     * Removes {@code serviceType} from this {@code HotelManagementSystem}.
     * {@code serviceType} must exist in the hms book.
     */
    public void removeServiceType(ServiceType b) {
        serviceTypes.remove(b);
        bookings.removeServiceTpe(b);
        indicateModified();
    }

    //// service type level operations

    /**
     * Adds a booking to the hms book.
     */
    public void addRoomType(RoomType p) {
        roomTypes.add(p);
        indicateModified();
    }

    /**
     * Replaces the roomType at the given {@code roomTypeIndex} in the list with {@code editedRoomType}.
     * {@code roomTypeIndex} must be within the list of roomTypes.
     */
    public void setRoomType(int roomTypeIndex, RoomType editedRoomType) {
        requireNonNull(editedRoomType);
        RoomType oldRoomType = roomTypes.asUnmodifiableObservableList().get(roomTypeIndex);
        roomTypes.setRoomType(roomTypeIndex, editedRoomType);
        reservations.setRoomType(oldRoomType, editedRoomType);
        indicateModified();
    }

    /**
     * Removes {@code key} from this {@code HotelManagementSystem}.
     * {@code key} must exist in the hms book.
     */
    public void removeRoomType(int removeIndex) {
        roomTypes.remove(removeIndex);
        indicateModified();
    }

    /**
     * Removes {@code roomType} from this {@code HotelManagementSystem}.
     * {@code roomType} must exist in the hms book.
     */
    public void removeRoomType(RoomType b) {
        roomTypes.remove(b);
        reservations.removeRoomType(b);
        indicateModified();
    }

    //// reservation-level operations

    /**
     * Adds a reservation to the hms book.
     */
    public void addReservation(Reservation p) {
        reservations.add(p);
        indicateModified();
    }

    /**
     * Replaces the reservation at the given {@code reservationIndex} in the list with {@code editedReservation}.
     * {@code reservationIndex} must be within the list of reservations.
     */
    public void setReservation(int reservationIndex, Reservation editedReservation) {
        requireNonNull(editedReservation);

        reservations.setReservation(reservationIndex, editedReservation);
        indicateModified();
    }

    /**
     * Removes {@code key} from this {@code HotelManagementSystem}.
     * {@code key} must exist in the hms book.
     */
    public void removeReservation(int removeIndex) {
        reservations.remove(removeIndex);
        indicateModified();
    }

    /**
     * Removes {@code reservation} from this {@code HotelManagementSystem}.
     * {@code reservation} must exist in the hms book.
     */
    public void removeReservation(Reservation r) {
        reservations.remove(r);
        indicateModified();
    }


    @Override
    public void addListener(InvalidationListener listener) {
        invalidationListenerManager.addListener(listener);
    }

    @Override
    public void removeListener(InvalidationListener listener) {
        invalidationListenerManager.removeListener(listener);
    }

    /**
     * Notifies listeners that the hms book has been modified.
     */
    protected void indicateModified() {
        invalidationListenerManager.callListeners(this);
    }

    //// statistics methods

    public Map<ServiceType, Long> getPopularServices() {
        return bookings.asUnmodifiableObservableList().stream()
            .collect(Collectors.groupingBy(Booking::getService, Collectors.counting()))
            .entrySet().stream().sorted(Collections.reverseOrder(Map.Entry.comparingByValue()))
            .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e2,
                LinkedHashMap::new));
    }

    public Map<RoomType, Long> getPopularRoomTypes() {
        return reservations.asUnmodifiableObservableList().stream()
            .collect(Collectors.groupingBy(Reservation::getRoom, Collectors.counting()))
            .entrySet().stream().sorted(Collections.reverseOrder(Map.Entry.comparingByValue()))
            .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e2,
                LinkedHashMap::new));
    }

    public Map<TimeRange, Long> getPeakHourForService(ServiceType serviceType) {
        return bookings.asUnmodifiableObservableList().stream().filter(b -> b.getService().equals(serviceType))
            .collect(Collectors.groupingBy(Booking::getTiming, Collectors.counting()))
            .entrySet().stream().sorted(Collections.reverseOrder(Map.Entry.comparingByValue()))
            .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e2,
                LinkedHashMap::new));
    }

    public Map<Customer, Long> getPopularCustomerForServices() {
        return bookings.asUnmodifiableObservableList().stream()
            .collect(Collectors.groupingBy(Booking::getPayer, Collectors.counting()))
            .entrySet().stream().sorted(Collections.reverseOrder(Map.Entry.comparingByValue()))
            .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e2,
                LinkedHashMap::new));
    }

    public Map<Customer, Long> getPopularCustomerForReservations() {
        return reservations.asUnmodifiableObservableList().stream()
            .collect(Collectors.groupingBy(Reservation::getPayer, Collectors.counting()))
            .entrySet().stream().sorted(Collections.reverseOrder(Map.Entry.comparingByValue()))
            .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e2,
                LinkedHashMap::new));
    }

    //// util methods

    @Override
    public ObservableList<Reservation> getReservationList() {
        return reservations.asUnmodifiableObservableList();
    }

    @Override
    public ObservableList<Booking> getBookingList() {
        return bookings.asUnmodifiableObservableList();
    }

    @Override
    public ObservableList<ServiceType> getServiceTypeList() {
        return serviceTypes.asUnmodifiableObservableList();
    }

    @Override
    public ObservableList<RoomType> getRoomTypeList() {
        return roomTypes.asUnmodifiableObservableList();
    }

    /**
     * Return a string to represent the hms book.
     */
    public String toString() {
        return (customers.asUnmodifiableObservableList().size() + " customers").trim();
        // TODO: refine later
    }

    @Override
    public ObservableList<Customer> getCustomerList() {
        return customers.asUnmodifiableObservableList();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
            || (other instanceof HotelManagementSystem // instanceof handles nulls
            && customers.equals(((HotelManagementSystem) other).customers)
            && bookings.equals(((HotelManagementSystem) other).bookings));
    }

    @Override
    public int hashCode() {
        return customers.hashCode();
    }

}
