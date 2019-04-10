package systemtests;

import static seedu.address.commons.core.Messages.MESSAGE_LESSON_COMMANDS;
import static seedu.address.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;
import static seedu.address.logic.commands.quiz.QuizAnswerCommand.MESSAGE_CORRECT;
import static seedu.address.logic.commands.quiz.QuizAnswerCommand.MESSAGE_SUCCESS;
import static seedu.address.testutil.TypicalSession.SESSION_REVIEW_2;

import org.junit.Test;

import seedu.address.logic.commands.quiz.QuizStartCommand;
import seedu.address.model.modelmanager.Model;
import seedu.address.model.modelmanager.QuizModel;
import seedu.address.model.quiz.Quiz;

public class QuizAnswerCommandSystemTest extends BrainTrainSystemTest {
    @Test
    public void answer() {
        // starts the quiz
        executeCommand(QuizStartCommand.COMMAND_WORD + " n/sampleData c/2 m/REVIEW\n");

        /* Case: some invalid quiz command in braintrain
         * -> fails, invalid command
         */
        assertCommandFailure("\\invalid", MESSAGE_UNKNOWN_COMMAND);

        /* Case: answer a question correctly in braintrain
         * -> answer 1st question
         */
        String command = "Brussels";
        QuizModel expectedModel = getQuizModel();
        Quiz quiz = new Quiz(SESSION_REVIEW_2.generateSession(), SESSION_REVIEW_2.getMode());
        expectedModel.init(quiz, SESSION_REVIEW_2);
        expectedModel.getNextCard();
        String expectedResultMessage = MESSAGE_CORRECT;
        expectedModel.updateTotalAttemptsAndStreak(0, command);
        expectedModel.getNextCard();

        assertCommandSuccess(command, expectedResultMessage, expectedModel);

        /* Case: answer a question correctly in braintrain
         * -> answer 2nd question
         */
        command = "Tokyo";
        expectedResultMessage = MESSAGE_CORRECT;
        expectedModel.updateTotalAttemptsAndStreak(1, command);
        expectedModel.getNextCard();

        assertCommandSuccess(command, expectedResultMessage, expectedModel);

        /* Case: answer a question correctly in braintrain
         * -> answer 3rd question
         */
        command = "Belgium";
        expectedResultMessage = MESSAGE_CORRECT;
        expectedModel.updateTotalAttemptsAndStreak(0, command);
        expectedModel.getNextCard();

        assertCommandSuccess(command, expectedResultMessage, expectedModel);

        /* Case: answer a question correctly in braintrain
         * -> answer 4th and last question
         */
        command = "Japan";
        expectedResultMessage = MESSAGE_CORRECT + MESSAGE_SUCCESS;
        expectedModel.updateTotalAttemptsAndStreak(1, command);
        expectedModel.setResultDisplay(true);
        assertCommandSuccess(command, expectedResultMessage, expectedModel);

        /* Case: exit quiz mode
         * -> returns to management mode
         */
        command = "any valid answer input works";
        expectedResultMessage = MESSAGE_LESSON_COMMANDS;
        expectedModel.setResultDisplay(false);
        expectedModel.updateUserProfile(expectedModel.end());

        assertCommandSuccess(command, expectedResultMessage, expectedModel);
    }

    /**
     * Executes {@code command} and verifies that the command box displays an empty string, the result display
     * box displays {@code Messages#MESSAGE_PERSONS_LISTED_OVERVIEW} with the number of people in the filtered list,
     * and the model related components equal to {@code expectedModel}.
     * These verifications are done by
     * {@code AddressBookSystemTest#assertApplicationDisplaysExpected(String, String, Model)}.<br>
     * Also verifies that the status bar remains unchanged, and the command box has the default style class, and the
     * selected card updated accordingly, depending on {@code cardStatus}.
     * @see BrainTrainSystemTest#assertApplicationDisplaysExpected(String, String, Model)
     */
    private void assertCommandSuccess(String command, String expectedResultMessage, QuizModel expectedModel) {
        executeCommand(command);
        assertApplicationDisplaysExpected("", expectedResultMessage, expectedModel);
        assertCommandBoxShowsDefaultStyle();
    }

    /**
     * Executes {@code command} and verifies that the command box displays {@code command}, the result display
     * box displays {@code expectedResultMessage} and the model related components equal to the current model.
     * These verifications are done by
     * {@code BrainTrainSystemTest#assertApplicationDisplaysExpected(String, String, Model)}.<br>
     * Also verifies that the browser url, selected card and status bar remain unchanged, and the command box has the
     * error style.
     * @see BrainTrainSystemTest#assertApplicationDisplaysExpected(String, String, Model)
     */
    private void assertCommandFailure(String command, String expectedResultMessage) {
        QuizModel expectedModel = getQuizModel();

        executeCommand(command);
        assertApplicationDisplaysExpected(command, expectedResultMessage, expectedModel);
        assertCommandBoxShowsErrorStyle();
    }
}
