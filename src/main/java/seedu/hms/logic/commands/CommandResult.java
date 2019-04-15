package seedu.hms.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.ArrayList;
import java.util.Objects;
import java.util.Optional;

import seedu.hms.commons.core.index.Index;

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
     * Stats should be shown to the user.
     */
    private final boolean showStats;

    /**
     * The indices of the stats to be shown.
     */
    private final Optional<ArrayList<Index>> optionalIndexList;

    /**
     * The application should exit.
     */
    private final boolean exit;

    /**
     * Constructs a {@code CommandResult} with the specified fields.
     */
    public CommandResult(String feedbackToUser, boolean showHelp, boolean exit) {
        this.feedbackToUser = requireNonNull(feedbackToUser);
        this.showHelp = showHelp;
        this.showStats = false;
        this.optionalIndexList = Optional.empty();
        this.exit = exit;
    }

    /**
     * Constructs a {@code CommandResult} with the specified fields, with showStats.
     */
    public CommandResult(String feedbackToUser, boolean showHelp, boolean showStats,
                         Optional<ArrayList<Index>> optionalIndexList, boolean exit) {
        this.feedbackToUser = requireNonNull(feedbackToUser);
        this.showHelp = showHelp;
        this.showStats = showStats;
        this.optionalIndexList = optionalIndexList;
        this.exit = exit;
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

    public boolean isShowStats() {
        return showStats;
    }

    public Optional<ArrayList<Index>> getIndexList() {
        return optionalIndexList;
    }

    public boolean isExit() {
        return exit;
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
            && showStats == otherCommandResult.showStats
            && exit == otherCommandResult.exit;
    }

    @Override
    public int hashCode() {
        return Objects.hash(feedbackToUser, showHelp, showStats, exit);
    }

}
