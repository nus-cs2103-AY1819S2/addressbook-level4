package seedu.address.logic.commands.quiz;

import static seedu.address.logic.commands.exceptions.CommandException.MESSAGE_EXPECTED_QUIZ_MODEL;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.modelmanager.Model;
import seedu.address.model.modelmanager.QuizModel;

/**
 * Display the total attempts, streak and progress of the current session
 */
public class QuizStatusCommand implements Command {
    public static final String COMMAND_WORD = "\\status";
    public static final String MESSAGE_USAGE =
        COMMAND_WORD + ": Shows the total attempts, streak and progress for the current session.\n"
        + "Example: " + COMMAND_WORD;
    public static final String MESSAGE_RESULT = "Total attempts: %1$s\n"
        + "Total correct answer: %2$s\n"
        + "Current progress: %3$s";

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        // CommandException will be thrown if and only if LogicManager passes in the incorrect Model
        // In other words, only incorrect code will result in a CommandException being thrown
        if (!(model instanceof QuizModel)) {
            throw new CommandException(MESSAGE_EXPECTED_QUIZ_MODEL);
        }

        QuizModel quizModel = (QuizModel) model;

        return new CommandResult(String.format(MESSAGE_RESULT, quizModel.getQuizTotalAttempts(),
            quizModel.getQuizTotalCorrectQuestions(), quizModel.getCurrentProgress()));
    }
}
