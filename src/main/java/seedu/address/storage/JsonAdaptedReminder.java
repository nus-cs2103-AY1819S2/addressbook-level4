package seedu.address.storage;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeParseException;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.address.model.reminder.Reminder;

/**
 * Jackson-friendly version of {@link Reminder}.
 */
public class JsonAdaptedReminder {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Reminder's %s field is missing!";

    private String title;
    private String comment;
    private String date;
    private String start;
    private String end = null;

    /**
     * Constructs a {@code JsonAdaptedAppointment} with the given appointment details.
     */
    @JsonCreator
    public JsonAdaptedReminder(@JsonProperty("title") String title,
                               @JsonProperty("comment") String comment,
                               @JsonProperty("date") String date,
                               @JsonProperty("start") String start,
                               @JsonProperty("end") String end) {
        this.title = title;
        this.comment = comment;
        this.date = date;
        this.start = start;
        this.end = end;
    }

    /**
     * Converts a given {@code Reminder} into this class for Jackson use.
     */
    public JsonAdaptedReminder(Reminder source) {
        this.title = source.getTitle();
        this.comment = source.getComment();
        this.date = source.getDate().toString();
        this.start = source.getStart().toString();
        LocalTime endTime = source.getEnd();
        if (endTime != null) {
            this.end = endTime.toString();
        }
    }

    /**
     * Converts this Jackson-friendly adapted reminder object into the model's {@code Reminder} object.
     *
     * @throws IllegalArgumentException if there were any data constraints violated for reminder fields.
     */
    public Reminder toModelType() throws IllegalArgumentException {
        String modelTitle = this.title;
        String modelComment = this.comment;
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
            if (end != null) {
                modelEnd = LocalTime.parse(end);
            } else {
                modelEnd = null;
            }
        } catch (DateTimeParseException e) {
            throw new IllegalArgumentException("Time format: HH:MM");
        }

        Reminder reminder = new Reminder(modelTitle, modelComment, modelDate, modelStart, modelEnd);
        return reminder;
    }


}
