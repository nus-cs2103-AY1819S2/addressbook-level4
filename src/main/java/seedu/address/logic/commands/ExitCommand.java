package seedu.address.logic.commands;

import seedu.address.logic.CommandHistory;
import seedu.address.model.Model;

/**
 * Terminates the program.
 */
public class ExitCommand extends Command {

    public static final String COMMAND_WORD = "exit";

    public static final String MESSAGE_EXIT_QUIZ = "Quiz mode ended. You got %s/%s correct!";
    public static final String MESSAGE_EXIT_ACKNOWLEDGEMENT = "Exiting Acquizition as requested ...";

    @Override
    public CommandResult execute(Model model, CommandHistory history) {
        if (model.getQuizMode() != 0) {
            model.setQuizMode(0);
            model.setSelectedFlashcard(null);
            model.commitCardCollection(QuizCommand.COMMAND_WORD);
            return new CommandResult(
                String.format(MESSAGE_EXIT_QUIZ,
                    String.valueOf(model.getQuizGood().getValue()),
                    String.valueOf(model.getQuizGood().getValue() + model.getQuizBad().getValue())));
        }
        return new CommandResult(MESSAGE_EXIT_ACKNOWLEDGEMENT, false, true);
    }

}
