package seedu.hms.storage;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.hms.commons.exceptions.IllegalValueException;
import seedu.hms.model.booking.ServiceType;

/**
 * Jackson-friendly version of {@link ServiceType}.
 */
class JsonAdaptedServiceType {

    private static final String MISSING_FIELD_MESSAGE_FORMAT = "Service Type's %s field is missing!";
    private static final String INVALID_NAME_MESSAGE_FORMAT = "Service Type %s doesn't exist!";

    private final String name;

    /**
     * Constructs a {@code JsonAdaptedCustomer} with the given customer details.
     */
    @JsonCreator
    public JsonAdaptedServiceType(@JsonProperty("name") String name) {
        this.name = name;
    }

    /**
     * Converts a given {@code Customer} into this class for Jackson use.
     */
    public JsonAdaptedServiceType(ServiceType source) {
        name = source.getName();
    }

    /**
     * Converts this Jackson-friendly adapted customer object into the model's {@code Customer} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted customer.
     */
    public ServiceType toModelType() throws IllegalValueException {

        if (name == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, "name"));
        }

        switch (name) {
        case "GYM":
            return ServiceType.GYM;
        case "SWIMMING POOL":
            return ServiceType.POOL;
        case "SPA":
            return ServiceType.SPA;
        case "GAMES ROOM":
            return ServiceType.GAMES;
        default:
            throw new IllegalValueException(String.format(INVALID_NAME_MESSAGE_FORMAT, "name"));
        }
    }

}
