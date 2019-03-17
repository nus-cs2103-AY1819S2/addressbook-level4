package seedu.address.storage;

import java.time.LocalDate;
import java.time.LocalTime;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.address.model.appointment.Appointment;
import seedu.address.model.patient.Patient;

/**
 * Jackson-friendly version of {@link Appointment}.
 */
public class JsonAdaptedAppointment {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Appointment's %s field is missing!";

    private Patient patient;
    private LocalDate date;
    private LocalTime start;
    private LocalTime end;
    private String comment;

    /**
     * Constructs a {@code JsonAdaptedAppointment} with the given appointment details.
     */
    @JsonCreator
    public JsonAdaptedAppointment(@JsonProperty("patient") Patient patient,
                                   @JsonProperty("date") LocalDate date,
                                   @JsonProperty("start") LocalTime start,
                                   @JsonProperty("end") LocalTime end,
                                   @JsonProperty("comment") String comment) {
        this.patient = patient;
        this.date = date;
        this.start = start;
        this.end = end;
        this.comment = comment;
    }

    /**
     * Converts a given {@code Appointment} into this class for Jackson use.
     */
    public JsonAdaptedAppointment(Appointment source) {

        this.patient = source.getPatient();
        this.date = source.getDate();
        this.start = source.getStartTime();
        this.end = source.getEndTime();
        this.comment = source.getComment();
    }

    /**
     * Converts this Jackson-friendly adapted appointment object into the model's {@code Appointment} object.
     *
     * @throws IllegalArgumentException if there were any data constraints violated for patient fields.
     */
    public Appointment toModelType() throws IllegalArgumentException {
        Patient modelPatient = this.patient;
        LocalDate modelDate = this.date;
        LocalTime modelStart = this.start;
        LocalTime modelEnd = this.end;
        String modelComment = this.comment;

        Appointment appointment = new Appointment(modelPatient, modelDate, modelStart, modelEnd, modelComment);
        return appointment;
    }


}
