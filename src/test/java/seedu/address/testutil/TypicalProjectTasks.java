package seedu.address.testutil;

import seedu.address.model.project.ProjectTask;

/**
 * A utility class containing a list of {@code ProjectTask} objects to be used in tests.
 */
public class TypicalProjectTasks {

    public static final ProjectTask PROJECT_TASK_DO_SOMETHING = new ProjectTaskBuilder()
        .withTaskDescription("Do something")
        .withStatus("ongoing")
        .build();

    public static final ProjectTask PROJECT_TASK_ONGOING = new ProjectTaskBuilder()
        .withTaskDescription("Ongoing task")
        .withStatus("ongoing")
        .build();

    public static final ProjectTask PROJECT_TASK_ONGOING_STATUS_CHANGE = new ProjectTaskBuilder()
        .withTaskDescription("Ongoing task")
        .withStatus("complete")
        .build();

    public static final ProjectTask PROJECT_TASK_COMPLETED = new ProjectTaskBuilder()
        .withTaskDescription("Completed task")
        .withStatus("complete")
        .build();

    public static final ProjectTask PROJECT_TASK_ON_HOLD = new ProjectTaskBuilder()
        .withTaskDescription("On hold task")
        .withStatus("on hold")
        .build();

    private TypicalProjectTasks() {} // prevents instantiation
}
