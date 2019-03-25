package seedu.address.logic.commands.quiz;

import static seedu.address.logic.commands.exceptions.CommandException.MESSAGE_EXPECTED_QUIZ_MODEL;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.modelmanager.Model;
import seedu.address.model.modelmanager.QuizModel;
import seedu.address.model.quiz.QuizCard;

/**
 * Label a card as difficult or not
 */
public class QuizDifficultCommand implements Command {
    public static final String COMMAND_WORD = "\\difficult";
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Label this current question as difficult.\n";
    public static final String MESSAGE_SUCCESS = "This question is labelled as %1$s.";
    /**
     * Executes the command and returns the result message.
     *
     * @param model {@link QuizModel} which the command should operate on.
     * @param history {@code CommandHistory} which the command should operate on.
     * @return feedback message of the operation result for display
     * @throws CommandException If the {@link Model} passed in is not a {@link QuizModel}
     */
    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        // CommandException will be thrown if and only if LogicManager passes in the incorrect Model
        // In other words, only incorrect code will result in a CommandException being thrown
        if (!(model instanceof QuizModel)) {
            throw new CommandException(MESSAGE_EXPECTED_QUIZ_MODEL);
        }

        QuizModel quizModel = (QuizModel) model;
        QuizCard card = quizModel.getCurrentQuizCard();

        if (quizModel.toggleIsCardDifficult(card.getIndex())) {
            return new CommandResult(String.format(MESSAGE_SUCCESS, "difficult"));
        }
        return new CommandResult(String.format(MESSAGE_SUCCESS, "not difficult"));
    }
}
