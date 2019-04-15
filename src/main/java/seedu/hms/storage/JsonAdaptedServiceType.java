package seedu.hms.storage;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.hms.commons.exceptions.IllegalValueException;
import seedu.hms.model.booking.serviceType.ServiceType;

/**
 * Jackson-friendly version of {@link ServiceType}.
 */
class JsonAdaptedServiceType {

    private static final String MISSING_FIELD_MESSAGE_FORMAT = "Service Type's %s field is missing!";
    private final int capacity;
    private final JsonAdaptedTimeRange timing;
    private final String name;
    private final double ratePerHour;

    /**
     * Constructs a {@code JsonAdaptedCustomer} with the given customer details.
     */
    @JsonCreator
    public JsonAdaptedServiceType(@JsonProperty("capacity") int capacity,
                                  @JsonProperty("name") String name,
                                  @JsonProperty("ratePerHour") double ratePerHour,
                                  @JsonProperty("timing") JsonAdaptedTimeRange timing) {
        this.name = name;
        this.capacity = capacity;
        this.ratePerHour = ratePerHour;
        this.timing = timing;
    }

    /**
     * Converts a given {@code Customer} into this class for Jackson use.
     */
    public JsonAdaptedServiceType(ServiceType source) {
        name = source.getName();
        timing = new JsonAdaptedTimeRange(source.getTiming());
        capacity = source.getCapacity();
        ratePerHour = source.getRatePerHour();
    }

    public String getName() {
        return name;
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
        if (timing == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, "timing"));
        }

        return new ServiceType(capacity, timing.toModelType(), name, ratePerHour);
    }

    @Override
    public boolean equals(Object other) {
        if (other instanceof JsonAdaptedServiceType) {
            return name.equalsIgnoreCase(((JsonAdaptedServiceType) other).getName());
        }
        return false;
    }

}
