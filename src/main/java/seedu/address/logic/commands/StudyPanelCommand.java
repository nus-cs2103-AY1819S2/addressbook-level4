package seedu.address.logic.commands;

/**
 * Represents a command result that requires the panel to reload.
 */
public class StudyPanelCommand extends CommandResult {

    public StudyPanelCommand(String feedbackToUser) {
        super(feedbackToUser);
    }
}
