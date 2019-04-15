package seedu.address.model.project;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

import seedu.address.model.util.Description;

/**
 * Description of a milestone. A summary of what the milestone is about.
 */
public class MilestoneDescription extends Description {

    public final String description;

    /**
     * Constructor for when adding description to the milestone.
     */
    public MilestoneDescription(String desc) {
        requireNonNull(desc);
        checkArgument(isValidDescription(desc), MESSAGE_CONSTRAINTS);
        this.description = desc;
    }

    public MilestoneDescription clone() {
        return new MilestoneDescription(this.description);
    }

    @Override
    public String toString() {
        return description;
    }

    @Override
    public boolean equals(Object other) {
        return other == this// short circuit if same object
            || (other instanceof MilestoneDescription // instanceof handles nulls
            && description.equals(((MilestoneDescription) other).description)); // state check
    }

    @Override
    public int hashCode() {
        return description.hashCode();
    }

}

