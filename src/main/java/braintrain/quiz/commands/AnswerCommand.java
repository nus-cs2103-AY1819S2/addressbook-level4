package braintrain.quiz.commands;

import static java.util.Objects.requireNonNull;

import braintrain.logic.CommandHistory;
import braintrain.logic.commands.CommandResult;
import braintrain.logic.commands.exceptions.CommandException;
import braintrain.quiz.Quiz;
import braintrain.quiz.QuizCard;
import braintrain.quiz.QuizModel;
import braintrain.quiz.exceptions.NotInitialisedException;

/**
 * Execute User answer
 */
public class AnswerCommand extends QuizCommand {
    public static final String MESSAGE_QUESTION = "Question: %1$s";
    public static final String MESSAGE_QUESTION_ANSWER = "Question: %1$s\nAnswer: %2$s";
    public static final String MESSAGE_CORRECT = "Your answer is correct.\n";
    public static final String MESSAGE_WRONG = "The correct answer is %1$s.\n";
    public static final String MESSAGE_COMPLETE = "You have completed all the questions in this quiz.\n";

    private String answer;

    public AnswerCommand(String answer) {
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

        try {
            model.updateTotalAttemptsAndStreak(card.getIndex(), answer);
        } catch (NotInitialisedException e) {
            e.printStackTrace();
        }

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
            || (other instanceof AnswerCommand // instanceof handles nulls
            && answer.equals(((AnswerCommand) other).answer)); // state check
    }
}
