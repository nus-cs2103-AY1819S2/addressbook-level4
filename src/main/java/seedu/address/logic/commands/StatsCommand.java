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
        ArrayList<String> onlyCommands = new ArrayList<>();
        ArrayList<String> outputStatistics = new ArrayList<>();
        long elapsedTime = history.getElapsedTime(System.nanoTime());
        // to be integrated to stats results
        System.out.println("Duration: " + elapsedTime + " seconds");

        if (previousCommands.isEmpty()) {
            return new CommandResult(MESSAGE_NO_HISTORY);
        }

        for (String string : previousCommands) {
            onlyCommands.add(string.split(" ")[0]); // Take first word
        }
        Set<String> set = new HashSet<>(onlyCommands);
        // get frequency of each command
        for (String s : set) {
            outputStatistics.add(s + ": " + Collections.frequency(onlyCommands, s));
        }

        return new CommandResult(String.format(MESSAGE_SUCCESS, String.join("\n", outputStatistics)));
    }

}
