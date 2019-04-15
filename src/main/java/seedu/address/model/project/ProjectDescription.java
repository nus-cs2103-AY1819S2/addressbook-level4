package seedu.address.model.project;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

import seedu.address.model.util.Description;

/**
 * Description of the project. A summary of what the project is about
 */
public class ProjectDescription extends Description {

    public final String description;

    /**
     * Constructor for when description is not added to the project yet and default description is needed
     */
    public ProjectDescription() {
        this.description = Project.PROJECT_DEFAULT_DESCRIPTION;
    }

    /**
     * Constructor for when adding description to the project or milestone
     */
    public ProjectDescription(String desc) {
        requireNonNull(desc);
        checkArgument(isValidDescription(desc), MESSAGE_CONSTRAINTS);
        this.description = desc;
    }

    public ProjectDescription clone() {
        return new ProjectDescription(this.description);
    }

    @Override
    public String toString() {
        return description;
    }

    @Override
    public boolean equals(Object other) {
        return other == this// short circuit if same object
            || (other instanceof ProjectDescription // instanceof handles nulls
            && description.equals(((ProjectDescription) other).description)); // state check
    }

    @Override
    public int hashCode() {
        return description.hashCode();
    }




}
