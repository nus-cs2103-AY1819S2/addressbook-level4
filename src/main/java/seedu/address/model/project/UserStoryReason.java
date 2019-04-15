package seedu.address.model.project;

/**
 *  Reason for a user story.
 */
public class UserStoryReason {

    public static final String DEFAULT_REASON = "";
    public static final String PREAMBLE_REASON = "so that ";

    private String reason;

    /**
     * Constructor for creating a reason of a user story.
     * @param reason
     */
    public UserStoryReason(String reason) {
        this.reason = reason;
    }

    /**
     * Alternative constructor to handle instances where the function is sufficient to represent a user story
     * and no explicit reason is required.
     */
    private UserStoryReason() {
        this.reason = DEFAULT_REASON;
    }

    public String getReason() {
        return this.reason;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof UserStoryReason // instanceof handles nulls
                && (reason.equals(((UserStoryReason) other).getReason()))); // state check
    }

    @Override
    public int hashCode() {
        return reason.hashCode();
    }

    /**
     * Returns a clone of this user story reason.
     */
    public UserStoryReason clone() {
        return new UserStoryReason(this.reason);
    }
}
