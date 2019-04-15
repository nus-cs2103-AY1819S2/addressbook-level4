package seedu.address.model.project;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

import seedu.address.model.util.Name;

/**
 * Name of the projects in the Pocket Project.
 */

public class ProjectName extends Name {

    public final String projectName;

    /**
     * Constructor for ProjectName.
     */
    public ProjectName(String name) {
        requireNonNull(name);
        checkArgument(isValidName(name), MESSAGE_CONSTRAINTS);
        this.projectName = name;
    }

    /**
     * Returns a clone of this ProjectName object.
     */
    public ProjectName clone() {
        return new ProjectName(this.projectName);
    }

    @Override
    public String toString() {
        return projectName;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof ProjectName // instanceof handles nulls
                && projectName.equals(((ProjectName) other).projectName)); // state check
    }

    @Override
    public int hashCode() {
        return projectName.hashCode();
    }
}
