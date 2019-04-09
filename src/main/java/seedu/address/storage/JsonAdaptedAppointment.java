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
import seedu.address.model.appointment.AppointmentStatus;
import seedu.address.model.appointment.AppointmentTime;

/**
 * Jackson-friendly version of {@link Appointment}.
 */
class JsonAdaptedAppointment {
    private final String patientId;
    private final String doctorId;
    private final LocalDate dateOfAppt;
    private final LocalTime timeOfAppt;
    private final String appointmentStatus;

    /**
     * Constructs a {@code JsonAdaptedAppointment} with the given {@code appointment}.
     */
    @JsonCreator
    public JsonAdaptedAppointment(@JsonProperty("patientId") String patientId,
                                  @JsonProperty("doctorId") String doctorId,
                                  @JsonProperty("dateOfAppt") LocalDate dateOfAppt,
                                  @JsonProperty("timeOfAppt") LocalTime timeOfAppt,
                                  @JsonProperty("appointmentStatus") String appointmentStatus) {
        this.patientId = patientId;
        this.doctorId = doctorId;
        this.dateOfAppt = dateOfAppt;
        this.timeOfAppt = timeOfAppt;
        this.appointmentStatus = appointmentStatus;
    }

    /**
     * Converts a given {@code Appointment} into this class for Jackson use.
     */
    public JsonAdaptedAppointment(Appointment source) {
        this.patientId = String.valueOf(source.getPatientId().patientId.personId);
        this.doctorId = String.valueOf(source.getDoctorId().doctorId.personId);
        this.dateOfAppt = source.getDate().date;
        this.timeOfAppt = source.getTime().time;
        this.appointmentStatus = source.getAppointmentStatus().name();
    }

    /**
     * Converts this Jackson-friendly adapted appointment object into the model's {@code Appointment} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted appointment.
     */
    public Appointment toModelType() throws IllegalValueException {
        return new Appointment(new AppointmentPatientId(patientId),
                new AppointmentDoctorId(doctorId),
                new AppointmentDate(dateOfAppt.toString()),
                new AppointmentTime(timeOfAppt.toString()),
                AppointmentStatus.valueOf(appointmentStatus));
    }

}
