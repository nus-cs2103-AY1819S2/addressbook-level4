package seedu.address.ui;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.Test;

import seedu.address.model.lesson.Lesson;
import seedu.address.testutil.TypicalLessonList;

public class FlashcardPanelTest extends GuiUnitTest {

    @Test
    public void updateCardListTest() {
        Lesson lesson = TypicalLessonList.LESSON_TRUE_FALSE;
        FlashcardPanel cardListPanel = new FlashcardPanel();

        cardListPanel.updateCardList(lesson);
        assertEquals(cardListPanel.getViewItemCount(), lesson.getCardCount());
    }

    @Test
    public void hideCardListTest() {
        Lesson lesson = TypicalLessonList.LESSON_TRUE_FALSE;
        FlashcardPanel cardListPanel = new FlashcardPanel();

        cardListPanel.updateCardList(lesson);
        assertEquals(cardListPanel.getViewItemCount(), lesson.getCardCount());

        // after hiding -> no more view items
        cardListPanel.hideCardList();
        assertEquals(cardListPanel.getViewItemCount(), 0);
    }
}
