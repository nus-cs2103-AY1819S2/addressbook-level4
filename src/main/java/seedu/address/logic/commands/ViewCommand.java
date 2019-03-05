package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.logic.CommandHistory;
import seedu.address.model.Model;

/**
 * Lists all restaurants in the food diary to the user.
 */
public class ViewCommand extends Command {

    public static final String COMMAND_WORD = "view";

    public static final String MESSAGE_SUCCESS = "View personalised statistics for ";


    @Override
    public CommandResult execute(Model model, CommandHistory history) {
        requireNonNull(model);
        int size = model.getSize();
        String name = model.getName();
        int numReviews = model.getNumReviews();
        return new CommandResult(MESSAGE_SUCCESS + name + ". You have a total of "
                + numReviews + " reviews and " + size + " restaurants.");
    }
}
