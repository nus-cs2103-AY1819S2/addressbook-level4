package seedu.address.model.project;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

import seedu.address.model.util.Description;

/**
 * Description of the project task in the Pocket Project.
 */

public class ProjectTaskDescription extends Description {

    public final String taskDescription;

    /**
     * Constructor for ProjectTaskDescription.
     */
    public ProjectTaskDescription(String taskDescription) {
        requireNonNull(taskDescription);
        checkArgument(isValidDescription(taskDescription), MESSAGE_CONSTRAINTS);
        this.taskDescription = taskDescription;
    }

    public String getTaskDescription() {
        return this.taskDescription;
    }

    /**
     * Returns a clone of this TaskName object.
     */
    public ProjectTaskDescription clone() {
        return new ProjectTaskDescription(this.taskDescription);
    }

    @Override
    public String toString() {
        return taskDescription;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
            || (other instanceof ProjectTaskDescription // instanceof handles nulls
            && taskDescription.equals(((ProjectTaskDescription) other).taskDescription)); // state check
    }

    @Override
    public int hashCode() {
        return taskDescription.hashCode();
    }
}
