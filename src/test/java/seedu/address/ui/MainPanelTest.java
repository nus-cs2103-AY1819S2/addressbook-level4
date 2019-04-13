package seedu.address.ui;

import static org.junit.Assert.assertEquals;

import java.util.Arrays;

import org.junit.Before;
import org.junit.Test;

import guitests.guihandles.MainPanelHandle;
import seedu.address.model.quiz.QuizCard;
import seedu.address.model.quiz.QuizMode;

public class MainPanelTest extends GuiUnitTest {

    private MainPanel mainPanel;
    private MainPanelHandle mainPanelHandle;

    @Before
    public void setUp() {
        mainPanel = new MainPanel();
        uiPartRule.setUiPart(mainPanel);

        mainPanelHandle = new MainPanelHandle(getChildNode(mainPanel.getRoot(),
            MainPanelHandle.MAIN_PANEL_ID));
    }

    @Test
    public void display() {
        // default result text
        guiRobot.pauseForHuman();
        assertEquals("", mainPanelHandle.getText());

        // both question and answer
        QuizCard quizCard = new QuizCard("some question", "some answer", Arrays.asList("Q", "A"),
            "Question", "Answer");
        String totalCorrectAndTotalAttempts = "0 out of 4";
        String currentLessonName = "default";

        guiRobot.interact(() -> mainPanel.setFeedbackToUser(
            quizCard.generateOrderedQuizCardWithIndex(0, QuizMode.PREVIEW),
                totalCorrectAndTotalAttempts, currentLessonName));
        guiRobot.pauseForHuman();
        assertEquals("Question: some question\nAnswer: some answer\n\nPress Enter to go to the next question"
                + "\n\nCurrent lesson name: default"
                + "\nCurrent total correct questions: " + totalCorrectAndTotalAttempts, mainPanelHandle.getText());

        // only question
        guiRobot.interact(() -> mainPanel.setFeedbackToUser(
            quizCard.generateOrderedQuizCardWithIndex(0, QuizMode.REVIEW),
                totalCorrectAndTotalAttempts, currentLessonName));
        guiRobot.pauseForHuman();
        assertEquals("Question: some question\n\nType the Answer for the Question above and press Enter"
                + "\n\nCurrent lesson name: default"
                + "\nCurrent total correct questions: " + totalCorrectAndTotalAttempts, mainPanelHandle.getText());

        // wrong twice in a row
        QuizCard wrongTwiceCard = quizCard.generateOrderedQuizCardWithIndex(0, QuizMode.REVIEW);
        wrongTwiceCard.isCorrect("wrong");
        wrongTwiceCard.isCorrect("wrong");

        guiRobot.interact(() -> mainPanel.setFeedbackToUser(wrongTwiceCard,
                totalCorrectAndTotalAttempts, currentLessonName));
        guiRobot.pauseForHuman();
        assertEquals("Question: some question\nAnswer: some answer\n"
            + "\nType the Answer for the Question above and press Enter"
            + "\n\nCurrent lesson name: default"
                + "\nCurrent total correct questions: " + totalCorrectAndTotalAttempts, mainPanelHandle.getText());

        // switch back to management mode, so become blank
        guiRobot.interact(() -> mainPanel.setFeedbackToUser(null,
                totalCorrectAndTotalAttempts, currentLessonName));
        guiRobot.pauseForHuman();
        assertEquals("", mainPanelHandle.getText());
    }
}
