package seedu.address.logic.commands.quiz;

import java.util.Arrays;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.modelmanager.Model;
import seedu.address.model.modelmanager.quiz.Quiz;
import seedu.address.model.modelmanager.quiz.QuizCard;
import seedu.address.model.modelmanager.quiz.QuizModel;

/**
 * TODO: implement the actual start command
 */
public class QuizStartCommand implements Command {
    public static final String COMMAND_WORD = "start";
    public static final String MESSAGE_USAGE = COMMAND_WORD
        + ": Starts a new quiz.\n";
    public static final String MESSAGE_SUCCESS = "Starting new quiz";
    public static final String MESSAGE_QUESTION_ANSWER = "Question: %1$s\nAnswer: %2$s";

    //    public QuizStartCommand() {
    //        // TODO start session
    //    }

    /**
     * Executes the command.
     * TODO change this ugly method if possible.
     */
    public CommandResult executeActual(QuizModel model, CommandHistory history) {
        // hardcoded values until session is ready
        // only have question and answer
        QuizCard card1 = new QuizCard("Japan", "Tokyo");
        QuizCard card2 = new QuizCard("Hungary", "Budapest");
        QuizCard card3 = new QuizCard("Christmas Island", "The Settlement");
        QuizCard card4 = new QuizCard("中国", "北京");
        Quiz quiz = new Quiz(Arrays.asList(card1, card2, card3, card4), Quiz.Mode.LEARN);

        model.init(quiz);
        QuizCard card = model.getNextCard();

        return new CommandResult(String.format(MESSAGE_QUESTION_ANSWER, card.getQuestion(), card.getAnswer()),
            true, false, false);
    }

    @Override
    /**
     * Executes the command and returns the result message.
     *
     * @param model {@code Model} which the command should operate on.
     * @param history {@code CommandHistory} which the command should operate on.
     * @return feedback message of the operation result for display
     * @throws CommandException If an error occurs during command execution.
     */
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        return new CommandResult(String.format(MESSAGE_SUCCESS));
    }
}
