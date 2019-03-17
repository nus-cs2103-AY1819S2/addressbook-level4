package seedu.address.logic.commands;

import seedu.address.logic.CommandHistory;
import seedu.address.model.modelmanager.quizmodel.QuizModel;

/**
 * Format full help instructions for every command for display.
 */
public class QuizHelpCommand extends QuizCommand {

    public static final String COMMAND_WORD = "\\help";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Shows program usage instructions.\n"
            + "Example: " + COMMAND_WORD;

    public static final String MESSAGE_QUIZ_USAGE = QuizAnswerCommand.MESSAGE_USAGE
        + "\n" + QuizHelpCommand.MESSAGE_USAGE;

    @Override
    public CommandResult execute(QuizModel model, CommandHistory history) {
        return new CommandResult(MESSAGE_QUIZ_USAGE);
    }
}
