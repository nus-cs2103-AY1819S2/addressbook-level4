package seedu.address.testutil;

import seedu.address.model.project.Status;
import seedu.address.model.project.UserStory;
import seedu.address.model.project.UserStoryFunction;
import seedu.address.model.project.UserStoryImportance;
import seedu.address.model.project.UserStoryReason;
import seedu.address.model.project.UserStoryUser;

/**
 * A utility class to help with building UserStory objects.
 */
public class UserStoryBuilder {

    public static final String DEFAULT_IMPORTANCE = "3";
    public static final String DEFAULT_USER = "Software engineering project manager";
    public static final String DEFAULT_FUNCTION = "Be able to add/delete projects in the app";
    public static final String DEFAULT_REASON = "Keep track of any new projects and remove unnecessary entries";

    private UserStoryImportance importance;
    private UserStoryUser user;
    private UserStoryFunction function;
    private UserStoryReason reason;
    private Status status;

    public UserStoryBuilder() {
        importance = new UserStoryImportance(DEFAULT_IMPORTANCE);
        user = new UserStoryUser(DEFAULT_USER);
        function = new UserStoryFunction(DEFAULT_FUNCTION);
        reason = new UserStoryReason(DEFAULT_REASON);
        status = new Status();
    }

    /**
     * Initializes the UserStoryBuilder with the data of {@code userStoryToCopy}.
     */
    public UserStoryBuilder(UserStory userStoryToCopy) {
        this.importance = new UserStoryImportance(userStoryToCopy.getUserStoryImportance());
        this.user = new UserStoryUser(userStoryToCopy.getUserStoryUser());
        this.function = new UserStoryFunction(userStoryToCopy.getUserStoryFunction());
        this.reason = new UserStoryReason(userStoryToCopy.getUserStoryReason());
        this.status = new Status(userStoryToCopy.getUserStoryStatus());
    }

    /**
     * Sets the {@code UserStoryImportance} of the {@code UserStory} that we are building.
     */
    public UserStoryBuilder withImportance(String importance) {
        this.importance = new UserStoryImportance(importance);
        return this;
    }

    /**
     * Sets the {@code UserStoryUser} of the {@code UserStory} that we are building.
     */
    public UserStoryBuilder withUser(String user) {
        this.user = new UserStoryUser(user);
        return this;
    }

    /**
     * Sets the {@code UserStoryFunction} of the {@code UserStory} that we are building.
     */
    public UserStoryBuilder withFunction(String function) {
        this.function = new UserStoryFunction(function);
        return this;
    }

    /**
     * Sets the {@code UserStoryReason} of the {@code UserStory} that we are building.
     */
    public UserStoryBuilder withReason(String reason) {
        this.reason = new UserStoryReason(reason);
        return this;
    }

    /**
     * Sets the {@code Status} of the {@code UserStory} that we are building.
     */
    public UserStoryBuilder withStatus(String status) {
        this.status = new Status(status);
        return this;
    }

    public UserStory build() {
        return new UserStory(importance, user, function, reason);
    }

}
