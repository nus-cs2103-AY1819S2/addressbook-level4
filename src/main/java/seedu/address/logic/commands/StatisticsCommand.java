package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.ArrayList;

import seedu.address.logic.CommandHistory;
import seedu.address.model.Model;

/**
 * Calculates and displays statistics of all existing apparel in Fashion Match.
 */

public class StatisticsCommand extends Command {

    public static final String COMMAND_WORD = "stats";
    public static final String ALTERNATE_COMMAND_WORD = "statistics";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Calculate the statistics for the different apparels. "
            + "Example: " + COMMAND_WORD
            + " OR " + ALTERNATE_COMMAND_WORD;


    public static final String MESSAGE_SUCCESS = "Current Statistics:\n%1$s";

    @Override
    public CommandResult execute(Model model, CommandHistory history) {
        requireNonNull(history);
        ArrayList<String> outputStats = new ArrayList<>();

        String favApparel = model.getFavApparel();
        String favColor = model.getFavColor();
        String totalApparel = "Total Apparel in Wardrobe : " + model.getFilteredApparelList().size();
        String totalColor = model.getTotalColor();
        String leastFavApparel = model.getLeastFavApparel();
        String cleanOrDirty = model.getCleanOrDirty();

        outputStats.add(favApparel);
        outputStats.add(favColor);
        outputStats.add(totalApparel);
        outputStats.add(totalColor);
        outputStats.add(leastFavApparel);
        outputStats.add(cleanOrDirty);

        CommandResult outputCommand = new CommandResult(
                String.format(MESSAGE_SUCCESS, String.join("\n", outputStats)));
        return outputCommand;
    }

    @Override
    public boolean equals(Object other) {
        return other == this //short circuit if same object
                || (other instanceof StatisticsCommand); // instance of handles nulls
    }

}
