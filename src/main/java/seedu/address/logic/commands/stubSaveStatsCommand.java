package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.io.IOException;

import seedu.address.logic.CommandHistory;

import seedu.address.model.Model;
import seedu.address.model.statistics.PlayerStatistics;



/**
 * Lists all the commands entered by user from the start of app launch.
 *
 */
public class stubSaveStatsCommand extends Command {

    public static final String COMMAND_WORD = "save";
    public static final String MESSAGE_SUCCESS = "Statistics Data Saved";
    public static PlayerStatistics playerStats;
    @Override
    public CommandResult execute(Model model, CommandHistory history) {
        requireNonNull(history);
        // get the obj
        this.playerStats = model.getPlayerStats();
        // throw obj into storage
        try {
            this.playerStats.saveToStorage(this.playerStats);
            System.out.println("saveToStorage");
        }
        catch (IOException ioe) {
            System.out.println("IO Exception");
        }
        return new CommandResult(String.format(MESSAGE_SUCCESS));
    }

}
