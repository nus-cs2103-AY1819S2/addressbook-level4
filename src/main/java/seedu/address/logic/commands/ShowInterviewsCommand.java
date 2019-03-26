package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.logic.CommandHistory;
import seedu.address.model.Model;

/**
 * Shows an interview date list from existing persons in the addressbook.
 */
public class ShowInterviewsCommand extends Command {

    public static final String COMMAND_WORD = "showInterviews";
    public static final String MESSAGE_INTERVIEWS_NOT_PRESENT = "Interviews not present";

    @Override
    public CommandResult execute(Model model, CommandHistory history) {
        requireNonNull(model);
        String result = model.getInterviews().toString();
        if (result.isEmpty()) {
            return new CommandResult(MESSAGE_INTERVIEWS_NOT_PRESENT);
        }
        return new CommandResult(result);
    }
}
