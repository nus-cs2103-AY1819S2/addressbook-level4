package seedu.address.ui;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import guitests.guihandles.QuizResultDisplayHandle;

public class QuizResultDisplayTest extends GuiUnitTest {

    private QuizResultDisplay quizResultDisplay;
    private QuizResultDisplayHandle quizResultDisplayHandle;

    @Before
    public void setUp() {
        quizResultDisplay = new QuizResultDisplay();
        uiPartRule.setUiPart(quizResultDisplay);

        quizResultDisplayHandle = new QuizResultDisplayHandle(getChildNode(quizResultDisplay.getRoot(),
            QuizResultDisplayHandle.RESULT_DISPLAY_ID));
    }

    @Test
    public void display() {
        // default result text
        guiRobot.pauseForHuman();
        assertEquals("", quizResultDisplayHandle.getText());

        // new result received
        guiRobot.interact(() -> quizResultDisplay.setFeedbackToUser("Dummy feedback to user"));
        guiRobot.pauseForHuman();
        assertEquals("Dummy feedback to user", quizResultDisplayHandle.getText());

        // only question
        guiRobot.interact(() -> quizResultDisplay.setFeedbackToUser("Question: Japan\n"));
        guiRobot.pauseForHuman();
        assertEquals("Question: Japan\nType the answer for the question above and press Enter:",
            quizResultDisplayHandle.getText());
    }
}
