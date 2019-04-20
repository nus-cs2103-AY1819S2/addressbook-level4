package seedu.address.ui;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.testutil.TypicalSession.SESSION_DEFAULT;

import java.util.List;

import org.junit.Test;

import guitests.guihandles.QuizResultPanelHandle;
import seedu.address.model.quiz.QuizCard;

public class QuizResultPanelTest extends GuiUnitTest {

    @Test
    public void setFeedbackToUser() {
        List<QuizCard> quizCardList = SESSION_DEFAULT.generateSession();
        QuizResultPanel quizResultPanel = new QuizResultPanel();
        quizResultPanel.setFeedbackToUser(quizCardList);

        guiRobot.pauseForHuman();

        QuizResultPanelHandle resultQuizCardHandle = new QuizResultPanelHandle(quizResultPanel.getRoot());

        assertEquals(resultQuizCardHandle.getViewItemCount(), quizCardList.size());
    }
}
