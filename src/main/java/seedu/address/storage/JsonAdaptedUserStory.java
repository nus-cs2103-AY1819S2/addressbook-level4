package seedu.address.storage;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.project.Status;
import seedu.address.model.project.UserStory;
import seedu.address.model.project.UserStoryFunction;
import seedu.address.model.project.UserStoryImportance;
import seedu.address.model.project.UserStoryReason;
import seedu.address.model.project.UserStoryUser;

/**
 * Jackson-friendly version of {@link UserStory}.
 */
class JsonAdaptedUserStory {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "User story's %s field is missing!";

    private final String importance;
    private final String user;
    private final String function;
    private final String reason;
    private final String status;

    /**
     * Constructs a {@code JsonAdaptedUserStory} with the given user story details.
     */
    @JsonCreator
    public JsonAdaptedUserStory(@JsonProperty("importance") String importance, @JsonProperty("user") String user,
                                @JsonProperty("function") String function, @JsonProperty("reason") String reason,
                                @JsonProperty("status") String status) {
        this.importance = importance;
        this.user = user;
        this.function = function;
        this.reason = reason;
        this.status = status;
    }

    /**
     * Converts a given {@code User Story} into this class for Jackson use.
     */
    public JsonAdaptedUserStory(UserStory source) {
        this.importance = source.getUserStoryImportance();
        this.user = source.getUserStoryUser();
        this.function = source.getUserStoryFunction();
        this.reason = source.getUserStoryReason();
        this.status = source.getUserStoryStatus();
    }

    /**
     * Converts this Jackson-friendly adapted user story object into the model's {@code UserStory} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted user story.
     */
    public UserStory toModelType() throws IllegalValueException {

        if (importance == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    UserStoryImportance.class.getSimpleName()));
        }
        if (!UserStoryImportance.isValidImportanceLevel(importance)) {
            throw new IllegalValueException(UserStoryImportance.MESSAGE_CONSTRAINTS);
        }
        final UserStoryImportance modelUserStoryImportance = new UserStoryImportance(importance);

        if (user == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    UserStoryUser.class.getSimpleName()));
        }
        if (!UserStoryUser.isValidName(user)) {
            throw new IllegalValueException(UserStoryUser.MESSAGE_CONSTRAINTS);
        }
        final UserStoryUser modelUserStoryUser = new UserStoryUser(user);

        if (function == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    UserStoryFunction.class.getSimpleName()));
        }
        if (!UserStoryFunction.isValidDescription(function)) {
            throw new IllegalValueException(UserStoryFunction.MESSAGE_CONSTRAINTS);
        }
        final UserStoryFunction modelUserStoryFunction = new UserStoryFunction(function);

        if (reason == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    UserStoryReason.class.getSimpleName()));
        }
        final UserStoryReason modelUserStoryReason = new UserStoryReason(reason);

        if (status == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    Status.class.getSimpleName()));
        }
        final Status modelStatus = new Status(status);

        return new UserStory(modelUserStoryImportance, modelUserStoryUser, modelUserStoryFunction,
                modelUserStoryReason, modelStatus);
    }

}
