package seedu.travel.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.travel.logic.CommandHistory;
import seedu.travel.model.Model;
import seedu.travel.model.TravelBuddy;

/**
 * Clears the travel book.
 */
public class ClearCommand extends Command {

    public static final String COMMAND_WORD = "clear";
    public static final String COMMAND_ALIAS = "c";
    public static final String MESSAGE_SUCCESS = "TravelBuddy has been cleared!";


    @Override
    public CommandResult execute(Model model, CommandHistory history) {
        requireNonNull(model);
        model.setTravelBuddy(new TravelBuddy());
        model.commitTravelBuddy();
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
