package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.Objects;

/**
 * Represents the result of a command execution.
 */
public class CommandResult {

    /**
     * Enum for all possible storage request types.
     */
    public enum UpdateStorage {
        NONE,
        SAVE,
        LOAD,
        DELETE
    }

    private final String feedbackToUser;

    /** Quiz UI should be shown to the user. */
    private final boolean showQuiz;

    /** Help information should be shown to the user. */
    private final boolean showHelp;

    /**
     * If lesson is changed, update UI and save to disk.
     */
    //private final boolean updateStorage;

    private final UpdateStorage updateStorage;

    /** The application should exit. */
    private final boolean exit;


    /**
     * Constructs a {@code CommandResult} with the specified {@code feedbackToUser},
     * and other fields set to their default value.
     */
    public CommandResult(String feedbackToUser) {
        this.feedbackToUser = requireNonNull(feedbackToUser);
        this.showQuiz = false;
        this.showHelp = false;
        this.exit = false;
        this.updateStorage = UpdateStorage.NONE;
    }

    /**
     * Constructs a {@code CommandResult} with the specified fields.
     */
    public CommandResult(String feedbackToUser, boolean showQuiz, boolean showHelp, boolean exit) {
        this.feedbackToUser = requireNonNull(feedbackToUser);
        this.showQuiz = showQuiz;
        this.showHelp = showHelp;
        this.exit = exit;
        this.updateStorage = UpdateStorage.NONE;
    }

    //TODO: Remove when refactored

    /**
     * Constructs a {@code CommandResult} with the specified fields.
     * @param feedbackToUser
     * @param updateStorage
     */
    public CommandResult (String feedbackToUser, boolean updateStorage) {
        this.feedbackToUser = requireNonNull(feedbackToUser);
        this.showQuiz = false;
        this.showHelp = false;
        this.exit = false;

        if (updateStorage) {
            this.updateStorage = UpdateStorage.SAVE;
        } else {
            this.updateStorage = UpdateStorage.NONE;
        }
    }

    public CommandResult (String feedbackToUser, UpdateStorage type) {
        this.feedbackToUser = requireNonNull(feedbackToUser);
        this.showQuiz = false;
        this.showHelp = false;
        this.exit = false;
        this.updateStorage = type;
    }

    public String getFeedbackToUser() {
        return feedbackToUser;
    }

    public boolean isShowQuiz() {
        return showQuiz;
    }

    public boolean isShowHelp() {
        return showHelp;
    }

    public boolean isExit() {
        return exit;
    }

    /**
     * If lesson is changed, update UI and save to disk.
     */
    //public boolean isUpdateStorage() {
    //    return updateStorage;
    //}

    public UpdateStorage getUpdateStorageType() {
        return updateStorage;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof CommandResult)) {
            return false;
        }

        CommandResult otherCommandResult = (CommandResult) other;
        return feedbackToUser.equals(otherCommandResult.feedbackToUser)
                && showHelp == otherCommandResult.showHelp
                && exit == otherCommandResult.exit;
    }

    @Override
    public int hashCode() {
        return Objects.hash(feedbackToUser, showHelp, exit);
    }
}
