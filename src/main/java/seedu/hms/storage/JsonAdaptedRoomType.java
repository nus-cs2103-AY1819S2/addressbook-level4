package seedu.hms.storage;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.hms.commons.exceptions.IllegalValueException;
import seedu.hms.model.reservation.RoomType;

/**
 * Jackson-friendly version of {@link RoomType}.
 */
class JsonAdaptedRoomType {

    private static final String MISSING_FIELD_MESSAGE_FORMAT = "Room Type's %s field is missing!";
    private static final String INVALID_NAME_MESSAGE_FORMAT = "Room Type %s doesn't exist!";

    private final String name;

    /**
     * Constructs a {@code JsonAdaptedCustomer} with the given customer details.
     */
    @JsonCreator
    public JsonAdaptedRoomType(@JsonProperty("name") String name) {
        this.name = name;
    }

    /**
     * Converts a given {@code Customer} into this class for Jackson use.
     */
    public JsonAdaptedRoomType(RoomType source) {
        name = source.getName();
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

        switch (name) {
        case "SINGLE ROOM":
            return RoomType.SINGLE;
        case "DOUBLE ROOM":
            return RoomType.DOUBLE;
        case "DELUXE ROOM":
            return RoomType.DELUXE;
        case "FAMILY SUITE":
            return RoomType.SUITE;
        default:
            throw new IllegalValueException(String.format(INVALID_NAME_MESSAGE_FORMAT, "name"));
        }
    }

}
