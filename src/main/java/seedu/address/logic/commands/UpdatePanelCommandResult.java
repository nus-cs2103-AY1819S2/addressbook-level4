package seedu.address.logic.commands;

/**
 * Represents a command result that requires the panel to reload.
 */
public class UpdatePanelCommandResult extends CommandResult {

    public UpdatePanelCommandResult(String feedbackToUser) {
        super(feedbackToUser);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof UpdatePanelCommandResult // instanceof handles nulls
                && feedbackToUser.equals(((UpdatePanelCommandResult) other).feedbackToUser));
    }
}

