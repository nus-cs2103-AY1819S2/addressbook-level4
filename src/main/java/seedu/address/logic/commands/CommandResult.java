package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.Objects;

/**
 * Represents the result of a command execution.
 */
public class CommandResult {

    private final String feedbackToUser;

    /** Help information should be shown to the user. */
    private final boolean showHelp;

    /** The application should exit. */
    private final boolean exit;

    /** Show a different panel to the user, depending on the command result */
    public enum ShowPanel {
        NO_CHANGE, MED_HIST_PANEL, APPOINTMENT_PANEL, PRESC_PANEL
    }

    /** Show a different browser panel to the user, depending on the command result */
    public enum ShowBrowser {
        NO_CHANGE, MED_HIST_BROWSER, PATIENT_BROWSER, DOCTOR_BROWSER, PRESCRIPTION_BROWSER
    }

    private ShowPanel showPanel = ShowPanel.NO_CHANGE;

    private ShowBrowser showBrowser = ShowBrowser.NO_CHANGE;

    /**
     * Constructs a {@code CommandResult} with the specified fields.
     */
    public CommandResult(String feedbackToUser, boolean showHelp, boolean exit) {
        this.feedbackToUser = requireNonNull(feedbackToUser);
        this.showHelp = showHelp;
        this.exit = exit;
    }

    /**
     * Constructs a {@code CommandResult} with the specified {@code feedbackToUser},
     * and other fields set to their default value.
     */
    public CommandResult(String feedbackToUser) {
        this(feedbackToUser, false, false);
    }

    /**
     * Constructs a {@code CommandResult} that will switch panels to display in the UI
     */
    public CommandResult(String feedbackToUser, ShowPanel showPanel) {
        this(feedbackToUser);
        this.showPanel = showPanel;
    }

    /**
     * Constructs a {@code CommandResult} that will switch browser panels to display in the UI
     */
    public CommandResult(String feedbackToUser, ShowBrowser showBrowser) {
        this(feedbackToUser);
        this.showBrowser = showBrowser;
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

    public ShowPanel getShowPanel() {
        return showPanel;
    }

    public ShowBrowser getShowBrowser() {
        return showBrowser;
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
