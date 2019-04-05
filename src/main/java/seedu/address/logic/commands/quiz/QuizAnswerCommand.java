package seedu.address.logic.commands.quiz;

import static java.util.Objects.requireNonNull;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.modelmanager.Model;
import seedu.address.model.modelmanager.QuizModel;
import seedu.address.model.quiz.QuizCard;
import seedu.address.model.quiz.QuizMode;
import seedu.address.model.quiz.QuizUiDisplayFormatter;

/**
 * Processes user answer
 */
public class QuizAnswerCommand extends QuizCommand {
    public static final String COMMAND_WORD = "answer";
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": * any character except word that starts with \\\n";
    public static final String MESSAGE_CORRECT = "Your answer is correct.\n";
    public static final String MESSAGE_WRONG_ONCE = "Your answer %1$s is wrong, "
            + "you have one more chance to answer it.\n";
    public static final String MESSAGE_WRONG = "Your answer is %1$s but the correct answer is %2$s.\n";
    public static final String MESSAGE_SUCCESS = "You have completed all the questions in this quiz.\n";

    private String answer;
    private QuizModel quizModel;
    private QuizCard card;
    private boolean currentCardIsWrong;

    public QuizAnswerCommand(String answer) {
        requireNonNull(answer);
        this.answer = answer;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);
        this.quizModel = requireQuizModel(model);
        this.card = quizModel.getCurrentQuizCard();
        this.currentCardIsWrong = false;

        StringBuilder sb = new StringBuilder();

        if (card.getQuizMode() != QuizMode.PREVIEW) {
            sb.append(handleCurrentCardAnswer());
        }

        if (!currentCardIsWrong) {
            String result = handleIfCardLeft();
            sb.append(result);
        }

        return new CommandResult(sb.toString(), true, false, false);
    }

    /**
     * Handles next available card or end the quiz.
     */
    private String handleIfCardLeft () {
        return quizModel.hasCardLeft() ? handleNextCard() : endQuiz();
    }

    /**
     * Returns message dependent on wrong or correct answer
     */
    private String handleCurrentCardAnswer() {
        if (!quizModel.updateTotalAttemptsAndStreak(card.getIndex(), answer)) {
            return handleWrongAnswer();
        }

        return MESSAGE_CORRECT;
    }

    /**
     * Returns prompt message with user answer, if user got it wrong twice
     * returns prompt message with the correct answer.
     */
    private String handleWrongAnswer() {
        this.currentCardIsWrong = true;

        if (!card.isWrongTwice()) {
            return String.format(MESSAGE_WRONG_ONCE, answer);
        }

        quizModel.setDisplayFormatter(new QuizUiDisplayFormatter(quizModel.getQuestionHeader(),
            card.getQuestion(), quizModel.getAnswerHeader(), card.getAnswer(), QuizMode.PREVIEW, true));
        return String.format(MESSAGE_WRONG, answer, card.getAnswer());
    }

    /**
     * Gets next card and process data for UI display.
     */
    private String handleNextCard() {
        QuizCard card = quizModel.getNextCard();

        quizModel.setDisplayFormatter(new QuizUiDisplayFormatter(quizModel.getQuestionHeader(),
            card.getQuestion(), quizModel.getAnswerHeader(), card.getAnswer(), card.getQuizMode()));

        return "";
    }

    /**
     * Updates user data when quiz end.
     */
    private String endQuiz() {
        if (card.getQuizMode() == QuizMode.PREVIEW) {
            // don't need to update card of 0 attempts
            quizModel.end();
        } else {
            quizModel.updateUserProfile(quizModel.end());
        }

        // set the display to blank for management mode display
        quizModel.setDisplayFormatter(null);
        return MESSAGE_SUCCESS;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
            || (other instanceof QuizAnswerCommand // instanceof handles nulls
            && answer.equals(((QuizAnswerCommand) other).answer)); // state check
    }
}
