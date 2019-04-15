package quickdocs.storage;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeParseException;
import java.util.Optional;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import quickdocs.model.reminder.Reminder;

/**
 * Jackson-friendly version of {@link Reminder}.
 */
public class JsonAdaptedReminder {
    public static final String REM_MISSING_FIELD_MESSAGE_FORMAT = "Reminder's %s field is missing!";

    private String title;
    private String date;
    private String start;
    private String comment = null;
    private String end = null;

    /**
     * Constructs a {@code JsonAdaptedAppointment} with the given {@code Reminder} details.
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
        title = source.getTitle();
        date = source.getDate().toString();
        start = source.getStart().toString();
        Optional<String> commentString = Optional.ofNullable(source.getComment());
        commentString.ifPresent(s -> comment = commentString.get());
        Optional<LocalTime> endTime = Optional.ofNullable(source.getEnd());
        endTime.ifPresent(e -> end = endTime.get().toString());
    }

    /**
     * Converts this Jackson-friendly adapted {@code Reminder} object into the model's {@code Reminder} object.
     *
     * @throws IllegalArgumentException if there were any data constraints violated for {@code Reminder} fields.
     */
    public Reminder toModelType() throws IllegalArgumentException {
        // check if any required fields are missing
        if (title == null) {
            throw new IllegalArgumentException(String.format(REM_MISSING_FIELD_MESSAGE_FORMAT, "Title"));
        }
        if (date == null) {
            throw new IllegalArgumentException(String.format(REM_MISSING_FIELD_MESSAGE_FORMAT, "Date"));
        }
        if (start == null) {
            throw new IllegalArgumentException(String.format(REM_MISSING_FIELD_MESSAGE_FORMAT, "Start"));
        }

        String modelTitle = title;
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
            if (Optional.ofNullable(end).isPresent()) {
                modelEnd = LocalTime.parse(end);
            } else {
                modelEnd = null;
            }
        } catch (DateTimeParseException e) {
            throw new IllegalArgumentException("Time format: HH:MM");
        }

        return new Reminder(modelTitle, modelComment, modelDate, modelStart, modelEnd);
    }
}
