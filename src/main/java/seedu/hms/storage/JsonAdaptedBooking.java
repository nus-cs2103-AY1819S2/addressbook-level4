package seedu.hms.storage;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.hms.commons.exceptions.IllegalValueException;
import seedu.hms.model.booking.Booking;
import seedu.hms.model.booking.serviceType.ServiceType;
import seedu.hms.model.customer.Customer;
import seedu.hms.model.util.TimeRange;

/**
 * Jackson-friendly version of {@link Booking}.
 */
class JsonAdaptedBooking {

    private static final String MISSING_FIELD_MESSAGE_FORMAT = "Booking's %s field is missing!";

    private final JsonAdaptedServiceType service;
    private final JsonAdaptedTimeRange timing;
    private final JsonAdaptedCustomer payer;
    private List<JsonAdaptedCustomer> otherUsers = new ArrayList<JsonAdaptedCustomer>();
    private String comment;

    /**
     * Constructs a {@code JsonAdaptedCustomer} with the given customer details.
     */
    @JsonCreator
    public JsonAdaptedBooking(@JsonProperty("service") JsonAdaptedServiceType service,
                              @JsonProperty("timing") JsonAdaptedTimeRange timing,
                              @JsonProperty("payer") JsonAdaptedCustomer payer,
                              @JsonProperty("otherUsers") List<JsonAdaptedCustomer> otherUsers,
                              @JsonProperty("comment") String comment) {
        this.service = service;
        this.timing = timing;
        this.payer = payer;
        if (otherUsers != null) {
            this.otherUsers.addAll(otherUsers);
        }
        if (comment != null) {
            this.comment = comment;
        }
    }

    /**
     * Converts a given {@code Customer} into this class for Jackson use.
     */
    public JsonAdaptedBooking(Booking source) {
        service = new JsonAdaptedServiceType(source.getService());
        timing = new JsonAdaptedTimeRange(source.getTiming());
        payer = new JsonAdaptedCustomer(source.getPayer());
        otherUsers = source.getOtherUsers().orElse(new ArrayList<Customer>()).stream()
            .map(customer -> new JsonAdaptedCustomer(customer)).collect(Collectors.toList());
        comment = source.getComment().orElse("");
    }

    /**
     * Converts this Jackson-friendly adapted customer object into the model's {@code Customer} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted customer.
     */
    public Booking toModelType(List<JsonAdaptedServiceType> serviceTypes) throws IllegalValueException {
        List<Customer> others = new ArrayList<>();
        for (JsonAdaptedCustomer user : otherUsers) {
            others.add(user.toModelType());
        }

        if (service == null || serviceTypes.indexOf(service) == -1) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                ServiceType.class.getSimpleName()));
        }

        if (timing == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                TimeRange.class.getSimpleName()));
        }

        if (payer == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, "payer"));
        }
        return new Booking(serviceTypes.get(serviceTypes.indexOf(service)).toModelType(), timing.toModelType(),
            payer.toModelType(), Optional.of(others), "".equals(comment) ? Optional.empty() : Optional.of(comment));
    }

}
