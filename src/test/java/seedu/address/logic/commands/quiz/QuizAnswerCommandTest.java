package seedu.address.logic.commands.quiz;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.quiz.QuizAnswerCommand.MESSAGE_CORRECT;
import static seedu.address.logic.commands.quiz.QuizAnswerCommand.MESSAGE_SUCCESS;
import static seedu.address.testutil.TypicalQuiz.FIRST_REVIEW_CARD;
import static seedu.address.testutil.TypicalQuiz.LAST_REVIEW_CARD;
import static seedu.address.testutil.TypicalSession.SESSION_REVIEW_2;

import java.util.List;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.modelmanager.ManagementModelManager;
import seedu.address.model.modelmanager.Model;
import seedu.address.model.modelmanager.QuizModelStub;
import seedu.address.model.quiz.Quiz;
import seedu.address.model.quiz.QuizCard;
import seedu.address.testutil.Assert;

public class QuizAnswerCommandTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private CommandHistory commandHistory = new CommandHistory();

    @Test
    public void constructor_nullAnswer_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        new QuizAnswerCommand(null);
    }

    @Test
    public void execute_wrongModel_throwsCommandException() {
        Model model = new ManagementModelManager();
        Assert.assertThrows(CommandException.class, () ->
            new QuizAnswerCommand("someanswer").execute(model, commandHistory));
    }

    @Test
    public void execute_correctAnswer_success() throws Exception {
        final String answer = FIRST_REVIEW_CARD.getAnswer();
        QuizModelStubAcceptingAnswer quizModelStubAcceptingAnswer = new QuizModelStubAcceptingAnswer();

        CommandResult commandResult = new QuizAnswerCommand(answer)
            .execute(quizModelStubAcceptingAnswer, commandHistory);

        assertEquals(MESSAGE_CORRECT, commandResult.getFeedbackToUser());
        assertEquals(1, quizModelStubAcceptingAnswer.getQuizTotalCorrectQuestions());
    }

    @Test
    public void execute_wrongAnswer_success() throws Exception {
        final String wrongAns = "wronganswer";
        QuizModelStubAcceptingAnswer quizModelStubAcceptingAnswer = new QuizModelStubAcceptingAnswer();

        // wrong
        CommandResult commandResult = new QuizAnswerCommand(wrongAns)
            .execute(quizModelStubAcceptingAnswer, commandHistory);

        String expectedMessage = String.format(QuizAnswerCommand.MESSAGE_WRONG_ONCE, wrongAns);

        assertEquals(expectedMessage, commandResult.getFeedbackToUser());
        assertEquals(0, quizModelStubAcceptingAnswer.getQuizTotalCorrectQuestions());
        assertEquals(1, quizModelStubAcceptingAnswer.getQuizTotalAttempts());

        // wrong twice
        commandResult = new QuizAnswerCommand(wrongAns).execute(quizModelStubAcceptingAnswer, commandHistory);

        expectedMessage = String.format(QuizAnswerCommand.MESSAGE_WRONG, wrongAns, FIRST_REVIEW_CARD.getAnswer());
        assertEquals(expectedMessage, commandResult.getFeedbackToUser());
        assertEquals(0, quizModelStubAcceptingAnswer.getQuizTotalCorrectQuestions());
        assertEquals(2, quizModelStubAcceptingAnswer.getQuizTotalAttempts());
    }

    @Test
    public void execute_completeQuiz_success() throws Exception {
        final String answer = LAST_REVIEW_CARD.getAnswer();
        QuizModelStubResultDisplay quizModelStubResultDisplay = new QuizModelStubResultDisplay();

        CommandResult commandResult = new QuizAnswerCommand(answer)
            .execute(quizModelStubResultDisplay, commandHistory);

        assertEquals(MESSAGE_CORRECT + MESSAGE_SUCCESS, commandResult.getFeedbackToUser());
        assertTrue(quizModelStubResultDisplay.isResultDisplay());
    }

    private class QuizModelStubAcceptingAnswer extends QuizModelStub {
        private final Quiz quiz;
        private List<QuizCard> quizCardList;


        public QuizModelStubAcceptingAnswer() {
            quiz = new Quiz(SESSION_REVIEW_2.generateSession(), SESSION_REVIEW_2.getMode());
            quizCardList = quiz.getGeneratedQuizCardList();
            quiz.getNextCard();
        }

        @Override
        public boolean hasCardLeft() {
            return true;
        }

        @Override
        public QuizCard getNextCard() {
            return quiz.getNextCard();
        }

        @Override
        public QuizCard getCurrentQuizCard() {
            return quizCardList.get(0);
        }

        @Override
        public boolean isResultDisplay() {
            return false;
        }

        @Override
        public boolean updateTotalAttemptsAndStreak(int index, String answer) {
            return quiz.updateTotalAttemptsAndStreak(index, answer);
        }

        @Override
        public int getQuizTotalCorrectQuestions() {
            return quiz.getQuizTotalCorrectQuestions();
        }

        @Override
        public int getQuizTotalAttempts() {
            return quiz.getQuizTotalAttempts();
        }
    }

    private class QuizModelStubResultDisplay extends QuizModelStub {
        private final Quiz quiz;
        private List<QuizCard> quizCardList;
        private boolean isResultDisplay;

        public QuizModelStubResultDisplay() {
            quiz = new Quiz(SESSION_REVIEW_2.generateSession(), SESSION_REVIEW_2.getMode());
            quizCardList = quiz.getGeneratedQuizCardList();
            quiz.getNextCard();
            quiz.getNextCard();
            quiz.getNextCard();
            quiz.getNextCard();
        }

        @Override
        public boolean hasCardLeft() {
            return false;
        }

        @Override
        public QuizCard getCurrentQuizCard() {
            return quizCardList.get(3);
        }

        @Override
        public boolean updateTotalAttemptsAndStreak(int index, String answer) {
            return quiz.updateTotalAttemptsAndStreak(1, LAST_REVIEW_CARD.getAnswer());
        }

        @Override
        public boolean isResultDisplay() {
            return isResultDisplay;
        }

        @Override
        public void setResultDisplay(boolean resultDisplay) {
            isResultDisplay = resultDisplay;
        }
    }
}
