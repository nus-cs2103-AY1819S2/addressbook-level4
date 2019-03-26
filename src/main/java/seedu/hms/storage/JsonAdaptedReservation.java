package seedu.hms.storage;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.hms.commons.exceptions.IllegalValueException;
import seedu.hms.model.customer.Customer;
import seedu.hms.model.reservation.Reservation;
import seedu.hms.model.reservation.RoomType;
import seedu.hms.model.util.DateRange;

/**
 * Jackson-friendly version of {@link Reservation}.
 */
class JsonAdaptedReservation {

    private static final String MISSING_FIELD_MESSAGE_FORMAT = "Reservation's %s field is missing!";

    private final JsonAdaptedRoomType room;
    private final JsonAdaptedDateRange dates;
    private final JsonAdaptedCustomer payer;
    private List<JsonAdaptedCustomer> otherUsers = new ArrayList<JsonAdaptedCustomer>();
    private String comment;

    /**
     * Constructs a {@code JsonAdaptedCustomer} with the given customer details.
     */
    @JsonCreator
    public JsonAdaptedReservation(@JsonProperty("room") JsonAdaptedRoomType room,
                                  @JsonProperty("dates") JsonAdaptedDateRange dates,
                                  @JsonProperty("payer") JsonAdaptedCustomer payer,
                                  @JsonProperty("otherUsers") List<JsonAdaptedCustomer> otherUsers,
                                  @JsonProperty("comment") String comment) {
        this.room = room;
        this.dates = dates;
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
    public JsonAdaptedReservation(Reservation source) {
        room = new JsonAdaptedRoomType(source.getRoom());
        dates = new JsonAdaptedDateRange(source.getDates());
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
    public Reservation toModelType() throws IllegalValueException {
        List<Customer> others = new ArrayList<>();
        for (JsonAdaptedCustomer user : otherUsers) {
            others.add(user.toModelType());
        }

        if (room == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                RoomType.class.getSimpleName()));
        }

        if (dates == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                DateRange.class.getSimpleName()));
        }

        if (payer == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, "payer"));
        }
        return new Reservation(room.toModelType(), dates.toModelType(), payer.toModelType(), Optional.of(others),
            "".equals(comment) ? Optional.empty() : Optional.of(comment));
    }

}
