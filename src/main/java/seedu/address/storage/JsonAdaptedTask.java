package seedu.address.storage;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.datetime.DateCustom;
import seedu.address.model.datetime.TimeCustom;
import seedu.address.model.patient.Nric;
import seedu.address.model.person.Name;
import seedu.address.model.task.LinkedPatient;
import seedu.address.model.task.Priority;
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
    private final String starttime;
    private final String endtime;
    private final String priority;
    private final String linkedname;
    private final String linkednric;

    /**
     * Constructs a {@code JsonAdaptedTask} with the given task details.
     */
    @JsonCreator
    public JsonAdaptedTask(@JsonProperty("title") String title, @JsonProperty("startdate") String startdate,
                             @JsonProperty("enddate") String enddate, @JsonProperty("starttime") String starttime,
                             @JsonProperty("endtime") String endtime, @JsonProperty("priority") String priority,
                             @JsonProperty("linkedname") String linkedname,
                             @JsonProperty("linkednric") String linkednric) {
        this.title = title;
        this.startdate = startdate;
        this.enddate = enddate;
        this.starttime = starttime;
        this.endtime = endtime;
        this.priority = priority;
        this.linkedname = linkedname;
        this.linkednric = linkednric;

    }

    /**
     * Converts a given {@code Task} into this class for Jackson use.
     */
    public JsonAdaptedTask(Task source) {
        title = source.getTitle().toString();
        startdate = source.getStartDate().toString();
        enddate = source.getEndDate().toString();
        starttime = source.getStartTime().toString();
        endtime = source.getEndTime().toString();
        priority = source.getPriority().toString();
        if (source.getLinkedPatient() == null) {
            linkedname = null;
            linkednric = null;
        } else {
            linkedname = source.getLinkedPatient().getLinkedPatientName();
            linkednric = source.getLinkedPatient().getLinkedPatientNric();
        }
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

        if (starttime == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, TimeCustom.class.getSimpleName()
            ));
        }
        if (!TimeCustom.isValidTime(starttime)) {
            throw new IllegalValueException(DateCustom.MESSAGE_CONSTRAINTS);
        }
        final TimeCustom modelStartTime = new TimeCustom(starttime);

        if (endtime == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, TimeCustom.class.getSimpleName()
            ));
        }
        if (!TimeCustom.isValidTime(endtime)) {
            throw new IllegalValueException(DateCustom.MESSAGE_CONSTRAINTS);
        }
        final TimeCustom modelEndTime = new TimeCustom(endtime);

        if (!Priority.isValidPriority(priority)) {
            throw new IllegalValueException(Priority.MESSAGE_CONSTRAINTS);
        }
        final Priority modelPriority = Priority.returnPriority(priority);

        final LinkedPatient modelLinkedPatient;
        if (linkednric == null || linkedname == null) {
            modelLinkedPatient = null;
        } else {
            modelLinkedPatient = new LinkedPatient(new Name(linkedname), new Nric(linkednric));
        }
        return new Task(modelTitle, modelStartdate, modelEnddate, modelStartTime, modelEndTime, modelPriority,
                modelLinkedPatient);
    }

}
