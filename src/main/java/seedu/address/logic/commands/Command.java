package seedu.address.logic.commands;

import java.util.ArrayList;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.parser.exceptions.InvalidCommandModeException;
import seedu.address.model.AppMode;
import seedu.address.model.Model;

/**
 * Represents a command with hidden internal logic and the ability to be executed.
 */
public abstract class Command {

    private ArrayList<AppMode.Modes> allowedModeList = new ArrayList<>();

    public void setAllowedModeList(ArrayList<AppMode.Modes> allowedModeList) {
        this.allowedModeList = allowedModeList;
    }

    /**
     * Executes the command and returns the result message.
     *
     * @param model {@code Model} which the command should operate on.
     * @param history {@code CommandHistory} which the command should operate on.
     * @return feedback message of the operation result for display
     * @throws CommandException If an error occurs during command execution.
     */
    public abstract CommandResult execute(Model model, CommandHistory history) throws CommandException,
            InvalidCommandModeException;

    /**
     * @return true if command can operate in (@param mode)
     * else return false
     */
    public boolean isValidMode (AppMode.Modes mode) {
        return allowedModeList.contains(mode);
    }

    /**
     * @return String of valid operation modes.
     */
    public String allowedModeListAsString () {
        String modeList = "";
        boolean first = true;
        for (AppMode.Modes modeTmp : this.allowedModeList) {
            if (!first) {
                modeList = modeList + ", ";
            } else {
                first = false;
            }
            modeList = modeList + modeTmp.toString();
        }
        return modeList;
    }

}
