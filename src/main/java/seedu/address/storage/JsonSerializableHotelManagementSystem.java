package seedu.address.storage;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.HotelManagementSystem;
import seedu.address.model.ReadOnlyHotelManagementSystem;
import seedu.address.model.booking.Booking;
import seedu.address.model.customer.Customer;

/**
 * An Immutable HotelManagementSystem that is serializable to JSON format.
 */
@JsonRootName(value = "hotelManagementSystem")
class JsonSerializableHotelManagementSystem {

    public static final String MESSAGE_DUPLICATE_CUSTOMER = "Customers list contains duplicate customer(s).";

    private final List<JsonAdaptedBooking> bookings = new ArrayList<>();
    private final List<JsonAdaptedCustomer> customers = new ArrayList<>();

    /**
     * Constructs a {@code JsonSerializableHotelManagementSystem} with the given customers.
     */
    @JsonCreator
    public JsonSerializableHotelManagementSystem(@JsonProperty("customers") List<JsonAdaptedCustomer> customers,
                                       @JsonProperty("bookings") List<JsonAdaptedBooking> bookings) {
        this.customers.addAll(customers);
        this.bookings.addAll(bookings);
    }

    /**
     * Converts a given {@code ReadOnlyHotelManagementSystem} into this class for Jackson use.
     *
     * @param source future changes to this will not affect the created {@code JsonSerializableHotelManagementSystem}.
     */
    public JsonSerializableHotelManagementSystem(ReadOnlyHotelManagementSystem source) {
        customers.addAll(source.getCustomerList().stream().map(JsonAdaptedCustomer::new).collect(Collectors.toList()));
        bookings.addAll(source.getBookingList().stream().map(JsonAdaptedBooking::new).collect(Collectors.toList()));
    }

    /**
     * Converts this address book into the model's {@code HotelManagementSystem} object.
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
        for (JsonAdaptedBooking jsonAdaptedBooking : bookings) {
            Booking booking = jsonAdaptedBooking.toModelType();
            hotelManagementSystem.addBooking(booking);
        }
        return hotelManagementSystem;
    }

}
