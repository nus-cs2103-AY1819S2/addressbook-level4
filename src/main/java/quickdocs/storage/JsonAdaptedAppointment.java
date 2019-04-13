package quickdocs.storage;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeParseException;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import quickdocs.model.appointment.Appointment;
import quickdocs.model.patient.Patient;

/**
 * Jackson-friendly version of {@link Appointment}.
 */
public class JsonAdaptedAppointment {
    public static final String APP_MISSING_FIELD_MESSAGE_FORMAT = "Appointment's %s field is missing!";

    private Patient patient;
    private String date;
    private String start;
    private String end;
    private String comment;

    /**
     * Constructs a {@code JsonAdaptedAppointment} with the given {@code Appointment} details.
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
        patient = source.getPatient();
        date = source.getDate().toString();
        start = source.getStart().toString();
        end = source.getEnd().toString();
        comment = source.getComment();
    }

    /**
     * Converts this Jackson-friendly adapted {@code Appointment} object into the model's {@code Appointment} object.
     *
     * @throws IllegalArgumentException if there were any data constraints violated for {@code Appointment} fields.
     */
    public Appointment toModelType() throws IllegalArgumentException {
        // check if any fields are missing
        if (patient == null) {
            throw new IllegalArgumentException(String.format(APP_MISSING_FIELD_MESSAGE_FORMAT, "Patient"));
        }
        if (date == null) {
            throw new IllegalArgumentException(String.format(APP_MISSING_FIELD_MESSAGE_FORMAT, "Date"));
        }
        if (start == null) {
            throw new IllegalArgumentException(String.format(APP_MISSING_FIELD_MESSAGE_FORMAT, "Start"));
        }
        if (end == null) {
            throw new IllegalArgumentException(String.format(APP_MISSING_FIELD_MESSAGE_FORMAT, "End"));
        }
        if (comment == null) {
            throw new IllegalArgumentException(String.format(APP_MISSING_FIELD_MESSAGE_FORMAT, "Comment"));
        }

        Patient modelPatient = patient;
        String modelComment = comment;
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

        return new Appointment(modelPatient, modelDate, modelStart, modelEnd, modelComment);
    }
}
