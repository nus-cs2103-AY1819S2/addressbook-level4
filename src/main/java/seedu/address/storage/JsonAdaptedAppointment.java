package seedu.address.storage;

import java.time.temporal.ChronoUnit;

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
    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Appointment's %s field is missing!";

    private final String patientId;
    private final String doctorId;
    private final String dateOfAppt;
    private final String timeOfAppt;
    private final String appointmentStatus;

    /**
     * Constructs a {@code JsonAdaptedAppointment} with the given {@code appointment}.
     */
    @JsonCreator
    public JsonAdaptedAppointment(@JsonProperty("patientId") String patientId,
                                  @JsonProperty("doctorId") String doctorId,
                                  @JsonProperty("dateOfAppt") String dateOfAppt,
                                  @JsonProperty("timeOfAppt") String timeOfAppt,
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
        this.dateOfAppt = source.getDate().date.toString();
        this.timeOfAppt = source.getTime().time.truncatedTo(ChronoUnit.HOURS).toString();
        this.appointmentStatus = source.getAppointmentStatus().name();
    }

    /**
     * Converts this Jackson-friendly adapted appointment object into the model's {@code Appointment} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted appointment.
     */
    public Appointment toModelType() throws IllegalValueException {
        if (patientId == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                 AppointmentPatientId.class.getSimpleName()));
        }
        if (!AppointmentPatientId.isValidAppointmentPatientId(patientId)) {
            throw new IllegalValueException(AppointmentPatientId.MESSAGE_CONSTRAINTS);
        }
        final AppointmentPatientId modelPatientId = new AppointmentPatientId(patientId);

        if (doctorId == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    AppointmentDoctorId.class.getSimpleName()));
        }
        if (!AppointmentDoctorId.isValidAppointmentDoctorId(doctorId)) {
            throw new IllegalValueException(AppointmentDoctorId.MESSAGE_CONSTRAINTS);
        }
        final AppointmentDoctorId modelDoctorId = new AppointmentDoctorId(doctorId);

        if (dateOfAppt == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    AppointmentDate.class.getSimpleName()));
        }
        if (!AppointmentDate.isValidAppointmentDate(dateOfAppt)) {
            throw new IllegalValueException(AppointmentDate.MESSAGE_CONSTRAINTS);
        }
        final AppointmentDate modelAppointmentDate = new AppointmentDate(dateOfAppt);

        if (timeOfAppt == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    AppointmentTime.class.getSimpleName()));
        }
        if (!AppointmentTime.isValidAppointmentTime(timeOfAppt)) {
            throw new IllegalValueException(AppointmentTime.MESSAGE_CONSTRAINTS);
        }
        final AppointmentTime modelAppointmentTime = new AppointmentTime(timeOfAppt);

        if (appointmentStatus == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    AppointmentStatus.class.getSimpleName()));
        }
        if (!AppointmentStatus.isValidAppointmentStatus(appointmentStatus)) {
            throw new IllegalValueException(AppointmentStatus.MESSAGE_CONSTRAINTS);
        }
        final AppointmentStatus modelAppointmentStatus = AppointmentStatus.valueOf(appointmentStatus);

        return new Appointment(modelPatientId, modelDoctorId, modelAppointmentDate, modelAppointmentTime,
                modelAppointmentStatus);
    }

}
