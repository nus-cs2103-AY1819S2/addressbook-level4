package seedu.address.logic.commands;

import seedu.address.commons.core.Messages;
import seedu.address.logic.AnswerCommandResultType;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;

/**
 * Displays to the user the next question in the folder during the test session.
 * This command is valid only when user is in a test session.
 */
public class NextCommand extends Command {

    public static final String COMMAND_WORD = "next";

    public static final String MESSAGE_NEXT_QUESTION_SUCCESS = "Displaying the next question in test session";

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        if (!model.checkIfInsideTestSession()) {
            throw new CommandException(Messages.MESSAGE_INVALID_COMMAND_OUTSIDE_TEST_SESSION);
        }

        return new CommandResult(MESSAGE_NEXT_QUESTION_SUCCESS, false, false, false, false, null, false,
                AnswerCommandResultType.NOT_ANSWER_COMMAND);
    }

}
