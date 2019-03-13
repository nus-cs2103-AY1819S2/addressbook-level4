package seedu.address.storage;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.datetime.DateCustom;
import seedu.address.model.task.Task;
import seedu.address.model.task.Title;


/**
 * Jackson-friendly version of {@link Task}.
 */
class JsonAdaptedTask {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Task's %s field is missing!";

    private final String title;
    private final String startdate;
    private final String enddate;

    /**
     * Constructs a {@code JsonAdaptedTask} with the given task details.
     */
    @JsonCreator
    public JsonAdaptedTask(@JsonProperty("title") String title, @JsonProperty("startdate") String startdate,
                             @JsonProperty("enddate") String enddate) {
        this.title = title;
        this.startdate = startdate;
        this.enddate = enddate;

    }

    /**
     * Converts a given {@code Task} into this class for Jackson use.
     */
    public JsonAdaptedTask(Task source) {
        title = source.getTitle().toString();
        startdate = source.getStartDate().toString();
        enddate = source.getEndDate().toString();
    }

    /**
     * Converts this Jackson-friendly adapted task object into the model's {@code Task} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted task.
     */
    public Task toModelType() throws IllegalValueException {
        if (title == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Title.class.getSimpleName()));
        }
        if (!Title.isValidTitle(title)) {
            throw new IllegalValueException(Title.MESSAGE_CONSTRAINTS);
        }
        final Title modelTitle = new Title(title);

        if (startdate == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, DateCustom.class.getSimpleName()
            ));
        }
        if (!DateCustom.isValidDate(startdate)) {
            throw new IllegalValueException(DateCustom.MESSAGE_CONSTRAINTS);
        }
        final DateCustom modelStartdate = new DateCustom(startdate);

        if (enddate == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, DateCustom.class.getSimpleName()
            ));
        }
        if (!DateCustom.isValidDate(enddate)) {
            throw new IllegalValueException(DateCustom.MESSAGE_CONSTRAINTS);
        }
        final DateCustom modelEnddate = new DateCustom(enddate);

        return new Task(modelTitle, modelStartdate, modelEnddate);
    }

}
