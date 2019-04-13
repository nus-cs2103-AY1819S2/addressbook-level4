package guitests.guihandles;

import javafx.scene.control.ListView;
import seedu.address.model.lesson.Lesson;

/**
 * Provides a handle for {@code LessonListPanel} containing the list of {@code LessonCard}.
 */
public class LessonListPanelHandle extends NodeHandle<ListView<Lesson>> {
    public static final String LESSON_LIST_VIEW_ID = "#lessonListView";

    public LessonListPanelHandle(ListView<Lesson> lessonListPanelNode) {
        super(lessonListPanelNode);
    }
}
