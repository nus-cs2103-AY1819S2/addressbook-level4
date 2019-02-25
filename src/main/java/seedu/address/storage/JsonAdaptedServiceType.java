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
import seedu.address.model.person.Address;
import seedu.address.model.person.Email;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.model.tag.Tag;
import seedu.address.model.util.TimeRange;

/**
 * Jackson-friendly version of {@link Person}.
 */
class JsonAdaptedServiceType {

    public static String MISSING_FIELD_MESSAGE_FORMAT = "Service Type's %s field is missing!";

    private final int capacity;
    private final JsonAdaptedTimeRange timing;
    private final String name;
    private final double ratePerHour;

    /**
     * Constructs a {@code JsonAdaptedPerson} with the given person details.
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
     * Converts a given {@code Person} into this class for Jackson use.
     */
    public JsonAdaptedServiceType(ServiceType source) {
        capacity = source.getCapacity();
        timing = new JsonAdaptedTimeRange(source.getTiming());
        name = source.getName();
        ratePerHour = source.getRatePerHour();
    }

    /**
     * Converts this Jackson-friendly adapted person object into the model's {@code Person} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted person.
     */
    public ServiceType toModelType() throws IllegalValueException {

        if (timing == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, TimeRange.class.getSimpleName()));
        }

        if (name == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, "name"));
        }
        return new ServiceType(capacity, timing.toModelType(), name, ratePerHour);
    }

}
