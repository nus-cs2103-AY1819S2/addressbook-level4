package seedu.address.storage;

import java.time.LocalDate;
import java.time.LocalTime;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.appointment.Appointment;
import seedu.address.model.appointment.AppointmentDate;
import seedu.address.model.appointment.AppointmentDoctorId;
import seedu.address.model.appointment.AppointmentPatientId;
import seedu.address.model.appointment.AppointmentTime;

/**
 * Jackson-friendly version of {@link Appointment}.
 */
class JsonAdaptedAppointment {
    private final int patientId;
    private final int doctorId;
    private final LocalDate dateOfAppt;
    private final LocalTime timeOfAppt;

    /**
     * Constructs a {@code JsonAdaptedAppointment} with the given {@code appointment}.
     */
    @JsonCreator
    public JsonAdaptedAppointment(@JsonProperty("patientId") int patientId,
                                  @JsonProperty("doctorId") int doctorId,
                                  @JsonProperty("dateOfAppt") LocalDate dateOfAppt,
                                  @JsonProperty("timeOfAppt") LocalTime timeOfAppt) {
        this.patientId = patientId;
        this.doctorId = doctorId;
        this.dateOfAppt = dateOfAppt;
        this.timeOfAppt = timeOfAppt;
    }

    /**
     * Converts a given {@code Appointment} into this class for Jackson use.
     */
    public JsonAdaptedAppointment(Appointment source) {
        this.patientId = source.getPatientId().patientId.personId;
        this.doctorId = source.getDoctorId().doctorId.personId;
        this.dateOfAppt = source.getDate().date;
        this.timeOfAppt = source.getTime().time;
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
        return new Appointment(new AppointmentPatientId(Integer.toString(patientId)),
                new AppointmentDoctorId(Integer.toString(doctorId)),
                new AppointmentDate(dateOfAppt.toString()),
                new AppointmentTime(timeOfAppt.toString()));
    }

}
