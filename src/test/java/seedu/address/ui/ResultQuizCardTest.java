package seedu.address.ui;

import static org.junit.Assert.assertEquals;
import static seedu.address.testutil.TypicalSession.SESSION_DEFAULT;

import java.util.List;

import org.junit.Test;

import guitests.guihandles.ResultQuizCardHandle;
import seedu.address.model.quiz.QuizCard;

public class ResultQuizCardTest extends GuiUnitTest {

    @Test
    public void display() {
        List<QuizCard> quizCardList = SESSION_DEFAULT.generateSession();
        QuizCard quizCard = quizCardList.get(0);

        ResultQuizCard resultQuizCard = new ResultQuizCard(quizCard, 1);
        uiPartRule.setUiPart(resultQuizCard);

        guiRobot.pauseForHuman();

        ResultQuizCardHandle resultQuizCardHandle = new ResultQuizCardHandle(resultQuizCard.getRoot());

        // verify id is displayed correctly
        assertEquals("1. ", resultQuizCardHandle.getId());

        // verify card details are displayed correctly
        assertEquals(String.format("%s / %s", quizCard.getQuestion(), quizCard.getAnswer()),
            resultQuizCardHandle.getCores());

        assertEquals("Streak: 0", resultQuizCardHandle.getStreak());
        assertEquals("Total attempts: 0", resultQuizCardHandle.getTotalAttempts());
        assertEquals("Accuracy: 0%", resultQuizCardHandle.getAccuracy());
    }
}
