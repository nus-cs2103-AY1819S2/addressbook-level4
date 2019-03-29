package seedu.address.logic.commands.quiz;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.model.modelmanager.Model;

/**
 * Format full help instructions for every command for display.
 */
public class QuizHelpCommand implements Command {

    public static final String COMMAND_WORD = "\\help";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Shows program usage instructions.\n"
            + "Example: " + COMMAND_WORD + "\n";

    public static final String MESSAGE_QUIZ_USAGE = QuizAnswerCommand.MESSAGE_USAGE
        + "\n" + QuizDifficultCommand.MESSAGE_USAGE
        + "\n" + QuizHelpCommand.MESSAGE_USAGE
        + "\n" + QuizStatusCommand.MESSAGE_USAGE;

    /**
     * Executes the command and returns the result message.
     *
     * @param model {@code Model} which the command should operate on.
     * @param history {@code CommandHistory} which the command should operate on.
     * @return feedback message of the operation result for display
     */
    @Override
    public CommandResult execute(Model model, CommandHistory history) {
        return new CommandResult(MESSAGE_QUIZ_USAGE);
    }
}
