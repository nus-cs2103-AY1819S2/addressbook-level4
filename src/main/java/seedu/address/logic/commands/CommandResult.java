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

    private final boolean showCards;

    private final UpdateStorage updateStorage;

    /**
     *  Name of lesson to be deleted. This would've been done in another way for CS2103T but
     *  sadly there isn't time to rewrite logic.
     */
    private final String deleteLessonName;

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
        this.deleteLessonName = null;
        this.showCards = false;
    }

    /**
     * Constructs a {@code CommandResult} with the specified fields.
     */
    public CommandResult(String feedbackToUser, boolean showCards) {
        this.feedbackToUser = requireNonNull(feedbackToUser);
        this.showQuiz = false;
        this.showHelp = false;
        this.exit = false;
        this.updateStorage = UpdateStorage.NONE;
        this.deleteLessonName = null;
        this.showCards = showCards;
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
        this.deleteLessonName = null;
        this.showCards = false;
    }

    public CommandResult (String feedbackToUser, UpdateStorage type) {
        this.feedbackToUser = requireNonNull(feedbackToUser);
        this.showQuiz = false;
        this.showHelp = false;
        this.exit = false;
        this.updateStorage = type;
        this.deleteLessonName = null;
        this.showCards = false;
    }

    public CommandResult (String feedbackToUser, String deleteLessonName) {
        this.feedbackToUser = requireNonNull(feedbackToUser);
        this.showQuiz = false;
        this.showHelp = false;
        this.exit = false;
        this.updateStorage = UpdateStorage.DELETE;
        this.deleteLessonName = deleteLessonName;
        this.showCards = false;
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

    public boolean isShowCards() {
        return showCards;
    }

    public UpdateStorage getUpdateStorageType() {
        return updateStorage;
    }

    public String getDeleteLessonName() {
        return deleteLessonName;
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
