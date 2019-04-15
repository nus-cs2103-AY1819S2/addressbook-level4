package seedu.hms.storage;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;

import seedu.hms.commons.exceptions.IllegalValueException;
import seedu.hms.model.HotelManagementSystem;
import seedu.hms.model.ReadOnlyHotelManagementSystem;
import seedu.hms.model.booking.Booking;
import seedu.hms.model.booking.serviceType.ServiceType;
import seedu.hms.model.customer.Customer;
import seedu.hms.model.reservation.Reservation;
import seedu.hms.model.reservation.roomType.RoomType;

/**
 * An Immutable HotelManagementSystem that is serializable to JSON format.
 */
@JsonRootName(value = "hotelManagementSystem")
class JsonSerializableHotelManagementSystem {

    public static final String MESSAGE_DUPLICATE_CUSTOMER = "Customers list contains duplicate customer(s).";

    private final List<JsonAdaptedBooking> bookings = new ArrayList<>();
    private final List<JsonAdaptedCustomer> customers = new ArrayList<>();
    private final List<JsonAdaptedReservation> reservations = new ArrayList<>();
    private final List<JsonAdaptedServiceType> serviceTypes = new ArrayList<>();
    private final List<JsonAdaptedRoomType> roomTypes = new ArrayList<>();

    /**
     * Constructs a {@code JsonSerializableHotelManagementSystem} with the given customers.
     */
    @JsonCreator
    public JsonSerializableHotelManagementSystem(@JsonProperty("customers") List<JsonAdaptedCustomer> customers,
                                                 @JsonProperty("bookings") List<JsonAdaptedBooking> bookings,
                                                 @JsonProperty("reservations") List<JsonAdaptedReservation>
                                                         reservations,
                                                 @JsonProperty("roomTypes") List<JsonAdaptedRoomType>
                                                         roomTypes,
                                                 @JsonProperty("serviceTypes") List<JsonAdaptedServiceType>
                                                         serviceTypes) {
        this.customers.addAll(customers);
        this.bookings.addAll(bookings);
        this.reservations.addAll(reservations);
        this.roomTypes.addAll(roomTypes);
        this.serviceTypes.addAll(serviceTypes);
    }

    /**
     * Converts a given {@code ReadOnlyHotelManagementSystem} into this class for Jackson use.
     *
     * @param source future changes to this will not affect the created {@code JsonSerializableHotelManagementSystem}.
     */
    public JsonSerializableHotelManagementSystem(ReadOnlyHotelManagementSystem source) {
        customers.addAll(source.getCustomerList().stream().map(JsonAdaptedCustomer::new).collect(Collectors.toList()));
        bookings.addAll(source.getBookingList().stream().map(JsonAdaptedBooking::new).collect(Collectors.toList()));
        reservations.addAll(source.getReservationList().stream().map(JsonAdaptedReservation::new)
            .collect(Collectors.toList()));
        roomTypes.addAll(source.getRoomTypeList().stream().map(JsonAdaptedRoomType::new)
            .collect(Collectors.toList()));
        serviceTypes.addAll(source.getServiceTypeList().stream().map(JsonAdaptedServiceType::new)
            .collect(Collectors.toList()));
    }

    /**
     * Converts this hms book into the model's {@code HotelManagementSystem} object.
     *
     * @throws IllegalValueException if there were any data constraints violated.
     */
    public HotelManagementSystem toModelType() throws IllegalValueException {
        HotelManagementSystem hotelManagementSystem = new HotelManagementSystem();
        for (JsonAdaptedCustomer jsonAdaptedCustomer : customers) {
            Customer customer = jsonAdaptedCustomer.toModelType();
            if (hotelManagementSystem.hasCustomer(customer)) {
                throw new IllegalValueException(MESSAGE_DUPLICATE_CUSTOMER);
            }
            hotelManagementSystem.addCustomer(customer);
        }
        for (JsonAdaptedRoomType jsonAdaptedRoomType : roomTypes) {
            RoomType roomType = jsonAdaptedRoomType.toModelType();
            hotelManagementSystem.addRoomType(roomType);
        }
        for (JsonAdaptedServiceType jsonAdaptedServiceType : serviceTypes) {
            ServiceType serviceType = jsonAdaptedServiceType.toModelType();
            hotelManagementSystem.addServiceType(serviceType);
        }
        for (JsonAdaptedBooking jsonAdaptedBooking : bookings) {
            Booking booking = jsonAdaptedBooking.toModelType(serviceTypes);
            hotelManagementSystem.addBooking(booking);
        }
        for (JsonAdaptedReservation jsonAdaptedReservation : reservations) {
            Reservation reservation = jsonAdaptedReservation.toModelType(roomTypes);
            hotelManagementSystem.addReservation(reservation);
        }
        return hotelManagementSystem;
    }

}
