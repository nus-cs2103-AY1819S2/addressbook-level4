package seedu.finance.logic.commands;

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

    /** The application will change theme **/
    private boolean changeTheme;

    /** The application will update budget **/
    private boolean changeBudget;

    /** The application will update category budget **/
    private boolean changeCategoryBudget;

    /** The application will show the summary **/
    private boolean showSummary;

    private String themeToChange;
    /**
     * Constructs a {@code CommandResult} with the specified fields.
     */
    public CommandResult(String feedbackToUser, boolean showHelp, boolean exit,
                         boolean changeTheme, String theme) {
        this.feedbackToUser = requireNonNull(feedbackToUser);
        this.showHelp = showHelp;
        this.exit = exit;
        this.changeTheme = changeTheme;
        this.themeToChange = theme;
    }

    /**
     * Constructs a {@code CommandResult} with the specified fields.
     * For changing of budget
     */
    public CommandResult(String feedbackToUser, boolean changeBudget, boolean showHelp, boolean exit) {
        this.feedbackToUser = requireNonNull(feedbackToUser);
        this.changeBudget = changeBudget;
        this.showHelp = showHelp;
        this.exit = exit;
    }

    /**
     * Constructs a {@code CommandResult} with the specified fields.
     * For showing of summary
     */
    public CommandResult(boolean showSummary, String feedbackToUser, boolean showHelp, boolean exit) {
        this.showSummary = showSummary;
        this.feedbackToUser = requireNonNull(feedbackToUser);
        this.showHelp = showHelp;
        this.exit = exit;
    }

    /**
     * Constructs a {@code CommandResult} with specified fields.
     * Given by AB4
     */
    public CommandResult(String feedbacktoUser, boolean showHelp, boolean exit) {
        this.feedbackToUser = requireNonNull(feedbacktoUser);
        this.showHelp = showHelp;
        this.exit = exit;
    }
    /**
     * Constructs a {@code CommandResult} with the specified {@code feedbackToUser},
     * and other fields set to their default value.
     */
    public CommandResult(String feedbackToUser) {
        this(feedbackToUser, false, false, false);
    }

    public CommandResult(String feedbackToUser, boolean changeCategoryBudget) {
        this(feedbackToUser, false, false, false);
        this.changeBudget = false;

    }

    public String getThemeToChange() {
        return themeToChange;
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

    public boolean isSwitchTheme() {
        return changeTheme;
    }

    public boolean isChangeBudget() {
        return changeBudget;
    }

    public boolean isChangeCategoryBudget() {
        return changeCategoryBudget;
    }

    public void changeCategoryBudget() {
        this.changeCategoryBudget = true;
    }

    public boolean isShowSummary() {
        return showSummary;
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
