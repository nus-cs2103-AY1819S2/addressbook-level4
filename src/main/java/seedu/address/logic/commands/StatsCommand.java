package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import seedu.address.logic.CommandHistory;
import seedu.address.model.Model;

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
        ArrayList<String> previousCommands = new ArrayList<>(history.getHistory());
        ArrayList<String> outputStatistics = new ArrayList<String>();

        if (previousCommands.isEmpty()) {
            return new CommandResult(MESSAGE_NO_HISTORY);
        }
        // parse through commands and count how many usage
        // remove the key command words from previousCommand
        // and parse it instead of raw input

        Set<String> set = new HashSet<String>(previousCommands);

        for (String s : set) {
            outputStatistics.add(s + ": " + Collections.frequency(previousCommands, s));
        }

        // System.out.println();
        //Collections.reverse(previousCommands);
        return new CommandResult(String.format(MESSAGE_SUCCESS, String.join("\n", outputStatistics)));
    }

}
