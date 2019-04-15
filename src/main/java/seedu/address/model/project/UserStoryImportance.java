package seedu.address.model.project;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * represents the importance level of a user story
 */
public class UserStoryImportance {

    public static final String MESSAGE_CONSTRAINTS =
            "User story should have a importance of 1-3. 3 being the most important and 1 being the least important.";

    /**
     * Checks if the string is a number between 1 and 3.
     */
    public static final String VALIDATION_REGEX = "[1-3]";

    private Integer importance;

    public UserStoryImportance(String importance) {
        requireNonNull(importance);
        checkArgument(isValidImportanceLevel(importance), MESSAGE_CONSTRAINTS);
        this.importance = Integer.parseInt(importance);
    }

    public String getImportance() {
        return this.importance.toString();
    }

    public boolean isHigherImportance(UserStoryImportance other) {
        return this.importance > other.importance;
    }

    /**
     * Returns true if a given string is a valid importance level.
     */
    public static boolean isValidImportanceLevel(String input) {
        return input.matches(VALIDATION_REGEX);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof UserStoryImportance // instanceof handles nulls
                && (importance.toString().equals(((UserStoryImportance) other).getImportance()))); // state check
    }

    @Override
    public int hashCode() {
        return importance.hashCode();
    }

    /**
     * Returns a clone of this user story importance.
     */
    public UserStoryImportance clone() {
        return new UserStoryImportance(this.importance.toString());
    }
}
