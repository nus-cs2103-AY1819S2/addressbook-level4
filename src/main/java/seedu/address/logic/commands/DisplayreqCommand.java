package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.logic.CommandHistory;
import seedu.address.model.Model;

/**
 * Command to display all requirements of course
 */
public class DisplayreqCommand extends Command {
    public static final String COMMAND_WORD = "displayreq";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Displays lists of requirement based on given "
            + "EXAMPLE: " + COMMAND_WORD;
    public static final String MESSAGE_SUCCESS = "Listed all requirements!";

    @Override
    public CommandResult execute(Model model, CommandHistory history) {
        requireNonNull(model);
        model.updateRequirementStatusList();
        return new CommandResult(String.format(MESSAGE_SUCCESS));
    }
}
