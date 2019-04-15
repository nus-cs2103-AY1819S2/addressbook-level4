package seedu.address.testutil;

import seedu.address.model.project.ProjectTask;
import seedu.address.model.project.ProjectTaskDescription;
import seedu.address.model.project.Status;

/**
 * A utility class to help with building Milestone objects.
 */
public class ProjectTaskBuilder {

    public static final String DEFAULT_TASK_NAME = "Create new feature";
    public static final String DEFAULT_STATUS = "ongoing";

    private ProjectTaskDescription taskName;
    private Status status;

    public ProjectTaskBuilder() {
        this.taskName = new ProjectTaskDescription(DEFAULT_TASK_NAME);
        this.status = new Status(DEFAULT_STATUS);
    }

    /**
     * Initializes the ProjectTaskBuilder with the data of {@code taskToCopy}.
     */
    public ProjectTaskBuilder(ProjectTask taskToCopy) {
        this.taskName = new ProjectTaskDescription(taskToCopy.getTaskDescription());
        this.status = new Status(taskToCopy.getTaskStatus());
    }

    /**
     * Sets the {@code ProjectTaskDescription} of the {@code ProjectTask} that we are building.
     */
    public ProjectTaskBuilder withTaskDescription(String taskDescription) {
        this.taskName = new ProjectTaskDescription(taskDescription);
        return this;
    }

    /**
     * Sets the {@code Status} of the {@code ProjectTask} that we are building.
     */
    public ProjectTaskBuilder withStatus(String status) {
        this.status = new Status(status);
        return this;
    }

    public ProjectTask build() {
        return new ProjectTask(taskName, status);
    }

}
