package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import seedu.address.logic.CommandHistory;
import seedu.address.model.Model;
import seedu.address.model.statistics.PlayerStatistics;

/**
 * Lists all the commands entered by user from the start of app launch.
 *
 */
public class StatsCommand extends Command {

    public static final String COMMAND_WORD = "stats";
    public static final String MESSAGE_SUCCESS = "CURRENT STATISTICS:\n%1$s";
    public static final String MESSAGE_NO_HISTORY = "There are no statistics to show";

    @Override
    public CommandResult execute(Model model, CommandHistory history) {
        requireNonNull(history);
        ArrayList<String> outputStatistics = new ArrayList<>();

        PlayerStatistics playerStats = model.getPlayerStats();
        String movesLeft = "Moves Left : " + playerStats.getMovesLeft();
        String hitCount = "Successful Hit : " + playerStats.getHitCount();
        String missCount = "Misses : " + playerStats.getMissCount();

        //        if (previousCommands.isEmpty()) {
        //            return new CommandResult(MESSAGE_NO_HISTORY);
        //        }
        //
        //        for (String string : previousCommands) {
        //            onlyCommands.add(string.split(" ")[0]); // Take first word
        //        }
        //        Set<String> set = new HashSet<>(onlyCommands);
        // get frequency of each command
        outputStatistics.add(movesLeft);
        outputStatistics.add(hitCount);
        outputStatistics.add(missCount);

        return new CommandResult(String.format(MESSAGE_SUCCESS, String.join("\n", outputStatistics)));
    }

}
