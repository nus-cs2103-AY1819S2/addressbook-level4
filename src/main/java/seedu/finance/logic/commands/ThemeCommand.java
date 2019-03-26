package seedu.finance.logic.commands;

import seedu.finance.commons.core.Messages;
import seedu.finance.logic.CommandHistory;
import seedu.finance.logic.commands.exceptions.CommandException;
import seedu.finance.model.Model;

/**
 * Changes the theme of the application
 */
public class ThemeCommand extends Command {

    public static final String COMMAND_WORD = "theme";
    public static final String COMMAND_ALIAS = "colour";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Changes the theme of the Finance Tracker "
            + "according to the input given by user.\n"
            + "Parameters: THEME (must be Light, Dark, Blue or Pink; case-insensitive)\n"
            + "Example: " + COMMAND_WORD + " Dark";

    public static final String MESSAGE_THEME_SUCCESS = "Successfully changed to %1$s theme";

    private String theme;

    public ThemeCommand(String theme) {
        this.theme = formatTheme(theme);
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        if (!isValidTheme(this.theme)) {
            throw new CommandException(Messages.MESSAGE_INVALID_THEME);
        }
        return new CommandResult(String.format(MESSAGE_THEME_SUCCESS, theme), false, false,
                true, this.theme);
    }

    /**
     * This methods formats the input String so that user input can be case-insensitive
     * @param theme the theme to change to
     * @return the theme to change to with first character being in upper-case and rest in lower-case
     */
    private String formatTheme(String theme) {
        theme = (theme.trim()).toLowerCase();
        return theme.substring(0, 1).toUpperCase() + theme.substring(1);
    }

    /**
     * Method to check if user input is the correct theme
     * @param theme the user input
     * @return true if the theme is in the library
     */
    private boolean isValidTheme(String theme) {
        return theme.equals("Dark") || theme.equals("Light")
                || theme.equals("Blue") || theme.equals("Pink");
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof ThemeCommand)) {
            return false;
        }

        ThemeCommand e = (ThemeCommand) other;
        return e.theme == this.theme;
    }
}
