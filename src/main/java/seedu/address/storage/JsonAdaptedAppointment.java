package seedu.address.storage;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.appointment.Appointment;

/**
 * Jackson-friendly version of {@link Appointment}.
 */
class JsonAdaptedAppointment {
    private final int patientId;
    private final int doctorId;
    private final String dateOfAppt;
    private final String timeOfAppt;

    /**
     * Constructs a {@code JsonAdaptedAppointment} with the given {@code appointment}.
     */
    @JsonCreator
    public JsonAdaptedAppointment(@JsonProperty("patientId") int patientId,
                                  @JsonProperty("doctorId") int doctorId,
                                  @JsonProperty("dateOfAppt") String dateOfAppt,
                                  @JsonProperty("timeOfAppt") String timeOfAppt) {
        this.patientId = patientId;
        this.doctorId = doctorId;
        this.dateOfAppt = dateOfAppt;
        this.timeOfAppt = timeOfAppt;
    }

    /**
     * Converts a given {@code Appointment} into this class for Jackson use.
     */
    public JsonAdaptedAppointment(Appointment source) {
        this.patientId = source.getPatientId();
        this.doctorId = source.getDoctorId();
        this.dateOfAppt = source.getDateOfAppt();
        this.timeOfAppt = source.getTimeOfAppt();
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
        return new Appointment(this.patientId, this.doctorId, this.dateOfAppt, this.timeOfAppt);
    }

}
