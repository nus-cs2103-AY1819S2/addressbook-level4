package seedu.address.ui;

import java.util.Collections;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.Region;
import seedu.address.model.lesson.Lesson;

/**
 * Panel containing the list of lessons.
 */
public class LessonListPanel extends UiPart<Region> {
    private static final String FXML = "LessonListPanel.fxml";
    //private final Logger logger = LogsCenter.getLogger(LessonListPanel.class);

    @FXML
    private ListView<Lesson> lessonListView;

    public LessonListPanel(List<Lesson> lessons) {
        super(FXML);
        lessonListView.setItems(FXCollections.observableList(lessons));
        lessonListView.setCellFactory(listView -> new LessonListViewCell());
    }

    /**
     * Updates the lesson list in GUI.
     *
     * @param lessons the list of lessons from {@code ManagementModel}
     */
    public void updateLessonList(List<Lesson> lessons) {
        lessonListView.setItems(FXCollections.observableList(lessons));
        lessonListView.setCellFactory(listView -> new ListCell<>());
    }

    /**
     * Updates the lesson list in GUI with a single lesson.
     *
     * @param lesson the list of lessons from {@code ManagementModel}
     */
    public void updateLessonList(Lesson lesson) {
        lessonListView.setItems(FXCollections.observableList(Collections.singletonList(lesson)));
        lessonListView.setCellFactory(listView -> new LessonListViewCell());
    }

    /**
     * @return the size of the ListView
     */
    public int getViewItemCount() {
        return lessonListView.getItems().size();
    }

    /**
     * Custom {@code ListCell} that displays the graphics of a {@code Lesson} using a {@code LessonCard}..
     */
    class LessonListViewCell extends ListCell<Lesson> {
    }
}
