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
        lessonListView.setCellFactory(listView -> new LessonListViewCell());
    }

    /**
     * Updates the lesson list in GUI with a single lesson.
     *
     * @param lesson the list of lessons from {@code ManagementModel}
     */
    public void updateLessonList(Lesson lesson) {
        lessonListView.setItems(FXCollections.observableList(Collections.singletonList(lesson)));
        lessonListView.setCellFactory(listView -> new LessonOverviewViewCell());
    }

    /**
     * @return the size of the ListView
     */
    public int getViewItemCount() {
        return lessonListView.getItems().size();
    }

    /**
     * Custom {@code ListCell} that displays the graphics of a {@code Lesson} using a {@code LessonCard}..
     * @return the size of the ListView
     */
    class LessonListViewCell extends ListCell<Lesson> {
        @Override
        protected void updateItem(Lesson lesson, boolean empty) {
            super.updateItem(lesson, empty);

            if (empty || lesson == null) {
                setGraphic(null);
                setText(null);
            } else {
                setGraphic(new LessonCard(lesson, getIndex() + 1).getRoot());
            }
        }
    }

    /**
     * Custom {@code ListCell} that displays the graphics of a {@code Lesson}
     * using a {@code LessonOverview}..
     *
     * @return the size of the ListView
     */
    class LessonOverviewViewCell extends ListCell<Lesson> {
        @Override
        protected void updateItem(Lesson lesson, boolean empty) {
            super.updateItem(lesson, empty);

            if (empty || lesson == null) {
                setGraphic(null);
                setText(null);
            } else {
                setGraphic(new LessonOverview(lesson).getRoot());
            }
        }
    }
}
