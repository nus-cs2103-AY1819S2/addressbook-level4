package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.Objects;

import seedu.address.logic.Mode;

/**
 * Represents the result of a command execution.
 */
public class CommandResult {

    private final String feedbackToUser;

    /**
     * Help information should be shown to the user.
     */
    private final boolean showHelp;

    /**
     * The application should exit.
     */
    private final boolean exit;

    /**
     * The application should change mode if not null.
     */
    private final Mode toMode;

    /**
     * The application should change display in StatisticsMode if true.
     */
    private final boolean isDaily;

    /**
     * The application should change display in StatisticsMode if true.
     */
    private final boolean isMonthly;

    /**
     * The application should change display in StatisticsMode if true.
     */
    private final boolean isYearly;

    /**
     * Constructs a {@code CommandResult} with the specified fields.
     */
    public CommandResult(String feedbackToUser, boolean showHelp, boolean exit) {
        this.feedbackToUser = requireNonNull(feedbackToUser);
        this.showHelp = showHelp;
        this.exit = exit;
        this.toMode = null;
        this.isDaily = true;
        this.isMonthly = false;
        this.isYearly = false;
    }

    /**
     * Constructs a {@code CommandResult} with the specified fields including toMode.
     */
    public CommandResult(String feedbackToUser, boolean showHelp, boolean exit, Mode mode) {
        this.feedbackToUser = requireNonNull(feedbackToUser);
        this.showHelp = showHelp;
        this.exit = exit;
        this.toMode = mode;
        this.isDaily = true;
        this.isMonthly = false;
        this.isYearly = false;
    }

    /**
     * Constructs a {@code CommandResult} with the specified fields including toMode.
     */
    public CommandResult(String feedbackToUser, boolean showHelp, boolean exit, Mode mode, boolean isDaily,
                         boolean isMonthly, boolean isYearly) {
        this.feedbackToUser = requireNonNull(feedbackToUser);
        this.showHelp = showHelp;
        this.exit = exit;
        this.toMode = mode;
        this.isDaily = isDaily;
        this.isMonthly = isMonthly;
        this.isYearly = isYearly;
    }

    /**
     * Constructs a {@code CommandResult} with the specified {@code feedbackToUser},
     * and other fields set to their default value.
     */
    public CommandResult(String feedbackToUser) {
        this(feedbackToUser, false, false);
    }

    public String getFeedbackToUser() {
        return feedbackToUser;
    }

    public boolean isShowHelp() {
        return showHelp;
    }

    public boolean isExit() {
        return exit;
    }

    public Mode newModeStatus() {
        return toMode;
    }

    public boolean isDaily() {
        return isDaily;
    }

    public boolean isMonthly() {
        return isMonthly;
    }

    public boolean isYearly() {
        return isYearly;
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
        return feedbackToUser.equals(otherCommandResult.feedbackToUser) && showHelp == otherCommandResult.showHelp
                && exit == otherCommandResult.exit
                && toMode == otherCommandResult.toMode;
    }

    @Override
    public int hashCode() {
        return Objects.hash(feedbackToUser, showHelp, exit, toMode);
    }

}
