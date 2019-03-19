package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.logic.CommandHistory;
import seedu.address.model.Model;

/**
 * Generates an interview date list from existing persons in the addressbook.
 */
public class GenerateInterviewsCommand extends Command {

    public static final String COMMAND_WORD = "generateInterviews";
    public static final String MESSAGE_SUCCESS = "Interviews generated";

    @Override
    public CommandResult execute(Model model, CommandHistory history) {
        requireNonNull(model);
        model.generateInterviews();
        model.commitAddressBook();
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
