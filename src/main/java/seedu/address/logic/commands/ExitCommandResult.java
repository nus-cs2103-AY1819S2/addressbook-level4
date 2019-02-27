package seedu.address.logic.commands;

/**
 * Represents a command result that exits the application.
 */
public class ExitCommandResult extends CommandResult {

    public ExitCommandResult(String feedbackToUser) {
        super(feedbackToUser);
    }
}
