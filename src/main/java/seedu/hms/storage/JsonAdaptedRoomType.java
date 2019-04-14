package seedu.hms.storage;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.hms.commons.exceptions.IllegalValueException;
import seedu.hms.model.reservation.roomType.RoomType;

/**
 * Jackson-friendly version of {@link RoomType}.
 */
class JsonAdaptedRoomType {

    private static final String MISSING_FIELD_MESSAGE_FORMAT = "Room Type's %s field is missing!";

    private final String name;
    private final double ratePerDay;
    private final int numberOfRooms;

    /**
     * Constructs a {@code JsonAdaptedCustomer} with the given customer details.
     */
    @JsonCreator
    public JsonAdaptedRoomType(@JsonProperty("name") String name,
                               @JsonProperty("ratePerDay") double ratePerDay,
                               @JsonProperty("numberOfRooms") int numberOfRooms) {
        this.name = name;
        this.ratePerDay = ratePerDay;
        this.numberOfRooms = numberOfRooms;
    }

    /**
     * Converts a given {@code Customer} into this class for Jackson use.
     */
    public JsonAdaptedRoomType(RoomType source) {
        name = source.getName();
        ratePerDay = source.getRatePerDay();
        numberOfRooms = source.getNumberOfRooms();
    }

    /**
     * Converts this Jackson-friendly adapted customer object into the model's {@code Customer} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted customer.
     */
    public RoomType toModelType() throws IllegalValueException {

        if (name == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, "name"));
        }

        return new RoomType(numberOfRooms, name, ratePerDay);
    }

    @Override
    public boolean equals(Object other) {
        if (other instanceof JsonAdaptedRoomType) {
            return name.equalsIgnoreCase(((JsonAdaptedRoomType) other).getName());
        }
        return false;
    }

    public String getName() {
        return name;
    }
}
