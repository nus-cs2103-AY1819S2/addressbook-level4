package seedu.address.logic.commands;

/**
 * Represents a command result that requires the panel to reload.
 */
public class UpdatePanelCommandResult extends CommandResult {

    public UpdatePanelCommandResult(String feedbackToUser) {
        super(feedbackToUser);
    }
}
