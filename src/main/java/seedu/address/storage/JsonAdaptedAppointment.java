package seedu.address.storage;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeParseException;

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
    private String date;
    private String start;
    private String end;
    private String comment;

    /**
     * Constructs a {@code JsonAdaptedAppointment} with the given appointment details.
     */
    @JsonCreator
    public JsonAdaptedAppointment(@JsonProperty("patient") Patient patient,
                                   @JsonProperty("date") String date,
                                   @JsonProperty("start") String start,
                                   @JsonProperty("end") String end,
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
        this.date = source.getDate().toString();
        this.start = source.getStart().toString();
        this.end = source.getEnd().toString();
        this.comment = source.getComment();
    }

    /**
     * Converts this Jackson-friendly adapted appointment object into the model's {@code Appointment} object.
     *
     * @throws IllegalArgumentException if there were any data constraints violated for appointment fields.
     */
    public Appointment toModelType() throws IllegalArgumentException {
        Patient modelPatient = this.patient;
        LocalDate modelDate;
        LocalTime modelStart;
        LocalTime modelEnd;

        try {
            modelDate = LocalDate.parse(date);
        } catch (DateTimeParseException e) {
            throw new IllegalArgumentException("Date format: YYYY-MM-DD");
        }

        try {
            modelStart = LocalTime.parse(start);
            modelEnd = LocalTime.parse(end);
        } catch (DateTimeParseException e) {
            throw new IllegalArgumentException("Time format: HH:MM");
        }
        String modelComment = this.comment;

        Appointment appointment = new Appointment(modelPatient, modelDate, modelStart, modelEnd, modelComment);
        return appointment;
    }


}
