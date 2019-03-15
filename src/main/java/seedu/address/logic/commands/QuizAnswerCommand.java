package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.quiz.Quiz;
import seedu.address.quiz.QuizCard;
import seedu.address.quiz.QuizModel;

/**
 * Execute User answer
 */
public class QuizAnswerCommand extends QuizCommand {
    public static final String COMMAND_WORD = "answer";
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": * any character except word that starts with \\\n";
    public static final String MESSAGE_QUESTION = "Question: %1$s";
    public static final String MESSAGE_QUESTION_ANSWER = "Question: %1$s\nAnswer: %2$s";
    public static final String MESSAGE_CORRECT = "Your answer is correct.\n";
    public static final String MESSAGE_WRONG = "The correct answer is %1$s.\n";
    public static final String MESSAGE_COMPLETE = "You have completed all the questions in this quiz.\n";

    private String answer;

    public QuizAnswerCommand(String answer) {
        requireNonNull(answer);
        this.answer = answer;
    }

    @Override
    public CommandResult execute(QuizModel model, CommandHistory history) throws CommandException {
        QuizCard card = model.getCurrentQuizCard();

        StringBuilder sb = new StringBuilder();

        if (card.getQuizMode() == Quiz.Mode.PREVIEW) {
            // don't need to update totalAttempts and streak
            if (model.hasCardLeft()) {
                card = model.getNextCard();
                if (card.getQuizMode() == Quiz.Mode.PREVIEW) {
                    return new CommandResult(String.format(MESSAGE_QUESTION_ANSWER,
                        card.getQuestion(), card.getAnswer()));
                }
                return new CommandResult(String.format(MESSAGE_QUESTION, card.getQuestion()));
            } else {
                sb.append(MESSAGE_COMPLETE);

                // TODO return this to session
                System.out.println(model.end());
            }

            return new CommandResult(sb.toString());
        }

        model.updateTotalAttemptsAndStreak(card.getIndex(), answer);

        if (card.isCorrect(answer)) {
            sb.append(MESSAGE_CORRECT);

            if (model.hasCardLeft()) {
                card = model.getNextCard();
                sb.append(String.format(MESSAGE_QUESTION, card.getQuestion()));
            } else {
                sb.append(MESSAGE_COMPLETE);

                // TODO return this to session
                System.out.println(model.end());
            }

        } else {
            sb.append(String.format(MESSAGE_WRONG, card.getAnswer()));
            sb.append(String.format(MESSAGE_QUESTION, card.getQuestion()));
        }

        return new CommandResult(sb.toString());
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
            || (other instanceof QuizAnswerCommand // instanceof handles nulls
            && answer.equals(((QuizAnswerCommand) other).answer)); // state check
    }
}
