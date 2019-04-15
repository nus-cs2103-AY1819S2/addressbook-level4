package seedu.address.storage;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.project.ProjectTask;
import seedu.address.model.project.ProjectTaskDescription;
import seedu.address.model.project.Status;

/**
 * Jackson-friendly version of {@link ProjectTask}.
 */
class JsonAdaptedProjectTask {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Project task's %s field is missing!";

    private final String taskName;
    private final String taskStatus;

    /**
     * Constructs a {@code JsonAdaptedProjectTask} with the given user story details.
     */
    @JsonCreator
    public JsonAdaptedProjectTask(@JsonProperty("taskDescription") String taskName,
                                  @JsonProperty("taskStatus") String taskStatus) {
        this.taskName = taskName;
        this.taskStatus = taskStatus;
    }

    /**
     * Converts a given {@code ProjectTask} into this class for Jackson use.
     */
    public JsonAdaptedProjectTask(ProjectTask source) {
        this.taskName = source.getTaskDescription();
        this.taskStatus = source.getTaskStatus();
    }

    /**
     * Converts this Jackson-friendly adapted project task object into the model's {@code ProjectTask} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted project task.
     */
    public ProjectTask toModelType() throws IllegalValueException {

        if (taskName == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    ProjectTaskDescription.class.getSimpleName()));
        }
        if (!ProjectTaskDescription.isValidDescription(taskName)) {
            throw new IllegalValueException(ProjectTaskDescription.MESSAGE_CONSTRAINTS);
        }
        final ProjectTaskDescription modelProjectTaskDescription = new ProjectTaskDescription(taskName);

        if (taskStatus == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
            Status.class.getSimpleName()));
        }
        if (!Status.isValidStatus(taskStatus)) {
            throw new IllegalValueException(Status.MESSAGE_CONSTRAINTS);
        }
        final Status modelProjectTaskStatus = new Status(taskStatus);

        return new ProjectTask(modelProjectTaskDescription, modelProjectTaskStatus);
    }

}
