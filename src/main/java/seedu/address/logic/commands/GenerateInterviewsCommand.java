package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.interviews.exceptions.InterviewsPresentException;

/**
 * Generates an interview date list from existing persons in the addressbook.
 */
public class GenerateInterviewsCommand extends Command {

    public static final String COMMAND_WORD = "generateInterviews";
    public static final String MESSAGE_SUCCESS = "Interviews generated";
    public static final String MESSAGE_PRESENT = "Interviews already present";

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);
        try {
            model.generateInterviews();
            model.commitAddressBook();
        } catch (InterviewsPresentException e) {
            throw new CommandException(MESSAGE_PRESENT);
        }
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
