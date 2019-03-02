package seedu.address.storage;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.booking.ServiceType;
import seedu.address.model.booking.ServiceType;
import seedu.address.model.customer.Address;
import seedu.address.model.customer.Email;
import seedu.address.model.customer.Name;
import seedu.address.model.customer.Customer;
import seedu.address.model.customer.Phone;
import seedu.address.model.tag.Tag;
import seedu.address.model.util.TimeRange;

/**
 * Jackson-friendly version of {@link Customer}.
 */
class JsonAdaptedServiceType {

    public static String MISSING_FIELD_MESSAGE_FORMAT = "Service Type's %s field is missing!";
    public static String INVALID_NAME_MESSAGE_FORMAT = "Service Type %s doesn't exist!";

    private final int capacity;
    private final JsonAdaptedTimeRange timing;
    private final String name;
    private final double ratePerHour;

    /**
     * Constructs a {@code JsonAdaptedCustomer} with the given customer details.
     */
    @JsonCreator
    public JsonAdaptedServiceType(@JsonProperty("capacity") int capacity,
                                  @JsonProperty("timing") JsonAdaptedTimeRange timing,
                                  @JsonProperty("name") String name,
                                  @JsonProperty("ratePerHour") double ratePerHour) {
        this.capacity = capacity;
        this.timing = timing;
        this.name = name;
        this.ratePerHour = ratePerHour;
    }

    /**
     * Converts a given {@code Customer} into this class for Jackson use.
     */
    public JsonAdaptedServiceType(ServiceType source) {
        capacity = source.getCapacity();
        timing = new JsonAdaptedTimeRange(source.getTiming());
        name = source.getName();
        ratePerHour = source.getRatePerHour();
    }

    /**
     * Converts this Jackson-friendly adapted customer object into the model's {@code Customer} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted customer.
     */
    public ServiceType toModelType() throws IllegalValueException {

        if (timing == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, TimeRange.class.getSimpleName()));
        }

        if (name == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, "name"));
        }

        switch(name) {
        case "GYM": return ServiceType.GYM;
        case "SWIMMING POOL": return ServiceType.POOL;
        case "SPA": return ServiceType.SPA;
        case "GAMES ROOM": return ServiceType.GAMES;
        default: throw new IllegalValueException(String.format(INVALID_NAME_MESSAGE_FORMAT, "name"));
        }
    }

}
