package seedu.address.model.project;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

import seedu.address.model.util.Description;

/**
 * represents the function of a user story
 */
public class UserStoryFunction extends Description {

    public static final String DEFAULT_FUNCTION = "Typical function";
    public static final String PREAMBLE_FUNCTION = "i want to ";

    private String function;

    /**
     * Constructor for creating a function in a user story.
     * @param function the input function
     */
    public UserStoryFunction(String function) {
        requireNonNull(function);
        checkArgument(isValidDescription(function), MESSAGE_CONSTRAINTS);
        this.function = function;
    }

    public String getFunction() {
        return this.function;
    }

    /**
     * Returns a clone of this user story function.
     */
    public UserStoryFunction clone() {
        return new UserStoryFunction(this.function);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof UserStoryFunction // instanceof handles nulls
                && (function.equals(((UserStoryFunction) other).getFunction()))); // state check
    }

    @Override
    public int hashCode() {
        return function.hashCode();
    }

}
