package seedu.address.ui;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import guitests.guihandles.QuizResultDisplayHandle;
import seedu.address.model.modelmanager.quiz.Quiz;
import seedu.address.model.modelmanager.quiz.QuizUiDisplayFormatter;

public class QuizResultDisplayTest extends GuiUnitTest {

    private QuizResultDisplay quizResultDisplay;
    private QuizResultDisplayHandle quizResultDisplayHandle;
    private QuizUiDisplayFormatter formatter;
    private QuizUiDisplayFormatter formatterReview;

    @Before
    public void setUp() {
        quizResultDisplay = new QuizResultDisplay();
        uiPartRule.setUiPart(quizResultDisplay);

        quizResultDisplayHandle = new QuizResultDisplayHandle(getChildNode(quizResultDisplay.getRoot(),
            QuizResultDisplayHandle.RESULT_DISPLAY_ID));

        formatter = new QuizUiDisplayFormatter("Question", "some question", "Answer", "some answer", Quiz.Mode.PREVIEW);
        formatterReview = new QuizUiDisplayFormatter("Question", "some question", "Answer", Quiz.Mode.REVIEW);
    }

    @Test
    public void display() {
        // default result text
        guiRobot.pauseForHuman();
        assertEquals("", quizResultDisplayHandle.getText());

        // both question and answer
        guiRobot.interact(() -> quizResultDisplay.setFeedbackToUser(formatter));
        guiRobot.pauseForHuman();
        assertEquals("Question: some question\nAnswer: some answer\nPress Enter to go to the next question",
            quizResultDisplayHandle.getText());

        // only question
        guiRobot.interact(() -> quizResultDisplay.setFeedbackToUser(formatterReview));
        guiRobot.pauseForHuman();
        assertEquals("Question: some question\nType the Answer for the Question above and press Enter",
            quizResultDisplayHandle.getText());

        // switch back to management mode, so become blank
        guiRobot.interact(() -> quizResultDisplay.setFeedbackToUser(null));
        guiRobot.pauseForHuman();
        assertEquals("", quizResultDisplayHandle.getText());
    }
}
