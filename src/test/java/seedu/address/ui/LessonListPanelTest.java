package seedu.address.ui;

import static java.time.Duration.ofMillis;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTimeoutPreemptively;

import java.util.List;

import org.junit.Test;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.model.lesson.Lesson;
import seedu.address.testutil.LessonBuilder;
import seedu.address.testutil.TypicalLessonList;

public class LessonListPanelTest extends GuiUnitTest {
    private static final long CARD_CREATION_AND_DELETION_TIMEOUT = 2500;

    private LessonListPanel lessonListPanel;

    @Test
    public void updateLessonListTest() {
        List<Lesson> lessons = TypicalLessonList.getTypicalLessonList();
        lessonListPanel = new LessonListPanel(lessons);
        int size = lessonListPanel.getViewItemCount();

        // add new lesson
        lessons.add(new LessonBuilder().build());
        // update lesson list with new lesson
        lessonListPanel.updateLessonList(lessons);
        // new lesson listed with other lessons
        assertEquals(lessonListPanel.getViewItemCount(), size + 1);
    }

    @Test
    public void updateSingleLessonListTest() {
        List<Lesson> lessons = TypicalLessonList.getTypicalLessonList();
        lessonListPanel = new LessonListPanel(lessons);
        // open a lesson
        lessonListPanel.updateLessonList(lessons.get(0));
        // only one lesson listed
        assertEquals(lessonListPanel.getViewItemCount(), 1);
    }

    /**
     * Verifies that creating and deleting large number of lessons in {@code LessonListPanel} requires lesser than
     * {@code CARD_CREATION_AND_DELETION_TIMEOUT} milliseconds to execute.
     */
    @Test
    public void performanceTest() {
        ObservableList<Lesson> backingList = createBackingList(10000);

        assertTimeoutPreemptively(ofMillis(CARD_CREATION_AND_DELETION_TIMEOUT), () -> {
            initUi(backingList);
            guiRobot.interact(backingList::clear);
        }, "Creation and deletion of lesson cards exceeded time limit");
    }

    /**
     * Returns a list of lessons containing {@code lessonCount} lessons that is used to populate the
     * {@code LessonListPanel}.
     */
    private ObservableList<Lesson> createBackingList(int lessonCount) {
        ObservableList<Lesson> backingList = FXCollections.observableArrayList();
        Lesson lesson = TypicalLessonList.LESSON_DEFAULT;
        for (int i = 0; i < lessonCount; i++) {
            lesson.setName(i + " lesson");
            backingList.add(lesson);
        }

        return backingList;
    }

    /**
     * Initializes {@code lessonListPanelHandle} with a {@code LessonListPanel} backed by {@code backingList}.
     * Also shows the {@code Stage} that displays only {@code LessonListPanel}.
     */
    private void initUi(ObservableList<Lesson> backingList) {
        LessonListPanel lessonListPanel =
                new LessonListPanel(backingList);
        uiPartRule.setUiPart(lessonListPanel);
    }
}
