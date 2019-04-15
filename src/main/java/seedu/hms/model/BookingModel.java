package seedu.hms.model;

import java.util.function.Predicate;

import javafx.beans.property.ReadOnlyProperty;
import javafx.collections.ObservableList;
import seedu.hms.model.booking.Booking;
import seedu.hms.model.booking.serviceType.ServiceType;

/**
 * The API of the Model component.
 */
public interface BookingModel extends Model {

    /**
     * Selected booking in the filtered booking list.
     * null if no booking is selected.
     */
    ReadOnlyProperty<Booking> selectedBookingProperty();

    /**
     * Returns the selected booking in the filtered booking list.
     * null if no booking is selected.
     */
    Booking getSelectedBooking();

    boolean hasServiceType(ServiceType st);

    /**
     * Sets the selected booking in the filtered booking list.
     */
    void setSelectedBooking(Booking booking);

    /**
     * Returns an unmodifiable view of the filtered booking list
     */
    ObservableList<Booking> getFilteredBookingList();

    /**
     * Returns an unmodifiable view of the serviceType list
     */
    ObservableList<ServiceType> getServiceTypeList();

    /**
     * Updates the filter of the filtered booking list to filter by the given {@code predicate}.
     *
     * @throws NullPointerException if {@code predicate} is null.
     */
    void updateFilteredBookingList(Predicate<Booking> predicate);

    /**
     * Deletes the given booking.
     * The booking must exist in the hms book.
     */
    void deleteBooking(int target);

    /**
     * Deletes the given booking.
     * The booking must exist in the hms book.
     */
    void deleteBooking(Booking b);

    /**
     * Adds the given booking.
     * {@code booking} must not already exist in the hms book.
     */
    void addBooking(Booking booking);

    /**
     * Replaces the given booking {@code target} with {@code editedBooking}.
     * {@code target} must exist in the hms book.
     * The booking identity of {@code editedBooking} must not be the same as another existing booking in the hms
     * book.
     */
    void setBooking(int target, Booking editedBooking);

    /**
     * Deletes the given booking.
     * The booking must exist in the hms book.
     */
    void deleteServiceType(int target);

    /**
     * Deletes the given booking.
     * The booking must exist in the hms book.
     */
    void deleteServiceType(ServiceType st);

    /**
     * Adds the given booking.
     * {@code booking} must not already exist in the hms book.
     */
    void addServiceType(ServiceType serviceType);

    /**
     * Replaces the given booking {@code target} with {@code editedBooking}.
     * {@code target} must exist in the hms book.
     * The booking identity of {@code editedBooking} must not be the same as another existing booking in the hms
     * book.
     */
    void setServiceType(int target, ServiceType editedServiceType);

    ServiceType getServiceType(String serviceName);

    /**
     * Selected serviceType in the serviceType list.
     * null if no service type is selected.
     */
    ReadOnlyProperty<ServiceType> selectedServiceTypeProperty();

    /**
     * Returns the selected serviceType in the serviceType list.
     * null if no serviceType is selected.
     */
    ServiceType getSelectedServiceType();

    /**
     * Sets the selected serviceType in the serviceType list.
     */
    void setSelectedServiceType(ServiceType serviceType);

    /**
     * Clears all the bookings present in the {@code hotelManagementSystem}.
     */
    void setClearBooking(ReadOnlyHotelManagementSystem hotelManagementSystem);

    /**
     * Clears all the service types present in the {@code hotelManagementSystem}.
     */
    void setClearServiceTypes(ReadOnlyHotelManagementSystem hotelManagementSystem);


}
