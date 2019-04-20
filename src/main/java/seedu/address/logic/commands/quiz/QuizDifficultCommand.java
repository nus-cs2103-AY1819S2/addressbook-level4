package seedu.address.logic.commands.quiz;

import static java.util.Objects.requireNonNull;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.modelmanager.Model;
import seedu.address.model.modelmanager.QuizModel;
import seedu.address.model.quiz.QuizCard;

/**
 * Labels a card as difficult or not difficult.
 */
public class QuizDifficultCommand extends QuizCommand {
    public static final String COMMAND_WORD = "\\difficult";
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Label this current question as difficult.\n";
    public static final String MESSAGE_SUCCESS = "This question is labelled as %1$s.";

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);
        QuizModel quizModel = requireQuizModel(model);
        QuizCard card = quizModel.getCurrentQuizCard();

        if (quizModel.toggleIsCardDifficult(card.getIndex())) {
            return new CommandResult(String.format(MESSAGE_SUCCESS, "difficult"));
        }
        return new CommandResult(String.format(MESSAGE_SUCCESS, "not difficult"));
    }
}
