package seedu.address.logic.commands.quiz;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.commands.exceptions.CommandException.MESSAGE_EXPECTED_QUIZ_MODEL;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.modelmanager.Model;
import seedu.address.model.modelmanager.quiz.Quiz;
import seedu.address.model.modelmanager.quiz.QuizCard;
import seedu.address.model.modelmanager.quiz.QuizModel;

/**
 * Execute User answer
 */
public class QuizAnswerCommand implements Command {
    public static final String COMMAND_WORD = "answer";
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": * any character except word that starts with \\\n";
    public static final String MESSAGE_QUESTION = "Question: %1$s\n";
    public static final String MESSAGE_QUESTION_ANSWER = "Question: %1$s\nAnswer: %2$s";
    public static final String MESSAGE_CORRECT = "Your answer is correct.\n";
    public static final String MESSAGE_WRONG_ONCE = "Your answer is wrong, you have one more chance to answer it\n";
    public static final String MESSAGE_WRONG = "The correct answer is %1$s.\n";
    public static final String MESSAGE_COMPLETE = "You have completed all the questions in this quiz.\n";

    private String answer;

    public QuizAnswerCommand(String answer) {
        requireNonNull(answer);
        this.answer = answer;
    }

    @Override
    /**
     * Executes the command and returns the result message.
     *
     * @param model {@link QuizModel} which the command should operate on.
     * @param history {@code CommandHistory} which the command should operate on.
     * @return feedback message of the operation result for display
     * @throws CommandException If the {@link Model} passed in is not a {@link QuizModel}
     */
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        // CommandException will be thrown if and only if LogicManager passes in the incorrect Model
        // In other words, only incorrect code will result in a CommandException being thrown
        if (!(model instanceof QuizModel)) {
            throw new CommandException(MESSAGE_EXPECTED_QUIZ_MODEL);
        }

        QuizModel quizModel = (QuizModel) model;

        QuizCard card = quizModel.getCurrentQuizCard();

        StringBuilder sb = new StringBuilder();

        if (card.getQuizMode() == Quiz.Mode.PREVIEW) {
            // don't need to update totalAttempts and streak
            if (quizModel.hasCardLeft()) {
                card = quizModel.getNextCard();
                if (card.getQuizMode() == Quiz.Mode.PREVIEW) {
                    return new CommandResult(String.format(MESSAGE_QUESTION_ANSWER,
                        card.getQuestion(), card.getAnswer()), true, false, false);
                }
                return new CommandResult(String.format(MESSAGE_QUESTION, card.getQuestion()), true, false, false);
            } else {
                sb.append(MESSAGE_COMPLETE);

                // TODO return this to session
                System.out.println(quizModel.end());
            }

            return new CommandResult(sb.toString(), true, false, false);
        }

        boolean result = quizModel.updateTotalAttemptsAndStreak(card.getIndex(), answer);

        if (result) {
            sb.append(MESSAGE_CORRECT);

            if (quizModel.hasCardLeft()) {
                card = quizModel.getNextCard();
                sb.append(String.format(MESSAGE_QUESTION, card.getQuestion()));
            } else {
                sb.append(MESSAGE_COMPLETE);

                // TODO return this to session
                System.out.println(quizModel.end());
            }

        } else {
            if (!card.isWrongTwice()) {
                sb.append(MESSAGE_WRONG_ONCE);
                sb.append(String.format(MESSAGE_QUESTION, card.getQuestion()));
            } else {
                sb.append(String.format(MESSAGE_WRONG, card.getAnswer()));
                sb.append(String.format(MESSAGE_QUESTION, card.getQuestion()));
            }
        }

        return new CommandResult(sb.toString(), true, false, false);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
            || (other instanceof QuizAnswerCommand // instanceof handles nulls
            && answer.equals(((QuizAnswerCommand) other).answer)); // state check
    }
}
