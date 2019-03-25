package seedu.address.ui;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import guitests.guihandles.MainPanelHandle;
import seedu.address.model.quiz.QuizMode;
import seedu.address.model.quiz.QuizUiDisplayFormatter;

public class MainPanelTest extends GuiUnitTest {

    private MainPanel mainPanel;
    private MainPanelHandle mainPanelHandle;
    private QuizUiDisplayFormatter formatter;
    private QuizUiDisplayFormatter formatterReview;

    @Before
    public void setUp() {
        mainPanel = new MainPanel();
        uiPartRule.setUiPart(mainPanel);

        mainPanelHandle = new MainPanelHandle(getChildNode(mainPanel.getRoot(),
            MainPanelHandle.RESULT_DISPLAY_ID));

        formatter = new QuizUiDisplayFormatter("Question", "some question", "Answer", "some answer", QuizMode.PREVIEW);
        formatterReview = new QuizUiDisplayFormatter("Question", "some question", "Answer", QuizMode.REVIEW);
    }

    @Test
    public void display() {
        // default result text
        guiRobot.pauseForHuman();
        assertEquals("", mainPanelHandle.getText());

        // both question and answer
        guiRobot.interact(() -> mainPanel.setFeedbackToUser(formatter));
        guiRobot.pauseForHuman();
        assertEquals("Question: some question\nAnswer: some answer\nPress Enter to go to the next question",
            mainPanelHandle.getText());

        // only question
        guiRobot.interact(() -> mainPanel.setFeedbackToUser(formatterReview));
        guiRobot.pauseForHuman();
        assertEquals("Question: some question\nType the Answer for the Question above and press Enter",
            mainPanelHandle.getText());

        // switch back to management mode, so become blank
        guiRobot.interact(() -> mainPanel.setFeedbackToUser(null));
        guiRobot.pauseForHuman();
        assertEquals("", mainPanelHandle.getText());
    }
}
