package seedu.address.logic.commands.quiz;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.commands.exceptions.CommandException.MESSAGE_EXPECTED_QUIZ_MODEL;

import java.util.List;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.modelmanager.Model;
import seedu.address.model.modelmanager.QuizModel;
import seedu.address.model.modelmanager.management.ManagementModel;
import seedu.address.model.quiz.QuizCard;
import seedu.address.model.quiz.QuizMode;
import seedu.address.model.quiz.QuizUiDisplayFormatter;
import seedu.address.model.user.CardSrsData;

/**
 * Execute User answer
 */
public class QuizAnswerCommand implements Command {
    public static final String COMMAND_WORD = "answer";
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": * any character except word that starts with \\\n";
    public static final String MESSAGE_CORRECT = "Your answer is correct.\n";
    public static final String MESSAGE_WRONG_ONCE = "Your answer %1$s is wrong, "
        + "you have one more chance to answer it.\n";
    public static final String MESSAGE_WRONG = "Your answer is %1$s but the correct answer is %2$s.\n";
    public static final String MESSAGE_COMPLETE = "You have completed all the questions in this quiz.\n";

    private String answer;
    private List<CardSrsData> updateCardSrsData;
    private boolean complete = false;
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
        String questionHeader = "question";
        String answerHeader = "answer";
        if (card.getQuizMode() == QuizMode.PREVIEW) {
            // don't need to update totalAttempts and streak
            if (quizModel.hasCardLeft()) {
                card = quizModel.getNextCard();
                if (card.getQuizMode() == QuizMode.PREVIEW) {
                    quizModel.setDisplayFormatter(new QuizUiDisplayFormatter(questionHeader, card.getQuestion(),
                            answerHeader, card.getAnswer(), QuizMode.PREVIEW));
                    return new CommandResult("", true, false, false);
                }
                quizModel.setDisplayFormatter(new QuizUiDisplayFormatter(questionHeader, card.getQuestion(),
                        answerHeader, QuizMode.REVIEW));
                return new CommandResult("", true, false, false);
            } else {
                /**
                 * Update the result taken from quiz session and change the storage information in user profile.
                 */
                //TODO: update user data
                /*List<List<Integer>> quizInformation = quizModel.end();
                Instant currentDate = Instant.now();
                SrsCardsManager updateManager = new SrsCardsManager(quizModel.getQuizSrsCards(),
                        quizInformation, currentDate);
                this.updateCardSrsData = updateManager.updateCardData();
                this.complete = true;*/
                // TODO change back to management mode display
                // set the display to blank
                quizModel.setDisplayFormatter(null);
                return new CommandResult(MESSAGE_COMPLETE);
            }
        }

        boolean result = quizModel.updateTotalAttemptsAndStreak(card.getIndex(), answer);

        if (result) {
            sb.append(MESSAGE_CORRECT);

            if (quizModel.hasCardLeft()) {
                card = quizModel.getNextCard();
                quizModel.setDisplayFormatter(new QuizUiDisplayFormatter(questionHeader, card.getQuestion(),
                    answerHeader, QuizMode.REVIEW));
            } else {
                sb.append(MESSAGE_COMPLETE);

                // TODO change back to management mode display
                // set the display to blank
                quizModel.setDisplayFormatter(null);

                /**
                 * Update the result taken from quiz session and change the storage information in user profile.
                 */
                //TODO: update user data
                /*Instant currentDate = Instant.now();
                List<List<Integer>> quizInformation = quizModel.end();
                SrsCardsManager updateManager = new SrsCardsManager(quizModel.getQuizSrsCards(),
                        quizInformation, currentDate);
                updateCardSrsData = updateManager.updateCardData();
                this.complete = true;*/
            }

        } else {
            if (!card.isWrongTwice()) {
                sb.append(String.format(MESSAGE_WRONG_ONCE, answer));
                quizModel.setDisplayFormatter(new QuizUiDisplayFormatter(questionHeader, card.getQuestion(),
                    answerHeader, QuizMode.REVIEW));
            } else {
                sb.append(String.format(MESSAGE_WRONG, answer, card.getAnswer()));
                quizModel.setDisplayFormatter(new QuizUiDisplayFormatter(questionHeader, card.getQuestion(),
                    answerHeader, card.getAnswer(), QuizMode.PREVIEW));
            }
        }

        return new CommandResult(sb.toString(), true, false, false);
    }


    /**
     * Update the cardSrsData of user profile through managementModel.
     */
    public void executeActual (ManagementModel model, CommandHistory history) {
        if (complete) {
            for (int i = 0; i < updateCardSrsData.size(); i++) {
                model.addCardSrsData(updateCardSrsData.get(i));
            }
        }
    }
    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
            || (other instanceof QuizAnswerCommand // instanceof handles nulls
            && answer.equals(((QuizAnswerCommand) other).answer)); // state check
    }
}
