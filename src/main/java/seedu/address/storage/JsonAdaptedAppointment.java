package seedu.address.storage;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.appointment.Appointment;

/**
 * Jackson-friendly version of {@link Appointment}.
 */
class JsonAdaptedAppointment {

    private final String appointmentName;

    /**
     * Constructs a {@code JsonAdaptedAppointment} with the given {@code appointmentName}.
     */
    @JsonCreator
    public JsonAdaptedAppointment(String appointmentName) {
        this.appointmentName = appointmentName;
    }

    /**
     * Converts a given {@code Appointment} into this class for Jackson use.
     */
    public JsonAdaptedAppointment(Appointment source) {
        appointmentName = source.value;
    }

    @JsonValue
    public String getAppointmentName() {
        return appointmentName;
    }

    /**
     * Converts this Jackson-friendly adapted appointment object into the model's {@code Appointment} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted appointment.
     */
    public Appointment toModelType() throws IllegalValueException {
        /*if (!Tag.isValidTagName(tagName)) {
            throw new IllegalValueException(Tag.MESSAGE_CONSTRAINTS);
        }*/
        return new Appointment(this.appointmentName);
    }

}
