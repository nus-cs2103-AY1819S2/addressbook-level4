package seedu.address.model.project;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

import seedu.address.model.util.Name;

/**
 * represents the user of a user story
 */
public class UserStoryUser extends Name {

    public static final String DEFAULT_USER = "Typical user";
    public static final String PREAMBLE_USER = "as a ";

    private String user;

    /**
     * Constructor for creating a user in a user story.
     * @param user
     */
    public UserStoryUser(String user) {
        requireNonNull(user);
        checkArgument(isValidName(user), MESSAGE_CONSTRAINTS);
        this.user = user;
    }

    public String getUser() {
        return this.user;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof UserStoryUser // instanceof handles nulls
                && (user.equals(((UserStoryUser) other).getUser()))); // state check
    }

    @Override
    public int hashCode() {
        return user.hashCode();
    }

    /**
     * Returns a clone of this user story user.
     */
    public UserStoryUser clone() {
        return new UserStoryUser(this.user);
    }

}
