package guitests.guihandles;

import java.util.Set;

import javafx.scene.Node;
import javafx.scene.control.ListView;
import seedu.address.model.lesson.Lesson;

/**
 * Provides a handle for {@code LessonListPanel} containing the list of {@code LessonCard}.
 */
public class LessonListPanelHandle extends NodeHandle<ListView<Lesson>> {
    public static final String PERSON_LIST_VIEW_ID = "#lessonListView";

    private static final String CARD_PANE_ID = "#cardPane";

    public LessonListPanelHandle(ListView<Lesson> lessonListPanelNode) {
        super(lessonListPanelNode);
    }

    /**
     * Returns all card nodes in the scene graph.
     * Card nodes that are visible in the listview are definitely in the scene graph, while some nodes that are not
     * visible in the listview may also be in the scene graph.
     */
    private Set<Node> getAllCardNodes() {
        return guiRobot.lookup(CARD_PANE_ID).queryAll();
    }

    /**
     * Returns the size of the list.
     */
    public int getListSize() {
        return getRootNode().getItems().size();
    }
}
